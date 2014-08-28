/**
 * Project: ocean.client.java.basic
 * 
 * File Created at 2011-10-25
 * $Id: UrlEncodedFormEntity.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import com.alibaba.openapi.client.util.URLEncodedUtils;

/**
 * An entity composed of a list of url-encoded pairs.
 * This is typically useful while sending an HTTP POST request.
 */
public class UrlEncodedFormEntity extends StringEntity {

    /**
     * Constructs a new {@link UrlEncodedFormEntity} with the list
     * of parameters in the specified encoding.
     *
     * @param parameters list of name/value pairs
     * @param encoding encoding the name/value pairs be encoded with
     * @throws UnsupportedEncodingException if the encoding isn't supported
     */
    public UrlEncodedFormEntity (
        final List <? extends NameValuePair> parameters,
        final String encoding) throws UnsupportedEncodingException {
        super(URLEncodedUtils.format(parameters, encoding),
            encoding);
        setContentType(URLEncodedUtils.CONTENT_TYPE);
    }

    /**
     * Constructs a new {@link UrlEncodedFormEntity} with the list
     * of parameters with the default encoding of {@link HTTP#DEFAULT_CONTENT_CHARSET}
     *
     * @param parameters list of name/value pairs
     * @throws UnsupportedEncodingException if the default encoding isn't supported
     */
    public UrlEncodedFormEntity (
        final List <? extends NameValuePair> parameters) throws UnsupportedEncodingException {
        super(URLEncodedUtils.format(parameters, HTTP.DEFAULT_CONTENT_CHARSET),
            HTTP.DEFAULT_CONTENT_CHARSET);
        setContentType(URLEncodedUtils.CONTENT_TYPE);
    }

}