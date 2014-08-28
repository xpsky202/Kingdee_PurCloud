package com.kingdee.purchase.destapi.alibaba;

import com.kingdee.purchase.destapi.IDestApiHandler;
import com.kingdee.purchase.destapi.factory.IDestApiFactory;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.exception.PurSysException;
import com.kingdee.purchase.platform.util.StringUtils;

public class AlibabaApiFactory implements IDestApiFactory {

	public IDestApiHandler getApiHandler(String apiName, int version) throws PurSysException {
		String className = OpenApi2HandlerMap.getHandlerClass(apiName, version);
		if (StringUtils.isEmpty(className)) {
			throw new PurSysException(PurExceptionDefine.UNSUPPORT_API, new String[] {apiName});
		}
		IDestApiHandler handler = null;
		try {
			Class<?> clazz = Class.forName(className);
			handler = (IDestApiHandler)clazz.newInstance();
		} catch (ClassNotFoundException e) {
			throw new PurSysException(PurExceptionDefine.NOT_FOUND_DESTHANDLER, new String[] {className});
		} catch (InstantiationException e) {
			throw new PurSysException(PurExceptionDefine.CREATE_DESTHANDLER_ERROR, new String[] {className});
		} catch (IllegalAccessException e) {
			throw new PurSysException(PurExceptionDefine.CREATE_DESTHANDLER_ERROR, new String[] {className});
		}
		
		return handler;
	}

}
