package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

/**
 * 根据prId查询询价单列表
 * @author RD_cary_lin
 *
 */
public class BuyOfferQueryListService extends OpenApiServiceImpl {
	//外部系统prId
	private static final String PRID = "prId";

	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
			throws BaseException {
		Object value = paramsMap.get(PRID);
		if (ParamCheckUtil.isEmpty(value)){
			throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{PRID,ParamCheckUtil.STRINGTYPE});
		}
	}
	
	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)
			throws BaseException {
		// TODO Auto-generated method stub

	}

}
