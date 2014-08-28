package com.kingdee.purchase.destapi;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.factory.DestApiFactoryBuilder;
import com.kingdee.purchase.destapi.factory.IDestApiFactory;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;

/**
 * 目标系统API调用服务
 * @author RD_sky_lv
 *
 */

public class DestApiInvokeService {
	
	/**
	 * 调用目标系统服务
	 * @param ctx
	 * @return
	 * @throws BaseException
	 */
	public static JSONObject invoke(RequestContext ctx) throws BaseException {
		//根据目标类型生成工厂
		IDestApiFactory factory = DestApiFactoryBuilder.getFactory(1);
		//获取URL请求中的apiName和version
		String apiName = ctx.getLocateParamInfo().getApiName();
		int version = Integer.parseInt(ctx.getLocateParamInfo().getVersion());
		
		//利用apiName和version进行mapping得到Alibaba等系统的对应的api
		return factory.getApiHandler(apiName, version).invokeHandle(ctx);
	}

}
