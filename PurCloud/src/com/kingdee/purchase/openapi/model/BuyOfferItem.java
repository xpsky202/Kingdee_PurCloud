package com.kingdee.purchase.openapi.model;

import net.sf.json.JSONObject;

import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 询价单分录POJO
 * @author RD_sky_lv
 *
 */
public class BuyOfferItem implements IJSONTransfer {

	private String purchaseNoteItemId;  //询价单本身分录ID

	private String subject;

	private String unit;

	private Long purchaseAmount;

	private String productCode;

	private String desc;

	private String prItemId;  //对应金蝶系统采购申请分录ID
	
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(purchaseNoteItemId))
			result.element("purchaseNoteItemId", purchaseNoteItemId);
		if (StringUtils.isNotEmpty(subject))
			result.element("subject", subject);
		if (StringUtils.isNotEmpty(unit))
			result.element("unit", unit);
		result.element("purchaseAmount", purchaseAmount);
		if (StringUtils.isNotEmpty(productCode))
			result.element("productCode", productCode);
		if (StringUtils.isNotEmpty(desc))
			result.element("desc", desc);
		if (StringUtils.isNotEmpty(prItemId))
			result.element("prItemId", prItemId);
		
		return result;
	}

	public String getPurchaseNoteItemId() {
		return purchaseNoteItemId;
	}

	public void setPurchaseNoteItemId(String purchaseNoteItemId) {
		this.purchaseNoteItemId = purchaseNoteItemId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(Long purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPrItemId() {
		return prItemId;
	}

	public void setPrItemId(String prItemId) {
		this.prItemId = prItemId;
	}

}
