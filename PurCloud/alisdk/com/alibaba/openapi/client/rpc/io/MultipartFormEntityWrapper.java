/*
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.openapi.client.rpc.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * 类MultipartFormEntity.java的实现描述：文件上传
 * @author fray.yangb Aug 13, 2012 1:57:33 PM
 */
public class MultipartFormEntityWrapper implements HttpEntity {

    private HttpEntity httpEntity;

    public MultipartFormEntityWrapper(HttpEntity httpEntity){
        this.httpEntity = httpEntity;
    }

    /* (non-Javadoc)
     * @see org.apache.http.HttpEntity#isRepeatable()
     */
    public boolean isRepeatable() {
        return httpEntity.isRepeatable();
    }

    /* (non-Javadoc)
     * @see org.apache.http.HttpEntity#isChunked()
     */
    public boolean isChunked() {
        return httpEntity.isChunked();
    }

    /* (non-Javadoc)
     * @see org.apache.http.HttpEntity#getContentLength()
     */
    public long getContentLength() {
        return httpEntity.getContentLength();
    }

    /* (non-Javadoc)
     * @see org.apache.http.HttpEntity#getContentType()
     */
    public Header getContentType() {
        return httpEntity.getContentType();
    }

    /* (non-Javadoc)
     * @see org.apache.http.HttpEntity#getContentEncoding()
     */
    public Header getContentEncoding() {
        return httpEntity.getContentEncoding();
    }

    /* (non-Javadoc)
     * @see org.apache.http.HttpEntity#getContent()
     */
    public InputStream getContent() throws IOException, IllegalStateException {
        return httpEntity.getContent();
    }

    /* (non-Javadoc)
     * @see org.apache.http.HttpEntity#writeTo(java.io.OutputStream)
     */
    public void writeTo(OutputStream outputstream) throws IOException {
        httpEntity.writeTo(outputstream);

    }

    /* (non-Javadoc)
     * @see org.apache.http.HttpEntity#isStreaming()
     */
    public boolean isStreaming() {
        return httpEntity.isStreaming();
    }

    /* (non-Javadoc)
     * @see org.apache.http.HttpEntity#consumeContent()
     */
    @SuppressWarnings("deprecation")
    public void consumeContent() throws IOException {
        httpEntity.consumeContent();
    }

}
