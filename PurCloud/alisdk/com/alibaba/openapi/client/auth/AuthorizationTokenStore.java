/*
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.openapi.client.auth;



/**
 * 类AuthorizationCodeStore.java的实现描述：
 * 提供Refresh Token 和 Access Token 的存取功能，
 * 记录Access Token的有限时间，有限时间之内可以直接使用，出现授权异常时，sdk会refresh token，重新获取accessToken，执行storeToken
 * 当refresh Token 过期时remove掉该authorizationCode。
 * @author fray.yangb Aug 21, 2012 2:44:11 PM
 */
public interface AuthorizationTokenStore {
    /**
     * 根据授权Code获取授权信息，主要是RefreshToken
     * @param authorizationCode
     * @return {@link AuthorizationToken}
     */
    public AuthorizationToken getToken(String authorizationCode);

    public void storeToken(String authorizationCode,AuthorizationToken token);

    public void removeToken(String authorizationCode);
    
    public void storeAccessToken(String uid, AuthorizationToken token);
    
    public AuthorizationToken getAccessToken(String uid);
}
