/**
 * Project: ocean.client.java.basic
 * 
 * File Created at 2011-11-23
 * $Id: SameObjectChecker.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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
package com.alibaba.openapi.client.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment of SameObjectChecker
 * @author jade
 *
 */
public class SameObjectChecker {
    private final List<Object> objs = new ArrayList<Object>(20);
    
    public boolean checkThenPush(Object in){
        if(in == null){
            return true;
        }
        for (Object obj : objs) {
            if(obj == in){
                return false;
            }
        }
        objs.add(in);
        return true;
    }
    
    public Object pop(){
        if(objs.size() == 0){
            return null;
        }
        return objs.remove(objs.size() - 1);
    }
}
