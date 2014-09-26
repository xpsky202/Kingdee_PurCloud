package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.openapi.model.ObjectCollection;
import com.kingdee.purchase.openapi.model.QuotationInfo;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.service.IQuotationService;
import com.kingdee.purchase.platform.service.ISupplierService;
import com.kingdee.purchase.platform.util.SpringContextUtils;

public class QuotationRptQueryListService extends OpenApiServiceImpl {
	public final static String BUYOFFERID = "buyofferId";
	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)
			throws BaseException {
		Object field = paramsMap.get(BUYOFFERID);
		if (ParamCheckUtil.isEmpty(field)){
			throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{BUYOFFERID,ParamCheckUtil.LONGTYPE});
		}
	}
	
	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
			throws BaseException {
		StringBuilder error = new StringBuilder();
		error.append(ParamCheckUtil.checkLong(paramsMap.get(BUYOFFERID), BUYOFFERID));
		if (error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.ILLEGAL_ARGS, error.toString());
		}
	}

	@Override
	protected JSONObject invokeDestApi(RequestContext context)
			throws BaseException {
		String buyOfferId = (String)context.getBussinessParams().get(BUYOFFERID);
		ObjectCollection<QuotationInfo> quoInfos = 
			SpringContextUtils.getBean(IQuotationService.class).getQuotationsByBuyOfferId(buyOfferId);
		for(QuotationInfo quoInfo:quoInfos.getItemsList()){
			quoInfo.setSupplier(SpringContextUtils.getBean(ISupplierService.class).getSupplierInfoByDestId(quoInfo.getSupplierMemberId()));
		}
		JSONObject result = new JSONObject();
		result.put("quotationList", quoInfos.toJSONArray());
		return result;
	}
}
