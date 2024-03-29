package cn.touki.web.servlet.cs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.touki.i18n.I18NMessage;
import cn.touki.util.MD5;
import cn.touki.util.StringUtils;
import cn.touki.web.core.orm.Page;
import cn.touki.web.core.orm.PropertyFilter;
import cn.touki.web.core.orm.hibernate.HibernateWebUtils;
import cn.touki.web.core.servlet.AbstractServlet;
import cn.touki.web.core.validation.WebBeanValidator;
import cn.touki.web.entity.csadmin.Admin;
import cn.touki.web.entity.csadmin.Role;
import cn.touki.web.exception.EntityAlreadyExistException;
import cn.touki.web.exception.EntityCantModifyException;
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.exception.WebException;
import cn.touki.web.service.AdminService;
import cn.touki.web.view.Button;

public class AdminServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;	
	
	private AdminService adminService;
	
    @Override 
    public void init() throws ServletException {
        super.init();
        this.adminService = (AdminService) beanFactory.getBean(AdminService.class.getSimpleName());
    }
	
	public void onDefault(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException, EntityNotFoundException {
		
		onBrowse(req, res);
	}
	
	/**
	 * 管理员浏览
	 */
	public void onBrowse(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException, EntityNotFoundException {
		
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(req);
		Page<Admin> page = HibernateWebUtils.buildPageParameters(req, PAGE_SIZE);
		page = adminService.searchAdmin(page, filters);
    	
    	req.setAttribute("page", page);
    	req.getRequestDispatcher(PAGE_ROOT_PATH + "/admin/admin_browse.jsp").forward(req, res);
	}
    
	/**
	 * 管理员详细信息
	 */        
    public void onDetail(HttpServletRequest req, HttpServletResponse res) 
	    	throws IOException, ServletException, WebException {
	    	
    	Long id = getRequestId(req);
    	Admin admin = adminService.getAdmin(id);
	
    	req.setAttribute("admin", admin);
    	req.getRequestDispatcher(PAGE_ROOT_PATH + "/admin/admin_detail.inc.jsp").forward(req, res);
	}	
	
	/**
	 * 管理员创建
	 */
    public void onCreate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException {

    	List<Role> roles = adminService.getAllRoles();
    	
    	req.setAttribute("roles", roles);
        req.getRequestDispatcher(PAGE_ROOT_PATH + "/admin/admin_create.jsp").forward(req, res);
    }	
    
    public void onDoCreate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException, EntityAlreadyExistException {

    	Admin admin = (Admin) WebBeanValidator.getValidBean(req, Admin.class);
        admin.setPassword(MD5.getHashString(admin.getPassword()));
        
        List<Long> roles = getRequestSelectedIds(req, "role");
        
        adminService.createAdmin(admin, roles);

        I18NMessage message = new I18NMessage("msg.ok", new I18NMessage("msg.admin.create", admin.getName()));
        
        List<Button> buttons = new ArrayList<Button>();
        Button bttnNext = new Button(Button.LABEL_NEXT, "location.href = './admin?method=create'");
        buttons.add(bttnNext);        
        Button bttnBack = new Button(Button.LABEL_BACK, "location.href = './admin'");
        buttons.add(bttnBack);        

        handleMessage(req, res, message, buttons);
    }
    
	/**
	 * 管理员修改
	 */
    public void onUpdate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException, WebException {
    	
    	Long id = getRequestId(req);
    	Admin admin = adminService.getAdmin(id);	
    	
    	List<Role> roles = adminService.getAllRoles();
    	
    	req.setAttribute("roles", roles);    	
    	req.setAttribute("admin", admin);
        req.getRequestDispatcher(PAGE_ROOT_PATH + "/admin/admin_update.jsp").forward(req, res);
    }
    
    public void onDoUpdate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException, EntityAlreadyExistException, EntityNotFoundException, EntityCantModifyException {

        Admin admin = (Admin) WebBeanValidator.getValidBean(req, Admin.class);
        
        List<Long> roles = getRequestSelectedIds(req, "role");
        
        if (StringUtils.isEmpty(admin.getPassword())) {
            Admin orgAdmin = adminService.getAdmin(admin.getName());
            admin.setPassword(orgAdmin.getPassword());
        }
        else {
            admin.setPassword(MD5.getHashString(admin.getPassword()));
        }
        
        adminService.updateAdmin(admin, roles);

        I18NMessage message = new I18NMessage("msg.ok", new I18NMessage("msg.admin.update", admin.getName()));
        Button button = new Button(Button.LABEL_OK, "");
        button.setAction("location.href = './admin'");

        handleMessage(req, res, message, button);
    }
    
	/**
	 * 管理员删除
	 */        
    public void onDelete(HttpServletRequest req, HttpServletResponse res) 
	    	throws IOException, ServletException, WebException {
	    	
        List<Long> ids = getCheckedIds(req);
        int deleted = 0;

        for (Long id : ids) {
            adminService.deleteAdmin(id);
            deleted ++;
        }

        I18NMessage message = new I18NMessage("msg.ok", new I18NMessage("msg.admin.delete", deleted));
        Button button = new Button(Button.LABEL_OK, "location.href = './admin'");

        handleMessage(req, res, message, button);
	}	    
   
}
