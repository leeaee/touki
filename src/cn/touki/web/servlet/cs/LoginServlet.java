package cn.touki.web.servlet.cs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.ui.AbstractProcessingFilter;

import cn.touki.web.core.servlet.AbstractServlet;
import cn.touki.web.core.servlet.Constants;
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
        
    	req.getRequestDispatcher(Constants.PAGE_LOGIN).forward(req, res);
    }
    
    public void onError(HttpServletRequest req, HttpServletResponse res)
    	throws IOException, ServletException, LoginException, EntityPausedException, EntityNotFoundException {
    	
    	HttpSession session = req.getSession();
    	
        req.setAttribute(Constants.ERROR_MESSAGE, session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY));
        req.getRequestDispatcher(Constants.PAGE_EXCEPTION).forward(req, res);
    }


}
