package com.kingdee.purchase.httpClient.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import junit.framework.TestCase;

public class HttpClientTest extends TestCase {
	HttpClient client;
	public void setUp(){
		client = new HttpClient();
	}
	public void execute(PostMethod method){
		try{
			client.executeMethod(method);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void testPostQuotitionMessage(){
		PostMethod method = new PostMethod("http://172.20.136.79:8080/purchase/message");
		String data = "{\"id\":\"10001\",\"gmtBorn\":\"1396257442468\",\"companyId\":\"8r0AAAAAAOzM567U\"," +
				"\"type\":\"CAIGOU_MSG_SUPPLIER_QUOTATION\"," +
				"\"data\":{\"supplierMemberId\":\"b2b-1919444412\",\"quotationId\":\"85024666\",\"prId\":\"8r0AAAADNFUlQo8u\",\"buyOfferId\":\"28528850\"}," +
				"\"enterpriseid\":\"12903212\",\"userInfo\":\"b2b-2010017793\",\"status\":\"1\"}";
		method.addParameter("message", data);
		execute(method);
	}
}
