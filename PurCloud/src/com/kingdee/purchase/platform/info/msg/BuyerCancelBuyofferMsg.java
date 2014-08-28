package com.kingdee.purchase.platform.info.msg;

import net.sf.json.JSONObject;

/***
 * 关闭询价单
 * @author RD_jiangkun_zhu
 *
 */
public class BuyerCancelBuyofferMsg extends MessageInfo {

	public BuyerCancelBuyofferMsg(){
		super();
		setType(MsgTypeEnum.BUYER_CANCEL_BUYOFFER);
	}
	
	@Override
	public void initData(JSONObject json) {
		getData().put("buyOfferId",json.getString("buyOfferId"));
		getData().put("prId",json.getString("prId"));
	}
}