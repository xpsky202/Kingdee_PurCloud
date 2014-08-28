package com.kingdee.purchase.platform.service;

import java.util.List;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.EnterpriseInfo;

public interface IEnterpriseService{

	public EnterpriseInfo get(long id) throws BaseException;

	public EnterpriseInfo getByEnterpriseId(long enterpriseId) throws BaseException;
	
	public int save(EnterpriseInfo info) throws BaseException;
	
	public List<EnterpriseInfo> query(String condition) throws BaseException;
	
	public EnterpriseInfo getByAccountId(long accountId) throws BaseException;
}
