package com.kingdee.purchase.openapi.client;

import java.util.LinkedHashMap;
import java.util.Map;

public class Request {

	private APIId apiId;
	private Map<String, Object> params;
	private Map<String, String> attachments;

	public Request(String namespace, String name) {
		this.apiId = new APIId(namespace, name);
	}

	public Request(String namespace, String name, int version) {
		this.apiId = new APIId(namespace, name, version);
	}

	public Request(APIId apiId) {
		this.apiId = apiId;
	}

	public APIId getApiId() {
		return this.apiId;
	}

	public Request setAttachment(String name, String value) {
		return this;
	}

	public Map<String, String> getAttachments() {
		if (this.attachments == null) {
			this.attachments = new LinkedHashMap<String, String>();
		}
		return this.attachments;
	}

	public Request setParam(String name, Object value) {
		getParams().put(name, value);
		return this;
	}

	public Map<String, Object> getParams() {
		if (this.params == null) {
			this.params = new LinkedHashMap<String, Object>();
		}
		return this.params;
	}
}
