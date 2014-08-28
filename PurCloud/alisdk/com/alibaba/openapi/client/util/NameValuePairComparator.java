/**
 * Project: ocean.client.java.basic
 * 
 * File Created at 2011-11-18
 * $Id: NameValuePairComparator.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.util.Comparator;

import org.apache.http.NameValuePair;

/**
 * Comment of NameValuePairComparator
 * 
 * @author jade
 */
public class NameValuePairComparator<T extends NameValuePair> implements Comparator<T> {
    public int compare(NameValuePair o1, NameValuePair o2) {
        if (o1 == o2) {
            return 0;
        }
        final String k1 = o1.getName();
        final String v1 = o1.getValue();
        final String k2 = o2.getName();
        final String v2 = o2.getValue();
        int l1 = k1.length();
        int l2a = k2.length();
        int l2b = v2.length();
        int i, j;
        for (i = 0; i < l1; i++) {
            char c1 = k1.charAt(i);
            char c2;
            if (i < l2a) {
                c2 = k2.charAt(i);
            } else if (i < l2a + l2b) {
                c2 = v2.charAt(i - l2a);
            } else {
                return 1;
            }
            if (c1 > c2) {
                return 1;
            } else if (c1 < c2) {
                return -1;
            }
        }
        l1 = v1.length();
        for (j = 0; j < l1; j++, i++) {
            char c1 = v1.charAt(j);
            char c2;
            if (i < l2a) {
                c2 = k2.charAt(i);
            } else if (i < l2a + l2b) {
                c2 = v2.charAt(i - l2a);
            } else {
                return 1;
            }
            if (c1 > c2) {
                return 1;
            } else if (c1 < c2) {
                return -1;
            }
        }
        if(i < l2a + l2b){
            return -1;
        }else if(i > l2a + l2b){
            return 1;
        }
        return 0;
    }
}
