package com.kingdee.purchase.destapi.alibaba.util;

import java.text.ParseException;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.openapi.client.util.DateUtil;
import com.kingdee.purchase.openapi.model.BusinessInfo;
import com.kingdee.purchase.openapi.model.BuyOfferInfo;
import com.kingdee.purchase.openapi.model.BuyOfferItem;
import com.kingdee.purchase.openapi.model.ContactInfo;
import com.kingdee.purchase.openapi.model.ObjectCollection;
import com.kingdee.purchase.openapi.model.QuotationInfo;
import com.kingdee.purchase.openapi.model.QuotationItem;
import com.kingdee.purchase.openapi.model.SupplierCompanyInfo;
import com.kingdee.purchase.openapi.model.SupplierContactInfo;
import com.kingdee.purchase.openapi.model.SupplierInfo;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

public class JSONUtil {
	
	/**
	 * json对象解析成询价单集合
	 * @param value
	 * @return
	 * @throws PurBizException
	 */
	public static ObjectCollection<BuyOfferInfo> json2BuyOfferList(JSONObject value) throws PurBizException {
		ObjectCollection<BuyOfferInfo> result = new ObjectCollection<BuyOfferInfo>();
		if (value == null || value.isEmpty()) {
			return result;
		}
		
		JSONArray buyOffList = value.optJSONArray("buyOfferList");
		BuyOfferInfo info = null;
		for(Object item: buyOffList) {
			info = json2BuyOffInfo((JSONObject)item, false);
			if (info != null) {
				result.addItem(info);
			}
		}
		
		return result;
	}
	
	/**
	 * json对象解析成询价单对象
	 * @param value
	 * @param isHasProfix  json对象是否存在 "buyOffer" 前缀
	 * @return
	 * @throws PurBizException
	 */
	public static BuyOfferInfo json2BuyOffInfo(JSONObject value, boolean isHasProfix) throws PurBizException {
		BuyOfferInfo result = new BuyOfferInfo();
		if (value == null || value.isEmpty()) {
			return null;
		}
		JSONObject buyOff;
		if (isHasProfix) {
			buyOff = value.optJSONObject("buyOffer");
			if (buyOff == null || buyOff.isEmpty()) {
				return null;
			}
		} else {
			buyOff = value;
		}
		
		result.setGmtQuotationExpire(parseDate(buyOff.opt("gmtQuotationExpire"), "gmtQuotationExpire"));
		result.setDescription(buyOff.optString("description"));
		result.setStatus(buyOff.optString("status"));
		result.setTitle(buyOff.optString("title"));
		result.setPurchaseNoteId(buyOff.optString("purchaseNoteId"));
		result.setSubUserId(buyOff.optString("subUserId"));
		result.setBuyerMemberId(buyOff.optString("buyerMemberId"));
		result.setGmtCreate(parseDate(buyOff.opt("gmtCreate"), "gmtCreate"));
		result.setPrId(buyOff.optString("prId"));
		result.setIncludeTax(buyOff.optBoolean("includeTax"));
		
		JSONObject contact = buyOff.optJSONObject("contactInfo");
		if (contact != null && !contact.isEmpty()) {
			result.setContactInfo(json2ContactInfo(contact));
		}
		
		JSONArray buyOffItems = buyOff.optJSONArray("purchaseNoteItems");
		ObjectCollection<BuyOfferItem> coll = new ObjectCollection<BuyOfferItem>();
		BuyOfferItem buyOfferItem = null;
		for(Object item: buyOffItems) {
			buyOfferItem = json2BuyOffItem((JSONObject)item);
			if (buyOfferItem != null) {
				coll.addItem(buyOfferItem);
			}
		}
		
		result.setPurchaseNoteItems(coll);
		
		return result;
	}
	
	/**
	 * json对象解析成询价单分录对象
	 * @param value
	 * @return
	 */
	public static BuyOfferItem json2BuyOffItem(JSONObject value) {
		BuyOfferItem result = new BuyOfferItem();
		if (value == null || value.isEmpty()) {
			return null;
		}
		result.setSubject(value.optString("subject"));
		result.setPurchaseAmount(value.optLong("purchaseAmount"));
		result.setUnit(value.optString("unit"));
		result.setDesc(value.optString("productFeature"));
		result.setPurchaseNoteItemId(value.optString("purchaseNoteItemId"));
		result.setProductCode(value.optString("productCode"));
		result.setPrItemId(value.optString("prItemId"));
		
		return result;
	}
	
	/**
	 * json对象解析成报价单集合
	 * @param value
	 * @return
	 * @throws PurBizException
	 */
	public static ObjectCollection<QuotationInfo> json2QuotationList(JSONObject value) throws PurBizException {
		ObjectCollection<QuotationInfo> result = new ObjectCollection<QuotationInfo>();
		if (value == null || value.isEmpty()) {
			return result;
		}
		
		JSONArray quotationList = value.optJSONArray("quotationList");
		QuotationInfo info = null;
		for(Object item: quotationList) {
			info = json2QuotationInfo((JSONObject)item, false);
			if (info != null) {
				result.addItem(info);
			}
		}
		
		return result;
	}
	
	/**
	 * json对象解析成报价单对象
	 * @param value
	 * @param isHasProfix  json对象是否存在 "quotation" 前缀
	 * @return
	 * @throws PurBizException
	 */
	public static QuotationInfo json2QuotationInfo(JSONObject value, boolean isHasProfix) throws PurBizException {
		QuotationInfo result = new QuotationInfo();
		if (value == null || value.isEmpty()) {
			return null;
		}
		
		JSONObject quotation;
		if (isHasProfix) {
			quotation = value.optJSONObject("quotation");
			if (quotation == null || quotation.isEmpty()) {
				return null;
			}
		} else {
			quotation = value;
		}
		result.setId(quotation.optLong("id"));
		result.setExpireDate(parseDate(quotation.opt("expireDate"), "expireDate"));
		result.setBuyOfferId(quotation.optString("buyOfferId"));
		result.setSupplierMemberId(quotation.optString("supplierMemberId"));
		result.setInvoiceType(quotation.optString("invoiceType"));
		result.setFreight(quotation.optLong("freight"));
		result.setTotalPrice(quotation.optLong("totalPrice"));
		result.setSpecifications(quotation.optString("specifications"));
		result.setGmtCreate(parseDate(quotation.opt("gmtCreate"), "gmtCreate"));
		result.setPrId(quotation.optString("prId"));
		
		JSONObject contact = quotation.optJSONObject("contactInfo");
		if (contact != null && !contact.isEmpty()) {
			result.setContactInfo(json2ContactInfo(contact));
		}
		JSONObject payRequirement = quotation.optJSONObject("JSONObject");
		if (payRequirement != null && !payRequirement.isEmpty()) {
			result.setPayType(payRequirement.optString("type"));
			result.setPaySpecification(payRequirement.optString("desc"));
		}
		
		JSONArray quotationItems = quotation.optJSONArray("supplyNoteItems");
		ObjectCollection<QuotationItem> coll = new ObjectCollection<QuotationItem>();
		QuotationItem quotationItem = null;
		for(Object item: quotationItems) {
			quotationItem = json2QuotationItem((JSONObject)item);
			if (quotationItem != null) {
				coll.addItem(quotationItem);
			}
		}
		
		result.setSupplyNoteItems(coll);
		
		return result;
	}
	
	/**
	 * json解析为联系人对象
	 * @param value
	 * @return
	 */
	public static ContactInfo json2ContactInfo(JSONObject value) {
		ContactInfo result = new ContactInfo();
		if (value == null || value.isEmpty()) {
			return null;
		}
		result.setContact(value.optString("contact"));
		result.setPhone(value.optString("phone"));
		result.setMobile(value.optString("mobile"));
		result.setEmail(value.optString("email"));
		
		return result;
	}
	
	/**
	 * json解析为报价单分录对象
	 * @param value
	 * @return
	 */
	public static QuotationItem json2QuotationItem(JSONObject value) {
		QuotationItem result = new QuotationItem();
		if (value == null || value.isEmpty()) {
			return null;
		}
		result.setId(value.optLong("id"));
		result.setSubject(value.optString("subject"));
		result.setProductId(value.optLong("productId"));
		result.setAmount(value.optInt("amount"));
		result.setUnit(value.optString("unit"));
		result.setPrice(value.optLong("price"));
		result.setPrItemId(value.optString("prItemId"));
		result.setTaxRate(value.optString("taxRate"));
		
		return result;
	}
	
	/**
	 * 将1688供应商信息加工成对应的供应商对象
	 * @param value
	 * @return
	 */
	public static SupplierInfo json2SupplierInfo(JSONObject value) {
		SupplierInfo supplierInfo = new SupplierInfo();
		if (value == null || value.isEmpty()) {
			return null;
		}
		
		JSONObject company = value.optJSONObject("supplier").optJSONObject("companyInfo");
		if (company != null && !company.isEmpty()) {
			SupplierCompanyInfo companyInfo = new SupplierCompanyInfo();
			companyInfo.setCompanySummary(company.optString("companySummary"));
			JSONObject icrInfo = company.optJSONObject("icrInfo");
			if (icrInfo != null && !icrInfo.isEmpty()) {
				companyInfo.setName(icrInfo.optString("name"));
				companyInfo.setPrincipal(icrInfo.optString("principal"));
				companyInfo.setCompanyAddress(icrInfo.optString("companyAddress"));
				companyInfo.setRegistrationId(icrInfo.optString("registrationId"));
				companyInfo.setEnterpriseType(icrInfo.optString("enterpriseType"));
				companyInfo.setDateOfEstablishment(icrInfo.optString("dateOfEstablishment"));
				companyInfo.setRegisteredCapital(icrInfo.optString("registeredCapital"));
				companyInfo.setBusinessTerm(icrInfo.optString("businessTerm"));
				companyInfo.setBusinessScope(icrInfo.optString("businessScope"));
				companyInfo.setBank(icrInfo.optString("bank"));
				companyInfo.setBankAccount(icrInfo.optString("bankAccount"));
			}
			supplierInfo.setCompanyInfo(companyInfo);
		}
		
		BusinessInfo businessInfo = new BusinessInfo();
		JSONObject business = value.optJSONObject("supplier").optJSONObject("businessInfo");
		if (business != null && !business.isEmpty()) {
			businessInfo.setMainIndustries(business.optString("mainIndustries"));
			businessInfo.setBusinessAddress(business.optString("businessAddress"));
		}
		supplierInfo.setBusinessInfo(businessInfo);
		
		SupplierContactInfo contactInfo = new SupplierContactInfo();
		JSONObject contact = value.optJSONObject("supplier").optJSONObject("contactInfo");
		if (contact != null && !contact.isEmpty()) {
			contactInfo.setName(contact.optString("name"));
			contactInfo.setMobile(contact.optString("mobile"));
			contactInfo.setPhone(contact.optString("phone"));
			contactInfo.setGender(contact.optString("gender"));
			contactInfo.setFax(contact.optString("fax"));
			contactInfo.setZipCode(contact.optString("zipCode"));
			contactInfo.setContactAddress(contact.optString("contactAddress"));
		}
		supplierInfo.setContactInfo(contactInfo);
		
		return supplierInfo;
	}
	
	/**
	 * 将供应商对象转换为json的字符串转换为供应商对象
	 * @param value
	 * @return
	 */
	public static SupplierInfo jsonSupplierInfo(JSONObject value) {
		SupplierInfo supplierInfo = new SupplierInfo();
		if (value == null || value.isEmpty()) {
			return null;
		}
		JSONObject company = value.optJSONObject("supplier").optJSONObject("companyInfo");
		if (company != null && !company.isEmpty()) {
			SupplierCompanyInfo companyInfo = new SupplierCompanyInfo();
			companyInfo.setCompanySummary(company.optString("companySummary"));
			companyInfo.setName(company.optString("name"));
			companyInfo.setPrincipal(company.optString("principal"));
			companyInfo.setCompanyAddress(company.optString("companyAddress"));
			companyInfo.setRegistrationId(company.optString("registrationId"));
			companyInfo.setEnterpriseType(company.optString("enterpriseType"));
			companyInfo.setDateOfEstablishment(company.optString("dateOfEstablishment"));
			companyInfo.setRegisteredCapital(company.optString("registeredCapital"));
			companyInfo.setBusinessTerm(company.optString("businessTerm"));
			companyInfo.setBusinessScope(company.optString("businessScope"));
			companyInfo.setBank(company.optString("bank"));
			companyInfo.setBankAccount(company.optString("bankAccount"));
			supplierInfo.setCompanyInfo(companyInfo);
		}
		
		BusinessInfo businessInfo = new BusinessInfo();
		JSONObject business = value.optJSONObject("supplier").optJSONObject("businessInfo");
		if (business != null && !business.isEmpty()) {
			businessInfo.setMainIndustries(business.optString("mainIndustries"));
			businessInfo.setBusinessAddress(business.optString("businessAddress"));
		}
		supplierInfo.setBusinessInfo(businessInfo);
		
		SupplierContactInfo contactInfo = new SupplierContactInfo();
		JSONObject contact = value.optJSONObject("supplier").optJSONObject("contactInfo");
		if (contact != null && !contact.isEmpty()) {
			contactInfo.setName(contact.optString("name"));
			contactInfo.setMobile(contact.optString("mobile"));
			contactInfo.setPhone(contact.optString("phone"));
			contactInfo.setGender(contact.optString("gender"));
			contactInfo.setFax(contact.optString("fax"));
			contactInfo.setZipCode(contact.optString("zipCode"));
			contactInfo.setContactAddress(contact.optString("contactAddress"));
		}
		supplierInfo.setContactInfo(contactInfo);
		
		return supplierInfo;
	}
	
	/**
	 * 处理日期字段
	 * @param dateValue
	 * @param fieldName
	 * @return
	 * @throws PurBizException
	 */
	public static Date parseDate(Object dateValue, String fieldName) throws PurBizException {
		if (dateValue != null && dateValue instanceof Date){
			return (Date)dateValue;
		} else if (dateValue != null) {
			try {
				return DateUtil.parse(dateValue.toString());
			} catch (ParseException e) {
				throw new PurBizException(PurExceptionDefine.ILLEGAL_DATE_FORMAT, new String[] {fieldName});
			}
		}
		
		return null;
	}

}
