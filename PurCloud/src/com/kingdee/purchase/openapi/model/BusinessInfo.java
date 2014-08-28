package com.kingdee.purchase.openapi.model;

import com.kingdee.purchase.platform.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * 经营信息
 * @author RD_jiangkun_zhu
 *
 */
public class BusinessInfo implements IJSONTransfer {
	
	private String mainIndustries;
	private String businessAddress;

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		if(!StringUtils.isEmpty(mainIndustries)){
			json.element("mainIndustries", mainIndustries);
		}
		if(!StringUtils.isEmpty(businessAddress)){
			json.element("businessAddress", businessAddress);
		}
		
		return json;
	}

	public String getMainIndustries() {
		return mainIndustries;
	}

	public void setMainIndustries(String mainIndustries) {
		this.mainIndustries = mainIndustries;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}	
}