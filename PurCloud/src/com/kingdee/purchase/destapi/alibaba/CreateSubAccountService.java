package com.kingdee.purchase.destapi.alibaba;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.DestApiInvokeService;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurSysException;
import com.kingdee.purchase.platform.info.UserInfo;
import com.kingdee.purchase.platform.service.SystemParamsServiceFactory;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 根据用户创建1688子账号服务
 * @author RD_sky_lv
 *
 */

public class CreateSubAccountService {
	
	public static final String RESULT_KEY = "subUserId";
	
	/**
	 *  创建1688子账号，并保存到用户信息表中
	 * @param ctx  调用上下文
	 * @return 创建的子账号在阿里的内部ID
	 * @throws PurSysException
	 * @throws PurBizException
	 */
	public static boolean createSubAccount(RequestContext ctx) throws BaseException {
		UserInfo user = ctx.getSysParamInfo().getUserInfo();
		
		if (!StringUtils.isEmpty(user.getDestId())) {
			return true;
		}
		RequestContext tmpCtx = ctx.clone();
		tmpCtx.getLocateParamInfo().setApiName("user.createSubAccount");
		JSONObject jsonObject = DestApiInvokeService.invoke(tmpCtx);
		
		String destId = jsonObject.getString(RESULT_KEY);
		user.setDestId(destId);
		
		long enterpriseId = ctx.getSysParamInfo().getEnterPriseID();
		String companyId = ctx.getSysParamInfo().getOrgUnitID();
		//插入用户与子账号映射关系
		SystemParamsServiceFactory.getServiceInstance4Alibaba()
			.saveUser2SubAccountInfo(enterpriseId, companyId, user.getId(), destId);
		
		return true;
	}

}
