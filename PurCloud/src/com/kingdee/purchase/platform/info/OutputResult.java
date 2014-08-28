package com.kingdee.purchase.platform.info;

import net.sf.json.JSONObject;

/**
 * 云服务输出结果，如果sucess成功返回，则携带数据，否则返回错误代码和原因
 * 
 * @author RD_cary_lin
 *
 */
public class OutputResult {
	
	public static final String ISSUCCESS = "isSuccess";
	public static final String DATA = "data";
	
	
	private SucessEnum success;
	
	private int errorCode;
	
	private String errormessage;
	
	private JSONObject data;

	public SucessEnum getSuccess() {
		return success;
	}

	public void setSuccess(SucessEnum success) {
		this.success = success;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
	
	/**
	 * 转换位jsonobject
	 * @return
	 */
	public String toJSONString() {
		JSONObject jsonObject  = new JSONObject();
		jsonObject.element(ISSUCCESS, this.success.getValue());
		jsonObject.element(DATA, this.data);
		
		return jsonObject.toString();
	}
	
}
