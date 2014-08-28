/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-25
 * $Id: AbstractHttpRequestBuilder.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.DefaultHttpRequestFactory;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.openapi.client.Request;
import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.policy.RequestPolicy;
import com.alibaba.openapi.client.rpc.io.GzipCompressingNEntity;
import com.alibaba.openapi.client.rpc.io.UrlEncodedFormEntity;
import com.alibaba.openapi.client.util.SignatureUtil;
import com.alibaba.openapi.client.util.URLEncodedUtils;

/**
 * Comment of AbstractHttpRequestBuilder
 * @author jade
 *
 */
public abstract class AbstractHttpRequestBuilder implements HttpRequestBuilder {
    public static final String   METHOD_GET                 = "GET";
    public static final String   METHOD_POST                = "POST";

    public static final char     QUERY_STRING_SEPARATOR     = '?';
    public static final char     PARAMETER_SEPARATOR        = '&';
    public static final char     NAME_VALUE_SEPARATOR       = '=';

    public static final String   PARAM_NAME_SIGNATURE       = "_aop_signature";
    public static final String   PARAM_NAME_TIMESTAMP       = "_aop_timestamp";
    public static final String   PARAM_NAME_RESPONSE_FORMAT = "_aop_responseFormat";
    public static final String   PARAM_NAME_ACCESS_TOKEY    = "access_token";

    protected HttpRequestFactory requestFactory;
    protected ClientPolicy       clientPolicy;

    public AbstractHttpRequestBuilder(ClientPolicy clientPolicy){
        requestFactory = new DefaultHttpRequestFactory();
        this.clientPolicy = clientPolicy;
    }

    abstract protected String getProtocol();

    abstract protected List<NameValuePair> buildParams(InvokeContext context);

    /**
     * url format:
     * (http|https)://(gw.open.1688.com)/(api|openapi)[/protocol[/defaultVersion[/defaultNamespace[/apiName[/appKey]]]]][?queryString]
     * @param context
     * @return {@link StringBuilder}
     */
    protected StringBuilder getApiRequestPath(InvokeContext context, RequestPolicy requestPolicy){
        StringBuilder path = new StringBuilder();
          if(requestPolicy.isAccessPrivateApi()){
              path.append("/api");
          }else{
            path.append("/openapi");
          }
        return path;
    }

    protected StringBuilder getProtocolRequestPath(InvokeContext context, String protocol){
        StringBuilder path = new StringBuilder();
        path.append(protocol).append('/');
        Request request = context.getRequest();
        if(request.getApiId().getVersion()<0){
            path.append(clientPolicy.getDefaultVersion());
        }else{
            path.append(request.getApiId().getVersion());
        }
        path.append('/').append(request.getApiId().getNamespace()).append('/').append(request.getApiId().getName());
        if(clientPolicy.getAppKey() != null){
            path.append('/').append(clientPolicy.getAppKey());
        }
        return path;
    }

    protected void buildSysParams(List<NameValuePair> params, StringBuilder queryString, InvokeContext context, RequestPolicy requestPolicy){
        if(!requestPolicy.getRequestProtocol().equals(requestPolicy.getResponseProtocol())){
            params.add(new BasicNameValuePair(PARAM_NAME_RESPONSE_FORMAT, requestPolicy.getResponseProtocol().name()));
        }
    }

    protected void signature(StringBuilder path, StringBuilder queryString, List<NameValuePair> parameters, RequestPolicy requestPolicy){
        if(!requestPolicy.isUseSignture())return;
        if((clientPolicy.getAppKey() == null|| "".equals(clientPolicy.getAppKey())) || (clientPolicy.getSigningKey() == null || "".equals(clientPolicy.getSigningKey()))){
            return;
        }
        byte[] sign = SignatureUtil.hmacSha1(path.toString(), parameters, clientPolicy.getSigningKey());
        if(sign != null && sign.length > 0){
            if(queryString.length() > 0){
                queryString.append(PARAMETER_SEPARATOR);
            }
            queryString.append(PARAM_NAME_SIGNATURE).append(NAME_VALUE_SEPARATOR);
            SignatureUtil.encodeHexStr(sign, queryString);
        }
    }

    public HttpRequest getHttpRequest(InvokeContext context){
        final RequestPolicy requestPolicy = context.getPolicy();
        final StringBuilder path = getProtocolRequestPath(context, getProtocol());
        final StringBuilder queryString = new StringBuilder();
        final String method;
        final List<NameValuePair> parameters = buildParams(context);
        buildSysParams(parameters, queryString, context, requestPolicy);
        HttpEntity entity = null;
        switch(requestPolicy.getHttpMethod()){
            case POST:
                method = METHOD_POST;
                break;
            case GET:
                method = METHOD_GET;
                break;
            case AUTO:
                if(hasParameters(parameters)||hasAttachments(context.getRequest())){
                    method = METHOD_POST;
                }else{
                    method = METHOD_GET;
                }
                break;
            default:
                method = METHOD_POST;
        }
        if(hasParameters(parameters)){
            if(METHOD_GET.equals(method)){
                if(queryString.length()>0){
                    queryString.append(PARAMETER_SEPARATOR);
                }
                queryString.append(URLEncodedUtils.format(parameters, requestPolicy.getQueryStringCharset()));
            }else{
                try {
                    entity = new UrlEncodedFormEntity(parameters, requestPolicy.getContentCharset());
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
        }
        signature(path, queryString, parameters, requestPolicy);
        HttpRequest httpRequest;
        try {
            httpRequest = requestFactory.newHttpRequest(method, getApiRequestPath(context, requestPolicy).append('/').append(path)
                    .append(QUERY_STRING_SEPARATOR).append(queryString).toString());
        } catch (MethodNotSupportedException e) {
            throw new UnsupportedOperationException("Unsupported http request method:" + e.getMessage(), e);
        }
        if(httpRequest instanceof BasicHttpEntityEnclosingRequest){
            if(hasAttachments(context.getRequest())){
                MultipartEntity multipartEntity = new MultipartEntity();
                for(Entry<String,String> entry:context.getRequest().getAttachments().entrySet()){
                    File file = new File(entry.getValue());
                    multipartEntity.addPart(entry.getKey(), new FileBody(file));
                }
                entity = multipartEntity;
            }else if(requestPolicy.getRequestCompressThreshold() >= 0 && entity.getContentLength() > requestPolicy.getRequestCompressThreshold()){
                entity = new GzipCompressingNEntity(entity);
                httpRequest.addHeader("Content-Encoding","gzip");
            }
            ((BasicHttpEntityEnclosingRequest)httpRequest).setEntity(entity);
        }
//        httpRequest.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        httpRequest.addHeader("Accept-Charset", "gb18030,utf-8;q=0.7,*;q=0.3");
//        httpRequest.addHeader("Accept-Encoding", "gzip,deflate,sdch");
//        httpRequest.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
//        httpRequest.addHeader("Cache-Control", "max-age=0");
//        httpRequest.addHeader("Connection", "keep-alive");
        return httpRequest;
    }

    /**
     * @param request
     * @return
     */
    private boolean hasAttachments(Request request) {
        return request.getAttachments()!=null&&request.getAttachments().size()>0;
    }

    private boolean hasParameters(final List<NameValuePair> parameters) {
        return parameters != null && parameters.size() > 0;
    }

}
