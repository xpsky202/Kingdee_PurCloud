/**
 * Project: ocean.client.java.basic
 * 
 * File Created at 2011-10-27
 * $Id: HttpResponseParser.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.io.InputStream;

import org.apache.http.HttpResponse;

import com.alibaba.openapi.client.Response;

/**
 * Comment of HttpResponseParser
 * @author jade
 *
 */
public interface HttpResponseParser {
    
    /**
     * @param httpResponse
     * @param invokeContext
     * @return {@link Response}
     */
    Response initResponse(final HttpResponse httpResponse, final InvokeContext invokeContext);

    /**
     * @param httpResponse
     * @param responseInStream
     * @param response
     * @param invokeContext
     */
    void parseResponse(final HttpResponse httpResponse, final InputStream responseInStream, final Response response, final InvokeContext invokeContext);

}
