package cn.touki.web.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.touki.i18n.I18NMessage;

public class OutputServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
    public void onDefault(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        onMessage(req, res);
    }

    public void onError(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        Exception e = (Exception) req.getAttribute(Constants.FILTER_ERROR);
        handleException(req, res, e);
    }

    public void onMessage(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        I18NMessage message = (I18NMessage) req.getAttribute(Constants.FILTER_MESSAGE);
        handleMessage(req, res, message);
    }
}
