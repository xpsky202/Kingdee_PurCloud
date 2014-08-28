package com.kingdee.purchase.platform.dao;

import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.BaseInfo;

public interface IBaseDao <T extends BaseInfo> {

	public T get(long id) throws PurDBException;
	
	public int insert(T t) throws PurDBException;
	
	public int update(T t) throws PurDBException;
	
	public int delete(long id) throws PurDBException;
}