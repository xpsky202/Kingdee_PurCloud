package com.kingdee.purchase.destapi.factory;

import com.kingdee.purchase.destapi.IDestApiHandler;
import com.kingdee.purchase.platform.exception.PurSysException;

public interface IDestApiFactory {
	
	public IDestApiHandler getApiHandler(String apiName, int version) throws PurSysException;

}
