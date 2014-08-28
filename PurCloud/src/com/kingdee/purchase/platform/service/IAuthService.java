package com.kingdee.purchase.platform.service;

import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.AccountInfo;

public interface IAuthService {
	
	AccountInfo loginByUsername(String username,String password) throws PurBizException;
	
	AccountInfo loginByEmail(String username,String password) throws PurBizException;
}
