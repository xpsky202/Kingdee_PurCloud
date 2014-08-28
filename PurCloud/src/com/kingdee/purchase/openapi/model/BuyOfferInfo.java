package com.kingdee.purchase.openapi.model;

import java.util.Date;

import net.sf.json.JSONObject;

import com.alibaba.openapi.client.util.DateUtil;
import com.kingdee.purchase.config.ConfigConstant;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 询价单POJO
 * @author RD_sky_lv
 *
 */
public class BuyOfferInfo implements IJSONTransfer {

	private String purchaseNoteId;
	private String title;
	private String status;
	private String subUserId;
	private String buyerMemberId;
	private Date gmtCreate;
	private Date gmtQuotationExpire;
	private ObjectCollection<BuyOfferItem> purchaseNoteItems;
	private String prId;
	private String description;
	private boolean includeTax;
	private AreaInfo receiptAddress;
	private ContactInfo contactInfo;
	

	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(purchaseNoteId))
			result.element("purchaseNoteId", purchaseNoteId);
		if (StringUtils.isNotEmpty(title))
			result.element("title", title);
		if (StringUtils.isNotEmpty(status))
			result.element("status", status);
		if (StringUtils.isNotEmpty(subUserId))
			result.element("subUserId", subUserId);
		if (StringUtils.isNotEmpty(buyerMemberId))
			result.element("buyerMemberId", buyerMemberId);
		if (gmtCreate != null)
			result.element("gmtCreate", DateUtil.format(gmtCreate, ConfigConstant.DateFormatPattern));
		if (gmtQuotationExpire != null)
			result.element("gmtQuotationExpire", DateUtil.format(gmtQuotationExpire, ConfigConstant.DateFormatPattern));
		if (StringUtils.isNotEmpty(prId))
			result.element("prId", prId);
		if (StringUtils.isNotEmpty(description))
			result.element("description", description);
		if (purchaseNoteItems.size() > 0) {
			result.element("purchaseNoteItems", purchaseNoteItems.toJSONArray());
		}
		if(receiptAddress!=null){
			result.element("receiptAddress", receiptAddress);
		}
		if(contactInfo!=null){
			result.element("contactInfo", contactInfo);
		}
		
		//是否含税
		result.element("includeTax", includeTax);
		
		return result;
	}

	public String getPurchaseNoteId() {
		return purchaseNoteId;
	}

	public String getTitle() {
		return title;
	}

	public String getStatus() {
		return status;
	}

	public String getSubUserId() {
		return subUserId;
	}

	public String getBuyerMemberId() {
		return buyerMemberId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public Date getGmtQuotationExpire() {
		return gmtQuotationExpire;
	}

	public ObjectCollection<BuyOfferItem> getPurchaseNoteItems() {
		return purchaseNoteItems;
	}

	public String getPrId() {
		return prId;
	}

	public String getDescription() {
		return description;
	}

	public void setPurchaseNoteId(String purchaseNoteId) {
		this.purchaseNoteId = purchaseNoteId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSubUserId(String subUserId) {
		this.subUserId = subUserId;
	}

	public void setBuyerMemberId(String buyerMemberId) {
		this.buyerMemberId = buyerMemberId;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtQuotationExpire(Date gmtQuotationExpire) {
		this.gmtQuotationExpire = gmtQuotationExpire;
	}

	public void setPurchaseNoteItems(ObjectCollection<BuyOfferItem> purchaseNoteItems) {
		this.purchaseNoteItems = purchaseNoteItems;
	}

	public void setPrId(String prId) {
		this.prId = prId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AreaInfo getReceiptAddress() {
		return receiptAddress;
	}
	public void setReceiptAddress(AreaInfo receiptAddress) {
		this.receiptAddress = receiptAddress;
	}
	public boolean isIncludeTax() {
		return includeTax;
	}
	public void setIncludeTax(boolean includeTax) {
		this.includeTax = includeTax;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
}
