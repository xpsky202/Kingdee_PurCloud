package com.kingdee.purchase.platform.info;

import java.util.Map;

public class UrlRequestInfo {
	private String enterPriseID;
	
	private String userID;
	
	private String orgUnitID;
	
	private String userName;
	
	private String urlHead;
	
	private String  protocol;
	
	private String  version;
	
	private String  nameSpace;
	
	private String  apiName;
	
	private Map<String, Object> params;

	public String getEnterPriseID() {
		return enterPriseID;
	}

	public void setEnterPriseID(String enterPriseID) {
		this.enterPriseID = enterPriseID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getOrgUnitID() {
		return orgUnitID;
	}

	public void setOrgUnitID(String orgUnitID) {
		this.orgUnitID = orgUnitID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUrlHead() {
		return urlHead;
	}

	public void setUrlHead(String urlHead) {
		this.urlHead = urlHead;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
