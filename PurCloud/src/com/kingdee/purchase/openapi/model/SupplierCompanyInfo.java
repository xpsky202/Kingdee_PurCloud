package com.kingdee.purchase.openapi.model;

import net.sf.json.JSONObject;

import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 获取供应商公司信息
 * @author RD_sky_lv
 *
 */

public class SupplierCompanyInfo implements IJSONTransfer {

	private String name;  //公司名字
	private String principal;  //法定代表人/负责人
	private String companyAddress; //公司注册地址
	private String registrationId; //工商注册号
	private String enterpriseType; //公司类型
	private String dateOfEstablishment; //成立日期  年份
	private String registeredCapital; //注册资本
	private String businessTerm; //营业期限
	private String businessScope; //经营范围
	private String bank; //开户银行
	private String bankAccount; //银行账号
	private String companySummary; //公司简介
	
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(name))
			result.element("name", name);
		if (StringUtils.isNotEmpty(principal))
			result.element("principal", principal);
		if (StringUtils.isNotEmpty(companyAddress))
			result.element("companyAddress", companyAddress);
		if (StringUtils.isNotEmpty(registrationId))
			result.element("registrationId", registrationId);
		if (StringUtils.isNotEmpty(enterpriseType))
			result.element("enterpriseType", enterpriseType);
		if (StringUtils.isNotEmpty(dateOfEstablishment))
			result.element("dateOfEstablishment", dateOfEstablishment);
		if (StringUtils.isNotEmpty(registeredCapital))
			result.element("registeredCapital", registeredCapital);
		if (StringUtils.isNotEmpty(businessTerm))
			result.element("businessTerm", businessTerm);
		if (StringUtils.isNotEmpty(businessScope))
			result.element("businessScope", businessScope);
		if (StringUtils.isNotEmpty(bank))
			result.element("bank", bank);
		if (StringUtils.isNotEmpty(bankAccount))
			result.element("bankAccount", bankAccount);
		if (StringUtils.isNotEmpty(companySummary))
			result.element("companySummary", companySummary);
		
		return result;
	}

	public String getName() {
		return name;
	}

	public String getPrincipal() {
		return principal;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public String getDateOfEstablishment() {
		return dateOfEstablishment;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public String getBusinessTerm() {
		return businessTerm;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public String getBank() {
		return bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public String getCompanySummary() {
		return companySummary;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public void setDateOfEstablishment(String dateOfEstablishment) {
		this.dateOfEstablishment = dateOfEstablishment;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public void setBusinessTerm(String businessTerm) {
		this.businessTerm = businessTerm;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public void setCompanySummary(String companySummary) {
		this.companySummary = companySummary;
	}

}
