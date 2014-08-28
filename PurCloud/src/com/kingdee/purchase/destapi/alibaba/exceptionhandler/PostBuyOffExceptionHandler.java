package com.kingdee.purchase.destapi.alibaba.exceptionhandler;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

public class PostBuyOffExceptionHandler extends BaseExceptionHandler {
	
	public static final String ERROR_CODE_EXCEED_BUYOFFER_LIMIT_AMOUNT = "ISV.EXCEED_BUYOFFER_LIMIT_AMOUNT";
	public static final String ERROR_CODE_USER_IN_BLACK_LIST = "ISV.USER_IN_BLACK_LIST";
	
	@Override
	protected BaseException buildException(String errorCode, String errorMessage) {
		BaseException exception = null;
		if (errorCode.equals(ERROR_CODE_EXCEED_BUYOFFER_LIMIT_AMOUNT)) {
			exception = new PurBizException(PurExceptionDefine.EXCEED_BUYOFFER_TIMELIMIT);
		} else if (errorCode.equals(ERROR_CODE_USER_IN_BLACK_LIST)) {
			exception = new PurBizException(PurExceptionDefine.USER_IN_BLACK_LIST);
		}
		
		return exception;
	}

}
