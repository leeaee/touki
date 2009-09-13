package cn.touki.web.servlet.cs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.touki.i18n.I18NMessage;
import cn.touki.web.core.orm.Page;
import cn.touki.web.core.orm.PropertyFilter;
import cn.touki.web.core.orm.hibernate.HibernateWebUtils;
import cn.touki.web.core.servlet.AbstractServlet;
import cn.touki.web.core.validation.WebBeanValidator;
import cn.touki.web.entity.csadmin.Authority;
import cn.touki.web.entity.csadmin.Role;
import cn.touki.web.exception.EntityAlreadyExistException;
import cn.touki.web.exception.EntityCantModifyException;
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.exception.WebException;
import cn.touki.web.service.RoleService;
import cn.touki.web.view.Button;

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
		
		onBrowse(req, res);
	}
	
	/**
	 * 角色浏览
	 */
	public void onBrowse(HttpServletRequest req, HttpServletResponse res)
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
    public void onDetail(HttpServletRequest req, HttpServletResponse res) 
	    	throws IOException, ServletException, WebException {
	    	
    	Long id = getRequestId(req);
    	Role role = roleService.getRole(id);
	
    	req.setAttribute("role", role);
    	req.getRequestDispatcher(PAGE_ROOT_PATH + "/role/role_detail.inc.jsp").forward(req, res);
	}
    
	
	/**
	 * 角色创建
	 */
    public void onCreate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException {
    	
    	List<Authority> authorities = roleService.getAllAuthorities();
    	
    	req.setAttribute("authorities", authorities);
        req.getRequestDispatcher(PAGE_ROOT_PATH + "/role/role_create.jsp").forward(req, res);
    }	
    
    public void onDoCreate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException, EntityAlreadyExistException {

    	Role role = (Role) WebBeanValidator.getValidBean(req, Role.class);
        List<Long> authorities = getRequestSelectedIds(req, "authority");
        
        roleService.createRole(role, authorities);
        
        I18NMessage message = new I18NMessage("msg.ok", new I18NMessage("msg.admin.create", role.getName()));
        
        List<Button> buttons = new ArrayList<Button>();
        Button bttnNext = new Button(Button.LABEL_NEXT, "location.href = './role?method=create'");
        buttons.add(bttnNext);        
        Button bttnBack = new Button(Button.LABEL_BACK, "location.href = './role'");
        buttons.add(bttnBack);        

        handleMessage(req, res, message, buttons);
    }
    
	/**
	 * 角色修改
	 */
    public void onUpdate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException, WebException {
    	
    	Long id = getRequestId(req);
    	Role role = roleService.getRole(id);
    	
    	List<Authority> authorities = roleService.getAllAuthorities();
    	
    	req.setAttribute("authorities", authorities); 	
    	req.setAttribute("role", role);
        req.getRequestDispatcher(PAGE_ROOT_PATH + "/role/role_update.jsp").forward(req, res);
    }
    
    public void onDoUpdate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException, EntityAlreadyExistException, EntityNotFoundException, EntityCantModifyException {

    	Role role = (Role) WebBeanValidator.getValidBean(req, Role.class);
        
        List<Long> authorities = getRequestSelectedIds(req, "authority");
        
        roleService.updateRole(role, authorities);

        I18NMessage message = new I18NMessage("msg.ok", new I18NMessage("msg.role.update", role.getName()));
        Button button = new Button(Button.LABEL_OK, "");
        button.setAction("location.href = './role'");

        handleMessage(req, res, message, button);
    }
    
	/**
	 * 角色删除
	 */        
    public void onDelete(HttpServletRequest req, HttpServletResponse res) 
	    	throws IOException, ServletException, WebException {
	    	
        List<Long> ids = getCheckedIds(req);
        int deleted = 0;

        for (Long id : ids) {
            roleService.deleteRole(id);
            deleted ++;
        }

        I18NMessage message = new I18NMessage("msg.ok", new I18NMessage("msg.role.delete", deleted));
        Button button = new Button(Button.LABEL_OK, "location.href = './role'");

        handleMessage(req, res, message, button);
	}	    
	
}
