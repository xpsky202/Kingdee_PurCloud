package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.openapi.model.ObjectCollection;
import com.kingdee.purchase.openapi.model.PurOrderInfo;
import com.kingdee.purchase.openapi.model.PurOrderItem;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.service.IPurOrderService;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 保存采购订单信息API
 * @author RD_cary_lin
 *
 */
public class PurOrderUpLoadService extends OpenApiServiceImpl {
	private static final String  ORDERID = "orderId";
	private static final String  SUBUSERID = "subUserId";
	private static final String  TOTALAMOUNT = "totalAmount";
	private static final String  FREIGHAMOUNT = "freightAmount";
	private static final String  ORDERAMOUNT = "orderAmount";
	private static final String  SOURCETYPE = "sourceType";
	private static final String  ORDERSTATUS = "orderStatus";
	private static final String  ITEMLIST = "orderItemList";
	private static final String  PAYTYPE = "payType";
	
	//orderItemList订单明细
	private static final String ORDERITEMID = "orderItemId";
	private static final String PRODUCTNAME = "productName";
	private static final String PRICE = "price";
	private static final String COUNT = "count";
	
	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
	throws BaseException {
		String[][] NOTNULLFIELD = new String[][]{
				{ORDERID,ParamCheckUtil.STRINGTYPE},
				{TOTALAMOUNT,ParamCheckUtil.LONGTYPE},
				{FREIGHAMOUNT,ParamCheckUtil.LONGTYPE},
				{ORDERAMOUNT,ParamCheckUtil.LONGTYPE},
				{SOURCETYPE,ParamCheckUtil.STRINGTYPE},
				{ORDERSTATUS,ParamCheckUtil.STRINGTYPE},
				{ITEMLIST,ParamCheckUtil.LISTTYPE},
				{PAYTYPE,ParamCheckUtil.STRINGTYPE}
			};
		
		StringBuilder error = new StringBuilder();
		error.append(ParamCheckUtil.checkIsNull(paramsMap,NOTNULLFIELD));
		if (error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS, error.toString());
		}
	}
	
	
	
	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)
			throws BaseException {
		//Long:123
		StringBuilder error = new StringBuilder();
		
		error.append(ParamCheckUtil.checkLong((String)paramsMap.get(SUBUSERID), SUBUSERID));
		error.append(ParamCheckUtil.checkLong((String)paramsMap.get(TOTALAMOUNT), TOTALAMOUNT));
		error.append(ParamCheckUtil.checkLong((String)paramsMap.get(FREIGHAMOUNT), FREIGHAMOUNT));
		error.append(ParamCheckUtil.checkLong((String)paramsMap.get(ORDERAMOUNT), ORDERAMOUNT));
		
		//jsonString
		error.append(checkJsonString((String)paramsMap.get(ITEMLIST), ITEMLIST));
		
		if (error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.ILLEGAL_ARGS, error.toString());
		}
		
	}

	//JSON:[{"orderItemId":"222","supplierCode":"00347","productName":"电脑","price":"100","count":"10","singleFreightAmount":"100"}]
	protected String  checkJsonString(String jsonField, String fieldName) throws PurBizException {
		StringBuilder error = new StringBuilder();
		if (StringUtils.isNotEmpty(jsonField)){
			String[][] NOTNULLFIELD = new String[][]{
					{ORDERITEMID,ParamCheckUtil.LONGTYPE},
					{PRODUCTNAME,ParamCheckUtil.STRINGTYPE},
					{PRICE,ParamCheckUtil.LONGTYPE},
					{COUNT,ParamCheckUtil.LONGTYPE}
				};
			
			String[] LONGFIELD = new String[]{PRICE,COUNT};
			
			JSONObject obj = ParamCheckUtil.getJsonObject(fieldName,jsonField);
			JSONArray array = obj.getJSONArray(ParamCheckUtil.ITEMSLIST);
			JSONObject data = null;
			StringBuilder chkNullStr = new StringBuilder();
			StringBuilder chkFormatStr = new StringBuilder();
			for(int i = 0, size = array.size();i < size;i++){ 
				data = array.getJSONObject(i); 
				chkNullStr.append(ParamCheckUtil.checkIsNull(fieldName,data,NOTNULLFIELD,i));
				
				chkFormatStr.append(ParamCheckUtil.checkLong(fieldName, data, LONGFIELD, i));
			}
			error.append(chkNullStr).append(chkFormatStr);
		}
		return error.toString();
	}
	
	/**
	 * 执行服务
	 * @param context
	 */
	protected JSONObject invokeDestApi(RequestContext context) throws BaseException{
		JSONObject jsonObject = null;		
		PurOrderInfo info = getPurOrder(context);
		try {
			jsonObject = super.invokeDestApi(context);
		} finally {
			if (jsonObject != null) {
				String aliOrderId = jsonObject.optString("orderId");
				info.setAliOrderId(aliOrderId);
			}
			Long enterPriseID = context.getSysParamInfo().getEnterPriseID();
			SpringContextUtils.getBean(IPurOrderService.class).savePurOrderInfo(enterPriseID, info);
		}
		
		return jsonObject;
	}


	/**
	 * 解析context的BussinessParams为purOrder对象
	 * @param context
	 * @return
	 * @throws PurBizException
	 */
	private PurOrderInfo getPurOrder(RequestContext context) throws PurBizException {
		Map<String, Object>paramMap = context.getBussinessParams();
		//拼装采购订单
		PurOrderInfo info = new PurOrderInfo();
		info.setOrderId((String) paramMap.get("orderId"));
		info.setTotalAmount(Long.getLong(paramMap.get("totalAmount").toString()));
		info.setFreightAmount(Long.getLong(paramMap.get("freightAmount").toString()));
		info.setOrderAmount(Long.getLong(paramMap.get("orderAmount").toString()));
		info.setSourceType((String) paramMap.get("sourceType"));
		info.setOrderStatus((String) paramMap.get("orderStatus"));
		info.setFailReason((String) paramMap.get("failReason"));
		info.setBuyerCompanyName((String) paramMap.get("buyerCompanyName"));
		info.setBuyerMobile((String) paramMap.get("buyerMobile"));
		info.setBuyerEmail((String) paramMap.get("buyerEmail"));
		
		info.setSupplierCode((String) paramMap.get("supplierCode"));
		info.setSupplierCompanyName((String) paramMap.get("supplierCompanyName"));
		info.setSupplierMemberId((String) paramMap.get("supplierMemberId"));
		info.setSupplierEmail((String) paramMap.get("supplierEmail"));
		info.setSupplierMobile((String) paramMap.get("supplierMobile"));
		
		
		//拼装采购订单分录
		PurOrderItem item = null;
		String itemValue = (String)paramMap.get("orderItemList");
		JSONObject jsonItem = ParamCheckUtil.getJsonObject("orderItemList",itemValue);
		JSONArray array = jsonItem.getJSONArray(ParamCheckUtil.ITEMSLIST);
		JSONObject data = null;
		ObjectCollection<PurOrderItem> col = new ObjectCollection<PurOrderItem>();
		for(int i = 0, size = array.size();i < size;i++){ 
			data = array.getJSONObject(i); 
			
			item = new PurOrderItem();
			item.setOrderItemId(data.optString("orderItemId"));
			
			item.setReceiverAddress(data.optString("receiverAddress"));
			item.setReceiverMobile(data.optString("receiverMobile"));
			item.setReceiverName(data.optString("receiverName"));
			item.setReceiverPhone(data.optString("receiverPhone"));
			item.setReceiverPostCode(data.optString("receiverPostCode"));
			
			
			item.setProductName(data.optString("productName"));
			item.setPrice(data.optLong("price"));
			item.setCount(data.optLong("count"));
			item.setOtherInfo(data.optString("otherInfo"));
			
			col.addItem(item);
		}
		info.setOrderItemList(col);
		
		return info;
	}
	
}
