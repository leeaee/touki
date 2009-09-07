package cn.touki.web.servlet.cs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.touki.web.core.servlet.AbstractServlet;
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
public class LoginServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;
	protected AdminService adminService;

    @Override public void init() throws ServletException {
        super.init();
    }

    @Override
    public void onDefault(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, LoginException, EntityPausedException, EntityNotFoundException {
        
    	req.getRequestDispatcher(PAGE_ROOT_PATH + "/login.jsp").forward(req, res);
    }


}
