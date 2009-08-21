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
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.exception.EntityPausedException;
import cn.touki.web.exception.LoginException;
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
	public Admin getAdmin(Long id) throws EntityNotFoundException {
		
		Admin admin = adminDao.get(id);
		
		logger.debug("Get admin by id: {}.",  id);
		return admin; 
	}
	
	@Transactional(readOnly = true)
	public Admin getAdmin(String adminId) throws EntityNotFoundException {
		
		Admin admin = adminDao.getAdminById(adminId);
		
		if (admin == null) {
			throw new EntityNotFoundException(Admin.KEY, adminId);
		}
		
		logger.debug("Get admin by admin_id: {}.",  adminId);
		return admin; 
	}
	
	public Admin login(String adminId, String password) throws LoginException, EntityNotFoundException, EntityPausedException {
        
		Admin admin = getAdmin(adminId);
		
		if (!password.equals(admin.getPassword())) {
		    throw new LoginException(LoginException.REASON_INVALID_PWD);
		}
		
		if (admin.getState() != Admin.NORMAL && admin.getState() != Admin.WARNING) {
			throw new EntityPausedException(Admin.KEY, adminId);
		}
		
		admin.setLastLogin(System.currentTimeMillis());
		adminDao.save(admin);
		
		return admin;
    }
	
    public void createAdmin(Admin admin) throws EntityAlreadyExistException {
    	
		Admin obj = adminDao.getAdminById(admin.getAdminId());
		
        if (obj != null) {
            throw new EntityAlreadyExistException(Admin.KEY, admin.getAdminId());
        }

        admin.setCreateTime(System.currentTimeMillis());
        admin.setLastLogin(DateUtils.TIME_OF_NA);
        admin.setLastModify(DateUtils.TIME_OF_NA);
        adminDao.save(admin);
    }
    
    public void updateAdmin(Admin admin) {
    	
    	admin.setLastModify(System.currentTimeMillis());
    	adminDao.merge(admin);
    }
    
	public void deleteAdmin(Long id) throws EntityNotFoundException {
		
		Admin admin = getAdmin(id);
		
		if (admin.getId() == 1 || admin.getAdminId().equalsIgnoreCase("admin")) {
						
		}
		
		adminDao.delete(id);
	}    
	 
}
