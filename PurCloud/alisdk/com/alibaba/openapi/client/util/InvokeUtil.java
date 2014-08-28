/**
 * Project: ocean.client.java.basic
 * 
 * File Created at 2011-6-20
 * $Id: InvokeUtil.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
package com.alibaba.openapi.client.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;


/**
 * API 用于生成签名的工具类
 * @author xiaoning.qxn
 *
 */
public class InvokeUtil {

    /**
     * 
     * @param nameValueList
     * @param secretKey
     * @param signatureDataPref
     * @return the signature
     */
    public static String signatureData(List<NameValuePair> nameValueList, String secretKey, String signatureDataPref){
        final List<String> paramValueList = new ArrayList<String>(nameValueList.size());
        for (NameValuePair nameValuePair : nameValueList) {
            paramValueList.add(nameValuePair.getName() + nameValuePair.getValue());
        }
        return signature(paramValueList, secretKey, signatureDataPref);
    }
    
    /**
     * 
     * @param params
     * @param secretKey
     * @param signatureDataPref
     * @return the signature
     */
    public static String signatureData(Map<String, Object> params, String secretKey, String signatureDataPref){
        final List<String> paramValueList = new ArrayList<String>(params.size());
        for (String key : params.keySet()) {
            paramValueList.add(key + params.get(key));
        }
        return signature(paramValueList, secretKey, signatureDataPref);
    }
    
    /**
     * 
     * @param appKey
     * @param secretKey
     * @param param
     * @return the signature
     */
    public static String signatureData(int appKey, String secretKey, String param){
        String[] datas = new String[2];
        datas[0] = Integer.toString(appKey);
        datas[1] = param;
        return SignatureUtil.encodeHexStr(SignatureUtil.hmacSha1(datas, SignatureUtil.buildKey(SignatureUtil.toBytes(secretKey))));
    }
    
    private static String signature(List<String> paramValueList, String secretKey, String signatureDataPref){
        final StringBuilder sb = signatureDataPref==null?new StringBuilder(): new StringBuilder(signatureDataPref);
        Collections.sort(paramValueList);
        for (String paramValue : paramValueList) {
            sb.append(paramValue);
        }
        return SignatureUtil.hmacSha1ToHexStr(SignatureUtil.toBytes(sb.toString()), SignatureUtil.buildKey(SignatureUtil.toBytes(secretKey)));
    }
}
