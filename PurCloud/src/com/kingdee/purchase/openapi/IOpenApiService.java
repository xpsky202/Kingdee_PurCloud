package com.kingdee.purchase.openapi;

import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;

public interface IOpenApiService {

	public JSONObject process(RequestContext context) throws BaseException;
}
