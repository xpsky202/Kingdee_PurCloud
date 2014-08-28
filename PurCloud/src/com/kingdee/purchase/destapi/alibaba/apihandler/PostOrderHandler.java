package com.kingdee.purchase.destapi.alibaba.apihandler;

import com.alibaba.openapi.client.ErrorHandler;
import com.kingdee.purchase.destapi.alibaba.exceptionhandler.PostOrderExceptionHandler;

/**
 * 上传采购订单API处理类
 * @author RD_sky_lv
 *
 */

public class PostOrderHandler extends AbstractDestApiHandler {

	/**
	 * 获取api名称
	 * @return
	 */
	@Override
	protected String getApiName() {
		return "caigou.api.order.postOrderInfo";
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
