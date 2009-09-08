package cn.touki.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.touki.web.core.orm.Page;
import cn.touki.web.core.orm.PropertyFilter;
import cn.touki.web.entity.admin.Role;
import cn.touki.web.exception.EntityAlreadyExistException;
import cn.touki.web.exception.EntityDefaultDeleteException;
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.service.dao.RoleDao;

@Service("RoleService")
@Transactional
public class RoleService {

	protected Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	private RoleDao roleDao;
	
	@Transactional(readOnly = true)
	public Page<Role> searchRole(final Page<Role> page, final List<PropertyFilter> filters) {
		return roleDao.findPage(page, filters);
	}	
	
	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		
		Role role = roleDao.get(id);
		
		logger.debug("Get admin role by id: {}.",  id);
		return role; 
	}
	
	
	@Transactional(readOnly = true)
	public Role getRole(String roleName) throws EntityNotFoundException {
		
		Role role = roleDao.getRoleByName(roleName);
		
		if (role == null) {
			throw new EntityNotFoundException(Role.KEY, roleName);
		}
		
		logger.debug("Get role by role name: {}.",  roleName);
		return role; 
	}	
	
	@Secured({"a_manage_role"})
    public void createRole(Role role) throws EntityAlreadyExistException {
    	
		Role obj = roleDao.getRoleByName(role.getRoleName());
		
        if (obj != null) {
            throw new EntityAlreadyExistException(Role.KEY, role.getRoleName());
        }

        roleDao.save(role);
    }
    
	@Secured({"a_manage_role"})	
    public void updateRole(Role role) {
    	
    	roleDao.merge(role);
    }
    
	@Secured({"a_manage_role"})	
	public void deleteRole(Long id) throws EntityNotFoundException, EntityDefaultDeleteException {
		
		Role role = getRole(id);
		roleDao.delete(role.getId());
	}    
	 
}
