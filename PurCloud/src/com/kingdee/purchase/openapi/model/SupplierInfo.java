package com.kingdee.purchase.openapi.model;

import com.kingdee.purchase.platform.util.StringUtils;

import net.sf.json.JSONObject;

public class SupplierInfo implements IJSONTransfer {

	private String id;  //金蝶系统ID

	private String destId;  //B2B目标系统对应ID

	private SupplierCompanyInfo companyInfo;

	private SupplierContactInfo contactInfo;
	
	private BusinessInfo businessInfo;
	
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(id))
			result.element("id", id);
		if (StringUtils.isNotEmpty(destId))
			result.element("destId", destId);
		if (companyInfo != null) {
			result.element("companyInfo", companyInfo.toJSONObject());
		}
		if (contactInfo != null) {
			result.element("contactInfo", contactInfo.toJSONObject());
		}
		if(businessInfo!=null){
			result.element("businessInfo", businessInfo.toJSONObject());
		}
		
		return result;
	}
	
	public BusinessInfo getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(BusinessInfo businessInfo) {
		this.businessInfo = businessInfo;
	}
	public String getId() {
		return id;
	}

	public String getDestId() {
		return destId;
	}

	public SupplierCompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public SupplierContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public void setCompanyInfo(SupplierCompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public void setContactInfo(SupplierContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

}
