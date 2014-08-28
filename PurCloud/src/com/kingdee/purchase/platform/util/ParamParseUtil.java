package com.kingdee.purchase.platform.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public final class ParamParseUtil {
	private ParamParseUtil(){}
	
	public static int getInt(HttpServletRequest request,String name){
		return getInt(request, name, 0);
	}
	
	public static int getInt(HttpServletRequest request,String name,int defaultVal){
		if(StringUtils.isEmpty(name)){
			return defaultVal;
		}
		
		String string = request.getParameter(name);
		try{
			return Integer.parseInt(string);
		}catch(Exception e){}
		
		return defaultVal;
	}
	
	public static long getLong(HttpServletRequest request,String name){
		return getLong(request, name, 0);
	}
	
	public static long getLong(HttpServletRequest request,String name,long defaultVal){
		if(StringUtils.isEmpty(name)){
			return defaultVal;
		}
		
		String string = request.getParameter(name);
		try{
			return Long.parseLong(string);
		}catch(Exception e){}
		
		return defaultVal;
	}
	
	
	public static String getString(HttpServletRequest request,String name){
		return getString(request, name, null);
	}
	
	public static String getString(HttpServletRequest request,String name,String defaultVal){
		if(StringUtils.isEmpty(name)){
			return defaultVal;
		}
		
		return request.getParameter(name);
	}
	
	/**
	 * 解析requestMap中的参数，其中可能包括queryString和setParameter()中的参数
	 * @param request
	 * @return
	 */
	public static Map<String, String[]> getParameters4Register(HttpServletRequest request) {
		Enumeration<?> paramNames = request.getParameterNames();
		Map<String, String[]> params = new HashMap<String, String[]>();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String values[] = request.getParameterValues(paramName);
			
			String decodeValue[] = new String[values.length];
			for(int i= 0, size = values.length; i< size; i++){
				decodeValue[i] = StringUtils.urlDecode(values[i]);
			}
			
			if (values != null && values.length > 0)
				params.put(paramName, decodeValue);
		}
		
		return params;
	}
	
	
	/**
	 * 解析requestMap中的参数，其中可能包括queryString和setParameter()中的参数
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParameters(HttpServletRequest request) {
		Enumeration<?> paramNames = request.getParameterNames();
		Map<String, Object> params = new HashMap<String, Object>();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String values[] = request.getParameterValues(paramName);
			if (values != null && values.length > 0)
				params.put(paramName, StringUtils.urlDecode(values[0]));
		}
		
		return params;
	}

	/**
	 * 解析queryString like "employeeId=1&items=1" 为map
	 * @param paramStr
	 * @return
	 */
	public static Map<String, Object> getRequestParams(String paramStr) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (paramStr == null || "".equals(paramStr)) {
			return params;
		}
		
		String[] conditions = paramStr.split("&");
		String key = null;
		for (String condition : conditions) {
			key = condition.substring(0, condition.indexOf("="));
			if (!params.containsKey(key)) {
				params.put(key, StringUtils.urlDecode(condition.substring(condition.indexOf("=") + 1)));
			}
		}

		return params;
	}
}
