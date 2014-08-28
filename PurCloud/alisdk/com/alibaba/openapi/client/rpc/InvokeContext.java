/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-18
 * $Id: InvokeContext.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import com.alibaba.openapi.client.FutureCallback;
import com.alibaba.openapi.client.Request;
import com.alibaba.openapi.client.Response;
import com.alibaba.openapi.client.policy.RequestPolicy;

/**
 * Comment of InvokeContext
 *
 * @author jade
 */

public class InvokeContext {
    private Queue<Request>    requestQueue;
    private boolean           completed;
    private Request           request;
    private Response          response;
    private FutureCallback<?> callback;
    private RequestPolicy     policy;
    private Class<?>          resultType;

    public InvokeContext(){
        requestQueue = new ConcurrentLinkedQueue<Request>();
    }

    public synchronized Request pollRequest(){
        this.request = requestQueue.poll();
        return this.request;
    }

    public synchronized void addRequest(Request request){
        this.requestQueue.add(request);
    }

    public synchronized boolean isCompleted(){
        return completed;
    }

    public synchronized void completed(){
        completed = true;
        this.notifyAll();
    }

    public synchronized void failed(Throwable exception){
        response = new Response();
        response.setException(exception);
        callback.failed(exception);
        //if(!isCompleted())completed();
    }

    public synchronized void waitForComplete() throws InterruptedException{
        while(!isCompleted()){
            this.wait(4000);
        }
    }

    public synchronized void waitForComplete(long timeout, TimeUnit unit) throws InterruptedException{
        if(!isCompleted()){
            this.wait(unit.toMillis(timeout));
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public FutureCallback<?> getCallback() {
        return callback;
    }

    public void setCallback(FutureCallback<?> callback) {
        this.callback = callback;
    }

    public RequestPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(RequestPolicy policy) {
        this.policy = policy;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }
}
