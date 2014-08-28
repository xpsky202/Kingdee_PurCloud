/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-26
 * $Id: AliNHttpRequstExecutionHandler.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.nio.ContentDecoder;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.entity.ConsumingNHttpEntity;
import org.apache.http.nio.entity.ConsumingNHttpEntityTemplate;
import org.apache.http.nio.entity.ContentInputStream;
import org.apache.http.nio.entity.ContentListener;
import org.apache.http.nio.protocol.NHttpRequestExecutionHandler;
import org.apache.http.nio.util.ByteBufferAllocator;
import org.apache.http.nio.util.SimpleInputBuffer;
import org.apache.http.protocol.HttpContext;

import com.alibaba.openapi.client.FutureCallback;
import com.alibaba.openapi.client.Response;
import com.alibaba.openapi.client.util.LoggerHelper;

/**
 * Comment of AliNHttpRequstExecutionHandler
 * 异步HTTP请求执行处理者，被当作客户端的协议处理者，用来触发新的HTTP请求的提交和HTTP响应的处理。
 * 
 * 真正处理http请求和进行http相应的类:
 * policy&SDK request->HttpRequest; 
 * httpinputStream->response->result
 * 
 * 响应客户端HTTP I/O事件
 * @author jade
 *
 */
public class AliNHttpRequstExecutionHandler implements NHttpRequestExecutionHandler {
    public static final String CONTEXT_ATTACHMENT = "context.attachment";
    private final ProtocolProvider protocolProvider;
    private final ByteBufferAllocator allocator;

    public AliNHttpRequstExecutionHandler(ProtocolProvider protocolProvider, ByteBufferAllocator allocator){
        this.protocolProvider = protocolProvider;
        this.allocator = allocator;
    }

    public void initalizeContext(HttpContext context, Object attachment) {
        context.setAttribute(CONTEXT_ATTACHMENT, attachment);
    }

    public HttpRequest submitRequest(HttpContext context) {
        //System.out.println("AliNHttpRequstExecutionHandler submitRequest。。。");
        InvokeContext invokeContext = (InvokeContext) context.getAttribute(CONTEXT_ATTACHMENT);
        if(invokeContext.isCompleted())return null;
        HttpRequest request = protocolProvider.getRequestBuilder(invokeContext.getPolicy().getRequestProtocol()).getHttpRequest(invokeContext);
        LoggerHelper.getClientLogger().finer("--submit Request--" + request.toString());
        return request;
    }
    //待完善,加入线程,在contentAvailable时处理
    public ConsumingNHttpEntity responseEntity(final HttpResponse httpResponse, final HttpContext context) throws IOException {
        //System.out.println("AliNHttpRequstExecutionHandler responseEntity。。。");

        LoggerHelper.getClientLogger().finer("Enter request handler's response entity.");
        final InvokeContext invokeContext = (InvokeContext) context.getAttribute(CONTEXT_ATTACHMENT);
        final Response response = protocolProvider.getResponseParser(invokeContext.getPolicy().getResponseProtocol()).initResponse(httpResponse, invokeContext);
        //ConsumingNHttpEntityTemplate委托ContentListener实例来处理入站内容
        ContentListener contentListener = new ContentListener() {
            private final SimpleInputBuffer buffer = new SimpleInputBuffer(2048, allocator);
            @SuppressWarnings("unchecked")
            public void finished() {
                InputStream istream = new ContentInputStream(this.buffer);
                protocolProvider.getResponseParser(invokeContext.getPolicy().getResponseProtocol()).parseResponse(httpResponse, istream, response, invokeContext);
                invokeContext.completed();
                if(response.getException() != null){
                    invokeContext.failed(response.getException());
                }else{
                    try{
                        ((FutureCallback<Object>) invokeContext.getCallback()).completed(response.getResult());
                    }catch (RuntimeException e) {
                        invokeContext.failed(e);
                    }
                }
            }
            //TODO 可用时，不应该直接缓存,可以处理
            public void contentAvailable(ContentDecoder decoder, IOControl ioctrl) throws IOException {
                this.buffer.consumeContent(decoder);
                LoggerHelper.getClientLogger().finer("content Listener "+ this.buffer.available());
            }
        };
        HttpEntity entity = httpResponse.getEntity();
        return new ConsumingNHttpEntityTemplate(entity, contentListener);
    }

    public void handleResponse(HttpResponse httpResponse, HttpContext context) throws IOException {
        //System.out.println("AliNHttpRequstExecutionHandler handleResponse。。。");

        //final InvokeContext invokeContext = (InvokeContext) context.getAttribute(CONTEXT_ATTACHMENT);
        //LoggerHelper.getClientLogger().finer("Enter request handler's handleResponse.");
    }

    public void finalizeContext(HttpContext context) {
        context.removeAttribute(CONTEXT_ATTACHMENT);
    }

}
