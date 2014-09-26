package com.kingdee.purchase.openapi.model;

import net.sf.json.JSONObject;

import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 报价单分录POJO
 * @author RD_sky_lv
 *
 */

public class QuotationItem implements IJSONTransfer {

	private Long id;
	private String subject;	//主题标题
	private Long productId;	//商品ID
	private int amount;		//数量
	private String unit;	//型号
	private Long price;		//单价
	private String prItemId;	//EAS采购申请单分录ID
	private String taxRate;		//税率
	
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.element("id", id);
		if (StringUtils.isNotEmpty(subject))
			result.element("subject", subject);
		result.element("productId", productId);
		result.element("amount", amount);
		result.element("price", price);
		if (StringUtils.isNotEmpty(unit))
			result.element("unit", unit);
		if (StringUtils.isNotEmpty(prItemId))
			result.element("prItemId", prItemId);
		if (StringUtils.isNotEmpty(taxRate))
			result.element("taxRate", taxRate);
		
		return result;
	}

	public Long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public Long getProductId() {
		return productId;
	}

	public int getAmount() {
		return amount;
	}

	public String getUnit() {
		return unit;
	}

	public Long getPrice() {
		return price;
	}

	public String getPrItemId() {
		return prItemId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public void setPrItemId(String prItemId) {
		this.prItemId = prItemId;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

}
