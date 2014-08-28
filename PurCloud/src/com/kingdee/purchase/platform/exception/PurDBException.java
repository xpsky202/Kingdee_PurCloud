package com.kingdee.purchase.platform.exception;

import java.text.MessageFormat;

public class PurDBException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3324257200606344049L;
	
	protected void initDefaultErrorCode() {
		this.errorCode = 500;
	}
	
	public PurDBException() {
		super();
		initDefaultErrorCode();
	}
	
	public PurDBException(int errorCode) {
		super(PurExceptionDefine.getErrorMessage(errorCode));
		this.errorCode = errorCode;
	}
	
	public PurDBException(int errorCode, Object[] params) {
		super(MessageFormat.format(PurExceptionDefine.getErrorMessage(errorCode), params));
		this.errorCode = errorCode;
	}
	
	public PurDBException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public PurDBException(String message) {
		super(message);
		initDefaultErrorCode();
	}
	
	public PurDBException(Throwable cause) {
		super(cause);
		initDefaultErrorCode();
	}
	
	public PurDBException(String message, Throwable cause) {
		super(message, cause);
		initDefaultErrorCode();
	}
}
