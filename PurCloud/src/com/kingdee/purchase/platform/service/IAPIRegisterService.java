package com.kingdee.purchase.platform.service;

import java.util.List;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.api.ApiBaseInfo;

public interface IAPIRegisterService {
	
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
	public void publishAPIDoc();
	
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
	public List<ApiBaseInfo> query(String condition) throws BaseException;
	
	/**
	 * 根据ID查询apiInfo
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public ApiBaseInfo get(long id) throws BaseException;
	
	/**
	 * 根据ID删除apiInfo
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public int deleteById(long id) throws BaseException;
	
}