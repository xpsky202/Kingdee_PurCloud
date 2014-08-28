/*
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.openapi.client.rpc;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.util.ExceptionParser;
import com.alibaba.openapi.client.util.JsonMapper;
import com.alibaba.openapi.client.util.LoggerHelper;


/**
 * 类BasicHttpResponseParser.java的实现描述：TODO 类实现描述
 * @author fray.yangb Aug 20, 2012 11:37:38 AM
 */
public class BasicHttpResponseParser extends AbstractHttpResponseParser {

    /**
     * @param clientPolicy
     */
    BasicHttpResponseParser(ClientPolicy clientPolicy){
        super(clientPolicy);
    }

    @Override
    protected boolean isContentTypeSupport(String contentType) {
        return contentType.startsWith("text/xml") || contentType.startsWith("text/plain");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T parseBody(Reader reader, Class<T> resultType) throws IOException, ParseException {
        LoggerHelper.getClientLogger().finer("Parse body by json.");

       StringWriter pw = new StringWriter();
       char c[] = new char[4096];
       int read = 0;
       while ((read = reader.read(c)) != -1){
           pw.write(c, 0, read);
       }
       pw.close();
       System.out.println(pw.toString());
       reader = new StringReader(pw.toString());
       
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
        //reader = new StringReader("{\"error\":\"invalid_request\",\"error_description\":\"wrong authorizationCode:6e8a7222-1328-4330-a031-be21ac64b107\"}");
        //System.out.println(pw.toString());
        Object result = parseBody(reader, null);
        return ExceptionParser.buildException4OAuth2(result);
    }

}
