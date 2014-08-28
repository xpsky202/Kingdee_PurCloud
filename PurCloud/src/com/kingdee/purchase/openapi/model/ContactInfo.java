package com.kingdee.purchase.openapi.model;

import net.sf.json.JSONObject;

import com.kingdee.purchase.platform.util.StringUtils;

public class ContactInfo implements IJSONTransfer {

	private String contact; // 姓名
	private String mobile; // 手机
	private String phone; // 电话
	private String email; // 电邮
	
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(contact))
			result.element("contact", contact);
		if (StringUtils.isNotEmpty(mobile))
			result.element("mobile", mobile);
		if (StringUtils.isNotEmpty(phone))
			result.element("phone", phone);
		if (StringUtils.isNotEmpty(email))
			result.element("email", email);
		
		return result;
	}

	public String getContact() {
		return contact;
	}

	public String getMobile() {
		return mobile;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
