package com.kingdee.purchase.destapi.alibaba.apihandler;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.alibaba.AlibabaApiCallService;
import com.kingdee.purchase.destapi.alibaba.InvokeContext;
import com.kingdee.purchase.destapi.alibaba.util.JSONUtil;
import com.kingdee.purchase.openapi.model.QuotationInfo;
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
		QuotationInfo quotation = JSONUtil.json2QuotationInfo(value, true);
		
		Map<String, Object> params = new HashMap<String, Object>();
		InvokeContext ctx = this.getInvokeContext();
		ctx.setApiName("caigou.api.supplier.getSupplier");
		params.clear();
		params.put("memberId", quotation.getSupplierMemberId());
		ctx.setParams(params);
		ctx.setHandler(null);
		JSONObject supplier = AlibabaApiCallService.callApi(ctx);
		quotation.setSupplier(JSONUtil.json2SupplierInfo(supplier));
		
		JSONObject result = new JSONObject();
		result.element("quotation", quotation.toJSONObject());
		
		return result;
	}

}
