package com.kingdee.purchase.destapi.alibaba.apihandler;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.alibaba.util.JSONUtil;
import com.kingdee.purchase.platform.exception.BaseException;

public class GetBuyOfferByIdHandler extends AbstractDestApiHandler {

	@Override
	protected String getApiName() {
		return "caigou.api.buyOffer.getBuyOfferById";
	}
	
	/**
	 * 返回结果处理
	 * @param value
	 * @return
	 */
	protected JSONObject handleResult(JSONObject value) throws BaseException {
		JSONObject result = new JSONObject();
		result.element("buyOffer", JSONUtil.json2BuyOffInfo(value, true).toJSONObject());
		
		return result;
	}

}
