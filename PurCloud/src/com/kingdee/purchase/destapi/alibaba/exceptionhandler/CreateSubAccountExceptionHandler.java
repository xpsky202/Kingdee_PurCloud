package com.kingdee.purchase.destapi.alibaba.exceptionhandler;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

public class CreateSubAccountExceptionHandler extends BaseExceptionHandler {
	
	public static final String ERROR_CODE_CONFLICT_MEMBER = "ISV.CONFLICT_MEMBER";
	
	@Override
	public BaseException buildException(String errorCode, String errorMessage) {
		BaseException exception = null;
		if (errorCode.equals(ERROR_CODE_CONFLICT_MEMBER)) {
			exception = new PurBizException(PurExceptionDefine.ALI_ACCOUNT_CONFLICT);
		}
		
		return exception;
	}

}
