package cn.touki.web.servlet.cs;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.touki.web.core.orm.Page;
import cn.touki.web.core.orm.PropertyFilter;
import cn.touki.web.core.orm.hibernate.HibernateWebUtils;
import cn.touki.web.core.servlet.AbstractServlet;
import cn.touki.web.entity.csadmin.Role;
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.exception.WebException;
import cn.touki.web.service.RoleService;

public class RoleServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;	
	
	private RoleService roleService;
	
    @Override 
    public void init() throws ServletException {
        super.init();
        this.roleService = (RoleService) beanFactory.getBean(RoleService.class.getSimpleName());
    }
	
	public void onDefault(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException, EntityNotFoundException {
		
		onRoleDoBrowse(req, res);
	}
	
	/**
	 * 角色浏览
	 */
	public void onRoleDoBrowse(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException, EntityNotFoundException {
		
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(req);
		Page<Role> page = HibernateWebUtils.buildPageParameters(req, PAGE_SIZE);
		page = roleService.searchRole(page, filters);
    	
    	req.setAttribute("page", page);
    	req.getRequestDispatcher(PAGE_ROOT_PATH + "/role/role_browse.jsp").forward(req, res);
	}
    
	/**
	 * 角色详细信息
	 */        
    public void onRoleDoDetail(HttpServletRequest req, HttpServletResponse res) 
	    	throws IOException, ServletException, WebException {
	    	
    	Long id = getRequestId(req);
    	Role role = roleService.getRole(id);
	
    	req.setAttribute("role", role);
    	req.getRequestDispatcher(PAGE_ROOT_PATH + "/admin/admin_detail.inc.jsp").forward(req, res);
	}	
	
}
