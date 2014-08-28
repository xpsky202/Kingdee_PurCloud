/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-27
 * $Id: ProtocolProvider.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.util.HashMap;
import java.util.Map;

import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.policy.Protocol;

/**
 * Comment of ProtocolProvider
 * @author jade
 *
 */
public class ProtocolProvider {
    public final static String OAuth2 = "OAUTH2";
    private final Map<String, HttpRequestBuilder> requestBuilderMap;
    private final Map<String, HttpResponseParser> responseParserMap;

    public HttpRequestBuilder getRequestBuilder(Protocol protocol){
        return getRequestBuilder(protocol.name());
    }
    public HttpRequestBuilder getRequestBuilder(String protocol){
        if(protocol == null){
            throw new IllegalArgumentException("protocol can not be null");
        }
        HttpRequestBuilder result = requestBuilderMap.get(protocol);
        if(result == null){
            throw new IllegalArgumentException("unsport request protocol: " + protocol);
        }
        return result;
    }

    public HttpResponseParser getResponseParser(Protocol protocol){
        return getResponseParser(protocol.name());
    }
    public HttpResponseParser getResponseParser(String protocol){
        if(protocol == null){
            throw new IllegalArgumentException("protocol can not be null");
        }
        HttpResponseParser result = responseParserMap.get(protocol);
        if(result == null){
            throw new IllegalArgumentException("unsport request protocol: " + protocol);
        }
        return result;
    }

    public ProtocolProvider(ClientPolicy clientPolicy){
        requestBuilderMap = new HashMap<String, HttpRequestBuilder>();
        HttpRequestBuilder builder = new Param2HttpRequestBuilder(clientPolicy);
        addRequestBuilder(Protocol.param2, builder);
        addRequestBuilder(Protocol.param, builder);

        responseParserMap = new HashMap<String, HttpResponseParser>();
        HttpResponseParser parser = new Json2HttpResponseParser(clientPolicy);
        HttpResponseParser xmlParser = new Xml2HttpResponseParser(clientPolicy);
        addResponseParser(Protocol.param2, parser);
        addResponseParser(Protocol.json2, parser);
        addResponseParser(Protocol.xml2, xmlParser);

        //TODO OAuth2
        addRequestBuilder(Protocol.http, new BasicHttpRequestBuilder(clientPolicy));
        addResponseParser(Protocol.http, new BasicHttpResponseParser(clientPolicy));
    }

    public void addRequestBuilder(Protocol protocol, HttpRequestBuilder requestBuilder){
        addRequestBuilder(protocol.name(), requestBuilder);
    }
    public void addRequestBuilder(String protocol, HttpRequestBuilder requestBuilder){
        requestBuilderMap.put(protocol, requestBuilder);
    }

    public void addResponseParser(Protocol protocol, HttpResponseParser responseParser){
        addResponseParser(protocol.name(), responseParser);
    }
    public void addResponseParser(String protocol, HttpResponseParser responseParser){
        responseParserMap.put(protocol, responseParser);
    }
}
