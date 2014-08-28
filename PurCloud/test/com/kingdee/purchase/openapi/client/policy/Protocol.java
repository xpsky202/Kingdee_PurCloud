package com.kingdee.purchase.openapi.client.policy;

public enum Protocol {
	/**
     * 默认推荐的协议格式，同http请求的get/post请求格式
     */
    param2,
    /**
     * 请求参数通过json串的方式传递,默认格式_data_={"key":"value"}
     */
    json2,
    /**
     * 
     * 请求参数通过xml的方式传递,默认格式_data_=&lt;test>data&lt;/test>
     */
    xml2,
    param,
    json,
    xml,
    http
}
