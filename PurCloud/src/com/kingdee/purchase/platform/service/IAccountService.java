package com.kingdee.purchase.platform.service;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.AccountInfo;
import com.kingdee.purchase.platform.info.EnterpriseInfo;

public interface IAccountService {

	/***
	 * 不修改密码信息
	 * @param info
	 * @throws BaseException
	 */
	public void updateAccountInfo(AccountInfo info)throws BaseException;

	public void createAccountInfo(AccountInfo info,EnterpriseInfo enterpriseInfo)throws BaseException;
	
	public AccountInfo getAccountInfo(long id) throws BaseException;
}