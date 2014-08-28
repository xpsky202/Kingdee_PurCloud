/**
 * Project: ocean.client.java.basic
 *
 * File Created at 2011-10-21
 * $Id: GzipCompressingNEntity.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.GZIPOutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.nio.ContentEncoder;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.entity.ProducingNHttpEntity;

/**
 * Comment of GzipCompressingNEntity
 *
 * @author jade
 */
public class GzipCompressingNEntity extends HttpEntityWrapper implements ProducingNHttpEntity {
    private final ReadableByteChannel channel;
    private final ByteBuffer buffer;
    private final long contentLength;

    public GzipCompressingNEntity(final HttpEntity httpEntity) {
        super(httpEntity);
        ByteArrayOutputStream ostream = new ByteArrayOutputStream(4096);
        try{
            InputStream istream = httpEntity.getContent();
            GZIPOutputStream gostream = new GZIPOutputStream(ostream);
            byte[] buffer = new byte[4096];
            int i;
            do{
                i =istream.read(buffer);
                if(i>0){
                    gostream.write(buffer, 0, i);
                }
                gostream.flush();
            }while(i>=0);
            gostream.finish();
        }catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        byte[] cbyte = ostream.toByteArray();
        contentLength = cbyte.length;
        this.channel = Channels.newChannel(new ByteArrayInputStream(cbyte));
        this.buffer = ByteBuffer.allocate(4096);
    }


    @Override
    public long getContentLength() {
        return contentLength;
    }

    /**
     * This method throws {@link UnsupportedOperationException}.
     */
    @Override
    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Does not support blocking methods");
    }

    @Override
    public boolean isStreaming() {
        return true;
    }

    /**
     * This method throws {@link UnsupportedOperationException}.
     */
    @Override
    public void writeTo(OutputStream out) throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Does not support blocking methods");
    }

    /**
     * This method is equivalent to the {@link #finish()} method.
     * <br/>
     * TODO: The name of this method is misnomer. It will be renamed to
     * #finish() in the next major release.
     */
    @Override
    public void consumeContent() throws IOException {
        finish();
    }

    public void produceContent(
            final ContentEncoder encoder,
            final IOControl ioctrl) throws IOException {
        int i = this.channel.read(this.buffer);
        this.buffer.flip();
        encoder.write(this.buffer);
        boolean buffering = this.buffer.hasRemaining();
        this.buffer.compact();
        if (i == -1 && !buffering) {
            encoder.complete();
            this.channel.close();
        }
    }

    public void finish() {
        try {
            this.channel.close();
        } catch (IOException ignore) {
        }
    }

}
