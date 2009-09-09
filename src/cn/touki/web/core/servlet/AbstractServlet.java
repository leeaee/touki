package cn.touki.web.core.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;

import cn.touki.i18n.I18NDictionary;
import cn.touki.i18n.I18NException;
import cn.touki.i18n.I18NMessage;
import cn.touki.util.RequestUtils;
import cn.touki.util.SensitiveWords;
import cn.touki.util.SensitiveWordsException;
import cn.touki.util.StringUtils;
import cn.touki.validation.ValidationException;
import cn.touki.web.core.listener.InitListener;
import cn.touki.web.core.validation.SearchValidator;
import cn.touki.web.core.validation.WebBeanValidator;
import cn.touki.web.core.validation.WebValidator;
import cn.touki.web.exception.WebException;
import cn.touki.web.view.Button;

/**
 * The Basic Servlet class of all the other Servlets.
 * <p/>
 * 本类为一个抽象的 Servlet 类，为所有其它的 Servlet 提供共同的机制，其它 Servlet 继承本类，主要包含： <Ul> <Li>Spring 架构 BeanFactory
 * (WebApplicationContext) 的提供，该对象在启动的时候由 InitListener 获取并存放于 ServletContext 的属性中。对于 {@code service}，下层 Servlet 只需调用
 * <code>protected service</code> 属性，即可获得数据事务操作的各 Service。如：
 * <pre>
 *        service.getUserService().login(userId, password);
 * </pre>
 * <p/>
 * <Li>从 WebApplicationContext 中获取由表单 POST 的 validators 参数指定名称的 validator，并调用它们。
 * <p/>
 * <Li>对 method 参数值对应的方法的反射机制。对于parameter中存在的"method"值，doGet/doPost方法将自动调用 Servlet 中对应的 onXxx()方法。各子 Servlet 在设置 onXxx()
 * 方法的时候，需声明为 public 属性。如：
 * <p/>
 * 对于 <code>?method=test</code>，将直接调用对应 Servlet 的 {@code public void onTest()} 方法。
 * <p/>
 * <Li>提供标准日志接口logger，使用其info(), debug(), warn(), error()方法，供 Servlet 在需要的时候记录日志。
 * <p/>
 * <Li>提供log()方法往数据库中记录日志。
 * <p/>
 * <Li>提供对 Servlet 中出现的异常处理的统一捕获和显示机制。在 Servlet 中，可以使用如下方法来处理异常和消息：
 * <pre>
 *        handleException(req, res, e);
 *        handleException(req, res, e, buttonList);
 * <p/>
 *        handleMessage(req, res, i18nMsg);
 *        handleMessage(req, res, i18nMsg, buttonList);
 * </pre>
 * </Ul>
 * <p/>
 * <p/>
 * 对于在 URL 中，以 GET 方式传入的参数，应务必先通过 {@code urlEncode(str)}，以免出现乱码和错误。
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.16 $
 * @see WebBeanValidator
 * @see SearchValidator
 * @since 7.00.00
 */
public abstract class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	//Properties
    /**
     * The BeanFactory, which can be used to get other beans.
     */
    protected static WebApplicationContext wac;

    /**
     * Basic Service, use <code>service.getXxxService()</code> method to get concrete service to deal with data
     * transaction.
     */
    protected static AutowireCapableBeanFactory beanFactory;

    protected static SensitiveWords sensitiveWords;

    /**
     * The size of records list in a page.
     */
    protected static int PAGE_SIZE;

    /**
     * Log4j instance, used to make logs in servlets.
     */
    protected Logger logger;

    private static Logger localLogger = LoggerFactory.getLogger(AbstractServlet.class);

    private static final String DISPACHER_METHOD_PREFIX = "on";

    protected static final String DEFAULT_CHARSET = "UTF-8";
    
    protected static final String PAGE_ROOT_PATH = "/WEB-INF/jsp";

    private static final String DEFAULT_I18N_RES = "/i18nres/";
    
    private static String contextPath;

    //Constructor

    //Methods

    /**
     * 在 Servlet 构造后加载时执行，用于从ServletContext中取得BeanFactory，获得service，初始化logger等。
     * 所有继承的新 Servlet 都不得直接覆盖本方法的实现.
     */
    @Override
    public void init() throws ServletException {

        super.init();

        //初始化logger
        logger = LoggerFactory.getLogger(this.getClass());

        //从ServletContext中获取初始设置数据
        PAGE_SIZE = Integer.parseInt(this.getServletContext().getInitParameter("PAGE_SIZE"));
        
        //从ServletContext中获取BeanFactory，该对象应由InitListener在启动时获取并加载到该属性中。
        wac = (WebApplicationContext) this.getServletContext().getAttribute(Constants.WAC_ATTRIB);

        //初始化service
        beanFactory = wac.getAutowireCapableBeanFactory();        

        if (localLogger.isDebugEnabled()) {
            localLogger.debug("Servlet [" + this.getClass().getName() + "] 初始化成功！");
        }
    }

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a GET request. <p />
     * 在本方法中，主要实现两个步骤： <ul> <li>从 POST 的参数 validators 中获取表单中设置的 validator 名称，并根据改名成从 WebApplicationContext 中取出，并调用。
     * <li>从 QueryString 的 method 属性中取得值 xxx，并通过一定的反射机制调用 Servlet 的 onXxx() 方法。 </ul>
     *
     * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param res an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @exception ServletException if the request for the GET could not be handled
     * @exception IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        //设置语言信息
        req.setCharacterEncoding(DEFAULT_CHARSET);
        res.setContentType("text/html;charset=" + DEFAULT_CHARSET);
        res.setCharacterEncoding(DEFAULT_CHARSET);

        //设置 ContextPath
        if (contextPath == null) {
            contextPath = req.getContextPath();
            if (contextPath.equals("")) {
                contextPath = "/";
            }
        }

        //设置session超时时限
        req.getSession().setMaxInactiveInterval(-1);

        try {
            //invoke corresponding validator on the POSTed parameter of 'validator'.
            invokeValidators(req);

            //invoke corresponding method on the mostly GET parameter of 'method'.
            invokeMethodDispatcher(req, res);
        }
        catch (ValidationException e) {
        	// TODO modified the return exception
            handleException(req, res, e);
        	
        }
        catch (NullPointerException e) {
            handleException(req, res, new WebException(WebException.NULL, e));
        }
        catch (SensitiveWordsException e) {
            handleException(req, res, new WebException(WebException.SENSITIVE_WORDS, e));
        }
        catch (IOException e) {
            handleException(req, res, new WebException(WebException.URL_NOT_FOUND, e));
        }
        catch (Exception e) {
            handleException(req, res, e);
        }
    }

    private static String getMethodFromRequest(HttpServletRequest req) {
        String method = req.getParameter("method");

        if (method == null || method.length() == 0) {
            method = Constants.DEFAULT_METHOD;
        }

        return method;
    }

    private static void invokeValidators(HttpServletRequest req) throws ValidationException, WebException {

        String method = getMethodFromRequest(req);

        String[] validators = req.getParameterValues("validator");
        if (validators != null && validators.length > 0) {
            for (String validatorName : validators) {
                Object validator = wac.getBean(validatorName);
                if (validator == null) {
                    localLogger.warn("Validator [".concat(validatorName).concat("] was not found!"));
                    continue;
                }
                else if (localLogger.isDebugEnabled()) {
                    localLogger.debug("Validator [".concat(validatorName).concat("] was loaded and about to go!"));
                }

                try {
                    // WebBeanValidator invokation.
                    if (validator instanceof WebBeanValidator) {
                        ((WebBeanValidator) validator).process(req, getWebValidatorMode(method));
                    }
                    // SearchValidator invokation.
                    else if (validator instanceof SearchValidator) {
                        ((SearchValidator) validator).process(req);
                    }
                    // WebValidator invokation.
                    else if (validator instanceof WebValidator) {
                        ((WebValidator) validator).process(req);
                    }

                    req.setAttribute(validatorName, validator);
                }
                catch (IllegalAccessException e) {
                    throw new ValidationException(e);
                }
                catch (InvocationTargetException e) {
                    throw new ValidationException(e);
                }
                catch (InstantiationException e) {
                    throw new ValidationException(e);
                }
            }
        }
    }

    private void invokeMethodDispatcher(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        Class<?> servletClass = this.getClass();
        String method = getMethodFromRequest(req);

        String methodPrefix = req.getMethod().equalsIgnoreCase("GET") ? getGetMethodPrefix() : getPostMethodPrefix();

        //Get the method from servlet class which is named 'onXxx' as the method's value is 'xxx'.
        try {
            Method dispatcher = servletClass.getMethod(methodPrefix + StringUtils.capitalize(method),
                    HttpServletRequest.class, HttpServletResponse.class);

            //Invoke the method with request and response.
            if (logger.isDebugEnabled()) {
                logger.debug("Servlet Method: " + servletClass.getName() + '.' + dispatcher.getName());
            }
            dispatcher.invoke(this, req, res);
        }
        catch (NoSuchMethodException e) {
            handleException(req, res, new WebException(WebException.URL_NOT_FOUND, e));
        }
        catch (IllegalAccessException e) {
            handleException(req, res, new WebException(WebException.URL_NOT_FOUND, e));
        }
        catch (InvocationTargetException e) {
            //The original exception thrown by the dispatcher method, would be wrapped in the InvocationTargetException,
            //which can be get by getCause() method.
            Throwable cause = e.getCause();
            if (cause instanceof UndeclaredThrowableException) {
                handleException(req, res, cause.getCause());
            }
            else {
                handleException(req, res, cause);
            }
        }
    }

    /**
     * Guess the mode WebBeanValidator by method.
     *
     * @param method the method string from attribute of HttpServletRequest
     */
    private static int getWebValidatorMode(String method) {

        method = method.toLowerCase();

        if (method.indexOf("save") >= 0 || method.indexOf("create") >= 0) {
            return WebBeanValidator.MODE_CREATE;
        }
        else if (method.indexOf("update") >= 0) {
            return WebBeanValidator.MODE_UPDATE;
        }
        else {
            return WebBeanValidator.MODE_DEFAULT;
        }
    }

    /**
     * Get the method prefix to formulate the method name in {@code doPost()}, whose default value is '{@code on}'.
     * Override this method if needed.
     *
     * @return a string, default value is '{@code on}'
     */
    protected static String getPostMethodPrefix() {
        return DISPACHER_METHOD_PREFIX;
    }

    /**
     * Get the method prefix to formulate the method name in {@code doGet()}, whose default value is '{@code on}'.
     * Override this method if needed.
     *
     * @return a string, default value is '{@code on}'
     */
    protected static String getGetMethodPrefix() {
        return DISPACHER_METHOD_PREFIX;
    }

    /**
     * 将异常消息取出来后发送到显示错误提示的页面上. 如果 <code>e</code> 是一个 {@link I18NException} 实例，则自动就系统上下文环境的语言将该国际化异常翻译后再显示，否则则直接显 示
     * <code>e.getMessage()</code> 的内容。
     *
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param e   需要处理的异常对象
     */
    protected static void handleException(HttpServletRequest req, HttpServletResponse res, Throwable e)
            throws IOException, ServletException {

        if (localLogger.isDebugEnabled()) {
            localLogger.debug(e.getMessage(), e);
        }

        //Prepare a default OK_BACK button
        Object obj = req.getAttribute(Constants.MSG_BUTTON);
        if (obj == null) {
            List<Button> buttons = new ArrayList<Button>();
            buttons.add(Button.OK_BACK);
            req.setAttribute(Constants.MSG_BUTTON, buttons);
        }

        req.setAttribute(Constants.ERROR_MESSAGE, e);
        req.getRequestDispatcher(Constants.PAGE_EXCEPTION).forward(req, res);
    }

    /**
     * 将异常消息取出来后发送到显示错误提示的页面上，并用<code>button</code>指定一个消息框显示的按钮. 如果 <code>e</code> 是一个 {@link I18NException}
     * 实例，则自动就系统上下文环境的语言将该国际化异常翻译后再显示，否则则直接显 示 <code>e.getMessage()</code> 的内容。
     *
     * @param req    HttpServletRequest
     * @param res    HttpServletResponse
     * @param e      需要处理的异常对象
     * @param button 在消息框中显示的按钮
     */
    protected void handleExcpetion(HttpServletRequest req, HttpServletResponse res, Throwable e, Button button)
            throws IOException, ServletException {

        List<Button> buttons = new ArrayList<Button>();
        buttons.add(button);
        req.setAttribute(Constants.MSG_BUTTON, buttons);
        handleException(req, res, e);
    }

    /**
     * 将异常消息取出来后发送到显示错误提示的页面上，并用<code>buttons</code>指定一个消息框显示的按钮列表. 如果 <code>e</code> 是一个 {@link I18NException}
     * 实例，则自动就系统上下文环境的语言将该国际化异常翻译后再显示，否则则直接显 示 <code>e.getMessage()</code> 的内容。
     *
     * @param req     HttpServletRequest
     * @param res     HttpServletResponse
     * @param e       需要处理的异常对象
     * @param buttons 在消息框中显示的按钮列表，节点为Button实体。
     */
    protected void handleExcpetion(HttpServletRequest req, HttpServletResponse res, Throwable e, List<Button> buttons)
            throws IOException, ServletException {

        req.setAttribute(Constants.MSG_BUTTON, buttons);
        handleException(req, res, e);
    }

    /**
     * 处理国际化消息的显示.
     *
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param msg 需要显示的国际化消息
     */
    protected static void handleMessage(HttpServletRequest req, HttpServletResponse res, I18NMessage msg)
            throws IOException, ServletException {

        req.setAttribute(Constants.MESSAGE, msg);
        req.getRequestDispatcher(Constants.PAGE_MESSAGE).forward(req, res);
    }

    /**
     * 处理国际化消息的显示，并可指定一个在消息框中显示的按钮<code>button</code>.
     *
     * @param req    HttpServletRequest
     * @param res    HttpServletResponse
     * @param msg    需要显示的国际化消息
     * @param button 在消息框中显示的按钮
     */
    protected void handleMessage(HttpServletRequest req, HttpServletResponse res, I18NMessage msg, Button button)
            throws IOException, ServletException {

        List<Button> buttons = new ArrayList<Button>();
        buttons.add(button);
        req.setAttribute(Constants.MSG_BUTTON, buttons);
        handleMessage(req, res, msg);
    }

    /**
     * 处理国际化消息的显示，并可指定一个在消息框中显示的按钮列表<code>buttons</code>.
     *
     * @param req     HttpServletRequest
     * @param res     HttpServletResponse
     * @param msg     需要显示的国际化消息
     * @param buttons 在消息框中显示的按钮列表，节点为Button实体。
     */
    protected void handleMessage(HttpServletRequest req, HttpServletResponse res, I18NMessage msg, List<Button> buttons)
            throws IOException, ServletException {

        req.setAttribute(Constants.MSG_BUTTON, buttons);
        handleMessage(req, res, msg);
    }

    protected static void forwardI18NJsp(HttpServletRequest req, HttpServletResponse res, String jspName,
                                         Locale userLocale)
            throws IOException, ServletException, WebException {

        StringBuffer jspFileName = new StringBuffer(DEFAULT_I18N_RES);
        jspFileName.append(userLocale.toString()).append(jspName);

        File jspFile = new File(InitListener.getSystemRoot(), jspFileName.toString());
        if (!jspFile.exists()) {
            throw new WebException(WebException.URL_NOT_FOUND, new Object[]{jspFileName.toString()});
        }

        req.getRequestDispatcher(jspFileName.toString()).forward(req, res);
    }

    protected void forwardI18NJsp(HttpServletRequest req, HttpServletResponse res, String jspName)
            throws IOException, ServletException, WebException {

        forwardI18NJsp(req, res, jspName, getCurrentUserLocale(req));
    }

    /**
     * Get the locale of current user session.
     *
     * @param req HttpServletRequest, used to get HttpSession
     * @return the locale found in session.
     */
    protected static Locale getCurrentUserLocale(HttpServletRequest req) {
        return (Locale) req.getSession().getAttribute(Constants.USER_LOCALE_KEY);
    }

    /**
     * Prefix the specified <code>relativePath</code> with the current context name.
     *
     * @return a absolute path which is suitable to be sendRedirect().
     */
    protected static String getAbsolutePath(String relativePath) {
        if (relativePath.startsWith("/") && contextPath.endsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        return contextPath + relativePath;
    }

    /**
     * Get current signed-in user.
     *
     * @param session the session to get user from.
     * @return the user entity if found, null when no user has signed-in.
     */
    protected static Object getCurrentLogin(HttpSession session) {
        if (session.getAttribute(Constants.LOGIN_USER) == null) {
            return null;
        }

        return session.getAttribute(Constants.LOGIN_USER);
    }

    /**
     * Get current signed-in group user, or the groupId for the signed-in user.
     *
     * @param session the session to get group from.
     * @return null when nothing has signed-in, and the groupId for the group found.
     */
    protected static String getCurrentGroupId(HttpSession session) {
        if (session.getAttribute(Constants.LOGIN_GROUP) == null) {
            return null;
        }

        return (String) session.getAttribute(Constants.LOGIN_GROUP);
    }
    
    /**
     * 从请求的 Request 中解析 id 和 chk_xxx 的值，并构架一个链表返回. <p/>若 id 的值存在，则返回只有一个节点的链表，值为该 id 的值，否则链表为所有以 'chk_' 开头的属性的值.
     *
     * @return List<String>
     */
    protected static List<String> getRequestIds(HttpServletRequest req) throws WebException {
        return RequestUtils.getRequestIds(req);
    }
    
    protected static List<Long> getCheckedIds(HttpServletRequest req) throws WebException {
    	return RequestUtils.getCheckedIds(req);
    }
    
    /**
     * 返回请求中的 id 值，若 id 不存在，则返回第一个以 chk_ 开头的属性的值.
     *
     * @return 字符串
     * @exception EWWebException 找不到合适的 id.
     */
    protected static Long getRequestId(HttpServletRequest req) throws WebException {
    	String id = RequestUtils.getRequestId(req, "id");
        return Long.valueOf(id);
    }
    
    /**
     * 返回 request 中所有以 '{@code chk_}' 开头的属性值.
     *
     * @param req HttpServletRequest
     * @return 字符串链表
     */
    protected static List<String> getRequestListCheckedIds(HttpServletRequest req) {
        return RequestUtils.getRequestListCheckedIds(req);
    }
    
    /**
     * 返回 request 中所有指定参数名称的Id属性值.
     *
     * @param req HttpServletRequest
     * @return 字符串链表
     */
    protected static List<Long> getRequestSelectedIds(HttpServletRequest req, String parameter) {
    	return RequestUtils.getRequestSelectedIds(req, parameter);
    }

    /**
     * 用默认的字符集编码（UTF-8）编码指定的字符串，以便能够正确的作为 GET 参数使用。
     *
     * @param str 要编码的字符串
     * @return 编码过的字符串
     */
    protected static String urlEncode(String str) {
        return RequestUtils.urlEncode(str);
    }

    /**
     * 用默认的字符集编码（UTF-8）解码指定的字符串，以便能够得到正确的原始参数.
     *
     * @param str 要解码的字符串
     * @return 解码过的字符串
     */
    protected static String urlDecode(String str) {
       return RequestUtils.urlDecode(str);
    }    

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a POST request.
     * <p/>
     * 本方法将检查POST发起的URL和请求的URL主机（Host）是否一致，如果不一致，将不予处理请求，并显示请求的页面不存在。
     *
     * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param res an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @exception ServletException if the request for the POST could not be handled
     * @exception IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        //All Post request should been sent from current site, all an invasion is suspected.
        String referer = req.getHeader("referer");
        if (referer != null && referer.length() > 0) {
            URL requestURL = new URL(req.getRequestURL().toString());
            URL refererURL = new URL(referer);

            if (!requestURL.getHost().equals(refererURL.getHost())) {
                handleException(req, res, new WebException(WebException.URL_NOT_FOUND));
            }
        }

        doGet(req, res);
    }

    /**
     * 由 doGet() 方法分派器分派后的默认方法，所有子 Servlet 都必须实现该方法，以响应当无 method 参数的情况。
     *
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     */
    public abstract void onDefault(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, I18NException;

    /**
     * 该方法覆盖了 {@link GenericServlet#log(String)} 方法，实现数据库的日志存储.
     *
     * @param s 记录日志的消息
     */
    @Override
    public void log(String s) {
        localLogger.info(s);
    }

    /**
     * 该方法覆盖了 {@link GenericServlet#log(String, Throwable)} 方法，实现数据库的日志存储.
     *
     * @param s 记录日志的消息
     * @param t 抛出的对象，如 error, exception 等
     */
    @Override
    public void log(String s, Throwable t) {
        localLogger.info(s, t);
    }

    /**
     * 将一个国际化消息日志存入数据库
     *
     * @param msg 要记录的国际化消息
     */
    public void log(I18NMessage msg) {
        log(I18NDictionary.translate(msg));
    }

}