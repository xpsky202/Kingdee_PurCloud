package com.kingdee.purchase.openapi.client.test;

import java.sql.Timestamp;

import junit.framework.TestCase;

import com.kingdee.purchase.openapi.client.PurCloudClient;
import com.kingdee.purchase.openapi.client.Request;
import com.kingdee.purchase.openapi.client.exception.PurCloudException;
import com.kingdee.purchase.openapi.client.policy.ClientPolicy;
import com.kingdee.purchase.openapi.client.policy.RequestPolicy;

public class OpenApiClientTest extends TestCase {
	
	public static final int TIMEOUT = 30000;  //调用超时时间
	public static final String ENCODE = "UTF-8";  //请求数据编码格式
	//public static final String host = "http://192.168.204.131:8086/purchase/openapi";
	public static final String nameSpace = "com.kingdee.purchase.openapi";
	
	private PurCloudClient client;
	
	public void setUp() {
		ClientPolicy policy = new ClientPolicy("192.168.204.131").setHttpPort(8086);
		policy.setEnterpriseId("16582049").setOrgUnitId("AIS20121114135552").setUserId("16394");
		client = new PurCloudClient(policy);
	}
	
	private void execute(Request request, RequestPolicy basePolicy) {
		try {
			String result = client.send(request, basePolicy);
			System.out.println(result);
		} catch (PurCloudException e) {
			System.out.println("ErrorCode: " + e.getErrorCode() + "; ErrorMessage: " + e.getErrorMessage());
		}
	}
	
	public void te11stPostBuyOff() {
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ENCODE).setTimeout(0);
		Request request = new Request(nameSpace, "buyoffer.postBuyOffer", 1);
		request.setParam("subject", "3605512032");
		request.setParam("sourceType", "kingdee");
		request.setParam("items", "[{\"subject\":\"苹果\",\"unit\":\"个\",\"purchaseAmount\":12,\"productCode\":\"123\",\"prItemId\":\"1\"},{\"subject\":\"香蕉\",\"unit\":\"斤\",\"purchaseAmount\":4,\"productCode\":\"345\",\"prItemId\":\"1\"}]");
		request.setParam("contact", "sky");
		request.setParam("phone", "13800010002");
		request.setParam("gmtQuotationExpire", "2010-10-12 12:32:55");
		request.setParam("prId", "10001");
		
		execute(request, basePolicy);
	}
	
	public void te12stQueryBuyOffByPrId() {
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ENCODE).setTimeout(0);
		Request request = new Request(nameSpace, "buyoffer.getBuyOfferListByPrId", 1);
		request.setParam("prId", "Oe+SJyxBQk68nalhsFBHHyVCjy4=");
		
		execute(request, basePolicy);
	}
	
	public void te12stQueryBuyOffById() {
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ENCODE).setTimeout(0);
		Request request = new Request(nameSpace, "buyoffer.getBuyOfferInfoById", 1);
		request.setParam("buyOfferId", "29126990");
		
		execute(request, basePolicy);
	}
	
	public void te12stCloseBuyOff() {
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ENCODE).setTimeout(0);
		Request request = new Request(nameSpace, "buyoffer.closeBuyOffer", 1);
		request.setParam("buyOfferId", "700026081");
		request.setParam("closeReason", "报价截止，已下单");
		
		execute(request, basePolicy);
	}
	
	public void te12stSaveOrderInfo() {
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ENCODE).setTimeout(0);
		Request request = new Request(nameSpace, "purorder.postOrderInfo", 1);
		request.setParam("orderId", "cmbYOT7sQiyL38yu9IUZkjFxv60=");
		request.setParam("totalAmount", "562013");
		request.setParam("freightAmount", "0");
		request.setParam("orderAmount", "562013");
		request.setParam("sourceType", "BUYOFFER");
		request.setParam("orderStatus", "ordered");
		request.setParam("supplierCompanyName", "引入04");
		request.setParam("supplierCode", "yr04");
		request.setParam("supplierMemberId", "foxtest002");
		
		request.setParam("orderItemList", "[{\"price\":1000,\"productName\":\"橄榄油\",\"orderItemId\":\"I4cD8Xk6QNatyZEoPdV4HCYEHMU=\",\"count\":50},{\"price\":2560,\"productName\":\"越野车\",\"orderItemId\":\"eYNGbxeaTyaEBbQgtJqwzyYEHMU=\",\"count\":200}]");
		request.setParam("payType", "offline");
		
		execute(request, basePolicy);
	}

	public void t12estUpdateOrderStatus() {
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ENCODE).setTimeout(0);
		Request request = new Request(nameSpace, "purorder.updateOrderInfo", 1);
		
		request.setParam("orderId", "10022");
		request.setParam("orderStatus", "invalid");
		
		execute(request, basePolicy);
	}
	
	public void te12stGetSupplier() {
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ENCODE).setTimeout(0);
		Request request = new Request(nameSpace, "supplier.getSupplier", 1);
		request.setParam("memberId", "foxtest001");
		
		execute(request, basePolicy);
	}
	
	public void testGetQuotationListByBuyOfferId() {
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ENCODE).setTimeout(0);
		Request request = new Request(nameSpace, "quotation.getQuotationListByBuyOffId", 1);
		request.setParam("buyofferId", "99385241251");
		
		execute(request, basePolicy);
	}

}
