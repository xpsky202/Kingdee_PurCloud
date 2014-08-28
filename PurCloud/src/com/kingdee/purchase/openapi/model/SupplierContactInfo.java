package com.kingdee.purchase.openapi.model;

import net.sf.json.JSONObject;

import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 获取供应商联系方式相关信息
 * @author RD_sky_lv
 *
 */

public class SupplierContactInfo implements IJSONTransfer {

	private String name;  //姓名
	private String mobile;  //手机
	private String gender;  //性别
	private String phone;   //电话
	private String fax;     //传真
	private String zipCode; //邮政编码
	private String contactAddress;  //联系地址
	
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(name))
			result.element("name", name);
		if (StringUtils.isNotEmpty(mobile))
			result.element("mobile", mobile);
		if (StringUtils.isNotEmpty(gender))
			result.element("gender", gender);
		if (StringUtils.isNotEmpty(phone))
			result.element("phone", phone);
		if (StringUtils.isNotEmpty(fax))
			result.element("fax", fax);
		if (StringUtils.isNotEmpty(zipCode))
			result.element("zipCode", zipCode);
		if (StringUtils.isNotEmpty(contactAddress))
			result.element("contactAddress", contactAddress);
		
		return result;
	}

	public String getName() {
		return name;
	}

	public String getMobile() {
		return mobile;
	}

	public String getGender() {
		return gender;
	}

	public String getPhone() {
		return phone;
	}

	public String getFax() {
		return fax;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

}
