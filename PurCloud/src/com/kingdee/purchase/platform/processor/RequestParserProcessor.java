package com.kingdee.purchase.platform.processor;

import java.util.Map;

import com.kingdee.purchase.config.SysParamConstant;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.info.UrlRequestInfo;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 解析URL为对象requestInfo处理器
 * @author RD_cary_lin
 *
 */
public class RequestParserProcessor {
	

	/**
	 * URL规则(http|https)://gw.open.1688.com/openapi[/protocol[/defaultVersion[/defaultNamespace[/apiName[/appKey]]]]][?queryString]
	 * rulhead:(http|https)://gw.open.1688.com/openapi
	 * protocol:params/json
	 * defaultVersion:
	 * defaultNamespace:
	 * apiName:
	 * appKey:
	 * queryString:
	 */
	private String url;
	
	private Map<String, Object> paramsMap;
	
	private final String OPENAPI = "openapi";
	/**
	 * urlPath:(http|https)://gw.open.1688.com/openapi[/protocol[/defaultVersion[/defaultNamespace[/apiName[/appKey]]]]]
	 * params:sourceType=1
	 */
	public RequestParserProcessor(String urlPath, Map<String, Object>params){
		url = urlPath;
		paramsMap = params; 
	}
	
	/**
	 * 解析(http|https)://gw.open.1688.com/openapi[/protocol[/defaultVersion[/defaultNamespace[/apiName]]]]]
	 * 返回UrlRequestInfo
	 * @throws PurBizException 
	 */
	public UrlRequestInfo process() throws PurBizException {

		String urlHead = null;
		
		checkUrlValidate();
		
		UrlRequestInfo requestInfo = new UrlRequestInfo();
		
		int index = url.indexOf(OPENAPI);
		urlHead = url.substring(0,index+OPENAPI.length());
		url = url.substring(index+OPENAPI.length()+1);
		String partsURL[] =  url.split("/");
		//URL组成部分[/protocol[/defaultVersion[/defaultNamespace[/apiName]必须要有4个部分
		if (partsURL!= null && partsURL.length<4){
			throw new PurBizException(PurExceptionDefine.URL_INVALID);
		}
		//system parameter
		requestInfo.setEnterPriseID(paramsMap.get(SysParamConstant.ENTERPRISEID).toString());
		if(url.indexOf("message.getMessageList")==-1){
			requestInfo.setUserID(paramsMap.get(SysParamConstant.USERID).toString());
			requestInfo.setOrgUnitID(paramsMap.get(SysParamConstant.ORGUNITID).toString());			
		}
		
		//locateparameter
		requestInfo.setUrlHead(urlHead);
		requestInfo.setProtocol(partsURL[0]);
		requestInfo.setVersion(partsURL[1]);
		requestInfo.setNameSpace(partsURL[2]);
		requestInfo.setApiName(partsURL[3]);
		
		//queryString and system parameters Map
		requestInfo.setParams(paramsMap);
		return requestInfo;
		
	}

	/**
	 * 检查URL的有效性及系统参数的有效性
	 * @throws PurBizException 
	 */
	private void checkUrlValidate() throws PurBizException {
		int index = url.indexOf(OPENAPI);
		if (index<0){
			throw new PurBizException(PurExceptionDefine.URL_INVALID);
		}
		
		if (url.indexOf("message.getMessageList")!=-1){
			if (StringUtils.isEmpty((String)paramsMap.get(SysParamConstant.ENTERPRISEID))
					|| StringUtils.isEmpty((String)paramsMap.get(SysParamConstant.USERID))){
				throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{"enterpriseid || userid ","java.lang.String"});
			}
		}else{
			if (StringUtils.isEmpty((String)paramsMap.get(SysParamConstant.ENTERPRISEID))
					|| StringUtils.isEmpty((String)paramsMap.get(SysParamConstant.USERID)) 
					|| StringUtils.isEmpty((String)paramsMap.get(SysParamConstant.ORGUNITID))){
				throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{"enterpriseid || userid || orgunitid","java.lang.String"});
			}
		}
		
		
	}
	
}
