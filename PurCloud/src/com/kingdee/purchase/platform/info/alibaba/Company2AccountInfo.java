package com.kingdee.purchase.platform.info.alibaba;

import java.util.Date;

import com.kingdee.purchase.platform.info.BaseInfo;

/***
 * 公司（财务组织）到主账号、token的映射信息
 * 
 * @author RD_jiangkun_zhu
 *
 */
public class Company2AccountInfo extends BaseInfo{

	private long enterpriseId;
	private String companyId;
	private String companyName;
	private String accountId;
	private String token;
	private Date expiredDate;			//过期时间
	
	private volatile String signUrl;
	
	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Company2AccountInfo(){
		super();
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
}