package com.kingdee.purchase.platform.info.msg;

import com.kingdee.purchase.platform.service.SystemParamsServiceFactory;
import com.kingdee.purchase.platform.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * 修改询价单
 * @author RD_jiangkun_zhu
 *
 */
public class BuyerModifyBuyofferMsg extends MessageInfo {

	public BuyerModifyBuyofferMsg(){
		super();
		setType(MsgTypeEnum.BUYER_MODIFY_BUYOFFER);
	}
	@Override
	public void initData(JSONObject json) {
		getData().put("buyOfferId", json.getString("buyOfferId"));
		getData().put("sourceBuyOfferId", json.getString("sourceBuyOfferId"));
		getData().put("prId", json.getString("prId"));
//		String accountId = json.getString("userId");
//		String userId = SystemParamsServiceFactory.getServiceInstance4Alibaba().getERPUserId(accountId);
//		getData().put("userId", userId);
		
		String subAccountId = json.optString("subUserId");
		if(StringUtils.isNotEmpty(subAccountId)){
			String userId = SystemParamsServiceFactory.getServiceInstance4Alibaba().getERPUserId(subAccountId);
			getData().put("userId", userId);			
		}
		
		String userId = json.optString("userId");
		if(StringUtils.isNotEmpty(userId)){
			getData().put("userId", userId);
		}
	}
}