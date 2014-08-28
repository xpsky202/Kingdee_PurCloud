/**
 * Project: alibaba-open_api-framework
 * 
 * File Created at 2009-12-11
 * $Id: SignatureUtil.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;

/**
 * SignatureUtil
 * 
 * @author yuming.wangym
 */
public final class SignatureUtil {
    public static final String  HMAC_SHA1         = "HmacSHA1";
    public static final String  CHARSET_NAME_UTF8 = "UTF-8";
    public static final Charset CHARSET_UTF8      = Charset.forName(CHARSET_NAME_UTF8);
    public static final char[]  digital           = "0123456789ABCDEF".toCharArray();

    public static SecretKeySpec buildKey(byte[] key) {
        return new SecretKeySpec(key, HMAC_SHA1);
    }
    
    public static String getKeyString(SecretKeySpec key){
        try {
            return  new String(key.getEncoded(),CHARSET_NAME_UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("key format error:"+e.getMessage());
        }
    }

    public static byte[] hmacSha1(byte[] data, SecretKeySpec signingKey) {
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return mac.doFinal(data);
    }

    public static String hmacSha1ToHexStr(byte[] data, SecretKeySpec key) {
        byte[] rawHmac = hmacSha1(data, key);
        return encodeHexStr(rawHmac);
    }

    public static String encodeHexStr(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder str = new StringBuilder(bytes.length * 2);
        encodeHexStr(bytes, str);
        return str.toString();
    }
    
    public static void encodeHexStr(final byte[] bytes, StringBuilder str){
        if(bytes != null){
            for (int i = 0; i < bytes.length; i++) {
                str.append(digital[(bytes[i] & 0xf0) >> 4]);
                str.append(digital[bytes[i] & 0x0f]);
            }
        }
    }

    public static byte[] hmacSha1(String[] datas, SecretKeySpec signingKey) {
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        for (String data : datas) {
            mac.update(data.getBytes(CHARSET_UTF8));
        }
        return mac.doFinal();
    }

    public static byte[] hmacSha1(String path, List<NameValuePair> parameters, SecretKeySpec signingKey) {
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        mac.update(path.getBytes(CHARSET_UTF8));
        Collections.sort(parameters, new NameValuePairComparator<NameValuePair>());
        for (NameValuePair parameter : parameters) {
            mac.update(parameter.getName().getBytes(CHARSET_UTF8));
            mac.update(parameter.getValue().getBytes(CHARSET_UTF8));
        }
        return mac.doFinal();
    }

    public static byte[] toBytes(final String str) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(CHARSET_NAME_UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
