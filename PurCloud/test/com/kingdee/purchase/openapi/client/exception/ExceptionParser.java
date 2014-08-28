package com.kingdee.purchase.openapi.client.exception;

import net.sf.json.JSONObject;

public class ExceptionParser {
	
	public static final PurCloudException buildException(String exception) {
		JSONObject json = JSONObject.fromObject(exception);
		String errorCode = json.optString("errorCode");
		String errorMessage = json.optString("errorMessage");
		
		return new PurCloudException(errorCode, errorMessage);
	}

}
