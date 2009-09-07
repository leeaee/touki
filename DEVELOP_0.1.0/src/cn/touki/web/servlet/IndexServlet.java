package cn.touki.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.touki.web.core.servlet.AbstractServlet;

/**
 * 
 * @author Liyi
 *
 */
public class IndexServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
    public void onDefault(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.getRequestDispatcher("/index.jsp").forward(req, res);
    }
}
