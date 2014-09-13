package com.kingdee.purchase.platform.service.message;

import com.kingdee.purchase.openapi.context.ReqLocateParamInfo;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.openapi.context.SysParamInfo;
import com.kingdee.purchase.platform.info.msg.MessageInfo;

public class MessageUtils {
	
	/**
	 * 获取API上下文
	 * @param messageInfo
	 * @return
	 */
	public static RequestContext getRequestContext(MessageInfo messageInfo,
			String nameSpace,String apiName, String version){
		RequestContext requestContext = new RequestContext();
		SysParamInfo sysParamInfo = new SysParamInfo();
		ReqLocateParamInfo locateParamInfo = new ReqLocateParamInfo();
		sysParamInfo.setEnterPriseID(messageInfo.getEnterpriseId());
		sysParamInfo.setOrgUnitID(messageInfo.getCompanyId());
		locateParamInfo.setNameSpace(nameSpace);
		locateParamInfo.setApiName(apiName);
		locateParamInfo.setVersion(version);
		requestContext.setSysParamInfo(sysParamInfo);
		requestContext.setLocateParamInfo(locateParamInfo);
		return requestContext;
	}
}
