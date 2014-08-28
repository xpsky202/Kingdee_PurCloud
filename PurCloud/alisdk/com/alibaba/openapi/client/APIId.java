/**
 * Project: ocean.client.java.basic
 * 
 * File Created at 2011-10-24
 * $Id: APIId.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.io.Serializable;

/**
 * 用于唯一确定一个API，包含: namespace, anme, version
 * @author yuming.wangym@alibaba-inc.com
 * @version 1.0
 */
public class APIId implements Serializable, Comparable<APIId> {
    /**
     * 
     */
    private static final long serialVersionUID = 3310099984731564724L;

    public static final int NONE_VERSION = -1;

    public static final int DEFAULT_VERSION = 1;
    /**
     * 名字空间
     */
    private final String namespace;
    /**
     * 名字
     */
    private final String name;
    /**
     * 版本号，-1表示默认版本
     */
    private final int version;

    public APIId(String namespace, String name) {
        this(namespace, name, NONE_VERSION);
    }

    public APIId(String namespace, String name, int version) {
        this.namespace = namespace;
        this.name = name;
        this.version = version;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(namespace).append(':').append(name).append('-').append(version).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || !(obj instanceof APIId)) {
            return false;
        }
        final APIId other = (APIId) obj;
        if(version == other.version){
            if((namespace != null && namespace.equals(other.namespace)) || (namespace == null && other.namespace == null)){
                if((name != null && name.equals(other.name)) || (name == null && other.name == null)){
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return _hashCode();
    }

    protected int _hashCode() {
        final int iConstant = 37;
        int iTotal = 17;
        if(namespace == null){
            iTotal = iTotal * iConstant;
        }else{
            iTotal = iTotal * iConstant + namespace.hashCode();
        }
        if(name == null){
            iTotal = iTotal * iConstant;
        }else{
            iTotal = iTotal * iConstant + name.hashCode();
        }
        return iTotal * iConstant + version;
    }

    /**
     * 获取名字空间
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * 获取名字
     */
    public String getName() {
        return name;
    }

    /**
     * 获取版本号，-1表示默认版本
     */
    public int getVersion() {
        return version;
    }

    public int compareTo(APIId o) {
        if(o == null) {
            return 1;
        }
        if(o == this) {
            return 0;
        }
        int t;
        if(namespace != o.namespace){
            if(namespace == null){
                return -1;
            }
            if(o.namespace == null){
                return 1;
            }
            t = namespace.compareTo(o.namespace);
            if(t!=0){
               return t;
            }
        }
        if(name != o.name){
            if(name == null){
                return -1;
            }
            if(o.name == null){
                return 1;
            }
            t = name.compareTo(o.name);
            if(t!=0){
               return t;
            }
        }
        if(version != o.version){
            return version > o.version? 1 : -1;
        }
        return 0;
    }
}