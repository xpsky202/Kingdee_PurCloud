package com.kingdee.purchase.platform.dao;

import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.AccountInfo;

public interface IAccountDao extends IBaseDao<AccountInfo>{

	public AccountInfo getByUsername(String username) throws PurDBException;
	
	public AccountInfo getByEmail(String email) throws PurDBException;

	public boolean isExistName(long id,String username);
	
	public boolean isExistEmail(long id,String email);
	
	public boolean changePassword(long id,String password);
}