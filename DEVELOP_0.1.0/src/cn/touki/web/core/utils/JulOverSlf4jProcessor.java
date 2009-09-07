package cn.touki.web.core.utils;

import javax.annotation.PostConstruct;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * 在Spring ApplicationContext中初始化Slf4对Java.util.logging的拦截.
 * 
 * @author Liyi
 */
public class JulOverSlf4jProcessor {

	//Spring在所有属性注入后自动执行的函数.
	@PostConstruct
	public void init() {
		SLF4JBridgeHandler.install();
	}
}
