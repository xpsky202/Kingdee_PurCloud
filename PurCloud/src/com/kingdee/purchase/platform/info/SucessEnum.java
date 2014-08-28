package com.kingdee.purchase.platform.info;

public enum SucessEnum {
	
	FAIL(0,"异常"),
	
	SUCCESS(1,"成功");
	
	private int val;
	
	private String name;
	SucessEnum(int value,String name){
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
