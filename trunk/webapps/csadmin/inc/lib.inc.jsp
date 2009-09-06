<%@ page import="
	java.util.List,
	java.util.Enumeration,
	java.util.Locale,
	org.springframework.security.ui.webapp.AuthenticationProcessingFilter,
	org.springframework.security.AuthenticationException,
	org.displaytag.tags.el.ELSetPropertyTag,
	cn.touki.web.core.servlet.Constants,
	cn.touki.i18n.I18NDictionary,
	cn.touki.util.StringUtils,cn.touki.web.taglib.displaytag.DateDecorator,
	cn.touki.web.entity.common.Stateful,
	cn.touki.web.entity.admin.Admin"
%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@ taglib prefix="f" uri="/WEB-INF/tld/f.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/taglibs-i18n.tld" %>
<%@ taglib prefix="html" uri="/WEB-INF/tld/html.tld" %>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld" %>
<fmt:bundle baseName="cn.touki.i18n.resource.LangResource" localeRef="<%=Constants.USER_LOCALE_KEY%>" scope="session" />
<%
	final String htmlHeaderCheck = "<input type=\"checkbox\" id=\"check-all\" class=\"ckbox\" name=\"check-all\" />";
	String baseUri = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	
    //Get current session language by 'lang'
    if (request.getParameter("lang") != null) {
        session.setAttribute(Constants.USER_LOCALE_KEY, I18NDictionary.getLocaleWithString(request.getParameter("lang")));
    }
    
    Locale userLocale = (Locale) session.getAttribute(Constants.USER_LOCALE_KEY);
    Admin curUser = (Admin) session.getAttribute(Constants.LOGIN_USER);
    DateDecorator dateDecorator = new DateDecorator();
	
	// Set for JSTL
	pageContext.setAttribute("htmlHeaderCheck", htmlHeaderCheck);
	pageContext.setAttribute("baseUri", baseUri);
	pageContext.setAttribute("userLocale", userLocale);
	pageContext.setAttribute("curUser", curUser);
	pageContext.setAttribute("dateDecorator", dateDecorator);
%>
