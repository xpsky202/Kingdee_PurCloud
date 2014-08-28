package com.kingdee.purchase.platform.service.alibaba;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.alibaba.User2SubAccountInfo;

public interface IUser2SubAccountService {

	public User2SubAccountInfo getUser2SubAccountInfo(long enterpriseId,String companyId,String userId) throws BaseException;
	
	public User2SubAccountInfo getUser2SubAccountInfo(String subAccountId) throws BaseException;
	
	public int saveUser2SubAccount(long enterpriseId,String companyId,String userId,String subAccountId) throws BaseException;	
}