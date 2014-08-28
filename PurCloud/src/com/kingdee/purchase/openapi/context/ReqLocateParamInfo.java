package com.kingdee.purchase.openapi.context;

public class ReqLocateParamInfo implements Cloneable {
	
	private String urlHead;
	
	private String  protocol;
	
	private String  version;
	
	private String  nameSpace;
	
	private String  apiName;

	public String getUrlHead() {
		return urlHead;
	}

	public void setUrlHead(String urlHead) {
		this.urlHead = urlHead;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	
	@Override
	public ReqLocateParamInfo clone() {
		ReqLocateParamInfo info = new ReqLocateParamInfo();
		info.urlHead = this.urlHead;
		info.apiName = this.apiName;
		info.nameSpace = this.nameSpace;
		info.protocol = this.protocol;
		info.version = this.version;
		
		return info;
	}
}
