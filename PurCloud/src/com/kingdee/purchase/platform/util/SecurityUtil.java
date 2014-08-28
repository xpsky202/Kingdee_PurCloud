package com.kingdee.purchase.platform.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.kingdee.purchase.destapi.alibaba.util.StringUtil;

/**
 * 安全工具类，包含授权签名所需的hmac_sha1算法，主要用于计算签名
 */
public final class SecurityUtil {

    public static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * HMAC see：http://www.ietf.org/rfc/rfc2104.txt
     */
    public static byte[] hmacSha1(byte[] data, byte[] key, int offset, int len) {
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        mac.update(data, offset, len);
        return mac.doFinal();
    }
    
    public static byte[] hmacSha1(byte[][] datas, byte[] key) {
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        for (byte[] data : datas) {
            mac.update(data);
        }
        return mac.doFinal();
    }

    public static byte[] hmacSha1(String[] datas, byte[] key) {
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        try {
            for (String data : datas) {
                mac.update(data.getBytes(StringUtil.CHARSET_NAME_UTF8));
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return mac.doFinal();
    }

    public static String hmacSha1ToHexStr(byte[] data, byte[] key, int offset, int len) {
        byte[] rawHmac = hmacSha1(data, key, offset, len);
        return StringUtil.encodeHexStr(rawHmac);
    }
    
    public static String hmacSha1ToHexStr(byte[] data, String key, int offset, int len) {
        try {
            return hmacSha1ToHexStr(data, key.getBytes(StringUtil.CHARSET_NAME_UTF8), offset, len);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static String hmacSha1ToHexStr(String str, String key) {
        try {
            byte[] data = str.getBytes(StringUtil.CHARSET_NAME_UTF8);
            return hmacSha1ToHexStr(data, key.getBytes(StringUtil.CHARSET_NAME_UTF8), 0, data.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    /** 
     * MD5加密
     * 
     * @param	str 	待md5处理的字符串	
     * @return			32位字符串
     */  
    public final static String getMD5String(String str){
    	if(StringUtils.isEmpty(str)){
    		return null;
    	}
    	
        MessageDigest messageDigest = null;  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (Exception e) {  
        	return null;
        }  
  
        byte[] byteArray = messageDigest.digest();  
        StringBuilder md5StrBuff = new StringBuilder();    
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length()== 1){
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            }else{
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }  
  
        return md5StrBuff.toString();  
    }
    
    public static void main(String[] args){
    	String s = "123456";
    	s = getMD5String(s);
    	System.out.println(s);
    	System.out.println(s.length());
    	
    	String s2 = "234345";
    	s2 = getMD5String(s2);    	
    	System.out.println(s2);
    	System.out.println(s2.length());
    	
    	System.out.println(s!=null && !s.equals(s2));
    }

    private SecurityUtil() {
    }
}