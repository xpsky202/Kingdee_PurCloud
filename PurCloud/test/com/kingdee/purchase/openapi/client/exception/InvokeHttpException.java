package com.kingdee.purchase.openapi.client.exception;

public class InvokeHttpException extends PurCloudException {

	private static final long serialVersionUID = -3337956670822128206L;

	public InvokeHttpException() {
		super();
		initDefaultCode();
	}
	
	public InvokeHttpException(String message) {
		super(message);
		initDefaultCode();
		this.errorMessage = message;
	}
	
	private void initDefaultCode() {
		this.errorCode = "2001";
	}

}
