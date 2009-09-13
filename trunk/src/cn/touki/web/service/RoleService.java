package cn.touki.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.touki.i18n.I18NDictionary;
import cn.touki.util.DateUtils;
import cn.touki.web.core.orm.Page;
import cn.touki.web.core.orm.PropertyFilter;
import cn.touki.web.core.orm.hibernate.HibernateWebUtils;
import cn.touki.web.entity.csadmin.Admin;
import cn.touki.web.entity.csadmin.Authority;
import cn.touki.web.entity.csadmin.Role;
import cn.touki.web.exception.EntityAlreadyExistException;
import cn.touki.web.exception.EntityCantDeleteException;
import cn.touki.web.exception.EntityCantModifyException;
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.service.dao.AuthorityDao;
import cn.touki.web.service.dao.RoleDao;

@Service("RoleService")
@Transactional
public class RoleService {

	protected Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authorityDao;
	
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
		
		logger.debug("Get role by name: {}.",  roleName);
		return role; 
	}	
	
    public void createRole(Role role, List<Long> authorities) throws EntityAlreadyExistException {
    	
		Role obj = roleDao.getRoleByName(role.getName());
		
        if (obj != null) {
            throw new EntityAlreadyExistException(Role.KEY, role.getName());
        }
        
        role.setCreateTime(System.currentTimeMillis());
        role.setLastModify(DateUtils.TIME_OF_NA);        

        HibernateWebUtils.mergeByCheckedIds(role.getAuthorities(), authorities, Authority.class);
        roleDao.save(role);
    }
    
    public void updateRole(Role role, List<Long> authorities) throws EntityCantModifyException {
    	
		if (role.getId() == 1) {
			throw new EntityCantModifyException(Role.KEY, I18NDictionary.getMessage("entity.admin"));		
		}    	
    	
    	role.setLastModify(System.currentTimeMillis());
    	
        HibernateWebUtils.mergeByCheckedIds(role.getAuthorities(), authorities, Authority.class);
    	roleDao.merge(role);
    }
    
	public void deleteRole(Long id) throws EntityNotFoundException, EntityCantDeleteException {
		
		Role role = getRole(id);
		
		if (role.getId() == 1) {
			throw new EntityCantDeleteException(Admin.KEY, I18NDictionary.getMessage("entity.admin"));		
		}		
		roleDao.delete(role.getId());
	}
	
	public List<Authority> getAllAuthorities() {
		
		return authorityDao.getAll();
	}
	 
}
