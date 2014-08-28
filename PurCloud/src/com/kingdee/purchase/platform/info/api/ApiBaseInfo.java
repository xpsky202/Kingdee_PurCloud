package com.kingdee.purchase.platform.info.api;

import java.util.ArrayList;
import java.util.List;

import com.kingdee.purchase.platform.info.BaseInfo;

/**
 * api注册
 * @author RD_cary_lin
 */
public class ApiBaseInfo extends BaseInfo{
	//子系统
	private String subSystem;
	//子系统别名--中文名
	private String subSystemAlias;
	//api名称（英文）
	private String apiName;
	//api名称用户显示用
	private String apiNameVo;
	//api别名（中文）
	private String apiAlias;
	//版本
	private int version;
	//描述
	private String remark;
	
	private List<ApiInputParamInfo> inputParamList;
	private List<ApiOutputParamInfo> outputParamList;
	private List<ApiErrorCodeInfo> errorCodeList;

	public ApiBaseInfo(){
		super();
		inputParamList = new ArrayList<ApiInputParamInfo>();
		outputParamList = new ArrayList<ApiOutputParamInfo>();
		errorCodeList = new ArrayList<ApiErrorCodeInfo>();
	}
	
	//状态
	private String state;

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getApiAlias() {
		return apiAlias;
	}

	public void setApiAlias(String apiAlias) {
		this.apiAlias = apiAlias;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getSubSystemAlias() {
		return subSystemAlias;
	}
	
	public void setSubSystemAlias(String subSystemAlias) {
		this.subSystemAlias = subSystemAlias;
	}

	public List<ApiInputParamInfo> getInputParamList() {
		return inputParamList;
	}

	public List<ApiOutputParamInfo> getOutputParamList() {
		return outputParamList;
	}

	public List<ApiErrorCodeInfo> getErrorCodeList() {
		return errorCodeList;
	}

	public String getApiNameVo() {
		return apiNameVo;
	}

	public void setApiNameVo(String apiNameVo) {
		this.apiNameVo = apiNameVo;
	}	
}