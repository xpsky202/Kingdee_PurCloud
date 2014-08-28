package com.kingdee.purchase.destapi.alibaba.apihandler;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.alibaba.util.JSONUtil;
import com.kingdee.purchase.platform.exception.BaseException;

public class GetQuotationByIdHandler extends AbstractDestApiHandler {

	@Override
	protected String getApiName() {
		return "caigou.api.quotation.buyerGetQuotationDetail";
	}
	
	/**
	 * 返回结果处理
	 * @param value
	 * @return
	 */
	protected JSONObject handleResult(JSONObject value) throws BaseException {
		JSONObject result = new JSONObject();
		result.element("quotation", JSONUtil.json2QuotationInfo(value, true).toJSONObject());
		
		return result;
	}

}
