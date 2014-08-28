package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

/**
 * 根据询价单id获取报价详情列表
 * @author RD_cary_lin
 *
 */
public class QuotationQueryListService extends OpenApiServiceImpl {
	//询价单ID
	private static final String BUYOFFERID="buyofferId";
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

}
