package com.kingdee.purchase.destapi.alibaba.apihandler;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.alibaba.util.JSONUtil;
import com.kingdee.purchase.platform.exception.BaseException;

public class GetBuyOfferListByPrIdHandler extends AbstractDestApiHandler {

	@Override
	protected String getApiName() {
		return "caigou.api.buyOffer.queryBuyOfferByPrId";
	}
	
	/**
	 * 返回结果处理
	 * @param value
	 * @return
	 */
	protected JSONObject handleResult(JSONObject value) throws BaseException {
		JSONObject result = new JSONObject();
		result.element("buyOfferList", JSONUtil.json2BuyOfferList(value).toJSONArray());
		
		return result;
	}

}
