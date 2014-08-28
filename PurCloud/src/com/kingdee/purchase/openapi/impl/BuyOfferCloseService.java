package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

/**
 * 关闭询价单API
 * @author RD_cary_lin
 */
public class BuyOfferCloseService extends OpenApiServiceImpl {
	
	//询价单id
	private static final String BUYOFFERID = "buyOfferId";
	//关闭询价单原因
	private static final String CLOSEREASON = "closeReason";

	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
			throws BaseException {
		
		String[][] NOTNULLFIELD = new String[][]{
				{BUYOFFERID,ParamCheckUtil.LONGTYPE},
				{CLOSEREASON,ParamCheckUtil.STRINGTYPE}
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
		//Long:123
		error.append(ParamCheckUtil.checkLong(paramsMap.get(BUYOFFERID), BUYOFFERID));
		if (error.toString()!= null && error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.ILLEGAL_ARGS, error.toString());
		}

	}

}
