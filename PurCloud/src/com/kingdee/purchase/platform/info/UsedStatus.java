package com.kingdee.purchase.platform.info;

/***
 * 使用状态
 * @author RD_jiangkun_zhu
 *
 */
public enum UsedStatus {

	UNREGISTERED(0,"未注册"),
	
	ENABLED(1,"启用"),
	
	DISABLED(2,"禁用");
	
	private int val;
	private String name;
	UsedStatus(int value,String name){
		this.val = value;
		this.name = name;
	}
	
	public int getValue(){
		return this.val;
	}
	
	public String getName(){
		return this.name;
	}
}
