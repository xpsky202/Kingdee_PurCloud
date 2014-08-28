/**
 * Project: ocean.client.java.basic
 * 
 * File Created at 2011-11-21
 * $Id: RequestAcceptEncoding.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
package com.alibaba.openapi.client.rpc.io;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import com.alibaba.openapi.client.rpc.AliNHttpRequstExecutionHandler;
import com.alibaba.openapi.client.rpc.InvokeContext;

/**
 * Comment of RequestAcceptEncoding
 * @author jade
 *
 */
public class RequestAcceptEncoding implements HttpRequestInterceptor {
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    
    public RequestAcceptEncoding() {
        super();
    }

    public void process(final HttpRequest request, final HttpContext context)
        throws HttpException, IOException {
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (!request.containsHeader(ACCEPT_ENCODING)) {
            final InvokeContext invokeContext = (InvokeContext) context.getAttribute(AliNHttpRequstExecutionHandler.CONTEXT_ATTACHMENT);
            if(invokeContext.getPolicy().isResponseCompress()){
                request.addHeader(ACCEPT_ENCODING, "gzip,deflate");
            }
        }
    }

}

