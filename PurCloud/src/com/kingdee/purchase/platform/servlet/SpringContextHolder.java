package com.kingdee.purchase.platform.servlet;

import javax.servlet.ServletContext;

public final class SpringContextHolder {
	
	private SpringContextHolder(){}

	private static ServletContext sc;
	
	static void setServletContext(ServletContext sc){
		SpringContextHolder.sc = sc;
	}
	
	public static ServletContext getServletContext(){
		return SpringContextHolder.sc;
	}
}
