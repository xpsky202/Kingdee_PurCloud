package com.kingdee.purchase.openapi.client;

import com.kingdee.purchase.openapi.client.exception.PurCloudException;

/**
 * 业务异常反调接口
 * @author RD_sky_lv
 *
 */
public interface ErrorHandler {
	
	void handle(PurCloudException targetException);
}
