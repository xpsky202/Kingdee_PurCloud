package com.kingdee.purchase.openapi.context;

import java.util.Map;

import javax.persistence.OneToOne;

public class RequestContext implements Cloneable {
	
	@OneToOne
	private SysParamInfo sysParamInfo;
	
	@OneToOne
	private ReqLocateParamInfo locateParamInfo;
	
	private Map<String, Object> bussinessParams;

	public SysParamInfo getSysParamInfo() {
		return sysParamInfo;
	}

	public void setSysParamInfo(SysParamInfo sysParamInfo) {
		this.sysParamInfo = sysParamInfo;
	}

	public ReqLocateParamInfo getLocateParamInfo() {
		return locateParamInfo;
	}

	public void setLocateParamInfo(ReqLocateParamInfo locateParamInfo) {
		this.locateParamInfo = locateParamInfo;
	}

	public Map<String, Object> getBussinessParams() {
		return bussinessParams;
	}

	public void setBussinessParams(Map<String, Object> bussinessParams) {
		this.bussinessParams = bussinessParams;
	}
	
	@Override
	public RequestContext clone() {
		RequestContext newCtx = new RequestContext();
		newCtx.sysParamInfo = this.sysParamInfo.clone();
		newCtx.locateParamInfo = this.locateParamInfo.clone();
		newCtx.bussinessParams = this.bussinessParams;
		
		return newCtx;
	}
}
