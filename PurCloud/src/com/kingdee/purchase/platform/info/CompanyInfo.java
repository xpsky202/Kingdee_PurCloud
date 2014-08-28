package com.kingdee.purchase.platform.info;

public class CompanyInfo {
	
	private String id;
	
	private String name;
	
	public CompanyInfo(){
		super();
	}
	
	public CompanyInfo(String id,String name){
		super();
		setId(id);
		setName(name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
