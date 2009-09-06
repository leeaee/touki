package cn.touki.web.service.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.touki.web.core.orm.Page;
import cn.touki.web.core.orm.PropertyFilter;
import cn.touki.web.core.security.SpringSecurityUtils;
import cn.touki.web.entity.admin.Admin;
import cn.touki.web.entity.admin.Authority;
import cn.touki.web.entity.admin.Resource;
import cn.touki.web.entity.admin.Role;
import cn.touki.web.service.dao.AdminDao;
import cn.touki.web.service.dao.AuthorityDao;
import cn.touki.web.service.dao.ResourceDao;
import cn.touki.web.service.dao.RoleDao;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class SecurityManager {

	private static Logger logger = LoggerFactory.getLogger(SecurityManager.class);

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private ResourceDao resourceDao;

	// User Manager //
	@Transactional(readOnly = true)
	public Admin getAdmin(Long id) {
		return adminDao.get(id);
	}

	public void saveUser(Admin admin) {
		adminDao.save(admin);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
		}
		adminDao.delete(id);
	}

	@Transactional(readOnly = true)
	public Page<Admin> searchUser(final Page<Admin> page, final List<PropertyFilter> filters) {
		return adminDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public Admin findUserByLoginName(String adminId) {
		return adminDao.findUniqueBy("adminId", adminId);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String loginName, String oldLoginName) {
		return adminDao.isPropertyUnique("loginName", loginName, oldLoginName);
	}

	// Role Manager //
	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		return roleDao.getAll();
	}

	public void saveRole(Role entity) {
		roleDao.save(entity);
	}

	public void deleteRole(Long id) {
		roleDao.delete(id);
	}

	// Resource Manager //
	@Transactional(readOnly = true)
	public List<Resource> getUrlResourceWithAuthorities() {
		return resourceDao.getUrlResourceWithAuthorities();
	}

	// Authority Manager //
	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}
}
