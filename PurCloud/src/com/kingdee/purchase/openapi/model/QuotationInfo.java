package com.kingdee.purchase.openapi.model;

import java.util.Date;

import net.sf.json.JSONObject;

import com.alibaba.openapi.client.util.DateUtil;
import com.kingdee.purchase.config.ConfigConstant;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 报价单POJO
 * 
 * @author RD_sky_lv
 * 
 */

public class QuotationInfo implements IJSONTransfer {

	private Long id;
	private Date expireDate;		//到期日期
	private String buyOfferId;		//询价单ID
	private String supplierMemberId;	//阿狸巴巴供应商ID
	private ContactInfo contactInfo;	//联系信息
	private InvoiceType invoiceType;	//发票类型
	private long freight;				//运费
	private long totalPrice;			//总价
	private String specifications;		//规格
	private ObjectCollection<QuotationItem> supplyNoteItems;
	private PayType payType;  //付款类型
	private String paySpecification;  //付款说明
	private Date gmtCreate;	//创建日期
	private String prId;	//EAS采购申请单ID
	private SupplierInfo supplier;	//供应商信息

	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.element("id", id);
		if (expireDate != null)
			result.element("expireDate", DateUtil.format(expireDate, ConfigConstant.DateFormatPattern));
		if (StringUtils.isNotEmpty(buyOfferId))
			result.element("buyOfferId", buyOfferId);
		if (StringUtils.isNotEmpty(supplierMemberId))
			result.element("supplierMemberId", supplierMemberId);
		if (contactInfo != null)
			result.element("contactInfo", contactInfo.toJSONObject());
		if (invoiceType != null)
			result.element("invoiceType", invoiceType.toString());
		result.element("freight", freight);
		result.element("totalPrice", totalPrice);
		if (StringUtils.isNotEmpty(specifications))
			result.element("specifications", specifications);
		if (supplyNoteItems != null)
			result.element("supplyNoteItems", supplyNoteItems.toJSONArray());
		if (payType != null)
			result.element("payType", payType.toString());
		if (gmtCreate != null)
			result.element("gmtCreate", DateUtil.format(gmtCreate, ConfigConstant.DateFormatPattern));
		if (StringUtils.isNotEmpty(prId))
			result.element("prId", prId);
		if (supplier != null) {
			result.element("supplier", supplier);
		}
		
		return result;
	}
	
	public static enum InvoiceType {
		common,  //普通发票
		vat,     //17%增值发票
		no;       //不提供发票
		
		public static InvoiceType getInvoiceType(String invokeType) {
			if (common.toString().equals(invokeType)) {
				return common;
			} else if (vat.toString().equals(invokeType)) {
				return vat;
			} else {
				return no;
			}
		}
	}
	
	public static enum PayType {
		guarantee,   //全额付款
		step,        //分阶段付款
		other;        //其它
		
		public static PayType getPayType(String payType) {
			if (guarantee.toString().equals(payType)) {
				return guarantee;
			} else if (step.toString().equals(payType)) {
				return step;
			} else {
				return other;
			}
		}
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getBuyOfferId() {
		return buyOfferId;
	}

	public String getSupplierMemberId() {
		return supplierMemberId;
	}

	public void setSupplierMemberId(String supplierMemberId) {
		this.supplierMemberId = supplierMemberId;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public long getFreight() {
		return freight;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public String getSpecifications() {
		return specifications;
	}

	public ObjectCollection<QuotationItem> getSupplyNoteItems() {
		return supplyNoteItems;
	}

	public PayType getPayType() {
		return payType;
	}

	public String getPaySpecification() {
		return paySpecification;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public String getPrId() {
		return prId;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setBuyOfferId(String buyOfferId) {
		this.buyOfferId = buyOfferId;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = InvoiceType.getInvoiceType(invoiceType);
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public void setSupplyNoteItems(ObjectCollection<QuotationItem> supplyNoteItems) {
		this.supplyNoteItems = supplyNoteItems;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	
	public void setPayType(String payType) {
		this.payType = PayType.getPayType(payType);
	}

	public void setPaySpecification(String paySpecification) {
		this.paySpecification = paySpecification;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setPrId(String prId) {
		this.prId = prId;
	}

	public SupplierInfo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierInfo supplier) {
		this.supplier = supplier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
