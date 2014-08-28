package com.kingdee.purchase.platform.exception;

import java.text.MessageFormat;

public class PurSysException extends BaseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void initDefaultErrorCode() {
		this.errorCode = 400;
	}

	public PurSysException() {
		super();
		initDefaultErrorCode();
	}
	
	public PurSysException(int errorCode) {
		super(PurExceptionDefine.getErrorMessage(errorCode));
		this.errorCode = errorCode;
	}
	
	public PurSysException(int errorCode, Object[] params) {
		super(MessageFormat.format(PurExceptionDefine.getErrorMessage(errorCode), params));
		this.errorCode = errorCode;
	}
	
	public PurSysException(String message) {
		super(message);
		initDefaultErrorCode();
	}
	
	public PurSysException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public PurSysException(Throwable cause) {
		super(cause);
		initDefaultErrorCode();
	}
	
	public PurSysException(String message, Throwable cause) {
		super(message, cause);
		initDefaultErrorCode();
	}

}
