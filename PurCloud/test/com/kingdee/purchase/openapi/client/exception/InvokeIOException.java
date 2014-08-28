package com.kingdee.purchase.openapi.client.exception;

public class InvokeIOException extends PurCloudException {
	
	private static final long serialVersionUID = -2959525371736988483L;

	public InvokeIOException() {
		super();
		initDefaultCode();
	}
	
	public InvokeIOException(String message) {
		super(message);
		initDefaultCode();
		this.errorMessage = message;
	}
	
	private void initDefaultCode() {
		this.errorCode = "3001";
	}

}
