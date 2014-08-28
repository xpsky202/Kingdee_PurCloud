package com.kingdee.purchase.platform.processor;

import com.kingdee.purchase.openapi.context.ReqLocateParamInfo;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.openapi.context.SysParamInfo;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.UrlRequestInfo;
import com.kingdee.purchase.platform.info.UserInfo;
import com.kingdee.purchase.platform.service.ISystemParamService;
import com.kingdee.purchase.platform.service.SystemParamsServiceFactory;

/**
 * 讲对象UrlRequestInfo封装为RequestContext处理器
 * @author RD_cary_lin
 *
 */
public class RequestParamsConversionProcessor {
	
	private UrlRequestInfo requestInfo;
	
	public RequestParamsConversionProcessor(UrlRequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}
	
	/**
	 * 将单纯的解析的URL对象封装为RequestContext
	 * @return
	 */
	public RequestContext process() throws BaseException {
		
		RequestContext requestContext = new RequestContext();
		SysParamInfo sysParamInfo = new SysParamInfo();
		ReqLocateParamInfo locateParamInfo = new ReqLocateParamInfo();
		
		long enterpriseId=  Long.parseLong(requestInfo.getEnterPriseID());
		String companyid = requestInfo.getOrgUnitID();
		String userId = requestInfo.getUserID();
		UserInfo userInfo = new UserInfo(userId);
		sysParamInfo.setUserInfo(userInfo);
		if(!"message.getMessageList".equals(requestInfo.getApiName())
				&& !"supplier.postSupplier".equals(requestInfo.getApiName())
				&& !"company.postCompanyOrg".equals(requestInfo.getApiName())){
			//查询B2B平台上的子账号ID
			ISystemParamService paramService = SystemParamsServiceFactory.getServiceInstance4Alibaba();
			String destId = paramService.getB2BSubAccount(enterpriseId, companyid, userId);
			userInfo.setDestId(destId);
		}
		sysParamInfo.setOrgUnitID(requestInfo.getOrgUnitID());
		//设置系统参数
		sysParamInfo.setEnterPriseID(Long.parseLong(requestInfo.getEnterPriseID()));
		//设置定位参数
		locateParamInfo.setUrlHead(requestInfo.getUrlHead());
		locateParamInfo.setProtocol(requestInfo.getProtocol());
		locateParamInfo.setVersion(requestInfo.getVersion());
		locateParamInfo.setNameSpace(requestInfo.getNameSpace());
		locateParamInfo.setApiName(requestInfo.getApiName());
		
		//组成上下文
		requestContext.setSysParamInfo(sysParamInfo);
		requestContext.setLocateParamInfo(locateParamInfo);
		//设置业务参数
		requestContext.setBussinessParams(requestInfo.getParams());
		
		return requestContext;
	}
	

}
