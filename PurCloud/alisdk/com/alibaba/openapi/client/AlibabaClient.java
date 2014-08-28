/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-25
 * $Id: AlibabaClient.java 333426 2014-03-13 03:12:47Z yichun.wangyc $
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
package com.alibaba.openapi.client;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.nio.reactor.IOReactorException;

import com.alibaba.openapi.client.annotation.API;
import com.alibaba.openapi.client.annotation.APIParameter;
import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.alibaba.openapi.client.auth.AuthorizationTokenStore;
import com.alibaba.openapi.client.auth.DefaultAuthorizationTokenStore;
import com.alibaba.openapi.client.exception.APIInvokeException;
import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.policy.Protocol;
import com.alibaba.openapi.client.policy.RequestPolicy;
import com.alibaba.openapi.client.policy.RequestPolicy.HttpMethodPolicy;
import com.alibaba.openapi.client.rpc.AlibabaClientReactor;
import com.alibaba.openapi.client.util.GenericsUtil;
import com.alibaba.openapi.client.util.LoggerHelper;
import com.alibaba.openapi.client.util.SignatureUtil;

/**
 * Alibaba SDK 客户端
 * <p>
 * 使用方式举例如下：
 * <p>
 * <code>//使用默认的client策略，设置app的appKey以及对应的密钥,使用client策略来初始化AlibabaClient<p>
 * <code>AlibabaClient client = new AlibabaClient(ClientPolicy.getDefaultChinaAlibabaPolicy().setAppKey(YOUR_APPKEY).setSigningKey(YOUR_SRCRET));<p>
 * <code>client.start();//启动AlibabaClient实例<p>
 * <code>//调用获取开放平台系统时间: cn.alibaba.open 分组的含义，开放平台默认值 ; system.time.get 具体的某一个api的名字<p>
 * <code>result = client.send(new Request("cn.alibaba.open" ,"system.time.get", 1).setParam("a", "b"));<p>
 * <code>client.shutdown();//释放AlibabaClient<p>
 * 
 * @author jade
 * @author hibernater
 */
public class AlibabaClient implements AlibabaAPIFactory {

    private ClientPolicy                            policy;
    private AlibabaClientReactor                    reactor;
    private AuthorizationTokenStore                 tokenStore;

    private static final ThreadLocal<AlibabaClient> clientHolder = new ThreadLocal<AlibabaClient>();

    /**
     * 通过client策略初始化一个AlibabaClient
     * 
     * @param policy client策略
     */
    public AlibabaClient(ClientPolicy policy){
        init(policy);
        tokenStore = new DefaultAuthorizationTokenStore();
        clientHolder.set(this);
    }

    private void init(ClientPolicy policy) {
        this.policy = policy;
        reactor = new AlibabaClientReactor();
    }

    public static final AlibabaClient getClient() {
        return clientHolder.get();
    }

    public AlibabaAPI getAPI(String namespace, String api, int version) {
        return new AlibabaAPI(this, namespace, api, version);
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> interfaceType) {
        LoggerHelper.getClientLogger().finer("Get Proxy Object for " + interfaceType.getName());
        try {
            return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] { interfaceType },
                                              new APIProxy(this, interfaceType));
        } catch (IllegalArgumentException e) {
            throw new InternalError(e.toString());
        }
    }

    public Object send(Request request) throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("request send...");
        return send(request, Object.class, null);
    }

    public <T> T send(Request request, Class<T> resultType) throws InterruptedException, ExecutionException,
                                                           TimeoutException {
        return send(request, resultType, null);
    }

    /**
     * 发起API访问
     * 
     * @param request Request
     * @param resultType 返回结果类型，可为null
     * @param policy 请求级策略
     * @return 返回自定义类型的结果
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    @SuppressWarnings("unchecked")
    public <T> T send(Request request, Class<T> resultType, RequestPolicy policy) throws InterruptedException,
                                                                                 ExecutionException, TimeoutException {
        final CountDownLatch requestCount = new CountDownLatch(1);
        final Response response = new Response();
        final FutureCallback<T> callback = new FutureCallback<T>() {

            public void completed(T result) {
                response.setResult(result);
                requestCount.countDown();
            }

            public void failed(Throwable ex) {
                response.setException(ex);
                requestCount.countDown();
            }

            public void cancelled() {
                requestCount.countDown();
            }
        };
        send(request, resultType, policy, callback);
        if (requestCount.await(policy == null ? this.policy.getTimeout() : policy.getTimeout(), TimeUnit.MILLISECONDS)) {
            if (response.getException() != null) {
                throw new ExecutionException(response.getException());
            }
            return (T) response.getResult();
        }
        LoggerHelper.getClientLogger().warning("Synchronous API [ namespace=" + request.getApiId().getNamespace()
                                                       + ",name=" + request.getApiId().getName()
                                                       + "] send request timeout.");
        throw new TimeoutException("send request timeout");
    }

    <T> Future<T> send(Request request, FutureCallback<T> callback) {
        return send(request, null, callback);
    }

    private <T> Future<T> send(Request request, RequestPolicy policy, FutureCallback<T> callback) {
        return send(request, null, policy, callback);
    }

    @SuppressWarnings("unchecked")
    private <T> Future<T> send(Request request, Class<T> resultType, RequestPolicy policy,
                               final FutureCallback<T> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback can not be null");
        }
        replaceAccessToken(request, policy, callback);
        if (resultType == null) {
            resultType = (Class<T>) GenericsUtil.getInterfaceGenricType(callback.getClass(), FutureCallback.class);
        }
        return reactor.send(request, resultType, this.policy, policy == null ? this.policy : policy, callback);
    }

    private <T> void replaceAccessToken(Request request, RequestPolicy policy, final FutureCallback<T> callback) {

        if (policy == null || policy.isNeedAuthorization() == false) {
            return;
        }
        // 1. 直接传入accessToken
        String accessToken = request.getAccessToken();
        if (accessToken != null) {
            request.setParam(Request.ACCESS_TOKEN, accessToken);
            return;
        }
        AuthorizationToken token = request.getAuthToken();
        if (token != null) {
            // 2. 传入token，取出accessToken
            accessToken = token.getAccess_token();
            if (token.getExpires_time().after(Calendar.getInstance().getTime())) {
                request.setParam(Request.ACCESS_TOKEN, accessToken);
                return;
            }
            if (tokenStore.getAccessToken(token.getUid()) != null) {
                // 3. 根据uid，取出内存中token，找到accessToken
                accessToken = tokenStore.getAccessToken(token.getUid()).getAccess_token();
                if (token.getExpires_time().after(Calendar.getInstance().getTime())) {
                    request.setParam(Request.ACCESS_TOKEN, accessToken);
                    return;
                }
            }

            OAuthAPI oauth = this.getProxy(OAuthAPI.class);
            RequestPolicy oauthPolicy = policy.clone();
            oauthPolicy.setHttpMethod(HttpMethodPolicy.POST);
            oauthPolicy.setRequestProtocol(Protocol.http);
            oauthPolicy.setAccessPrivateApi(true);
            oauthPolicy.setUseHttps(true);
            oauthPolicy.setUseSignture(true);
            // TODO 暂时用这种方法来解决 proxy和send的循环调用
            oauthPolicy.setNeedAuthorization(false);
            // 4. 用refreshToken换取accessToken
            accessToken = oauth.refreshToken(token.getRefresh_token(), "refresh_token", SignatureUtil.getKeyString(this.policy.getSigningKey()),
                                             this.policy.getAppKey(), oauthPolicy).getAccess_token();
            // 更新token里的accessToken，存储
            token.setAccess_token(accessToken);
            token.setExpires_in(3600 * 10);
            tokenStore.storeAccessToken(token.getUid(), token);
            request.setParam(Request.ACCESS_TOKEN, accessToken);
        }
    }

    /**
     * 启动AlibabaClient实例
     */
    public void start() {
        try {
            reactor.start(policy);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOReactorException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void shutdown() {
        try {
            reactor.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public ClientPolicy getPolicy() {
        return policy;
    }

    protected static class APIMethodDefination {

        Method              method;
        APIId               api;
        RequestPolicy       policy;
        Map<String, String> paramNames;
        boolean             ansyInvoke = false;

        public String toString() {
            return " api [ namespace=" + api.getNamespace() + ";name=" + api.getName() + ";version=" + api.getVersion()
                   + ";method name=" + method.getName() + " ]";
        }
    }

    protected static final class AnnotationAPIParser {

        public static HashMap<Method, APIMethodDefination> parseDefinations(ClientPolicy clientPolicy,
                                                                            Class<?> proxyInterface) {
            API interfaceAPIMeta = proxyInterface.getAnnotation(API.class);
            HashMap<Method, APIMethodDefination> methodDefinations = new HashMap<Method, AlibabaClient.APIMethodDefination>();
            for (Method method : proxyInterface.getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers())) {
                    String namespace = null, name = null;
                    int version = -1;
                    if (interfaceAPIMeta != null) {
                        namespace = interfaceAPIMeta.namespace();
                        name = interfaceAPIMeta.name();
                        version = interfaceAPIMeta.version();
                    }
                    API apiMeta = method.getAnnotation(API.class);
                    if (apiMeta != null) {
                        if (apiMeta.namespace() != null && apiMeta.namespace().length() > 0) {
                            namespace = apiMeta.namespace();
                        }
                        if (apiMeta.name() != null && apiMeta.name().length() > 0) {
                            name = apiMeta.name();
                        }
                        if (apiMeta.version() >= 0) {
                            version = apiMeta.version();
                        }
                    }
                    if (namespace == null || namespace.length() == 0) {
                        LoggerHelper.getClientLogger().warning(method.getName() + " api namespace is necessary");
                        continue;
                    }
                    if (name == null || name.length() == 0) {
                        name = method.getName();
                    }
                    final APIMethodDefination methodDefination = new APIMethodDefination();
                    methodDefination.api = new APIId(namespace, name, version);
                    methodDefination.method = method;

                    Annotation[][] paramAnnotations = method.getParameterAnnotations();
                    Class<?>[] paramTypes = method.getParameterTypes();
                    if (paramAnnotations != null && paramTypes != null && paramTypes.length > 0) {
                        if (paramAnnotations.length != paramTypes.length) {
                            LoggerHelper.getClientLogger().warning(method.getName()
                                                                           + " paramAnnotations.length and args.length is not equale");
                            continue;
                        }
                        methodDefination.paramNames = new LinkedHashMap<String, String>();
                        final int length = paramTypes.length;
                        for (int i = 0; i < length; i++) {
                            for (Annotation annotation : paramAnnotations[i]) {
                                if (APIParameter.class.equals(annotation.annotationType())) {
                                    APIParameter apiParameter = ((APIParameter) annotation);
                                    methodDefination.paramNames.put(apiParameter.value(),
                                                                    apiParameter.isAttachment() ? "isAttachment" : "");
                                    methodDefination.paramNames.put(apiParameter.value(),
                                                                    apiParameter.iaAuthorizationCode() ? "iaAuthorizationCode" : "");
                                }
                            }
                        }

                        Class<?> lastParamType = paramTypes[length - 1];
                        if (FutureCallback.class.isAssignableFrom(lastParamType)) {
                            methodDefination.ansyInvoke = true;
                        }
                    }
                    LoggerHelper.getClientLogger().finer("Parse API interface's method defination:"
                                                                 + methodDefination.toString());
                    methodDefinations.put(method, methodDefination);
                }
            }
            return methodDefinations;
        }
    }

    protected static class APIProxy implements InvocationHandler {

        private AlibabaClient                        client;
        private HashMap<Method, APIMethodDefination> methodDefinations;

        public APIProxy(AlibabaClient client, Class<?> proxyInterface){
            this.client = client;
            methodDefinations = AnnotationAPIParser.parseDefinations(client.getPolicy(), proxyInterface);
        }

        @SuppressWarnings("unchecked")
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            LoggerHelper.getClientLogger().finer("Start invoke " + method.getName());
            APIMethodDefination defination = methodDefinations.get(method);
            if (defination == null) {
                LoggerHelper.getClientLogger().warning(method.getName() + " can not be proxy");
                throw new InternalError(method.getName() + " can not be proxy");
            }
            Request request = new Request(defination.api);
            final Map<String, String> paramNames = defination.paramNames;
            if (paramNames != null) {
                int i = 0;
                for (Entry<String, String> entry : paramNames.entrySet()) {
                    if ("iaAuthorizationCode".equals(entry.getValue())) {
                        request.setAuthCodeKey(entry.getKey());
                    }
                    if ("isAttachment".equals(entry.getValue())) {
                        if (args[i] == null || !new File((String) args[i]).exists()) {
                            throw new APIInvokeException("Not exsit File " + args[i] + "!");
                        }
                        request.setAttachment(entry.getKey(), (String) args[i]);
                    } else {
                        request.setParam(entry.getKey(), args[i]);
                    }
                    i++;
                }
            }
            if (args != null && args.length > 0) for (Object arg : args) {
                if (arg instanceof RequestPolicy) {
                    defination.policy = (RequestPolicy) arg;
                    break;
                }
            }
            Class<?> returnType = method.getReturnType();
            if (defination.ansyInvoke) {
                LoggerHelper.getClientLogger().finer("Asynchronous invoke " + method.getName());
                final FutureCallback<?> callback = (FutureCallback<?>) args[args.length - 1];
                if (Future.class.isAssignableFrom(returnType)) {
                    returnType = GenericsUtil.getMethodGenericReturnType(method, Future.class, 0);
                    return client.send(request, (Class<Object>) returnType, defination.policy,
                                       (FutureCallback<Object>) callback);
                } else if (Void.TYPE.equals(returnType)) {
                    client.send(request, defination.policy, callback);
                    return null;
                } else {
                    client.send(request, (Class<Object>) returnType, defination.policy,
                                (FutureCallback<Object>) callback);
                    return null;
                }
            } else {
                LoggerHelper.getClientLogger().finer("Synchronous invoke " + method.getName());
                return client.send(request, method.getReturnType(), defination.policy);
            }
        }
    }

    @API(namespace = "system.oauth2", version = 1)
    private interface OAuthAPI {

        String disableRefreshToken(@APIParameter("refresh_token") String refreshToken);

        @API(name = "getToken")
        AuthorizationToken getToken(@APIParameter("code") String code, @APIParameter("grant_type") String grant_type,
                                    @APIParameter("need_refresh_token") boolean need_refresh_token,
                                    @APIParameter("client_secret") String client_secret,
                                    @APIParameter("client_id") String client_id,
                                    @APIParameter("redirect_uri") String redirect_uri, RequestPolicy policy);

        @API(name = "getToken")
        AuthorizationToken refreshToken(@APIParameter("refresh_token") String refreshToken,
                                        @APIParameter("grant_type") String grant_type,
                                        @APIParameter("client_secret") String client_secret,
                                        @APIParameter("client_id") String client_id, RequestPolicy policy);
    }

    /**
     * 使用临时code换取用户授权的access_token 以及refresh_token
     * 
     * <pre>
     * 使用默认的全局RequestPolicy.getAuthPolicy(),默认超时为5s，
     * 如果报发送请求超时，自行设置:
     * 1 设置授权全局的超时时间
     *  RequestPolicy.getAuthPolicy().setTimeout(100000);
     * 2 @see getToken(String code,int timeout)
     * </pre>
     * 
     * @param code 临时code
     * @return 用户授权信息
     */
    public AuthorizationToken getToken(String code) {
        return getToken(code, 0);
    }

    /**
     * 使用临时code换取用户授权的access_token 以及refresh_token
     * 
     * @param code 临时code
     * @param timeout 请求超时时间
     * @return 用户授权信息
     */
    public AuthorizationToken getToken(String code, int timeout) {
        OAuthAPI oauth = this.getProxy(OAuthAPI.class);
        RequestPolicy oauthPolicy = RequestPolicy.getAuthPolicy();
        if (timeout != 0) {
            RequestPolicy.getAuthPolicy().setTimeout(timeout);
        }
        return oauth.getToken(code, "authorization_code", true,
                              SignatureUtil.getKeyString(this.policy.getSigningKey()), this.policy.getAppKey(),
                              "default", oauthPolicy);
    }

    /**
     * 使用用户的refresh_token 换取access_token
     * 
     * <pre>
     * 使用默认的全局RequestPolicy.getAuthPolicy(),默认超时为5s，
     * 如果报发送请求超时，自行设置:
     * 1 设置授权全局的超时时间
     *  RequestPolicy.getAuthPolicy().setTimeout(100000);
     * 2 @see refreshToken(String refreshToken,int timeout)
     * </pre>
     * 
     * @param refreshToken
     * @return 用户授权信息
     */
    public AuthorizationToken refreshToken(String refreshToken) {
        return refreshToken(refreshToken, 0);
    }

    /**
     * 使用用户的refresh_token 换取access_token
     * 
     * @param refreshToken
     * @param timeout 请求超时时间
     * @return 用户授权信息
     */
    public AuthorizationToken refreshToken(String refreshToken, int timeout) {
        OAuthAPI oauth = this.getProxy(OAuthAPI.class);
        RequestPolicy oauthPolicy = RequestPolicy.getAuthPolicy();
        if (timeout != 0) {
            RequestPolicy.getAuthPolicy().setTimeout(timeout);
        }
        return oauth.refreshToken(refreshToken, "refresh_token",
                                  SignatureUtil.getKeyString(this.policy.getSigningKey()), this.policy.getAppKey(),
                                  oauthPolicy);
    }
}
