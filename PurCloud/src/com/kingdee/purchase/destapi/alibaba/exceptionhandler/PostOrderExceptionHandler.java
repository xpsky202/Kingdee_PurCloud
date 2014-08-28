package com.kingdee.purchase.destapi.alibaba.exceptionhandler;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

public class PostOrderExceptionHandler extends BaseExceptionHandler {
	
	public static final String ERROR_CODE_DUPLICATE_INSERT = "ISV.DUPLICATE_INSERT";
	public static final String ERROR_CODE_DATA_NOT_EXIST = "ISV.DATA_NOT_EXIST";
	

	@Override
	protected BaseException buildException(String errorCode, String errorMessage) {
		BaseException exception = null;
		if (errorCode.equals(ERROR_CODE_DUPLICATE_INSERT)) {
			exception = new PurBizException(PurExceptionDefine.DUPLICATE_INSERT);
		}
		
		return exception;
	}
}
