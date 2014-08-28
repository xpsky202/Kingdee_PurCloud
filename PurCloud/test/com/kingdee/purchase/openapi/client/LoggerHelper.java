package com.kingdee.purchase.openapi.client;

import java.util.logging.Logger;

public final class LoggerHelper {
	
	private static Logger logger = Logger.getLogger("com.alibaba.openapi.client");

    public static Logger getClientLogger(){
        return logger;
    }

    private LoggerHelper(){}
}
