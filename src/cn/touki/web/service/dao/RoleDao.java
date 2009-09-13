package cn.touki.web.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.touki.web.core.orm.hibernate.HibernateDao;
import cn.touki.web.entity.csadmin.Admin;
import cn.touki.web.entity.csadmin.Role;

/**
 * 角色对象的泛型DAO.
 * 
 * @author liyi
 */
@Repository
public class RoleDao extends HibernateDao<Role, Long> {

	public static final String QUERY_USER_BY_ROLEID = "select a from Admin a left join a.roles r where r.id=?";

	public Role getRoleByName(String roleName) {
		
		return findUniqueBy("name", roleName);
	}	
	
	/**
	 * 重载函数,因为Role中没有建立与User的关联,因此需要以较低效率的方式进行删除User与Role的多对多中间表.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long id) {
		Role role = get(id);
		//查询出拥有该角色的用户,并删除该用户的角色.
		List<Admin> admins = createQuery(QUERY_USER_BY_ROLEID, role.getId()).list();
		for (Admin admin : admins) {
			admin.getRoles().remove(role);
		}
		super.delete(role);
	}
}
