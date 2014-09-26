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
		//解决传递过来的prID存在"+"号时，"+"号被换成了空格，下面操作进行还原
		String tmpValue = value.toString();
		tmpValue = tmpValue.replaceAll(" ", "+");
		paramsMap.put(PRID, tmpValue);

	}
	
	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)
			throws BaseException {
		// TODO Auto-generated method stub

	}

}
