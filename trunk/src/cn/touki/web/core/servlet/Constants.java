package cn.touki.web.core.servlet;

public interface Constants {
    
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String DEFAULT_METHOD = "default";

    public static final String BEAN_FACTORY = "context-attr-beanfactory";

    public static final String WAC_ATTRIB = "context-web-application-context-key";

    public static final String WEBMOD_ATTRIB = "context-web-module-key";

    public static final String MSG_BUTTON = "page-attr-message-button";

    public static final String ERROR_MESSAGE = "page-attr-error-message";

    public static final String MESSAGE = "page-attr-message";

    public static final String MESSAGE_BUTTONS = "page-attr-message-buttons";

    public static final String USER_LOCALE_KEY = "page-attr-user-locale";

    public static final String FILTER_ERROR = "page-attr-filter-error";

    public static final String FILTER_MESSAGE = "page-attr-filter-message";

    public static final String LOGIN_USER = "session-attr-login-user";

    public static final String LOGIN_TOPENAME = "session-attr-login-topename";

    public static final String LOGIN_GROUP = "session-attr-login-group";

    public static final String LOGIN_VIP = "session-attr-login-vip";

    public static final String ORG_REQ_URL = "session-attr-original-requested-url";

    public static final String EXPANDED_NODES = "session-attr-expanded-nodes";

    public static final String PAGE_EXCEPTION = "/WEB-INF/jsp/common/exception.jsp";
    
    public static final String PAGE_ERROR_500 = "/WEB-INF/jsp/common/500.jsp";
    
    public static final String PAGE_ERROR_404 = "/WEB-INF/jsp/common/404.jsp";
    
    public static final String PAGE_ERROR_403 = "/WEB-INF/jsp/common/403.jsp";

    public static final String PAGE_MESSAGE = "/WEB-INF/jsp/common/message.jsp";

    public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";

    public static final String PAGE_INDEX = "/";

    public static final String QUERY_PAGE_INDEX = "page.pageIndex";
} // end interface
