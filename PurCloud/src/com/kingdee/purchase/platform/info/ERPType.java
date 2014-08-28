package com.kingdee.purchase.platform.info;

public enum ERPType {

	EAS(1,"EAS"),
	K3_WISE(2,"K3 Wise"),
	K3_CLOUD(3,"K3 Cloud");
	
	private int value;
	private String name;
	
	ERPType(int v,String n){
		this.value=v;
		this.name=n;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static ERPType getErpType(int value){
		ERPType[] items = ERPType.values();
		for(ERPType t:items){
			if(t.getValue()==value){
				return t;
			}
		}
		
		return null;
	}
	
	public static ERPType getErpType(String name){
		ERPType[] items = ERPType.values();
		for(ERPType t:items){
			if(t.getName().equals(name)){
				return t;
			}
		}
		
		return null;
	}
}