package cn.touki.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.touki.util.DateUtils;
import cn.touki.web.core.orm.Page;
import cn.touki.web.core.orm.PropertyFilter;
import cn.touki.web.core.orm.hibernate.HibernateWebUtils;
import cn.touki.web.entity.csadmin.Admin;
import cn.touki.web.entity.csadmin.Role;
import cn.touki.web.exception.EntityAlreadyExistException;
import cn.touki.web.exception.EntityCantDeleteException;
import cn.touki.web.exception.EntityCantModifyException;
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.exception.IdEntityException;
import cn.touki.web.service.dao.AdminDao;
import cn.touki.web.service.dao.RoleDao;

@Service("AdminService")
@Transactional
public class AdminService {

	protected Logger logger = LoggerFactory.getLogger(AdminService.class);
	
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private RoleDao roleDao;
	
	@Transactional(readOnly = true)
	public Page<Admin> searchAdmin(final Page<Admin> page, final List<PropertyFilter> filters) {
		return adminDao.findPage(page, filters);
	}	
	
	@Transactional(readOnly = true)
	public Admin getAdmin(Long id) {
		
		Admin admin = adminDao.get(id);
		
		logger.debug("Get admin by id: {}.",  id);
		return admin; 
	}
	
	@Transactional(readOnly = true)
	public Admin getAdmin(String name) throws EntityNotFoundException {
		
		Admin admin = adminDao.getAdminByName(name);
		
		if (admin == null) {
			throw new EntityNotFoundException(Admin.KEY, name);
		}
		
		logger.debug("Get admin by name: {}.",  name);
		return admin; 
	}
	
	public Admin login(String name) throws EntityNotFoundException {
        
		Admin admin = adminDao.getAdminByName(name);
		
		if (admin == null) 
			throw new EntityNotFoundException(Admin.KEY, name);

		
		admin.setLastLogin(System.currentTimeMillis());
		adminDao.save(admin);
		
		logger.debug("Login admin: {}.",  name);
		return admin;
    }
	
    public void createAdmin(Admin admin, List<Long> roleIds) throws EntityAlreadyExistException {
    	
		Admin obj = adminDao.getAdminByName(admin.getName());
		
        if (obj != null) {
            throw new EntityAlreadyExistException(Admin.KEY, admin.getName());
        }

        admin.setCreateTime(System.currentTimeMillis());
        admin.setLastLogin(DateUtils.TIME_OF_NA);
        admin.setLastModify(DateUtils.TIME_OF_NA);
        
        HibernateWebUtils.mergeByCheckedIds(admin.getRoles(), roleIds, Role.class);
        adminDao.save(admin);
    }
    
    public void updateAdmin(Admin admin, List<Long> roles) throws EntityCantModifyException {
    	
		if (admin.getId() == 1 || admin.getName().equalsIgnoreCase("admin")) {
			throw new EntityCantModifyException(Admin.KEY, "admin");		
		}
    	
    	admin.setLastModify(System.currentTimeMillis());
    	
        HibernateWebUtils.mergeByCheckedIds(admin.getRoles(), roles, Role.class);
    	adminDao.merge(admin);
    }
    
	public void deleteAdmin(Long id) throws EntityNotFoundException, EntityCantDeleteException, IdEntityException {
		
		Admin admin = getAdmin(id);
		
		if (admin.getId() == 1 || admin.getName().equalsIgnoreCase("admin")) {
			throw new EntityCantDeleteException(Admin.KEY, "admin");		
		}
		
		adminDao.delete(id);
	} 
	
	public List<Role> getAllRoles() {
		
		return roleDao.getAll();
	}
	 
}
