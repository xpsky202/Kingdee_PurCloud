/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-27
 * $Id: Xml2HttpResponseParser.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
import java.io.Reader;
import java.io.StringWriter;
import java.text.ParseException;

import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.util.ExceptionParser;
import com.alibaba.openapi.client.util.LoggerHelper;

/**
 * Comment of Json2HttpResponseParser
 * @author jade
 *
 */
public class Xml2HttpResponseParser extends AbstractHttpResponseParser {
    /**
     * @param clientPolicy
     */
    public Xml2HttpResponseParser(ClientPolicy clientPolicy) {
        super(clientPolicy);
    }

    @Override
    protected boolean isContentTypeSupport(String contentType) {
        return contentType.startsWith("text/xml");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T parseBody(Reader reader, Class<T> resultType) throws IOException, ParseException{
        LoggerHelper.getClientLogger().finer("Parse body by xml.");
        StringWriter pw = new StringWriter();
        char c[] = new char[4096];
        int read = 0;
        while ((read = reader.read(c)) != -1)
            pw.write(c, 0, read);

        pw.close();
        return (T) pw.toString();
    }

    @Override
    protected Throwable buildException(Reader reader, int statusCode) throws IOException, ParseException {
        Object result = parseBody(reader, null);
        return ExceptionParser.buildException4Json2(result);
    }
}
