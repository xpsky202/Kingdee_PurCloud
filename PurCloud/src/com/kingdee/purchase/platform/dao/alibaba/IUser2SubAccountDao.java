package com.kingdee.purchase.platform.dao.alibaba;

import com.kingdee.purchase.platform.dao.IBaseDao;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.alibaba.User2SubAccountInfo;

public interface IUser2SubAccountDao extends IBaseDao<User2SubAccountInfo>{

	public User2SubAccountInfo get(long enterpriseid,String companyId,String userid) throws PurDBException;
	
	public User2SubAccountInfo get(String subAccountId) throws PurDBException;
}
