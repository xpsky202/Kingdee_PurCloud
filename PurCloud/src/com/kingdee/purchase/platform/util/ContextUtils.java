package com.kingdee.purchase.platform.util;

import javax.servlet.http.HttpServletRequest;

import com.kingdee.purchase.platform.info.AccountInfo;
import com.kingdee.purchase.platform.servlet.WebConstant;

public final class ContextUtils {
	private ContextUtils(){}
	
	public static final AccountInfo getCurrentAccount(HttpServletRequest request){
		return (AccountInfo) request.getSession(true).getAttribute(WebConstant.SESSION_KEY_USER);
	}
}