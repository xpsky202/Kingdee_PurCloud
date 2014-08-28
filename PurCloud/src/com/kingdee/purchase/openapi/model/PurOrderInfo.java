package com.kingdee.purchase.openapi.model;

import com.kingdee.purchase.platform.util.StringUtils;

import net.sf.json.JSONObject;


/**
 * 采购订单
 * @author RD_cary_lin
 *
 */
public class PurOrderInfo implements IJSONTransfer{
	
	private String orderId;
	private Long subUserId;
	private String supplierMemberId;
	private String supplierCode;
	private String supplierCompanyName;
	private String supplierMobile;
	private String supplierEmail;
	private Long totalAmount;
	private Long freightAmount;
	private Long orderAmount;
	private String sourceType;
	private String orderStatus;
	private String failReason;
	private String buyerCompanyName;
	private String buyerMobile;
	private String buyerEmail;
	private ObjectCollection<PurOrderItem> orderItemList;
	private String payType;
	private String aliOrderId;
	
	
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(orderId))
			result.element("orderId", orderId);
		
		result.element("subUserId", subUserId);
		if (StringUtils.isNotEmpty(supplierCode)){
			result.element("supplierCode", supplierCode);
		}
		if (StringUtils.isNotEmpty(supplierMemberId)){
			result.element("supplierMemberId", supplierMemberId);
		}
		if (StringUtils.isNotEmpty(supplierCompanyName)){
			result.element("supplierCompanyName", supplierCompanyName);
		}
		if (StringUtils.isNotEmpty(supplierMobile)){
			result.element("supplierMobile", supplierMobile);
		}
		if (StringUtils.isNotEmpty(supplierEmail)){
			result.element("supplierEmail", supplierEmail);
		}
		result.element("totalAmount", totalAmount);
		result.element("freightAmount", freightAmount);
		result.element("orderAmount", orderAmount);
		
		if (StringUtils.isNotEmpty(sourceType))
			result.element("sourceType", sourceType);
		
		if (StringUtils.isNotEmpty(orderStatus))
			result.element("orderStatus", orderStatus);
		
		if (StringUtils.isNotEmpty(failReason))
			result.element("failReason", failReason);
		
		if (StringUtils.isNotEmpty(buyerCompanyName))
			result.element("buyerCompanyName", buyerCompanyName);
		
		if (StringUtils.isNotEmpty(buyerMobile))
			result.element("buyerMobile", buyerMobile);
		
		if (StringUtils.isNotEmpty(buyerEmail))
			result.element("buyerEmail", buyerEmail);
		
		if (orderItemList.size()>0)
			result.element("orderItemList", orderItemList.toJSONArray());
		
		if (StringUtils.isNotEmpty(payType))
			result.element("payType", payType);
		
		if (StringUtils.isNotEmpty(aliOrderId))
			result.element("aliOrderId", aliOrderId);
		
		return result;
	}
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getSubUserId() {
		return subUserId;
	}
	public void setSubUserId(Long subUserId) {
		this.subUserId = subUserId;
	}
	public String getSupplierMemberId() {
		return supplierMemberId;
	}


	public String getSupplierCode() {
		return supplierCode;
	}


	public String getSupplierCompanyName() {
		return supplierCompanyName;
	}


	public String getSupplierMobile() {
		return supplierMobile;
	}


	public String getSupplierEmail() {
		return supplierEmail;
	}


	public void setSupplierMemberId(String supplierMemberId) {
		this.supplierMemberId = supplierMemberId;
	}


	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}


	public void setSupplierCompanyName(String supplierCompanyName) {
		this.supplierCompanyName = supplierCompanyName;
	}


	public void setSupplierMobile(String supplierMobile) {
		this.supplierMobile = supplierMobile;
	}


	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}


	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getFreightAmount() {
		return freightAmount;
	}
	public void setFreightAmount(Long freightAmount) {
		this.freightAmount = freightAmount;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}
	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}
	public String getBuyerMobile() {
		return buyerMobile;
	}
	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public ObjectCollection<PurOrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(ObjectCollection<PurOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getAliOrderId() {
		return aliOrderId;
	}
	public void setAliOrderId(String aliOrderId) {
		this.aliOrderId = aliOrderId;
	}
}
