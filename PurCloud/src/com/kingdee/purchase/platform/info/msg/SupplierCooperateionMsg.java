package com.kingdee.purchase.platform.info.msg;

import net.sf.json.JSONObject;

public class SupplierCooperateionMsg extends MessageInfo {
	
	public SupplierCooperateionMsg(){
		super();
		setType(MsgTypeEnum.CREATE_COOPERATEION);
	}

	@Override
	public void initData(JSONObject json) {
		
		getData().put("supplierMemberId",json.optString("supplierMemberId"));
		getData().put("externalId",json.optString("externalId"));

	}

}
