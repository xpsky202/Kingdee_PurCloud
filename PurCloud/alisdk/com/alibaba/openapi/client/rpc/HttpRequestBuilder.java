/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-25
 * $Id: HttpRequestBuilder.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
package com.alibaba.openapi.client.rpc;

import org.apache.http.HttpRequest;

/**
 * HttpRequestBuilder
 *
 * @author jade
 */
public interface HttpRequestBuilder {

    HttpRequest getHttpRequest(InvokeContext context);

}
