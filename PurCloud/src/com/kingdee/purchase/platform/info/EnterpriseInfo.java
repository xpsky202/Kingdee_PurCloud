package com.kingdee.purchase.platform.info;

import java.sql.Date;

/***
 * 企业
 * <br>集团唯一</br>
 * 
 * @author RD_jiangkun_zhu
 *
 */
public class EnterpriseInfo extends BaseInfo{

	private String name;				/* 企业名称 */
//	private String contact;				/* 联系人 */
//	private String email;				/* 注册邮箱 */
	private UsedStatus usedstatus;
	private String serviceUrl;			/* 服务URL */
//	private String mobile;
	private long accountId;				/* 账号ID */
	private ERPType erpType;			/* ERP类型 */
	private long enterpriseid;			/* 企业ID */

	private Date createDate;
	private Date updateDate;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
//	public String getMobile() {
//		return mobile;
//	}
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
	public EnterpriseInfo(){
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getContact() {
//		return contact;
//	}
//	public void setContact(String contact) {
//		this.contact = contact;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
	public UsedStatus getUsedstatus() {
		return usedstatus;
	}
	public void setUsedstatus(UsedStatus usedstatus) {
		this.usedstatus = usedstatus;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public ERPType getErpType() {
		return erpType;
	}
	public void setErpType(ERPType erpType) {
		this.erpType = erpType;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getEnterpriseid() {
		return enterpriseid;
	}
	public void setEnterpriseid(long enterpriseid) {
		this.enterpriseid = enterpriseid;
	}
}