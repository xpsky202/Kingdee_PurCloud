package com.kingdee.purchase.destapi.alibaba.apihandler;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.alibaba.util.JSONUtil;
import com.kingdee.purchase.platform.exception.BaseException;


public class GetSupplierHandler extends AbstractDestApiHandler {

	@Override
	protected String getApiName() {
		return "caigou.api.supplier.getSupplier";
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
	 * 返回结果处理
	 * @param value
	 * @return
	 */
	protected JSONObject handleResult(JSONObject value) throws BaseException {
		JSONObject result = new JSONObject();
		result.element("supplier", JSONUtil.json2SupplierInfo(value).toJSONObject());
		
		return result;
	}

}
