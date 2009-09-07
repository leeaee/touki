package cn.touki.web.servlet.cs;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.touki.util.MD5;
import cn.touki.web.core.servlet.AbstractServlet;
import cn.touki.web.core.servlet.Constants;
import cn.touki.web.entity.admin.Admin;
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.exception.EntityPausedException;
import cn.touki.web.exception.LoginException;
import cn.touki.web.service.AdminService;

/**
 * The login servlet for administrators.
 *
 * @author Liyi
 * 
 */
public class AdminLoginServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;
	protected AdminService adminService;

    @Override public void init() throws ServletException {
        super.init();
        this.adminService = (AdminService) beanFactory.getBean(AdminService.class.getSimpleName());
    }

    @Override
    public void onDefault(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, LoginException, EntityPausedException, EntityNotFoundException {
        
    	req.getRequestDispatcher(PAGE_ROOT_PATH + "/login.jsp").forward(req, res);
    }

    /**
     * 管理员登录.
     * 
     * @throws EntityNotFoundException 
     */
    public void onLogin(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, LoginException, EntityPausedException, EntityNotFoundException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Admin admin = adminService.login(username, MD5.getHashString(password));
        loginUser(req, res, admin);
    }

    /**
     * 管理员退出.
     * 
     */
    public void onLogout(HttpServletRequest req, HttpServletResponse res) throws IOException {
        logoutUser(req, res);
    }

    private static void loginUser(HttpServletRequest req, HttpServletResponse res, Admin admin)
            throws IOException {

        HttpSession session = req.getSession();
        session.setAttribute(Constants.LOGIN_USER, admin);

        String orgReqUrl = getAbsolutePath(Constants.PAGE_INDEX);
        if (session.getAttribute(Constants.ORG_REQ_URL) != null) {
            orgReqUrl = (String) session.getAttribute(Constants.ORG_REQ_URL);
        }

        res.sendRedirect(orgReqUrl);
    }

    private static void logoutUser(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession();
        session.removeAttribute(Constants.LOGIN_USER);

        Enumeration<?> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (name.indexOf("search") >= 0) {
                session.removeAttribute(name);
            }
        }

        res.sendRedirect(getAbsolutePath(Constants.PAGE_INDEX));
    }

}
