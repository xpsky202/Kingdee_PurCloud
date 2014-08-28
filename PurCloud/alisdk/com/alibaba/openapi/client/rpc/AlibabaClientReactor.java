/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-17
 * $Id: AlibabaClientReactor.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
package com.alibaba.openapi.client.rpc;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpException;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.NHttpConnection;
import org.apache.http.nio.protocol.AsyncNHttpClientHandler;
import org.apache.http.nio.protocol.EventListener;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOEventDispatch;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.nio.reactor.SessionRequest;
import org.apache.http.nio.util.ByteBufferAllocator;
import org.apache.http.nio.util.HeapByteBufferAllocator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;

import com.alibaba.openapi.client.FutureCallback;
import com.alibaba.openapi.client.Request;
import com.alibaba.openapi.client.Response;
import com.alibaba.openapi.client.exception.InvokeConnectException;
import com.alibaba.openapi.client.exception.InvokeTimeoutException;
import com.alibaba.openapi.client.exception.OceanException;
import com.alibaba.openapi.client.policy.ClientPolicy;
import com.alibaba.openapi.client.policy.RequestPolicy;
import com.alibaba.openapi.client.rpc.io.AutoSSLClientIOEventDispatch;
import com.alibaba.openapi.client.rpc.io.RequestAcceptEncoding;
import com.alibaba.openapi.client.rpc.io.SessionRequestCallbackTrigger;
import com.alibaba.openapi.client.util.LoggerHelper;
import com.alibaba.openapi.client.util.NamedThreadFactory;

/**
 * Comment of AlibabaClientReactor
 * 反应器,I/O反应器的目的是响应I/O事件并且分派事件通知给特定的I/O会话。
 * @author jade
 */
public class AlibabaClientReactor implements Callable<Integer> {

    private ConnectingIOReactor ioReactor;
    private IOEventDispatch     ioEventDispatch;
    private ExecutorService     executorService;
    private Future<Integer>     workerFuture;
    private ClientPolicy        policy;

    public Integer call() throws Exception {
        try {
            System.out.println("call...Starts the reactor and initiates the dispatch");
            //反应器,I/O反应器的目的是响应I/O事件并且分派事件通知给特定的I/O会话。
            ioReactor.execute(ioEventDispatch);
        } catch (InterruptedIOException ex) {
            LoggerHelper.getClientLogger().warning("AlibabaClientReactor Interrupted");
        } catch (IOException e) {
            LoggerHelper.getClientLogger().warning("AlibabaClientReactor I/O error: " + e.getMessage());
            e.printStackTrace();
        }
        LoggerHelper.getClientLogger().finer("AlibabaClientReactor Shutdown");
        return null;
    }

    protected HttpParams getHttpParams(){
        HttpParams params = new BasicHttpParams();
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000).setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000)
        .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);

        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, policy.getContentCharset());
        HttpConnectionParams.setTcpNoDelay(params, true);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        //HttpConnectionParams.setSoTimeout(params, 0);
        HttpProtocolParams.setUserAgent(params, "OceanClient/0.1");
        return params;
    }

    public synchronized void start(ClientPolicy policy) throws IOReactorException, NoSuchAlgorithmException, KeyManagementException {
        if (workerFuture != null) {
            throw new IllegalStateException("[AlibabaClientReactor]is already started");
        }
        LoggerHelper.getClientLogger().finer("[AlibabaClientReactor START]-- start OK");
        this.policy = policy;

        //Initialize HTTP processor
        HttpParams params = getHttpParams();
        //System.out.println("create IOEventDispatch...");
        createIOEventDispatch(policy, params);
        //System.out.println("ioReactor init...");
        ioReactor = new DefaultConnectingIOReactor(1, params);
        executorService = Executors.newSingleThreadExecutor(new NamedThreadFactory("AlibabaClientReactor", true));
        //System.out.println("AlibabaClientReactor submit...");
        workerFuture = executorService.submit(this);
    }

    private void createIOEventDispatch(ClientPolicy policy, HttpParams params) throws NoSuchAlgorithmException,
                                                                              KeyManagementException {
        HttpProcessor httpproc = createHttpProcessor();
        ByteBufferAllocator allocator = new HeapByteBufferAllocator();
        ////HTTP I/O事件调度器可通过I/O反应器将一般的已触发I/O事件转换为特定的HTTP协议事件。他们依赖NHttpClientHandler和NHttpServiceHandler接口将HTTP协议事件传递给HTTP协议处理(handler).
        AsyncNHttpClientHandler handler = new AsyncNHttpClientHandler(httpproc, new AliNHttpRequstExecutionHandler(new ProtocolProvider(policy), allocator)
            , new DefaultConnectionReuseStrategy(), allocator, params);
//        BufferingHttpClientHandler handler = new BufferingHttpClientHandler(httpproc, new MyHttpRequestExecutionHandler(),
//                new DefaultConnectionReuseStrategy(), params);
        handler.setEventListener(new EventLogger());
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
            }
            public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
            }
        } }, new SecureRandom());
        sslcontext.getClientSessionContext().setSessionCacheSize(200);
        sslcontext.getClientSessionContext().setSessionTimeout(30000);
        //通过dispatch把IO事件传递给handler
        ioEventDispatch = new AutoSSLClientIOEventDispatch(handler, sslcontext, params);
    }

    protected BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor httpproc = new BasicHttpProcessor();
        // Required protocol interceptors
        httpproc.addInterceptor(new RequestContent());
        httpproc.addInterceptor(new RequestTargetHost());
        // Recommended protocol interceptors
        httpproc.addInterceptor(new RequestUserAgent());
        httpproc.addInterceptor(new RequestAcceptEncoding());
        return httpproc;
    }

    public synchronized void shutdown() throws IOException {
        if (workerFuture == null) {
            LoggerHelper.getClientLogger().warning("[AlibabaClientReactor]is already shutdown");
        } else {
            if (workerFuture.isCancelled()) {
                LoggerHelper.getClientLogger().warning("[AlibabaClientReactor] worker is already cancelled");
            } else if (workerFuture.isDone()) {
                LoggerHelper.getClientLogger().warning("[AlibabaClientReactor] worker is already done");
            } else {
                workerFuture.cancel(true);
            }
        }
        workerFuture = null;
        if (executorService != null) {
            System.out.println("AlibabaClientReactor executorService will shutdown...");
            executorService.shutdownNow();
        }
        executorService = null;
        LoggerHelper.getClientLogger().finer("[AlibabaClientReactor SHUTDONW]-- shutdown OK");
        ioReactor.shutdown();
    }

    public <T> Future<T> send(Request request, Class<T> resultType, ClientPolicy clientPolicy, final RequestPolicy requestPolicy, FutureCallback<T> callback) {
        final InvokeContext context = new InvokeContext();
        context.setRequest(request);
        context.setPolicy(requestPolicy);
        context.setCallback(callback);
        context.setResultType(resultType);
        int serverPort = requestPolicy.isUseHttps()?clientPolicy.getHttpsPort():clientPolicy.getHttpPort();
        LoggerHelper.getClientLogger().finer("Use "+(clientPolicy.isUseHttps()?"https":"http")+" connect and create SessionRequest");
        //使用SessionRequestCallback进行异步连接而无需等待，不会堵塞当前线程的执行
        //SessionRequest监控在建立连接过程中的会话初始化。
        final SessionRequest req = ioReactor.connect(new InetSocketAddress(clientPolicy.getServerHost(), serverPort), null, context,
                new SessionRequestCallbackTrigger());

        return new Future<T>(){
            private boolean cancelled = false;
            public boolean cancel(boolean mayInterruptIfRunning) {
                if(req.isCompleted() || cancelled){
                    return false;
                }
                cancelled = true;
                req.cancel();
                context.completed();
                return true;
            }
            public boolean isCancelled() {
                return cancelled;
            }

            public boolean isDone() {
                return context.isCompleted() || cancelled;
            }

            public T get() throws InterruptedException, ExecutionException {
                context.waitForComplete();
                return _get();
            }

            public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                context.waitForComplete(timeout, unit);
                return _get();
            }

            @SuppressWarnings("unchecked")
            private T _get() throws ExecutionException {
                Response response = ((InvokeContext)req.getAttachment()).getResponse();
                Throwable targetException = response.getException();
                if(targetException != null){
                    if(requestPolicy.getErrorHandler()!=null&&targetException instanceof OceanException){
                        requestPolicy.getErrorHandler().handle((OceanException)targetException);
                    }
                    throw new ExecutionException(targetException.getMessage(), targetException);
                }
                return (T)response.getResult();
            }
        };
    }

    static class EventLogger implements EventListener {
        public final static String CONTEXT_ATTACHMENT = "context.attachment";
        public void connectionOpen(final NHttpConnection conn) {
            LoggerHelper.getClientLogger().finer("AliClient Connection open: " + conn);
        }

        public void connectionTimeout(final NHttpConnection conn) {
            LoggerHelper.getClientLogger().warning("AliClient Connection timed out: " + conn);
            InvokeContext context = (InvokeContext) conn.getContext().getAttribute(CONTEXT_ATTACHMENT);
            context.completed();
            context.failed(new InvokeTimeoutException("AliClient Connection timed out."));
        }

        public void connectionClosed(final NHttpConnection conn) {
            LoggerHelper.getClientLogger().finer("AliClient Connection closed: " + conn);
        }

        public void fatalIOException(final IOException ex, final NHttpConnection conn) {
            InvokeContext context = (InvokeContext) conn.getContext().getAttribute(CONTEXT_ATTACHMENT);
            context.completed();
            context.failed(new InvokeConnectException("AliClient I/O error: ",ex));
            LoggerHelper.getClientLogger().warning("AliClient I/O error: " + ex.getMessage() + ", conn: " + conn);
        }

        public void fatalProtocolException(final HttpException ex, final NHttpConnection conn) {
            LoggerHelper.getClientLogger().warning("AliClient HTTP error." + ex.getMessage() + ", conn: " + conn);
            InvokeContext context = (InvokeContext) conn.getContext().getAttribute(CONTEXT_ATTACHMENT);
            context.completed();
            context.failed(new InvokeConnectException("AliClient HTTP error.",ex));
        }
    }
}
