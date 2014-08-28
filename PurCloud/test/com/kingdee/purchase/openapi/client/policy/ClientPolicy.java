package com.kingdee.purchase.openapi.client.policy;

import com.kingdee.purchase.openapi.client.APIId;


public class ClientPolicy extends RequestPolicy {
	
	private static Integer            DEFAULT_HTTP_PORT  = 80;
    private static Integer            DEFAULT_HTTPS_PORT = 443;

    private static final ClientPolicy CBU_POLICY         = new ClientPolicy("localhost");

    private String                    serverHost;
    private int                       httpPort           = DEFAULT_HTTP_PORT;
    private boolean                   isProxy            = false;
    private String                    proxyHost;
    private int                       proxyPort;
    private int                       httpsPort          = DEFAULT_HTTPS_PORT;
    private int                       defaultVersion     = APIId.DEFAULT_VERSION;
    private String                    enterpriseId;
    private String                    orgUnitId;
    private String                    userId;
    

    /**
     * 生成默认的ClientPolicy实例，包括默认的http(80)，https(443)端口,默认的域名gw.open.1688.com，以及默认的api版本为1
     * @return
     */
    public static ClientPolicy getDefaultPolicy() {
        return CBU_POLICY.clone();
    }

    /**
     * 拷贝生成新的对象
     */
    public ClientPolicy clone() {
        ClientPolicy newObj = (ClientPolicy) super.clone();
        newObj.httpPort = httpPort;
        newObj.httpsPort = httpsPort;
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

    public boolean isProxy() {
		return isProxy;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public ClientPolicy setProxy(boolean isProxy) {
		this.isProxy = isProxy;
		return this;
	}

	public ClientPolicy setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
		return this;
	}

	public ClientPolicy setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
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

	public ClientPolicy setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
		return this;
	}

	public ClientPolicy setOrgUnitId(String orgUnitId) {
		this.orgUnitId = orgUnitId;
		return this;
	}

	public ClientPolicy setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public String getOrgUnitId() {
		return orgUnitId;
	}

	public String getUserId() {
		return userId;
	}
}
