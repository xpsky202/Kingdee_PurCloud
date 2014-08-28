package com.kingdee.purchase.platform.service;

import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.AccountInfo;

public interface ILoginService {
	
	AccountInfo loginByUsername(String username,String password) throws PurBizException;
	
	AccountInfo loginByEmail(String username,String password) throws PurBizException;
	
	public boolean chgPassword(long id, String oldPassword,String password,String rePassword) throws PurBizException;
}
