/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-11-28
 * $Id: AbstractHttpResponseParser.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.alibaba.openapi.client.Response;
import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.util.LoggerHelper;

/**
 * Comment of AbstractHttpResponseParser
 *
 * @author jade
 */
public abstract class AbstractHttpResponseParser implements HttpResponseParser {
    private static final String HEADER_NAME_CONTENT_ENCODING            = "Content-Encoding";
    protected static final String CONTENT_ENCODING_GZIP                   = "gzip";
    private static final String HEADER_NAME_CONTENT_TYPE                = "Content-Type";
    private static final String HEADER_FLAG_CONTENT_TYPE_CHARSET        = ";charset=";
    private static final int    HEADER_FLAG_LENGTH_CONTENT_TYPE_CHARSET = HEADER_FLAG_CONTENT_TYPE_CHARSET.length();
    protected ClientPolicy      clientPolicy;

    AbstractHttpResponseParser(ClientPolicy clientPolicy){
        this.clientPolicy = clientPolicy;
    }

    abstract protected boolean isContentTypeSupport(String contentType);
    abstract protected <T> T parseBody(Reader reader, Class<T> resultType) throws IOException, ParseException;
    abstract protected Throwable buildException(Reader reader, int statusCode) throws IOException, ParseException;

    public Response initResponse(HttpResponse httpResponse, InvokeContext invokeContext) {
        LoggerHelper.getClientLogger().finer("--Start init Response--");
        final Response response = new Response();
        invokeContext.setResponse(response);
        final int statusCode = httpResponse.getStatusLine().getStatusCode();
        response.setStatusCode(statusCode);
        Header contentEncoding = httpResponse.getFirstHeader(HEADER_NAME_CONTENT_ENCODING);
        if (contentEncoding != null) {
            response.setEncoding(contentEncoding.getValue());
        }
        Header contentType = httpResponse.getFirstHeader(HEADER_NAME_CONTENT_TYPE);
        if (contentType == null) {
            response.setException(new IllegalStateException("response need content type"));
        } else{
            if (!isContentTypeSupport(contentType.getValue())) {
                response.setException(new IllegalStateException("unsport response content type:" + contentType.getValue()));
            }
            int flag = contentType.getValue().indexOf(HEADER_FLAG_CONTENT_TYPE_CHARSET);
            if (flag > 0) {
                response.setCharset(contentType.getValue().substring(flag + HEADER_FLAG_LENGTH_CONTENT_TYPE_CHARSET));
            }
        }
        return response;
    }

    public void parseResponse(HttpResponse httpResponse, InputStream istream, Response response, InvokeContext invokeContext) {
        try {
            if (CONTENT_ENCODING_GZIP.equalsIgnoreCase(response.getEncoding())) {
                LoggerHelper.getClientLogger().finer(" translate InputStream to GZIPInputStream ");
                istream = new GZIPInputStream(istream);
            }
            final Reader reader = new InputStreamReader(istream, response.getCharset());
            LoggerHelper.getClientLogger().finer("Response status code :"+response.getStatusCode());
            if (response.getStatusCode() == 200) {
                Object result = parseBody(reader, invokeContext.getResultType());
                response.setResult(result);
            }else{
                Throwable exception = buildException(reader, response.getStatusCode());
                response.setException(exception);
            }
        //Todo 授权、加强异常处理
        } catch (IOException e) {
            response.setException(e);
        } catch (RuntimeException e) {
            response.setException(e);
        } catch (ParseException e) {
            response.setException(e);
        }
    }
}
