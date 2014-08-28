package com.kingdee.purchase.platform.info.api;

import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.model.IJSONTransfer;

/**
 * API错误码 
 * @author RD_cary_lin
 */
public class ApiErrorCodeInfo implements IJSONTransfer{
	
	//关联的API
	private long parentid;
	//错误码
	private String code;
	//描述
	private String description;
	//解决方法
	private String dealSolution;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDealSolution() {
		return dealSolution;
	}
	public void setDealSolution(String dealSolution) {
		this.dealSolution = dealSolution;
	}
	public long getParentid() {
		return parentid;
	}
	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
	public JSONObject toJSONObject() {
		return null;
	}
	
}
