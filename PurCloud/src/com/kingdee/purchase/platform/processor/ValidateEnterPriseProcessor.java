package com.kingdee.purchase.platform.processor;

import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.info.UsedStatus;
import com.kingdee.purchase.platform.service.ISystemParamService;
import com.kingdee.purchase.platform.service.SystemParamsServiceFactory;


/**
 * 校验企业是否注册的处理器
 * @author RD_cary_lin
 *
 */
public class ValidateEnterPriseProcessor {
	
	private RequestContext requestContext;
	
	public ValidateEnterPriseProcessor(RequestContext context) {
		this.requestContext = context;
	}
	
	public boolean process()throws BaseException {
		//获取企业注册信息
		ISystemParamService paramService = SystemParamsServiceFactory.getServiceInstance4Alibaba();
		UsedStatus status = paramService.getEnterpriseStatus(requestContext.getSysParamInfo().getEnterPriseID());
		//是否注册
		if (UsedStatus.UNREGISTERED.equals(status)) {
			throw new PurBizException(PurExceptionDefine.ENTERPRISE_UNREGISTED);
		}
		//是否禁用
		if (UsedStatus.DISABLED.equals(status)) {
			throw new PurBizException(PurExceptionDefine.ENTERPRISE_FORBIDDEN);
		}
		
		return true;
	}
	
	

}
