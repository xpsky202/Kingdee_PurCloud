/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-21
 * $Id: GzipDecompressingEntity.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

/**
 * Comment of GzipDecompressingEntity
 *
 * @author jade
 */

//TODO 没有用到httpResponseParser中
public class GzipDecompressingEntity extends HttpEntityWrapper {

    /**
     * Default buffer size.
     */
    private static final int BUFFER_SIZE = 1024 * 2;

    /**
     * DecompressingEntities are not repeatable, so they will return the same
     * InputStream instance when {@link #getContent()} is called.
     */
    private InputStream      content;

    /**
     * Creates a new {@link GzipDecompressingEntity}.
     *
     * @param wrapped the non-null {@link HttpEntity} to be wrapped
     */
    public GzipDecompressingEntity(final HttpEntity wrapped) {
        super(wrapped);
    }

    InputStream getDecompressingInputStream(final InputStream wrapped) throws IOException {
        return new GZIPInputStream(wrapped);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Header getContentEncoding() {

        /* This HttpEntityWrapper has dealt with the Content-Encoding. */
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getContentLength() {

        /* length of ungzipped content is not known */
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getContent() throws IOException {
        if (wrappedEntity.isStreaming()) {
            if (content == null) {
                content = getDecompressingInputStream(wrappedEntity.getContent());
            }
            return content;
        } else {
            return getDecompressingInputStream(wrappedEntity.getContent());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        if (outstream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream instream = getContent();
        try {
            byte[] buffer = new byte[BUFFER_SIZE];

            int l;

            while ((l = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, l);
            }
        } finally {
            instream.close();
        }
    }
}
