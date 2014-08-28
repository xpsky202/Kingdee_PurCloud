package com.kingdee.purchase.platform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.dao.IAPIRegisterDao;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.api.ApiBaseInfo;
import com.kingdee.purchase.platform.service.IAPIRegisterService;

@Service
public class APIRegisterServiceImpl implements IAPIRegisterService{
	
	@Resource
	private IAPIRegisterDao registerDao;
	
	public void publishAPIDoc() {
		
	}

	public void registerService(ApiBaseInfo apiInfo) throws PurDBException {
		
		registerDao.registerService(apiInfo);
	}

	public List<ApiBaseInfo> getOpenApiInfoList() throws PurDBException{
		return registerDao.getOpenApiInfoList();
	}

	public ApiBaseInfo get(long id) throws BaseException {
		return registerDao.get(id);
	}

	public List<ApiBaseInfo> query(String condition) throws BaseException {
		return registerDao.query(condition);
	}

	public int deleteById(long id) throws BaseException {
		return registerDao.delete(id);
		
	}
	
}

