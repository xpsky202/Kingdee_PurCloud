package com.kingdee.purchase.platform.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public final class SpringContextUtils {

	private SpringContextUtils(){}
	
	private static WebApplicationContext wac;
	static{
		wac = ContextLoader.getCurrentWebApplicationContext();
	}
	
	public static <T> T getBean(String beanName,Class<T> claazz){
		return wac.getBean(beanName, claazz);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return wac.getBean(clazz);
	}
	
	public static Object getBean(String name){
		return wac.getBean(name);
	}
}