package com.kingdee.purchase.openapi;

/**
 * 对外API接口定义
 * @author RD_cary_lin
 *
 */
public class OpenApiDefine {
	
	//发布询价单API
	public static final String BuyOffer_PostBuyOffer = "buyoffer.postBuyOffer:1";
	//通过id获取询价单API
	public static final String BuyOffer_GetBuyOfferInfoById = "buyoffer.getBuyOfferInfoById:1";
	//根据prId查询询价单列表API
	public static final String BuyOffer_GetBuyOfferListByPrId = "buyoffer.getBuyOfferListByPrId:1";
	//关闭询价单
	public static final String BuyOffer_CloseBuyOffer = "buyoffer.closeBuyOffer:1";
	
	//获取供应商API
	public static final String Supplier_GetSupplier = "supplier.getSupplier:1";
	//上传供应商信息API
	public static final String Supplier_PostSupplier = "supplier.postSupplier:1";
	
	//上传订单API
	public static final String PurOrder_PostOrderInfo = "purorder.postOrderInfo:1";
	//更新订单API
	public static final String PurOrder_UpdateOrderInfo = "purorder.updateOrderInfo:1";
	
	//上传财务组织API
	public static final String Company_PostCompanyOrg = "company.postCompanyOrg:1";
	//创建子账号
	public static final String User_CreateSubAccount = "user.createSubAccount:1";
	
	//根据报价单ID获取采购商获取报价详情
	public static final String Quotation_GetQuotationInfoById = "quotation.getQuotationInfoById:1";
	//根据询价单ID获取报价单列表
	public static final String Quotation_GetQuotationListByBuyOffId = "quotation.getQuotationListByBuyOffId:1";
	
	//消息轮循处理
	public static final String Message_LoopList = "message.getMessageList:1";
	
	
}
