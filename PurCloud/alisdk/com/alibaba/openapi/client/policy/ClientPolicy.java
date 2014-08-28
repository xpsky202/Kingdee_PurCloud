/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-18
 * $Id: ClientPolicy.java 324325 2014-02-11 07:39:28Z yichun.wangyc $
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
package com.alibaba.openapi.client.policy;

import javax.crypto.spec.SecretKeySpec;

import com.alibaba.openapi.client.APIId;
import com.alibaba.openapi.client.util.SignatureUtil;

/**
 * 客户端级访问策略
 * <p>
 * 定义域名、端口、appKey、秘钥等客户端级信息
 * <p>
 * 参考<code>RequestPolicy</code>
 * <p>
 * 
 * @author jade
 */
public class ClientPolicy extends RequestPolicy implements Cloneable {

    private static Integer            DEFAULT_HTTP_PORT  = 80;
    private static Integer            DEFAULT_HTTPS_PORT = 443;

    private static final ClientPolicy CBU_POLICY         = new ClientPolicy("gw.open.1688.com");

    private String                    serverHost;
    private int                       httpPort           = DEFAULT_HTTP_PORT;
    private int                       httpsPort          = DEFAULT_HTTPS_PORT;
    private String                    appKey;
    private SecretKeySpec             signingKey;
    private int                       defaultVersion     = APIId.DEFAULT_VERSION;

    /**
     * 生成默认的ClientPolicy实例，包括默认的http(80)，https(443)端口,默认的域名gw.open.1688.com，以及默认的api版本为1
     * @return
     */
    public static ClientPolicy getDefaultChinaAlibabaPolicy() {
        return CBU_POLICY.clone();
    }

    /**
     * 拷贝生成新的对象
     */
    public ClientPolicy clone() {
        ClientPolicy newObj = (ClientPolicy) super.clone();
        newObj.httpPort = httpPort;
        newObj.httpsPort = httpsPort;
        newObj.appKey = appKey;
        newObj.signingKey = signingKey;
        newObj.defaultVersion = defaultVersion;
        return newObj;
    }

    @Override
    protected ClientPolicy newPolicy() {
        return new ClientPolicy(serverHost);
    }

    /**
     * 指定开放平台服务地址来生成ClientPolicy
     * @param serverHost 开放平台服务ip地址或者域名
     */
    public ClientPolicy(String serverHost){
        if (serverHost == null || serverHost.length() < 1) {
            throw new IllegalArgumentException("serverHost can not be empty");
        }
        this.serverHost = serverHost;
    }

    /**
     * 获取http端口
     * @return
     */
    public int getHttpPort() {
        return httpPort;
    }

    /**
     * 获取https端口
     * @return
     */
    public int getHttpsPort() {
        return httpsPort;
    }

    /**
     * 获取开放平台服务ip地址或者域名
     * @return
     */
    public String getServerHost() {
        return serverHost;
    }

    /**
     * 设置http端口
     * 
     * @param httpPort 端口
     * @return a reference to this object
     */
    public ClientPolicy setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
        return this;
    }

    /**
     * 设置https端口
     * 
     * @param httpsPort 端口
     * @return a reference to this object
     */
    public ClientPolicy setHttpsPort(Integer httpsPort) {
        this.httpsPort = httpsPort;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    /**
     * 设置app key
     * 
     * @param appKey
     * @return a reference to this object
     */
    public ClientPolicy setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public SecretKeySpec getSigningKey() {
        return signingKey;
    }

    /**
     * 设置秘钥
     * 
     * @param signingKey
     * @return a reference to this object
     */
    public ClientPolicy setSigningKey(String signingKey) {
        return setSigningKey(SignatureUtil.toBytes(signingKey));
    }

    /**
     * 设置秘钥
     * 
     * @param signingKey
     * @return a reference to this object
     */
    public ClientPolicy setSigningKey(byte[] signingKey) {
        this.signingKey = SignatureUtil.buildKey(signingKey);
        return this;
    }

    /**
     * 获取api调用默认版本
     * @return
     */
    public int getDefaultVersion() {
        return defaultVersion;
    }

    /**
     * 设置调用api的默认版本
     * 
     * @param defaultVersion 版本，如1，2
     * @return a reference to this object
     */
    public ClientPolicy setDefaultVersion(int defaultVersion) {
        this.defaultVersion = defaultVersion;
        return this;
    }
}
