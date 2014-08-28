package com.kingdee.purchase.destapi.alibaba.apihandler;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.alibaba.AlibabaApiCallService;
import com.kingdee.purchase.destapi.alibaba.InvokeContext;
import com.kingdee.purchase.destapi.alibaba.util.JSONUtil;
import com.kingdee.purchase.openapi.model.ObjectCollection;
import com.kingdee.purchase.openapi.model.QuotationInfo;
import com.kingdee.purchase.platform.exception.BaseException;

public class GetQuotationListByBuyOffIdHandler extends AbstractDestApiHandler {

	@Override
	protected String getApiName() {
		return "caigou.api.quotation.buyerGetQuotationListByBuyOfferId";
	}
	
	/**
	 * 返回结果处理
	 * @param value
	 * @return
	 */
	protected JSONObject handleResult(JSONObject value) throws BaseException {
		ObjectCollection<QuotationInfo> coll = JSONUtil.json2QuotationList(value);
		QuotationInfo info = null;
		Map<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < coll.size(); i++) {
			info = coll.getItem(i);
			InvokeContext ctx = this.getInvokeContext();
			ctx.setApiName("caigou.api.supplier.getSupplier");
			params.clear();
			params.put("memberId", info.getSupplierMemberId());
			ctx.setParams(params);
			ctx.setHandler(null);
			JSONObject result = AlibabaApiCallService.callApi(ctx);
			info.setSupplier(JSONUtil.json2SupplierInfo(result));
		}
		
		JSONObject result = new JSONObject();
		result.element("quotationList", coll.toJSONArray());
		
		return result;
	}

}
