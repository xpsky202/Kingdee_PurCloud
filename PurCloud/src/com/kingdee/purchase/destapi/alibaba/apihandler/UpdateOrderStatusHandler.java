package com.kingdee.purchase.destapi.alibaba.apihandler;

import com.alibaba.openapi.client.ErrorHandler;
import com.kingdee.purchase.destapi.alibaba.exceptionhandler.PostOrderExceptionHandler;

public class UpdateOrderStatusHandler extends AbstractDestApiHandler {

	@Override
	protected String getApiName() {
		return "caigou.api.order.updateOrderStatus";
	}
	
	/**
	 * 获取api版本
	 * @return
	 */
	@Override
	protected int getApiVersion() {
		return 1;
	}
	
	/**
	 * 获取api异常处理器
	 * @return
	 */
	@Override
	protected ErrorHandler getApiErrorHandler() {
		return new PostOrderExceptionHandler();
	}

}
