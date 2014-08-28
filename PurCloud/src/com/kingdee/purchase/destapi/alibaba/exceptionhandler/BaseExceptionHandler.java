package com.kingdee.purchase.destapi.alibaba.exceptionhandler;

import net.sf.json.JSONObject;

import com.alibaba.openapi.client.exception.OceanException;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

public class BaseExceptionHandler implements IAliExceptionHandler {
	
	public static final String ERROR_CODE = "error_code";
	public static final String ERROR_MESSAGE = "error_message";
	public static final String ERROR_CODE_INVALID_PARAM = "ISV.INVALID_PARAM";
	public static final String ERROR_CODE_DATA_PRIVILEGE_ILLEGAL = "ISV.DATA_PRIVILEGE_ILLEGAL";
	public static final String ERROR_CODE_SYSTEM_ERROR = "ISP.SYSTEM_ERROR";
	public static final String ERROR_CODE_DATA_NOT_EXIST = "ISV.DATA_NOT_EXIST";
	
	protected boolean isHandled;
	protected String errorCode;
	protected String errorMessage;
	
	public BaseException buildException(Object exception) {
		if (!isHandled) {
			JSONObject tmpException = (JSONObject)exception;
			if (tmpException == null || tmpException.isEmpty()) {
				return new PurBizException(PurExceptionDefine.ALI_NULL_RESULT);
			}
			errorCode = tmpException.optString(ERROR_CODE);
			errorMessage = tmpException.optString(ERROR_MESSAGE);
		}
		BaseException result = buildCommonException(errorCode, errorMessage);
		if (result == null) {
			if (isHandled) {
				result = new PurBizException(PurExceptionDefine.ALI_UNKNOW_ERROR, new String[] {errorCode, errorMessage});
			} else {
				result = new PurBizException(PurExceptionDefine.ALI_UNKNOW_RESULT, new String[] {exception.toString()});
			}
		}
		
		return result;
	}
	
	/**
	 * 处理通用异常
	 * @param errorCode
	 * @param errorMessage
	 * @return
	 */
	private BaseException buildCommonException(String errorCode, String errorMessage) {
		BaseException exception = null;
		if (errorCode.equals("100")){
			exception = new PurBizException(PurExceptionDefine.ALI_APIDEF_ERROR);
		} else if (errorCode.equals("400")){
			exception = new PurBizException(PurExceptionDefine.ALI_INVALID_SIGN);
		} else if (errorCode.equals("401")){
			exception = new PurBizException(PurExceptionDefine.ALI_NEED_AUTH);
		} else if (errorCode.equals("403")){
			exception = new PurBizException(PurExceptionDefine.ALI_EXCEED_APP_TIME);
		} else if (errorCode.equals("404")){
			exception = new PurBizException(PurExceptionDefine.ALI_UNSUPPORT_API);
		} else if (errorCode.equals("500")){
			exception = new PurBizException(PurExceptionDefine.ALI_INVOKE_TARGET);
		} else if (errorCode.equals("502")){
			exception = new PurBizException(PurExceptionDefine.ALI_CONNECT_ERROR);
		} else if (errorCode.equals("504")){
			exception = new PurBizException(PurExceptionDefine.ALI_INVOKE_TIMEOUT);
		} else if (errorCode.equals(ERROR_CODE_DATA_PRIVILEGE_ILLEGAL)) {
			exception = new PurBizException(PurExceptionDefine.ALI_NO_PRIVILEGE);
		} else if (errorCode.equals(ERROR_CODE_INVALID_PARAM)) {
			exception = new PurBizException(PurExceptionDefine.ALI_INVALID_PARAM, new String[]{errorMessage});
		} else if (errorCode.equals(ERROR_CODE_SYSTEM_ERROR)) {
			exception = new PurBizException(PurExceptionDefine.ALI_SYSTEM_ERROR, errorMessage);
		} else if (errorCode.equals(ERROR_CODE_DATA_NOT_EXIST)){
			exception = new PurBizException(PurExceptionDefine.DATA_NOT_EXIST);
		} else {
			exception = buildException(errorCode, errorMessage);
		}
		
		return exception;
	}
	
	public void handle(OceanException exception) {
		errorCode = exception.getErrorCode();
		errorMessage = exception.getMessage();
		
		isHandled = true;
	}
	
	/**
	 * 是否有异常需要处理
	 * @return
	 */
	public boolean isHasException() {
		return isHandled;
	}
	
	/**
	 * 构建子类特有异常信息，子类重载
	 * @param errorCode
	 * @param errorMessage
	 * @return
	 */
	protected BaseException buildException(String errorCode, String errorMessage) {
		return new PurBizException(PurExceptionDefine.ALI_UNKNOW_ERROR, new String[] {errorCode, errorMessage});
	}

}
