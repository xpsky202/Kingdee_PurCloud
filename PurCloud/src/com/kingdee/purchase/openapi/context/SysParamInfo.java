package com.kingdee.purchase.openapi.context;

import javax.persistence.OneToOne;

import com.kingdee.purchase.platform.info.UserInfo;


public class SysParamInfo implements Cloneable {

	private Long enterPriseID;
	
	@OneToOne
	private UserInfo userInfo;
	
	private String orgUnitID;

	public Long getEnterPriseID() {
		return enterPriseID;
	}

	public void setEnterPriseID(Long enterPriseID) {
		this.enterPriseID = enterPriseID;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getOrgUnitID() {
		return orgUnitID;
	}

	public void setOrgUnitID(String orgUnitID) {
		this.orgUnitID = orgUnitID;
	}
	
	@Override
	public SysParamInfo clone() {
		SysParamInfo info = new SysParamInfo();
		info.enterPriseID = this.enterPriseID;
		info.userInfo = this.userInfo;
		info.orgUnitID = this.orgUnitID;
		
		return info;
	}
}
