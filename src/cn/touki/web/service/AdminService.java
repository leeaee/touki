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
import cn.touki.web.entity.admin.Admin;
import cn.touki.web.exception.EntityAlreadyExistException;
import cn.touki.web.exception.EntityCantDeleteException;
import cn.touki.web.exception.EntityCantModifyException;
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.service.dao.AdminDao;

@Service("AdminService")
@Transactional
public class AdminService {

	protected Logger logger = LoggerFactory.getLogger(AdminService.class);
	
	@Autowired
	private AdminDao adminDao;
	
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
	
	public Admin login(String adminId) {
        
		Admin admin = adminDao.getAdminByName(adminId);
		
		admin.setLastLogin(System.currentTimeMillis());
		adminDao.save(admin);
		
		logger.debug("Login admin: {}.",  adminId);
		return admin;
    }
	
    public void createAdmin(Admin admin) throws EntityAlreadyExistException {
    	
		Admin obj = adminDao.getAdminByName(admin.getName());
		
        if (obj != null) {
            throw new EntityAlreadyExistException(Admin.KEY, admin.getName());
        }

        admin.setCreateTime(System.currentTimeMillis());
        admin.setLastLogin(DateUtils.TIME_OF_NA);
        admin.setLastModify(DateUtils.TIME_OF_NA);
        adminDao.save(admin);
    }
    
    public void updateAdmin(Admin admin) throws EntityCantModifyException {
    	
		if (admin.getId() == 1 || admin.getName().equalsIgnoreCase("admin")) {
			throw new EntityCantModifyException(Admin.KEY, "admin");		
		}
    	
    	admin.setLastModify(System.currentTimeMillis());
    	adminDao.merge(admin);
    }
    
	public void deleteAdmin(Long id) throws EntityNotFoundException, EntityCantDeleteException {
		
		Admin admin = getAdmin(id);
		
		if (admin.getId() == 1 || admin.getName().equalsIgnoreCase("admin")) {
			throw new EntityCantDeleteException(Admin.KEY, "admin");		
		}
		
		adminDao.delete(id);
	}    
	 
}
