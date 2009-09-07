package cn.touki.web.core.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.touki.web.core.servlet.Constants;

/**
 * The initializating Listener When Web Application Starts.
 * 
 * @author Liyi
 * 
 * 本监听器在 Servlet 容器启动时，自动加载 Spring 框架的 BeanFactory，并将其设置到 ServletContext 的属性中，所有的 Servlet 将在 init() 方法中获取该属性，以供需要时调用。
 * 
 * 在 Webapp 退出的时候，本监听器会先断开与 Socket Server 的连接，然后清空整个 Spring 容器。
 *
 */
public class InitListener implements ServletContextListener {

    // Properties
    private Logger logger = LoggerFactory.getLogger(InitListener.class);

    private WebApplicationContext wac;

    private ContextLoader contextLoader = new ContextLoader();

    private String appName;

    /**
     * WEBAPP's ROOT Path.
     */
    private static String systemRoot;

    
    // Constructor

    public InitListener() {
        super();
    }


    // Methods

    public void contextInitialized(ServletContextEvent event) {
        // Get the root path of webapp
        ServletContext sc = event.getServletContext();

        systemRoot = sc.getRealPath("/");
        appName = sc.getInitParameter("APP_NAME");

        if (logger.isDebugEnabled()) {
            logger.debug("当前 WebApp (" + appName + ") 的 systemRoot: " + getSystemRoot());
        }

        // Get the beanFactory of Spring Framework
        logger.info("正在初始化 WebApplicationContext...");
        try {
            wac = contextLoader.initWebApplicationContext(sc);
        } catch (Throwable t) {
            logger.error("WebApp (" + appName + ") 初始化失败！无法获得 WebApplicationContext，请检查 Spring Framework 配置！", t);
            System.exit(0);
        }

        // 将beanFactory设置到Context属性中，以便Servlet使用。
        sc.setAttribute(Constants.WAC_ATTRIB, wac);
        logger.info("WebApp (" + appName + ") 初始化成功！");
    }

    public void contextDestroyed(ServletContextEvent event) {

        contextLoader.closeWebApplicationContext(event.getServletContext());
        logger.info("WebApp (" + appName + ") 退出！");
    }

    public static String getSystemRoot() {
        return systemRoot;
    }
}
