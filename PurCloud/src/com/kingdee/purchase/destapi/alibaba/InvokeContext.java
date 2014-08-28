package com.kingdee.purchase.destapi.alibaba;

import java.util.Map;

import com.alibaba.openapi.client.ErrorHandler;

public class InvokeContext {
	
	private String apiName;
	
	private int apiVersion;
	
	private String refreshToken;
	
	private boolean isNeedAuth;
	
	private boolean isNeedSign;
	
	private boolean isNeedTimestamp;
	
	private Map<String, Object> params;
	
	private ErrorHandler handler;

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public int getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(int apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public boolean isNeedAuth() {
		return isNeedAuth;
	}

	public void setNeedAuth(boolean isNeedAuth) {
		this.isNeedAuth = isNeedAuth;
	}

	public boolean isNeedSign() {
		return isNeedSign;
	}

	public void setNeedSign(boolean isNeedSign) {
		this.isNeedSign = isNeedSign;
	}

	public boolean isNeedTimestamp() {
		return isNeedTimestamp;
	}

	public void setNeedTimestamp(boolean isNeedTimestamp) {
		this.isNeedTimestamp = isNeedTimestamp;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public ErrorHandler getHandler() {
		return handler;
	}

	public void setHandler(ErrorHandler handler) {
		this.handler = handler;
	}
}
