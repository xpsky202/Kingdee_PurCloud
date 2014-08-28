package com.kingdee.purchase.platform.info.msg;

import com.kingdee.purchase.platform.service.SystemParamsServiceFactory;
import com.kingdee.purchase.platform.util.StringUtils;

import net.sf.json.JSONObject;

/***
 * 发布询价单
 * @author RD_jiangkun_zhu
 *
 */
public class BuyerPublishBuyofferMsg extends MessageInfo {

	public BuyerPublishBuyofferMsg(){
		super();
		setType(MsgTypeEnum.BUYER_PUBLISH_BUYOFFER);
	}

	@Override
	public void initData(JSONObject json) {
		getData().put("buyOfferId", json.getString("buyOfferId"));
		getData().put("prId", json.getString("prId"));
		String subAccountId = json.optString("subUserId");
		if(StringUtils.isNotEmpty(subAccountId)){
			String userId = SystemParamsServiceFactory.getServiceInstance4Alibaba().getERPUserId(subAccountId);
			getData().put("userId", userId);			
		}
		
		String userId = json.optString("userId");
		if(StringUtils.isNotEmpty(userId)){
			getData().put("userId", userId);
		}
		//Mappping
	}
}