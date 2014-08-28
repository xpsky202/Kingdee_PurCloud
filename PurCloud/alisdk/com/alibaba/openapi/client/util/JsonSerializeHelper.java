/**
 * Project: ocean.client.java.basic
 * 
 * File Created at 2011-11-23
 * $Id: JsonSerializeHelper.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
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

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.BigIntegerNode;
import org.codehaus.jackson.node.BinaryNode;
import org.codehaus.jackson.node.BooleanNode;
import org.codehaus.jackson.node.DecimalNode;
import org.codehaus.jackson.node.DoubleNode;
import org.codehaus.jackson.node.IntNode;
import org.codehaus.jackson.node.LongNode;
import org.codehaus.jackson.node.NullNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.TextNode;

/**
 * Comment of JsonSerializeHelper
 * @author jade
 *
 */
public final class JsonSerializeHelper {
    private static final int DEFAULT_VERSION = 2;

    public static String object2json(Object result) throws JsonProcessingException, IOException {
        return object2json(result, null);
    }

    /**
     * 将一个对象序列化成json格式，目前只支持java基本对象：String,long,int,flout,double,boolean,Date
     * 及其基本的List,Set,Map等collections接口
     * 
     * @param result
     * @param objs 已经处理过的对象，防止递归引用
     * @return json
     * @throws IOException 
     * @throws JsonProcessingException 
     */
    public static String object2json(Object result, SameObjectChecker objs) throws JsonProcessingException, IOException {
        JsonNode node = object2Node(result, objs, DEFAULT_VERSION);
        return JsonMapper.node2json(node);
    }
    
    public static JsonNode object2Node(Object result, SameObjectChecker objs){
        return object2Node(result, objs, DEFAULT_VERSION);
    }
    
    public static JsonNode object2Node(Object result, SameObjectChecker objs, int version) {
        if (result == null) {
            return NullNode.instance;
        }
        final Class<?> clazz = result.getClass();
        if(String.class.isAssignableFrom(clazz) || Character.TYPE.equals(clazz) || Character.class.equals(clazz)){
            return new TextNode(result.toString());
        }
        if(Integer.TYPE.equals(clazz) || Integer.class.equals(clazz) 
                || Byte.TYPE.equals(clazz) || Byte.class.equals(clazz)
                || Short.TYPE.equals(clazz) || Short.class.equals(clazz)){
            return new IntNode(((Number) result).intValue());
        }
        if(Boolean.TYPE.equals(clazz) || Boolean.class.equals(clazz)){
            return BooleanNode.valueOf((Boolean) result);
        }
        if(Long.TYPE.equals(clazz) || Long.class.equals(clazz)){
            return new LongNode((Long) result);
        }
        if(Double.TYPE.equals(clazz) || Double.class.equals(clazz)){
            return new DoubleNode((Double) result);
        }
        if(Float.TYPE.equals(clazz) || Float.class.equals(clazz)){
            return new DoubleNode((Float) result);
        }
        // 日期
        if (Date.class.isAssignableFrom(clazz)) {
            return new TextNode(DateUtil.format((Date) result));
        }
        if (objs == null) {
            objs = new SameObjectChecker();
        }
     // map
        if (Map.class.isAssignableFrom(clazz)) {
            if(objs.checkThenPush(result)){
                Map<?, ?> map = (Map<?, ?>) result;
                ObjectNode node = objectMapSerialize(map, objs, version);
                objs.pop();
                return node;
            }else{
                LoggerHelper.getClientLogger().warning("iterative reference ,ignore it .");
                return NullNode.instance;
            }
        }
        if(byte[].class.equals(clazz)){
            return new BinaryNode((byte[]) result);
        }
        // 数组和列表
        if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
            if(objs.checkThenPush(result)){
                if (result.getClass().isArray()) {
                    ArrayNode node = JsonMapper.createArrayNode();
                    for (int i = 0; i < Array.getLength(result); i++) {
                        if(Array.get(result, i) != null || version == 1){
                            node.add(object2Node(Array.get(result, i), objs, version));
                        }
                    }
                    return node;
                } else {
                    Collection<?> coll = (Collection<?>) result;
                    ArrayNode node = JsonMapper.createArrayNode();
                    for (Object obj : coll) {
                        if(obj != null || version == 1){
                            node.add(object2Node(obj, objs, version));
                        }
                    }
                    return node;
                }
            }
            LoggerHelper.getClientLogger().warning("iterative reference ,ignore it .");
            return NullNode.instance;
        }
        if(BigInteger.class.equals(clazz)){
            return new BigIntegerNode((BigInteger)result);
        }
        if(BigDecimal.class.equals(clazz)){
            return new DecimalNode((BigDecimal)result);
        }
        if(Class.class.equals(clazz)){
            return new TextNode(((Class<?>)result).getSimpleName());
        }
        return new TextNode(result.toString());
//        OceanLog.system.warn("Not supported type" + result.getClass() +", use toString() serialize.");
//        return MissingNode.getInstance();
    }

    private static ObjectNode objectMapSerialize(Map<?, ?> map, final SameObjectChecker objs, int version) {
        ObjectNode node = JsonMapper.createObjectNode();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = String.valueOf(entry.getKey());
            Object value = entry.getValue();
            if(value != null || version == 1){
                node.put(key, object2Node(value, objs, version));
            }
        }
        return node;
    }
    
    private JsonSerializeHelper(){
    }
}
