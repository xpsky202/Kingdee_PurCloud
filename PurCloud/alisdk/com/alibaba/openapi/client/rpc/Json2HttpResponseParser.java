/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-27
 * $Id: Json2HttpResponseParser.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
import java.text.ParseException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.util.ExceptionParser;
import com.alibaba.openapi.client.util.JsonMapper;
import com.alibaba.openapi.client.util.LoggerHelper;

/**
 * Comment of Json2HttpResponseParser
 * @author jade
 *
 */
public class Json2HttpResponseParser extends AbstractHttpResponseParser {
    /**
     * @param clientPolicy
     */
    public Json2HttpResponseParser(ClientPolicy clientPolicy) {
        super(clientPolicy);
    }

    @Override
    protected boolean isContentTypeSupport(String contentType) {
        return contentType.startsWith("application/json");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T parseBody(Reader reader, Class<T> resultType) throws IOException, ParseException{
        LoggerHelper.getClientLogger().finer("Parse body by json.");
        /*StringWriter pw = new StringWriter();
        char c[] = new char[4096];
        int read = 0;
        while ((read = reader.read(c)) != -1)
            pw.write(c, 0, read);

        pw.close();
        System.out.println(pw.toString());
        reader = new StringReader(pw.toString());*/
        T result;
        if(resultType == null || Object.class.equals(resultType)){
            JsonNode json = JsonMapper.json2node(reader);
            if(json == null || json.isNull()){
                return null;
            }
            if(json.isTextual()){
                return (T) json.getTextValue();
            }
            if(json.isNumber()){
                return (T) json.getNumberValue();
            }
            if(json.isBoolean()){
                return (T) Boolean.valueOf(json.getBooleanValue());
            }
            if(json.isObject()){
                return (T) JsonMapper.node2map(json);
            }
            if(json.isArray()){
                return (T) JsonMapper.node2pojo(json, Object[].class);
            }
            if(json.isBinary()){
                return (T)json.getBinaryValue();
            }
            return (T)json.getTextValue();
        }
        try {
            result = JsonMapper.json2value(reader, resultType);
        } catch (JsonParseException e) {
            LoggerHelper.getClientLogger().warning("Pasre response body faild."+e.getMessage());
            throw new ParseException(e.getMessage(), 0);
        } catch (JsonMappingException e) {
            LoggerHelper.getClientLogger().warning("Pasre response body faild."+e.getMessage());
            throw new ParseException(e.getMessage(), 0);
        }
        return result;
    }

    @Override
    protected Throwable buildException(Reader reader, int statusCode) throws IOException, ParseException {
        Object result = parseBody(reader, null);
        return ExceptionParser.buildException4Json2(result);
    }
}
