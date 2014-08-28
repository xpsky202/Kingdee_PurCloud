package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

/**
 * 根据报价单ID获取报价单
 * @author RD_cary_lin
 *
 */
public class QuotationQueryInfoService extends OpenApiServiceImpl {
	//报价单ID
	private static final String QUOTATIONID = "quotationId";
	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)
			throws BaseException {
		Object field = paramsMap.get(QUOTATIONID);
		if (ParamCheckUtil.isEmpty(field)){
			throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{QUOTATIONID,ParamCheckUtil.LONGTYPE});
		}
	}

	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
			throws BaseException {
		StringBuilder error = new StringBuilder();
		error.append(ParamCheckUtil.checkLong(paramsMap.get(QUOTATIONID), QUOTATIONID));
		if (error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.ILLEGAL_ARGS, error.toString());
		}
	}

}
