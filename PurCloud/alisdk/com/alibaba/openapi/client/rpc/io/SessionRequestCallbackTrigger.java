/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-11-22
 * $Id: SessionRequestCallbackTrigger.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import org.apache.http.nio.reactor.SessionRequest;
import org.apache.http.nio.reactor.SessionRequestCallback;

import com.alibaba.openapi.client.exception.InvokeConnectException;
import com.alibaba.openapi.client.exception.InvokeTimeoutException;
import com.alibaba.openapi.client.rpc.InvokeContext;
import com.alibaba.openapi.client.util.LoggerHelper;

/**
 * Comment of FutureCallbackTrigger
 * 异步监控session创建事件
 * @author jade
 *
 */
public class SessionRequestCallbackTrigger implements SessionRequestCallback {
    public void cancelled(final SessionRequest request) {
        LoggerHelper.getClientLogger().finer("Enter SessionRequestCallbackTrigger cancelled.");
        InvokeContext context = (InvokeContext) request.getAttachment();
        if(context != null){
            context.completed();
            context.getCallback().cancelled();
        }
    }

    //TODO Session创建成功后
    public void completed(final SessionRequest request) {
        System.out.println("SessionRequestCallbackTrigger completed。。。");
        LoggerHelper.getClientLogger().finer("Enter SessionRequestCallbackTrigger completed.");
    }

    public void failed(final SessionRequest request) {
        System.out.println("SessionRequestCallbackTrigger failed。。。");
        LoggerHelper.getClientLogger().finer("Enter SessionRequestCallbackTrigger failed.");
        InvokeContext context = (InvokeContext) request.getAttachment();
        if(context != null){
            context.completed();
            LoggerHelper.getClientLogger().finer(request.getException().getCause().toString());
            context.failed(new InvokeConnectException("Request call failed", request.getException()));
        }
    }

    public void timeout(final SessionRequest request) {
        System.out.println("SessionRequestCallbackTrigger timeout。。。");
        LoggerHelper.getClientLogger().finer("Enter SessionRequestCallbackTrigger timeout.");
        InvokeContext context = (InvokeContext) request.getAttachment();
        if(context != null){
            context.completed();
            context.failed(new InvokeTimeoutException("Request call timeout"));
        }
    }
}
