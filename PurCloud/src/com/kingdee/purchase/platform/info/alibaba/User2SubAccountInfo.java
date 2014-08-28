package com.kingdee.purchase.platform.info.alibaba;

import com.kingdee.purchase.platform.info.BaseInfo;

public class User2SubAccountInfo extends BaseInfo{

	private long enterpriseId;
	private String companyId;
	private String userId;
	private String subAccountId;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubAccountId() {
		return subAccountId;
	}
	public void setSubAccountId(String subAccountId) {
		this.subAccountId = subAccountId;
	}
}
