package com.kingdee.purchase.openapi;

import java.util.Map;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.DestApiInvokeService;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;

public abstract class OpenApiServiceImpl implements IOpenApiService {
	
	/**
	 * 接口方法，利用模板实现：
	 * 1 参数的初始化和校验
	 * 2 调用外部系统的handler
	 * 3 转换结果集
	 * @param context
	 */
	public JSONObject process(RequestContext context) throws BaseException  {
		//初始化具体参数对象后校验
		initParamAndCheckValidate(context);
		
		//执行服务
		JSONObject outputResult = invokeDestApi(context);
			
		//转换结果集
		JSONObject result = buildOutPutResult(outputResult);
		
		return result;
	}
	
	/**
	 * 初始化具体参数对象后校验
	 * @param context
	 */
	protected void initParamAndCheckValidate(RequestContext context) throws BaseException {
		
		initSpecialParam(context);
		
		Map<String, Object> paramsMap = context.getBussinessParams();
		//校验业务参数是否为空
		checkParamIsNull(paramsMap);
		//校验业务参数格式是否合法
		checkParamFormat(paramsMap);
	}
	
	/**
	 * 处理特殊的场景参数
	 * @param context
	 */
	protected void initSpecialParam(RequestContext context)throws BaseException{
		
	}
	
	/**
	 * 校验业务参数是否为空
	 * @param paramsMap
	 */
	protected abstract void checkParamIsNull(Map<String, Object> paramsMap) throws BaseException;

	
	/**
	 * 校验业务参数格式是否合法
	 * @param paramsMap
	 */
	protected abstract void checkParamFormat(Map<String, Object> paramsMap) throws BaseException;
	
	
	
	/**
	 * 执行服务
	 * @param context
	 */
	protected JSONObject invokeDestApi(RequestContext context) throws BaseException{
		return DestApiInvokeService.invoke(context);
	}
	
	/**
	 * 转换结果集
	 * @param alibabaResult
	 * @return
	 */
	protected JSONObject buildOutPutResult(JSONObject outputResult) {
		return outputResult;
	}

}
