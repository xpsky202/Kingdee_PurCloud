package com.kingdee.purchase.platform.service;

import com.kingdee.purchase.platform.util.SpringContextUtils;

/***
 * 系统参数服务接口的实例化工厂
 * 
 * @author RD_jiangkun_zhu
 *
 */
public final class SystemParamsServiceFactory {
	
	private SystemParamsServiceFactory(){}
	
	public final static ISystemParamService getServiceInstance4Alibaba(){
		return SpringContextUtils.getBean("systemParamService4Alibaba",ISystemParamService.class);
	}
}