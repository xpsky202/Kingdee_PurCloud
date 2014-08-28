package com.kingdee.purchase.platform.dao;

import java.util.List;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.api.ApiErrorCodeInfo;
import com.kingdee.purchase.platform.info.api.ApiInputParamInfo;
import com.kingdee.purchase.platform.info.api.ApiOutputParamInfo;
import com.kingdee.purchase.platform.info.api.ApiBaseInfo;

public interface IAPIRegisterDao extends IBaseDao<ApiBaseInfo> {
	
	/**
	 * 注册API
	 * 
	 * @param apiInfo API注册对象
	 */
	public void registerService(ApiBaseInfo apiInfo) throws PurDBException;
	
	/**
	 * 发布OPENAPI为静态HTML APIDOC
	 * 
	 * @author RD_cary_lin
	 */
	public void publishAPIDoc() ;
	
	/**
	 * 获取OpenApiInfo列表
	 * 
	 * @author RD_cary_lin
	 */
	public List<ApiBaseInfo> getOpenApiInfoList() throws PurDBException;
	
	/**
	 * 根据条件获取列表
	 * @param condition
	 * @return
	 * @throws BaseException
	 */
	public List<ApiBaseInfo> query(String condition) throws PurDBException;
	
	/***
	 * 查询api输入参数
	 * @return
	 * @throws PurDBException
	 */
	public List<ApiInputParamInfo> getApiInputParamList() throws PurDBException;
	
	/***
	 * 查询api输出参数
	 * @return
	 * @throws PurDBException
	 */
	public List<ApiOutputParamInfo> getApiOutputParamList() throws PurDBException;
	
	/***
	 * 查询错误码
	 * @return
	 * @throws PurDBException
	 */
	public List<ApiErrorCodeInfo> getApiErrorCodeList() throws PurDBException;
}