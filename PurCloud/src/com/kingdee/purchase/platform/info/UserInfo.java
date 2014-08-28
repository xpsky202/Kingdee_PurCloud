package com.kingdee.purchase.platform.info;

import java.util.Random;

/**
 * 用户信息类 
 * @author RD_sky_lv
 *
 */

public class UserInfo {
	
	private static final String PROFIX = "NO";
	
	//用户在金蝶系统ID
	private String id;
	//用户在金蝶系统的编码
	private String number;		/* optional */
	//用户在金蝶系统的名称
	private String name;		/* optional */
	private String mobileNO;
	private String email;
	private Sex sex;
	private String department;
	private Role role;
	//用户在B2B网站注册ID
	private String destId;
	
	public UserInfo(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		if (name == null || name.length() == 0) {
			Random random = new Random();
			name = PROFIX + String.valueOf(Math.abs(random.nextInt()) % 10000000);
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNO() {
		return mobileNO;
	}

	public void setMobileNO(String mobileNO) {
		this.mobileNO = mobileNO;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Sex getSex() {
		if (sex == null) {
			sex = Sex.male;
		}
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	/**
	 * 默认采购员角色
	 * @return
	 */
	public Role getRole() {
		if (role == null) {
			role = Role.purchaser;
		}
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	/**
	 * 是否存在目标系统子账号
	 * @return
	 */
	public boolean isHasDestId() {
		return destId == null || destId.length() == 0;
	}

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}
	
	/**
	 * 用户角色
	 * @author RD_sky_lv
	 *
	 */

	public enum Role {
		
		purchaser, purchaseManager, accountant;  /*采购员， 采购经理， 财务*/
		
		public static String getAlias(Role role) {
			if (role.equals(purchaser)) {
				return "采购员";
			} else if (role.equals(purchaseManager)) {
				return "采购经理";
			} else if (role.equals(accountant)) {
				return "财务";
			} else {
				return null;
			}
		}
	}
	
	/**
	 * 用户性别
	 * @author RD_sky_lv
	 *
	 */
	public enum Sex {
		male, female
	}

}
