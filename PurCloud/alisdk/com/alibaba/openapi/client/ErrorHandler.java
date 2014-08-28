/*
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.openapi.client;

import com.alibaba.openapi.client.exception.OceanException;

/**
 * 类ErrorHandler.java的实现描述：业务异常处理
 * @author fray.yangb Aug 15, 2012 4:00:33 PM
 */
public interface ErrorHandler {
    void handle(OceanException targetException);
}
