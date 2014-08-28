/**
 * Project: ocean.client.java.basic
 * 
 * File Created at 2011-10-21
 * $Id: AlibabaAPI.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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



/**
 * 一个api的唯一标识，包括namespace+name+version
 * @author jade
 *
 */
public class AlibabaAPI {
    protected AlibabaClient client;
    protected APIId apiId;
    
    public AlibabaAPI(AlibabaClient client, String namespace, String name, int version){
        this.client = client;
        this.apiId = new APIId(namespace, name, version);
    }
    
    public Object call(){
        
        return null;
    }
}
