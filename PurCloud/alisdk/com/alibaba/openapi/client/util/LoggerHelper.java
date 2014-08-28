/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-11-23
 * $Id: LoggerHelper.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
 *
 * Copyright 2008 Alibaba.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.openapi.client.util;

import java.util.logging.Logger;

/**
 * Comment of LoggerHelper
 * @author jade
 *
 */
public final class LoggerHelper {
    private static Logger       logger = Logger.getLogger("com.alibaba.openapi.client");

    public static Logger getClientLogger(){
        return logger;
    }

    private LoggerHelper(){}
}
