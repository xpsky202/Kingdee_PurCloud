package com.kingdee.purchase.platform.info;

/***
 * 账号
 * <br>网站会员
 * @author RD_jiangkun_zhu
 *
 */
public class AccountInfo extends BaseInfo{

	private String username;					/*  用户名就是邮箱名  */
	private String email;						/* 注册邮箱 */
	private String displayname;
	private String mobile;
	private String password;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}