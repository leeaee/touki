package cn.touki.web.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.touki.web.core.orm.hibernate.HibernateDao;
import cn.touki.web.entity.admin.Admin;

@Repository
public class AdminDao extends HibernateDao<Admin, Long> {
	
	
	/**
	 * Get unique admin by admin_id
	 * 
	 * @param adminId
	 * @return cn.touki.web.entity.admin.Admin
	 */
	public Admin getAdminByName(String name) {
		
		return findUniqueBy("name", name);
	}
	
	/**
	 * Get all admins
	 * 
	 * @return List<cn.touki.web.entity.admin.Admin>
	 */
	public List<Admin> getAllAdmins() {
		
		return getAll();
	}
}
