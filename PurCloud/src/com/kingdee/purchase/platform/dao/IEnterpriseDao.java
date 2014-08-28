package com.kingdee.purchase.platform.dao;

import java.util.List;

import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.EnterpriseInfo;

public interface IEnterpriseDao extends IBaseDao<EnterpriseInfo>{
	
	public List<EnterpriseInfo> query(String condition) throws PurDBException;
		
	public EnterpriseInfo getByAccountId(long accountId);
	
	public boolean isExistEnterpriseId(long enterpriseId);
	
	public boolean isExistName(long id,String name);
	
	public EnterpriseInfo getByEnterpriseId(long enterpriseId)throws PurDBException;
}