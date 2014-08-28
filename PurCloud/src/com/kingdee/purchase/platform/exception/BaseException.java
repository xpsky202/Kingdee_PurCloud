package com.kingdee.purchase.platform.exception;

import net.sf.json.JSONObject;

/**
 * 采购云平台异常基类
 * @author RD_sky_lv
 *
 */

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public static final String ERROR_CODE = "errorCode";
	public static final String ERROR_MESSAGE = "errorMessage";
	
	protected int errorCode;   //错误码
	
	public BaseException() {
		super();
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(Throwable cause) {
		super(cause);
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public String toJSONString() {
		JSONObject jsonObject  = new JSONObject();
		jsonObject.element(ERROR_CODE, errorCode);
		jsonObject.element(ERROR_MESSAGE, this.getMessage());
		
		return jsonObject.toString();
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsonObject  = new JSONObject();
		jsonObject.element(ERROR_CODE, errorCode);
		jsonObject.element(ERROR_MESSAGE, this.getMessage());
		
		return jsonObject;
	}
}
