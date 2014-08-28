package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

/**
 * 通过id获取询价单
 * @author RD_cary_lin
 *
 */
public class BuyOfferQueryInfoService extends OpenApiServiceImpl {
	//ali返回询价单ID
	private static final String BUYOFFERID = "buyOfferId";


	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
			throws BaseException {
		// TODO Auto-generated method stub
		Object value = paramsMap.get(BUYOFFERID);
		if (ParamCheckUtil.isEmpty(value)){
			throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{BUYOFFERID,ParamCheckUtil.LONGTYPE});
		}
	}
	
	
	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)
	throws BaseException {
		StringBuilder error = new StringBuilder();
		error.append(ParamCheckUtil.checkLong(paramsMap.get(BUYOFFERID), BUYOFFERID));
		if (error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.ILLEGAL_ARGS, error.toString());
		}
		
	}
	
	/**
	 * 转换结果集
	 * @param alibabaResult
	 * @return
	 */
	protected JSONObject buildOutPutResult(JSONObject outputResult) {
		
		//TODO 包装为询价单对象
		return outputResult;
	}

}
