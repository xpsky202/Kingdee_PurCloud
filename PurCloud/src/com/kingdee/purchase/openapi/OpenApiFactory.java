package com.kingdee.purchase.openapi;

import com.kingdee.purchase.openapi.context.ReqLocateParamInfo;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.exception.PurSysException;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 创建云服务上的具体服务类
 * @author RD_cary_lin
 *
 */
public class OpenApiFactory  {
	
	public static IOpenApiService create(RequestContext context)throws BaseException{
		
		ReqLocateParamInfo locateParamInfo = context.getLocateParamInfo();
		String apiName = locateParamInfo.getApiName();
		String version = locateParamInfo.getVersion();
		//拼接解析的apiName
		String apiClassName = apiName;
		//通过apiClassName查找ApiMappingService找到云服务上真正的类
		String innerClassName = ApiMappingService.getServiceClass(apiClassName, version);
		
		if (StringUtils.isEmpty(innerClassName)) {
			throw new PurSysException(PurExceptionDefine.NOT_FOUND_OPENAPIPROCESSOR,new String[]{apiClassName});
		}
		IOpenApiService apiService;
		try {
			apiService = (IOpenApiService)Class.forName(innerClassName).newInstance();
		} catch (ClassNotFoundException e) {
			throw new PurSysException(PurExceptionDefine.NOT_FOUND_OPENAPIPROCESSOR, new String[] {apiClassName});
		} catch (InstantiationException e) {
			throw new PurSysException(PurExceptionDefine.CREATE_OPENPROCESSOR_ERROR, new String[] {apiClassName});
		} catch (IllegalAccessException e) {
			throw new PurSysException(PurExceptionDefine.CREATE_OPENPROCESSOR_ERROR, new String[] {apiClassName});
		} 
		return apiService;
	}
	
	

}
