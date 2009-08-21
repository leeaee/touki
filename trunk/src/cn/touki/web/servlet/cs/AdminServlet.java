package cn.touki.web.servlet.cs;

import java.io.IOException;
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
import cn.touki.web.core.servlet.Constants;
import cn.touki.web.core.validation.WebBeanValidator;
import cn.touki.web.entity.admin.Admin;
import cn.touki.web.exception.EntityAlreadyExistException;
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
		
		onAdminDoBrowse(req, res);
	}
	
	/**
	 * 管理员浏览
	 */
	public void onAdminDoBrowse(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException, EntityNotFoundException {
		
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(req);
		Page<Admin> page = HibernateWebUtils.buildPageParameters(req, PAGE_SIZE);
		page = adminService.searchAdmin(page, filters);
    	
    	req.setAttribute("page", page);
    	req.getRequestDispatcher("/admin/admin_browse.jsp").forward(req, res);
	}
    
	/**
	 * 管理员详细信息
	 */        
    public void onAdminDoDetail(HttpServletRequest req, HttpServletResponse res) 
	    	throws IOException, ServletException, WebException {
	    	
    	Long id = getRequestId(req);
    	Admin admin = adminService.getAdmin(id);
	
    	req.setAttribute("admin", admin);
    	req.getRequestDispatcher("/admin/admin_detail.inc.jsp").forward(req, res);
	}	
	
	/**
	 * 管理员创建
	 */
    public void onAdminPrepareCreate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException {

        req.getRequestDispatcher("/admin/admin_create.jsp").forward(req, res);
    }	
    
    public void onAdminDoCreate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException, EntityAlreadyExistException {

    	Admin admin = (Admin) WebBeanValidator.getValidBean(req, Admin.class);
        admin.setPassword(MD5.getHashString(admin.getPassword()));
        
        adminService.createAdmin(admin);

        I18NMessage message = new I18NMessage("msg.ok", new I18NMessage("msg.admin.create", admin.getAdminId()));
        Button button = new Button(Button.LABEL_OK, "");
        button.setAction("location.href = './admin.do'");

        handleMessage(req, res, message, button);
    }
    
	/**
	 * 管理员修改
	 */
    public void onAdminPrepareUpdate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException, WebException {
    	
    	Long id = getRequestId(req);
    	Admin admin = adminService.getAdmin(id);	
    	
    	req.setAttribute("admin", admin);
        req.getRequestDispatcher("/admin/admin_update.jsp").forward(req, res);
    }
    
    public void onAdminPrepareUpdateSelf(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException, WebException {
		
		Admin admin = (Admin) req.getSession().getAttribute(Constants.LOGIN_USER);
		
		req.setAttribute("admin", admin);
		req.getRequestDispatcher("/admin/admin_update.jsp").forward(req, res);
	}    
    
    public void onAdminDoUpdate(HttpServletRequest req, HttpServletResponse res) 
    		throws IOException, ServletException, EntityAlreadyExistException, EntityNotFoundException {

        Admin admin = (Admin) WebBeanValidator.getValidBean(req, Admin.class);
        
        if (StringUtils.isEmpty(admin.getPassword())) {
            Admin orgAdmin = adminService.getAdmin(admin.getAdminId());
            admin.setPassword(orgAdmin.getPassword());
        }
        else {
            admin.setPassword(MD5.getHashString(admin.getPassword()));
        }
        
        adminService.updateAdmin(admin);

        // To check whether the updated admin is the currnet one.
        Admin currentAdmin = (Admin) req.getSession().getAttribute(Constants.LOGIN_USER);
        if (currentAdmin.getAdminId().equals(admin.getAdminId())) {
            req.getSession().setAttribute(Constants.LOGIN_USER, admin);
        }

        I18NMessage message = new I18NMessage("msg.ok", new I18NMessage("msg.admin.update", admin.getAdminId()));
        Button button = new Button(Button.LABEL_OK, "");
        button.setAction("location.href = './admin.do'");

        handleMessage(req, res, message, button);
    }
    
	/**
	 * 管理员删除
	 */        
    public void onAdminDoDelete(HttpServletRequest req, HttpServletResponse res) 
	    	throws IOException, ServletException, WebException {
	    	
        List<String> ids = getRequestIds(req);
        int deleted = 0;

        for (String id : ids) {
            adminService.deleteAdmin(Long.valueOf(id));
            deleted ++;
        }

        I18NMessage message = new I18NMessage("msg.ok", new I18NMessage("msg.admin.delete", deleted));
        Button button = new Button(Button.LABEL_OK, "");
        button.setAction("location.href = './admin.do'");

        handleMessage(req, res, message, button);
	}	    
   
}
