package com.kingdee.purchase.destapi;

import com.kingdee.purchase.platform.exception.BaseException;

/**
 * 调用目标API异常结果处理接口
 * @author RD_sky_lv
 *
 */
public interface IDestApiExceptionHandler {
	
	public BaseException buildException(Object exception);

}
