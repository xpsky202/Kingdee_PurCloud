package com.kingdee.purchase.destapi.alibaba;

import com.kingdee.purchase.config.DeployConfig;

/*
 *  测试环境：
	appKey：351843
	秘钥：bylYiZ8IKw
	测试账号：
	登录名/密码：金蝶001/taobao1234
	refresh_token： cdca3331-3649-46eb-8b87-13e841658b28
	 
	正式环境：
	appKey：316330
	秘钥：7n5HNqOoPj
	登录名分别为：alitestforisv01   alitestforisv02   alitestforisv03   alitestforisv04
	密码统一为：  test1234
	refresh_token：f4970971-a8f9-47c4-b194-4cb4b014cf15
 */

public final class AlibabaConfiguration {

	private AlibabaConfiguration(){}
	
	public final static String HOST = "gw.open.1688.com";
//	public final static String HOST = "gw.open.china.alibaba.com";
	
	private final static String APP_KEY = "316330";
	private final static String APP_SECRET = "7n5HNqOoPj";
	private final static String APP_KEY_DEBUG = "316330";
	private final static String APP_SECRET_DEBUG = "7n5HNqOoPj";
	
	/***
	 * 获取APPkey
	 * @return
	 */
	public final static String getAppKey(){
		if(DeployConfig.DEBUG){
			return APP_KEY_DEBUG;
		}
		
		return APP_KEY;
	}
	
	/***
	 * 获取APPSecret
	 * @return
	 */
	public final static String getAppSecret(){
		if(DeployConfig.DEBUG){
			return APP_SECRET_DEBUG;
		}
		
		return APP_SECRET;
	}
	
	public final static String API_NAMESPACE = "cn.alibaba.open";
	
	//192.168.204.131   
	//202.104.120.130
//	private final static String REDIRECT_URI = "http://192.168.204.131:8086/purchase/auth";
	
	private final static String REDIRECT_URI_CODE = "http://202.104.120.130:8086/purchase/auth/code";
	private final static String REDIRECT_URI_CODE_DEBUG = "http://192.168.204.131:8086/purchase/auth/code";

	private static final String AUTO_LOGGIN_URL = "https://gw.open.1688.com/openapi/param2/1/caigou/caigou.platform.sso/";
	private static final String AUTO_LOGGIN_URL_DEBUG = "https://gw.open.1688.com/api/param2/1/caigou/caigou.platform.sso/";
	
	/**
	 * 获取免登认证的url
	 * @return
	 */
	public final static String getAutoLoginUrl(){
		if(DeployConfig.DEBUG){
			return AUTO_LOGGIN_URL_DEBUG;
		}
		
		return AUTO_LOGGIN_URL;
	}
	
	/***
	 * 授权认证获取code的回调url
	 * @return
	 */
	public final static String getRedirectUriCode(){
		if(DeployConfig.DEBUG){
			return REDIRECT_URI_CODE_DEBUG;
		}
		
		return REDIRECT_URI_CODE;
	}
	
//	public final static String REDIRECT_URI_TOKEN = REDIRECT_URI+"/token";

	public final static String SITE = "china";
	
	public final static String URL_AUTHORIZE = "http://gw.open.1688.com/auth/authorize.htm";
	
	public final static String URL_GET_TOKEN = "https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/"+APP_KEY;
	
	public final static int TIMEOUT = 6000;
}