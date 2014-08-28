/*
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.openapi.client.rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonProcessingException;

import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.policy.Protocol;
import com.alibaba.openapi.client.util.JsonSerializeHelper;


/**
 * 类BasicHttpRequestBuilder.java的实现描述：TODO 类实现描述
 * @author fray.yangb Aug 19, 2012 10:45:04 PM
 */
public class BasicHttpRequestBuilder extends AbstractHttpRequestBuilder {

    /**
     * @param clientPolicy
     */
    public BasicHttpRequestBuilder(ClientPolicy clientPolicy){
        super(clientPolicy);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.alibaba.openapi.client.rpc.AbstractHttpRequestBuilder#getProtocol()
     */
    @Override
    protected String getProtocol() {
        return Protocol.http.name();
    }

    /* (non-Javadoc)
     * @see com.alibaba.openapi.client.rpc.AbstractHttpRequestBuilder#buildParams(com.alibaba.openapi.client.rpc.InvokeContext)
     */
    @Override
    protected List<NameValuePair> buildParams(InvokeContext context) {
        Map<String, Object> reqParams = context.getRequest().getParams();
        final List<NameValuePair> params = new ArrayList<NameValuePair>(1);
        for (Map.Entry<String, Object> entry : reqParams.entrySet()) {
            Object value = entry.getValue();
            if(value != null){
                try {
                    String valueStr;
                    Class<?> valueType = value.getClass();
                    if(valueType.isPrimitive() || CharSequence.class.isAssignableFrom(valueType)
                            || Number.class.isAssignableFrom(valueType) || Boolean.class.equals(valueType)
                            || Character.class.equals(valueType)){
                        valueStr = value.toString();
                    }else{
                        valueStr = JsonSerializeHelper.object2json(entry.getValue());
                    }
                    params.add(new BasicNameValuePair(entry.getKey(), valueStr));
                } catch (JsonProcessingException e) {
                    throw new IllegalArgumentException("illegal argument " + entry.getKey() + ", error:" + e.getMessage(), e);
                } catch (IOException e) {
                    throw new IllegalArgumentException("illegal argument " + entry.getKey() + ", error:" + e.getMessage(), e);
                }
            }
        }
        return params;
    }

}
