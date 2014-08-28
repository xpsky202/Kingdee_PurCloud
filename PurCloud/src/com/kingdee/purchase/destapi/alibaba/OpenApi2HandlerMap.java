package com.kingdee.purchase.destapi.alibaba;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.purchase.openapi.OpenApiDefine;

public class OpenApi2HandlerMap {
	
	public static Map<String, String> apiName2class = new HashMap<String, String>();
	
	static {
		//创建子账号
		apiName2class.put(OpenApiDefine.User_CreateSubAccount, "com.kingdee.purchase.destapi.alibaba.apihandler.CreateSubAccountHandler");
		//发布询价单
		apiName2class.put(OpenApiDefine.BuyOffer_PostBuyOffer, "com.kingdee.purchase.destapi.alibaba.apihandler.PostBuyOfferHandler");
		//查询询价单信息
		apiName2class.put(OpenApiDefine.BuyOffer_GetBuyOfferInfoById, "com.kingdee.purchase.destapi.alibaba.apihandler.GetBuyOfferByIdHandler");
		//查询询价单列表
		apiName2class.put(OpenApiDefine.BuyOffer_GetBuyOfferListByPrId, "com.kingdee.purchase.destapi.alibaba.apihandler.GetBuyOfferListByPrIdHandler");
		//关闭询价单
		apiName2class.put(OpenApiDefine.BuyOffer_CloseBuyOffer, "com.kingdee.purchase.destapi.alibaba.apihandler.CloseBuyOfferHandler");
		
		//保存采购订单
		apiName2class.put(OpenApiDefine.PurOrder_PostOrderInfo, "com.kingdee.purchase.destapi.alibaba.apihandler.PostOrderHandler");
		//更新采购订单
		apiName2class.put(OpenApiDefine.PurOrder_UpdateOrderInfo, "com.kingdee.purchase.destapi.alibaba.apihandler.UpdateOrderStatusHandler");
		
		//获取供应商信息
		apiName2class.put(OpenApiDefine.Supplier_GetSupplier, "com.kingdee.purchase.destapi.alibaba.apihandler.GetSupplierHandler");
		//上传供应商信息
		apiName2class.put(OpenApiDefine.Supplier_PostSupplier, "com.kingdee.purchase.destapi.alibaba.apihandler.PostSupplierHandler");
		
		//查询报价单信息
		apiName2class.put(OpenApiDefine.Quotation_GetQuotationInfoById, "com.kingdee.purchase.destapi.alibaba.apihandler.GetQuotationByIdHandler");
		//查询报价单列表
		apiName2class.put(OpenApiDefine.Quotation_GetQuotationListByBuyOffId, "com.kingdee.purchase.destapi.alibaba.apihandler.GetQuotationListByBuyOffIdHandler");
	}
	
	public static String getHandlerClass(String apiName, int version) {
		String key = apiName + ":" + version;
		return apiName2class.get(key);
	}

}
