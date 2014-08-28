package com.kingdee.purchase.platform.info.api;

import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.model.IJSONTransfer;

/**
 * API入参
 * @author RD_cary_lin
 */
public class ApiInputParamInfo implements IJSONTransfer{
	
	//关联的api
	private long parentid;
	//序号
	private int seq;
	//参数名
	private String field;
	//参数类型
	private String type;
	//描述
	private String description;
	//是否为空
	private boolean isNull;
	//示例
	private String example;
	//是否系统参数
	private boolean isSysParam;
	//默认值
	private String defaultValue;
	
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isIsNull() {
		return isNull;
	}
	public void setIsNull(boolean isNull) {
		this.isNull = isNull;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	
	public boolean isIsSysParam() {
		return isSysParam;
	}
	
	public void setIsSysParam(boolean isSysParam) {
		this.isSysParam = isSysParam;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public long getParentid() {
		return parentid;
	}
	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
	public JSONObject toJSONObject() {
		return null;
	}	
}