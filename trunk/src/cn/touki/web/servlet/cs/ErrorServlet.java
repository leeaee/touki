package cn.touki.web.servlet.cs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.touki.web.core.servlet.AbstractServlet;
import cn.touki.web.core.servlet.Constants;
import cn.touki.web.exception.EntityNotFoundException;
import cn.touki.web.exception.EntityPausedException;
import cn.touki.web.exception.LoginException;

/**
 * The login servlet for administrators.
 *
 * @author Liyi
 * 
 */
public class ErrorServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

    @Override public void init() throws ServletException {
        super.init();
    }

    @Override
    public void onDefault(HttpServletRequest req, HttpServletResponse res)
    	throws IOException, ServletException, LoginException, EntityPausedException, EntityNotFoundException {
        
    	onAuthentication(req, res);
    }
    
    public void onAuthentication(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException, LoginException, EntityPausedException, EntityNotFoundException {
		
	    req.getRequestDispatcher(Constants.PAGE_ERROR_500).forward(req, res);
	}
    
    public void on500(HttpServletRequest req, HttpServletResponse res)
    	throws IOException, ServletException, LoginException, EntityPausedException, EntityNotFoundException {
    	
        req.getRequestDispatcher(Constants.PAGE_ERROR_500).forward(req, res);
    }
    
    public void on404(HttpServletRequest req, HttpServletResponse res)
    	throws IOException, ServletException, LoginException, EntityPausedException, EntityNotFoundException {
    	
    	req.getRequestDispatcher(Constants.PAGE_ERROR_404).forward(req, res);
    }
    
    public void on403(HttpServletRequest req, HttpServletResponse res)
    	throws IOException, ServletException, LoginException, EntityPausedException, EntityNotFoundException {
    	
    	req.getRequestDispatcher(Constants.PAGE_ERROR_403).forward(req, res);
    }
    


}
