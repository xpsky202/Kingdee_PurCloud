package com.kingdee.purchase.platform.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class StringUtils {
	
	private StringUtils(){}

	public final static boolean isEmpty(String s){
		return s == null || s.trim().length()==0;
	}
	
	public final static boolean isNotEmpty(String s){
		return s!=null && s.trim().length()!=0;
	}
	
	public final static String urlEncode(String s){
		String encodeStr = null;
		try {
			encodeStr = URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
		return encodeStr;
	}
	
	public final static String urlDecode(String s){
		String decodeStr = null;
		try {
			decodeStr = URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
		
		return decodeStr;
	}
}