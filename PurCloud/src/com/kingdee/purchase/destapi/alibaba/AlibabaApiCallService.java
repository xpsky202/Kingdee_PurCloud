package com.kingdee.purchase.destapi.alibaba;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.openapi.client.AlibabaClient;
import com.alibaba.openapi.client.Request;
import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.alibaba.openapi.client.exception.OceanException;
import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.policy.RequestPolicy;
import com.kingdee.purchase.config.ConfigConstant;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.exception.PurSysException;

/**
 * 1688 API调用服务类
 * @author RD_sky_lv
 *
 */

public class AlibabaApiCallService {
	
	private static final Logger logger = LogManager.getLogger(AlibabaApiCallService.class);
	
	private static AlibabaClient client = null;
	private static Map<String, AuthorizationToken> AuthTokenMap = new HashMap<String, AuthorizationToken>();
	
	/**
	 * 初始化alibaba客户端
	 */
	private static synchronized void init() {
		if (client != null) {
			return;
		}
		//使用默认的client策略，包括使用域名gw.open.1688.com,端口http 80，https443等
		ClientPolicy policy = new ClientPolicy(AlibabaConfiguration.HOST);
		//设置app的appKey以及对应的密钥，信息由注册app时生成
		policy = policy.setAppKey(AlibabaConfiguration.getAppKey()).setSigningKey(AlibabaConfiguration.getAppSecret());
		//使用client策略来初始化AlibabaClient,建议保持单例
		client = new AlibabaClient(policy);
		//启动AlibabaClient实例
		client.start();
		//启动RefreshToken轮询定时器
		RefreshTokenTimerTask.beginTimer();
	}
	
	/**
	 * 根据长时令牌获取授权信息
	 * @param refreshToken
	 * @return
	 */
	public static AuthorizationToken getAuthorizationToken(String refreshToken) {
		if (client == null) {
			init();
		}
		AuthorizationToken result = AuthTokenMap.get(refreshToken);
		if (result == null || isTokenOutOfDate(result)) {
			result = client.refreshToken(refreshToken);
			result.setRefresh_token(refreshToken);
			AuthTokenMap.put(refreshToken, result);
		}
		
		return result;
	}
	
	/**
	 * Accesstoken是否过期
	 * @param result
	 * @return
	 */
	private static boolean isTokenOutOfDate(AuthorizationToken result) {
		if (result.getExpires_time().before(Calendar.getInstance().getTime())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 通过SDK调用1688的API
	 * @param refreshToken 1688访问的长时令牌
	 * @param apiName      调用的api名称，例如：caigou.api.buyoffer.postBuyoffer
	 * @param version      调用的api版本号
	 * @param params       调用的api参数
	 * @param isNeedAuth   调用请求是否需要授权
	 * @param isNeedSign   调用请求是否需要签名
	 * @param isNeedTimestamp   调用请求是否需要请求时间戳
	 * @return  调用api后返回合法JSONObject对象
	 * @throws PurSysException 
	 */
	public static JSONObject callApi(InvokeContext ctx) throws PurSysException {
		if (client == null) {
			init();
		}
		AuthorizationToken authorizationToken = getAuthorizationToken(ctx.getRefreshToken());
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ConfigConstant.ENCODE).setTimeout(AlibabaConfiguration.TIMEOUT);
		basePolicy.setErrorHandler(ctx.getHandler());
		basePolicy.setNeedAuthorization(ctx.isNeedAuth()).setUseSignture(ctx.isNeedSign()).setRequestSendTimestamp(ctx.isNeedTimestamp());
		Request apiRequest = new Request(AlibabaConfiguration.API_NAMESPACE, ctx.getApiName(), ctx.getApiVersion());
		apiRequest.setAuthToken(authorizationToken);

		if (ctx.getParams() != null) {
			for (Map.Entry<String, Object> entry : ctx.getParams().entrySet()) {
				apiRequest.setParam(entry.getKey(), entry.getValue());
			}
		}

		JSONObject result = null;
		try {
			Object value = client.send(apiRequest, null, basePolicy);
			result = JSONObject.fromObject(value);
		} catch (ExecutionException e) {
			logger.error(e.getMessage());
			if (ctx.getHandler() != null && e.getCause() != null) {
				ctx.getHandler().handle((OceanException)e.getCause());
			} else {
				throw new PurSysException(PurExceptionDefine.API_INVOKE_EXCUTEERROR, new String[] {e.getMessage()});
			}
		} catch (TimeoutException e) {
			logger.error(e.getMessage());
			throw new PurSysException(PurExceptionDefine.API_INVOKE_TIMEOUT);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			throw new PurSysException(PurExceptionDefine.API_INVOKE_INTERRUPTED);
		}
		
		return result;
	}
	
	/**
	 * 根据code获取Token
	 * @param code
	 * @return
	 */
	public static String getToken(String code) {
		if (client == null) {
			init();
		}
		AuthorizationToken token = client.getToken(code, AlibabaConfiguration.TIMEOUT);
		return token.getRefresh_token();
	}
	
	/**
	 * 释放关闭
	 */
	public static void shutdown() {
		if (client != null)
			client.shutdown();
	}
	
	private AlibabaApiCallService() {}
}
