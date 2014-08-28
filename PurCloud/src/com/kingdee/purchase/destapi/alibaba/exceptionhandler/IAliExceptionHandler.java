package com.kingdee.purchase.destapi.alibaba.exceptionhandler;

import com.alibaba.openapi.client.ErrorHandler;
import com.kingdee.purchase.destapi.IDestApiExceptionHandler;

public interface IAliExceptionHandler extends IDestApiExceptionHandler, ErrorHandler {
	
	public boolean isHasException();

}
