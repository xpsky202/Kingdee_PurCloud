package com.kingdee.purchase.openapi.client.exception;

public class PurCloudException extends Exception {
	
	private static final long serialVersionUID = 8746165788591418359L;
	protected String errorCode;
	protected String errorMessage;
	
	public PurCloudException() {
		super();
	}
	
	public PurCloudException(String code) {
		super();
		this.errorCode = code;
	}
	
	public PurCloudException(String code, String message) {
		super(message);
		this.errorCode = code;
		this.errorMessage = message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
