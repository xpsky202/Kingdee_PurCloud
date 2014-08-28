package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 获取供应商
 * @author RD_cary_lin
 *
 */
public class SupplierQueryService extends OpenApiServiceImpl {


	private static final String MEMBERID = "memberId";
	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
			throws BaseException {
		String field = (String)paramsMap.get(MEMBERID);
		if (StringUtils.isEmpty(field)){
			throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{MEMBERID,ParamCheckUtil.STRINGTYPE});
		}
	}
	
	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)
	throws BaseException {
		// TODO Auto-generated method stub
	}
	


}
