package com.kingdee.purchase.openapi.model;

import com.kingdee.purchase.platform.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * 采购订单分录
 * @author RD_cary_lin
 *
 */
public class PurOrderItem implements IJSONTransfer {
	private String orderItemId;
	private String productName;
	private Long price;
	private Long count;
	private Long singleFreightAmount;  //运费
	private String receiverPhone;
	private String receiverMobile;
	private String receiverPostCode;
	private String receiverAddress;
	private String receiverName;
	private String otherInfo;

	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(orderItemId)) {
			result.element("orderItemId", orderItemId);
		}
		if (StringUtils.isNotEmpty(productName)) {
			result.element("productName", productName);
		}

		result.element("price", price);
		result.element("count", count);

		if (StringUtils.isNotEmpty(otherInfo)) {
			result.element("otherInfo", otherInfo);
		}
		result.element("singleFreightAmount", singleFreightAmount);
		if (StringUtils.isNotEmpty(receiverPhone)) {
			result.element("receiverPhone", receiverPhone);
		}
		if (StringUtils.isNotEmpty(receiverMobile)) {
			result.element("receiverMobile", receiverMobile);
		}
		if (StringUtils.isNotEmpty(receiverPostCode)) {
			result.element("receiverPostCode", receiverPostCode);
		}
		if (StringUtils.isNotEmpty(receiverAddress)) {
			result.element("receiverAddress", receiverAddress);
		}
		if (StringUtils.isNotEmpty(receiverName)) {
			result.element("receiverName", receiverName);
		}
		return result;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getSingleFreightAmount() {
		return singleFreightAmount;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public String getReceiverPostCode() {
		return receiverPostCode;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setSingleFreightAmount(Long singleFreightAmount) {
		this.singleFreightAmount = singleFreightAmount;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public void setReceiverPostCode(String receiverPostCode) {
		this.receiverPostCode = receiverPostCode;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

}
