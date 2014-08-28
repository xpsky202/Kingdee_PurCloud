package com.kingdee.purchase.openapi.model;

import com.kingdee.purchase.platform.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * 地址
 * <br>目前只是文本的省、市、区县详情
 * @author RD_jiangkun_zhu
 *
 */
public class AreaInfo implements IJSONTransfer {

	private String province;
	private String city;
	private String district;
	
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(province)){
			result.element("province", province);
		}
		if (StringUtils.isNotEmpty(city)){
			result.element("city", city);
		}
		if (StringUtils.isNotEmpty(district)){
			result.element("district", district);
		}
		return result;
	}

	protected String getProvince() {
		return province;
	}

	protected void setProvince(String province) {
		this.province = province;
	}

	protected String getCity() {
		return city;
	}

	protected void setCity(String city) {
		this.city = city;
	}

	protected String getDistrict() {
		return district;
	}

	protected void setDistrict(String district) {
		this.district = district;
	}	
}