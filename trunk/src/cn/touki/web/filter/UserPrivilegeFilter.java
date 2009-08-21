package cn.touki.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.touki.web.core.servlet.Constants;

/**
 * The filter to process user's visiting privilege.
 *
 * @author Liyi
 * 
 */
public class UserPrivilegeFilter implements Filter {

    private static String contextName;

    private static final String DEFAULT_ENCODING = "UTF-8";

    public void init(FilterConfig config) throws ServletException {
        contextName = config.getServletContext().getServletContextName();
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;

        httpReq.setCharacterEncoding(DEFAULT_ENCODING);
        httpRes.setContentType("text/html;charset=" + DEFAULT_ENCODING);
        httpRes.setCharacterEncoding(DEFAULT_ENCODING);

        String loginUrl = '/' + contextName + Constants.PAGE_LOGIN;
        if (httpReq.getRequestURL().toString().indexOf(loginUrl) >= 0 ||
                httpReq.getRequestURI().indexOf(loginUrl) >= 0) {

            chain.doFilter(req, res);
            return;
        }

        HttpSession session = httpReq.getSession(true);

        Object user = session.getAttribute(Constants.LOGIN_USER);
        if (user == null /*|| !(user instanceof User)*/) {
            session.setAttribute(Constants.ORG_REQ_URL, httpReq.getRequestURL().toString());
            //httpRes.sendRedirect(loginUrl);
            httpReq.getRequestDispatcher(Constants.PAGE_LOGIN).forward(httpReq, httpRes);
        }
        else {
            chain.doFilter(req, res);
        }
    }

    public void destroy() {
    }
}
