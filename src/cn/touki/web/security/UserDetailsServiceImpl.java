package cn.touki.web.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import cn.touki.web.entity.csadmin.Admin;
import cn.touki.web.entity.csadmin.Authority;
import cn.touki.web.entity.csadmin.Role;
import cn.touki.web.service.dao.AdminDao;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author liyi
 */
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AdminDao adminDao;

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {

		Admin admin = adminDao.getAdminByName(userName);
		
		if (admin == null)
			throw new UsernameNotFoundException("user not found!");

		GrantedAuthority[] grantedAuths = obtainGrantedAuthorities(admin);
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		if (admin.getState() == Admin.PAUSED || admin.getState() == Admin.OPEN || admin.getState() == Admin.CLOSED) {
			enabled = false;
		}
		
		if (admin.getState() == Admin.EXPIRED) {
			accountNonExpired = false;
		}			

		User userdetail = new User(
				admin.getName(), admin.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantedAuths);
		
		admin.setLastLogin(System.currentTimeMillis());
		adminDao.save(admin);		
		
		return userdetail;
	}

	/**
	 * 获得用户所有角色的权限集合.
	 */
	@Transactional(readOnly = true)
	private GrantedAuthority[] obtainGrantedAuthorities(Admin user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			for (Authority authority : role.getAuthorities()) {
				authSet.add(new GrantedAuthorityImpl(authority.getName()));
			}
		}
		return authSet.toArray(new GrantedAuthority[authSet.size()]);
	}
}
