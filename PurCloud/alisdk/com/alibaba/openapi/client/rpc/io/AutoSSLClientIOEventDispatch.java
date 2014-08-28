/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-21
 * $Id: AutoSSLClientIOEventDispatch.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
package com.alibaba.openapi.client.rpc.io;

import java.io.IOException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.http.impl.nio.DefaultClientIOEventDispatch;
import org.apache.http.impl.nio.reactor.SSLIOSession;
import org.apache.http.impl.nio.reactor.SSLMode;
import org.apache.http.impl.nio.reactor.SSLSetupHandler;
import org.apache.http.nio.NHttpClientHandler;
import org.apache.http.nio.NHttpClientIOTarget;
import org.apache.http.nio.reactor.IOSession;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;

import com.alibaba.openapi.client.rpc.InvokeContext;
import com.alibaba.openapi.client.util.LoggerHelper;

/**
 * Comment of AutoSSLClientIOEventDispatch
 * @author jade
 *
 */
public class AutoSSLClientIOEventDispatch extends DefaultClientIOEventDispatch {
    private static final String SSL_SESSION = "http.nio.ssl-session";
    private final SSLContext sslcontext;
    private final SSLSetupHandler sslHandler;

    /**
     * Creates a new instance of this class to be used for dispatching I/O event
     * notifications to the given protocol handler using the given
     * {@link SSLContext}. This I/O dispatcher will transparently handle SSL
     * protocol aspects for HTTP connections.
     *
     * @param handler the client protocol handler.
     * @param sslcontext the SSL context.
     * @param sslHandler the SSL setup handler.
     * @param params HTTP parameters.
     */
    public AutoSSLClientIOEventDispatch(
            final NHttpClientHandler handler,
            final SSLContext sslcontext,
            final SSLSetupHandler sslHandler,
            final HttpParams params) {
        super(handler, params);
        if (sslcontext == null) {
            throw new IllegalArgumentException("SSL context may not be null");
        }
        this.sslcontext = sslcontext;
        this.sslHandler = sslHandler;
    }

    /**
     * Creates a new instance of this class to be used for dispatching I/O event
     * notifications to the given protocol handler using the given
     * {@link SSLContext}. This I/O dispatcher will transparently handle SSL
     * protocol aspects for HTTP connections.
     *
     * @param handler the client protocol handler.
     * @param sslcontext the SSL context.
     * @param params HTTP parameters.
     */
    public AutoSSLClientIOEventDispatch(
            final NHttpClientHandler handler,
            final SSLContext sslcontext,
            final HttpParams params) {
        this(handler, sslcontext, null, params);
    }
    /**
     * Creates an instance of {@link SSLIOSession} decorating the given
     * {@link IOSession}.
     * <p>
     * This method can be overridden in a super class in order to provide
     * a different implementation of SSL I/O session.
     *
     * @param session the underlying I/O session.
     * @param sslcontext the SSL context.
     * @param sslHandler the SSL setup handler.
     * @return newly created SSL I/O session.
     */
    protected SSLIOSession createSSLIOSession(
            final IOSession session,
            final SSLContext sslcontext,
            final SSLSetupHandler sslHandler) {
        return new SSLIOSession(session, sslcontext, sslHandler);
    }

    public void connected(final IOSession session) {
        System.out.println("AutoSSLClientIOEventDispatch connected...");
        LoggerHelper.getClientLogger().finer("Enter client connected.");
        IOSession targetSession = session;
        SSLIOSession sslSession = null;
        Object attachment = session.getAttribute(IOSession.ATTACHMENT_KEY);
        //�Ƿ�ʹ��ssl����
        if(attachment instanceof InvokeContext){
            InvokeContext context = (InvokeContext)attachment;
            if(context.getPolicy().isUseHttps()){
                LoggerHelper.getClientLogger().finer("IOEventDispatch create ssl io session.");
                sslSession = createSSLIOSession(
                        session,
                        this.sslcontext,
                        this.sslHandler);
                session.setAttribute(SSL_SESSION, sslSession);
                targetSession = sslSession;
            }
        }

        NHttpClientIOTarget conn = createConnection(
                targetSession);
        session.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
        this.handler.connected(conn, attachment);

        if(sslSession != null){
            try {
                sslSession.bind(SSLMode.CLIENT, this.params);
            } catch (SSLException ex) {
                this.handler.exception(conn, ex);
                sslSession.shutdown();
            }
        }
    }
    private void ensureNotNull(final NHttpClientIOTarget conn) {
        if (conn == null) {
            throw new IllegalStateException("HTTP connection is null");
        }
    }

    public void inputReady(final IOSession session) {
        System.out.println("AutoSSLClientIOEventDispatch inputReady...");

        LoggerHelper.getClientLogger().finer("Enter client input ready.");
        NHttpClientIOTarget conn =
            (NHttpClientIOTarget) session.getAttribute(ExecutionContext.HTTP_CONNECTION);
        ensureNotNull(conn);
        SSLIOSession sslSession =
            (SSLIOSession) session.getAttribute(SSL_SESSION);
        //when use https,sslSession is not null
        if(sslSession == null){
            conn.consumeInput(this.handler);
            //session.clearEvent(SelectionKey.OP_WRITE);
        }else{
            try {
                if (sslSession.isAppInputReady()) {
                    conn.consumeInput(this.handler);
                }
                sslSession.inboundTransport();
            } catch (IOException ex) {
                this.handler.exception(conn, ex);
                sslSession.shutdown();
            }
        }
    }

    public void outputReady(final IOSession session) {
        System.out.println("AutoSSLClientIOEventDispatch outputReady...");

        LoggerHelper.getClientLogger().finer("Enter client output ready.");
        NHttpClientIOTarget conn =
            (NHttpClientIOTarget) session.getAttribute(ExecutionContext.HTTP_CONNECTION);
        ensureNotNull(conn);
        SSLIOSession sslSession =
            (SSLIOSession) session.getAttribute(SSL_SESSION);
        if(sslSession == null){
            conn.produceOutput(this.handler);
        }else{
            try {
                if (sslSession.isAppOutputReady()) {
                    conn.produceOutput(this.handler);
                }
                sslSession.outboundTransport();
            } catch (IOException ex) {
                this.handler.exception(conn, ex);
                sslSession.shutdown();
            }
        }
    }

    public void timeout(final IOSession session) {
        LoggerHelper.getClientLogger().finer("Enter client timeout.");
        NHttpClientIOTarget conn =
            (NHttpClientIOTarget) session.getAttribute(ExecutionContext.HTTP_CONNECTION);
        ensureNotNull(conn);
        this.handler.timeout(conn);

        SSLIOSession sslSession =
            (SSLIOSession) session.getAttribute(SSL_SESSION);
        if(sslSession != null){
            synchronized (sslSession) {
                if (sslSession.isOutboundDone() && !sslSession.isInboundDone()) {
                    // The session failed to terminate cleanly
                    sslSession.shutdown();
                }
            }
        }
    }

    @Override
    public void disconnected(IOSession session) {
        super.disconnected(session);
    }



}
