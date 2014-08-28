package com.kingdee.purchase.platform.info.msg;

import net.sf.json.JSONObject;

/****
 * 供应商撤销认证
 * @author RD_jiangkun_zhu
 *
 */
public class SupplierCancelQuotationMsg extends MessageInfo {

	public SupplierCancelQuotationMsg(){
		super();
		setType(MsgTypeEnum.SUPPLIER_CANCEL_QUOTATION);
	}

	@Override
	public void initData(JSONObject json) {
		getData().put("quotationId", json.getString("quotationId"));
		getData().put("buyOfferId", json.getString("buyOfferId"));
		getData().put("prId", json.getString("prId"));
		getData().put("supplierMemberId", json.getString("supplierMemberId"));
	}	
}