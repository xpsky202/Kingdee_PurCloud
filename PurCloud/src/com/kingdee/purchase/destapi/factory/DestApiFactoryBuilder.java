package com.kingdee.purchase.destapi.factory;

import com.kingdee.purchase.destapi.alibaba.AlibabaApiFactory;

public final class DestApiFactoryBuilder {
	
	public static final IDestApiFactory getFactory(int DestSystemType) {
		return new AlibabaApiFactory();
	}

}
