/*
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.openapi.client.auth;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 授权返回结果
 * 
 * @author fray.yangb Aug 21, 2012 2:47:07 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationToken implements Serializable {

    private static final long serialVersionUID = -7582222338466164367L;

    private String            access_token;
    private String            refresh_token;
    private long              expires_in;
    private Date              expires_time;
    private Date              refresh_token_timeout;
    private String            resource_owner;
    private String            uid;
    private long              aliId;
    private String            memberId;

    public AuthorizationToken(){
    }

    public AuthorizationToken(String accessToken, long accessTokenTimeout, String resourceOwnerId, long aliId){
        this(accessToken, accessTokenTimeout, null, null, resourceOwnerId, null, aliId, null);
    }

    public AuthorizationToken(String accessToken, long accessTokenTimeout, String resourceOwnerId, String uid,
                              long aliId, String memberId){
        this(accessToken, accessTokenTimeout, null, null, resourceOwnerId, uid, aliId, memberId);
    }

    public AuthorizationToken(String accessToken, long accessTokenTimeout, String refreshToken,
                              Date refreshTokenTimeout, String resourceOwnerId, long aliId, String memberId){
        this(accessToken, accessTokenTimeout, refreshToken, refreshTokenTimeout, resourceOwnerId, null, aliId, memberId);
    }

    public AuthorizationToken(String accessToken, long accessTokenTimeout, String refreshToken,
                              Date refreshTokenTimeout, String resourceOwnerId, String uid, long aliId, String memberId){
        this.access_token = accessToken;
        this.expires_in = accessTokenTimeout;
        this.refresh_token = refreshToken;
        this.refresh_token_timeout = refreshTokenTimeout;
        this.resource_owner = resourceOwnerId;
        this.uid = uid;
        this.aliId = aliId;
        this.memberId = memberId;
    }

    /**
     * 获取access_token
     * 
     * @return the accessToken
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * 获取access_token过期时间
     * 
     * @return the accessTokenTimeout
     */
    public long getExpires_in() {
        return expires_in;
    }

    /**
     * 获取refresh_token
     * 
     * @return the refreshToken
     */
    public String getRefresh_token() {
        return refresh_token;
    }

    /**
     * 获取refresh_token过期时间
     * 
     * @return the refreshTokenTimeout
     */
    public Date getRefresh_token_timeout() {
        return refresh_token_timeout;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取resource_owner
     * 
     * @return the resourceOwnerId
     */
    public String getResource_owner() {
        return resource_owner;
    }

    /**
     * 获取uid
     * 
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 获取aliId
     * 
     * @return the aliId
     */
    public long getAliId() {
        return aliId;
    }

    public void setAccess_token(String accessToken) {
        this.access_token = accessToken;
    }

    public void setRefresh_token(String refreshToken) {
        this.refresh_token = refreshToken;
    }

    public void setExpires_in(long accessTokenTimeout) {
        this.expires_in = accessTokenTimeout;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, (int) expires_in);
        this.expires_time = cal.getTime();
    }

    public void setRefresh_token_timeout(Date refresh_token_timeout) {
        this.refresh_token_timeout = refresh_token_timeout;
    }

    public void setResource_owner(String resourceOwnerId) {
        this.resource_owner = resourceOwnerId;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAliId(long aliId) {
        this.aliId = aliId;
    }

    /**
     * 获取access_token过期时间,Date格式
     * 
     * @return the accessTokenTimeout
     */
    public Date getExpires_time() {
        return expires_time;
    }
}
