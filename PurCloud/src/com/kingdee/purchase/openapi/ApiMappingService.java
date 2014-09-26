package com.kingdee.purchase.openapi;

import java.util.HashMap;
import java.util.Map;

/**
 * 映射SDK中的api 和 云服务中的具体的服务
 * @author RD_cary_lin
 *
 */
public class ApiMappingService {
	
	private static final Map<String, String> apiMap= new HashMap<String, String>();
	
	
	static {
		//发布询价单API
		apiMap.put(OpenApiDefine.BuyOffer_PostBuyOffer,"com.kingdee.purchase.openapi.impl.BuyOfferPostService");
		//关闭询价单API
		apiMap.put(OpenApiDefine.BuyOffer_CloseBuyOffer,"com.kingdee.purchase.openapi.impl.BuyOfferCloseService");
		
		//通过id获取询价单API
		apiMap.put(OpenApiDefine.BuyOffer_GetBuyOfferInfoById,"com.kingdee.purchase.openapi.impl.BuyOfferQueryInfoService");
		//根据prId查询询价单列表API
		apiMap.put(OpenApiDefine.BuyOffer_GetBuyOfferListByPrId,"com.kingdee.purchase.openapi.impl.BuyOfferQueryListService");
		
		//获取供应商API
		apiMap.put(OpenApiDefine.Supplier_GetSupplier,"com.kingdee.purchase.openapi.impl.SupplierQueryService");
		//上传供应商信息API
		apiMap.put(OpenApiDefine.Supplier_PostSupplier,"com.kingdee.purchase.openapi.impl.SupplierUpLoadService");
		
		//上传订单API
		apiMap.put(OpenApiDefine.PurOrder_PostOrderInfo,"com.kingdee.purchase.openapi.impl.PurOrderUpLoadService");
		//更新订单API
		apiMap.put(OpenApiDefine.PurOrder_UpdateOrderInfo,"com.kingdee.purchase.openapi.impl.PurOrderUpdateService");
		
		//上传财务组织API
		apiMap.put(OpenApiDefine.Company_PostCompanyOrg,"com.kingdee.purchase.openapi.impl.CompanyOrgUpLoadService");
		
		//根据报价单ID获取采购商获取报价详情
		apiMap.put(OpenApiDefine.Quotation_GetQuotationInfoById,"com.kingdee.purchase.openapi.impl.QuotationQueryInfoService");
		//根据询价单ID获取报价单列表
		apiMap.put(OpenApiDefine.Quotation_GetQuotationListByBuyOffId,"com.kingdee.purchase.openapi.impl.QuotationQueryListService");
		
		//消息轮循处理
		apiMap.put(OpenApiDefine.Message_LoopList,"com.kingdee.purchase.openapi.impl.MessageLoopService");
		
		apiMap.put(OpenApiDefine.Quotation_GetRptQuotationListByBuyOfferId, "com.kingdee.purchase.openapi.impl.QuotationRptQueryListService");
	}
	
	public static String getServiceClass(String apiName, String version){
		String key = apiName + ":" + version;
		return apiMap.get(key);
	}

}
