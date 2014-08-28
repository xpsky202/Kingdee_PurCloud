/*
 * Copyright 2013 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.openapi.client;

/**
 * authorization code
 * <p>访问需授权的api，用户只需调用<code>Code.setCode(String code)</code>, 
 * SDK会参照OAuth2协议自动换取<code>token</code>，并把<code>token</code>设为访问参数
 * 
 * @author xiaoning.qxn 2013-4-7 下午2:35:42
 */
@Deprecated
public class Code {

    
    private static final ThreadLocal<String> codeHolder = new ThreadLocal<String>();
    
    private static final String getCode(){
        return codeHolder.get();
    }
    
    private static final void setCode(String code){
        codeHolder.set(code);
    }
    
    private static final void removeCode() {
        codeHolder.remove();
    }
    
}
