package com.kingdee.purchase.platform.processor;

import java.util.Map;

import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.IOpenApiService;
import com.kingdee.purchase.openapi.OpenApiFactory;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.UrlRequestInfo;

public class AbstractApiProcessor {
	
	private String urlPath = null;
	private Map<String, Object>paramMap = null;
	
	public AbstractApiProcessor(String urlPath,Map<String, Object>paramMap) {
		this.urlPath = urlPath;
		this.paramMap = paramMap;
		
	}
	
	public JSONObject execute() throws BaseException {
		//解析URL处理器
		RequestParserProcessor parserProcessor = new RequestParserProcessor(urlPath,paramMap);
		UrlRequestInfo requestInfo = parserProcessor.process();
		
		//URL对象转换处理器
		RequestParamsConversionProcessor conversionProcessor = new RequestParamsConversionProcessor(requestInfo);
		RequestContext context = conversionProcessor.process();
		
		//企业注册校验处理器
		ValidateEnterPriseProcessor validateProcessor = new ValidateEnterPriseProcessor(context);
		validateProcessor.process();
		
		//调用具体API服务
		return processApiService(context);
	}

	/**
	 * 调用具体API服务
	 * @param context
	 * @return
	 * @throws BaseException
	 */
	public JSONObject processApiService(RequestContext context) throws BaseException {
		//根据context的nameSpace和apiName反射生成具体的service
		IOpenApiService service = OpenApiFactory.create(context);
	
		JSONObject result = service.process(context);
		return result;
	}


}
