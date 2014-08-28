package com.kingdee.purchase.platform.exception;

import java.text.MessageFormat;

/**
 * 业务异常
 * @author RD_jiangkun_zhu
 *
 */
public class PurBizException  extends BaseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7424855402618584086L;
	
	protected void initDefaultErrorCode() {
		this.errorCode = 600;
	}
	
	public PurBizException() {
		super();
		initDefaultErrorCode();
	}
	
	public PurBizException(int errorCode) {
		super(PurExceptionDefine.getErrorMessage(errorCode));
		this.errorCode = errorCode;
	}
	
	public PurBizException(int errorCode, Object[] params) {
		super(MessageFormat.format(PurExceptionDefine.getErrorMessage(errorCode), params));
		this.errorCode = errorCode;
	}
	
	public PurBizException(String message) {
		super(message);
		initDefaultErrorCode();
	}
	
	public PurBizException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public PurBizException(Throwable cause) {
		super(cause);
		initDefaultErrorCode();
	}
	
	public PurBizException(int errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public PurBizException(String message, Throwable cause) {
		super(message, cause);
		initDefaultErrorCode();
	}
	
	public PurBizException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
}
