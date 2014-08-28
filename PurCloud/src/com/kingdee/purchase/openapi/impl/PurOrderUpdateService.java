package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.openapi.model.PurOrderInfo;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.service.IPurOrderService;
import com.kingdee.purchase.platform.util.SpringContextUtils;

/**
 * 修改PO订单状态API
 * @author RD_cary_lin
 *
 */
public class PurOrderUpdateService extends OpenApiServiceImpl {
	
	private static final String  ORDERID    = "orderId";
	private static final String  SUBUSERID  = "subUserId";
	private static final String  ORDERSTATUS= "orderStatus";
	
	private IPurOrderService orderService = SpringContextUtils.getBean(IPurOrderService.class);

	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
			throws BaseException {
		String[][] NOTNULLFIELD = new String[][]{
				{ORDERID,ParamCheckUtil.STRINGTYPE},
				{ORDERSTATUS,ParamCheckUtil.STRINGTYPE}
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
		StringBuilder error = new StringBuilder();
		error.append(ParamCheckUtil.checkLong(paramsMap.get(SUBUSERID), SUBUSERID));
		if (error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.ILLEGAL_ARGS, error.toString());
		}
	}
	
	
	/**
	 * 执行服务
	 * @param context
	 */
	protected JSONObject invokeDestApi(RequestContext context) throws BaseException{
		Long enterPriseID = context.getSysParamInfo().getEnterPriseID();
		PurOrderInfo info = getPurOrder(context);
		orderService.updatePurOrderStatus(enterPriseID, info);
		
		JSONObject jsonObject = super.invokeDestApi(context);
		
		return jsonObject;
	}
	
	
	/**
	 * 解析context的BussinessParams为purOrder对象
	 * @param context
	 * @return
	 * @throws PurBizException
	 */
	private PurOrderInfo getPurOrder(RequestContext context) throws BaseException {
		Map<String, Object>paramMap = context.getBussinessParams();
		//拼装采购订单
		PurOrderInfo info = new PurOrderInfo();
		String orderId = (String) paramMap.get("orderId");
		info.setOrderId(orderId);
		
		Long enterPriseID = context.getSysParamInfo().getEnterPriseID();
		String aliOrderId = orderService.getAliOrderId(enterPriseID, orderId);
		paramMap.put("orderId", aliOrderId);
		info.setAliOrderId(aliOrderId);
		
		info.setSubUserId((Long)paramMap.get("subUserId"));
		info.setOrderStatus((String) paramMap.get("orderStatus"));
		info.setFailReason((String) paramMap.get("failReason"));
		return info;
	}

}
