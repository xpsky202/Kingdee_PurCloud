package com.kingdee.purchase.destapi;

import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;

/**
 * 调用目标系统API处理接口
 * @author RD_sky_lv
 *
 */

public interface IDestApiHandler {
	
	public JSONObject invokeHandle(RequestContext ctx) throws BaseException;

}
