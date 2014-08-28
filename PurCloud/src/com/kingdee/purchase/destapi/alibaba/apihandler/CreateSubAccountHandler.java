package com.kingdee.purchase.destapi.alibaba.apihandler;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.openapi.client.ErrorHandler;
import com.kingdee.purchase.destapi.alibaba.exceptionhandler.CreateSubAccountExceptionHandler;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.UserInfo;
import com.kingdee.purchase.platform.info.UserInfo.Role;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 创建1688子账号API处理器
 * @author RD_sky_lv
 *
 */

public class CreateSubAccountHandler extends AbstractDestApiHandler {
	
	public static final String PARAM_EMPLOYEEID = "employeeId";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_MOBILENO = "mobileNo";
	public static final String PARAM_EMAIL = "email";
	public static final String PARAM_SEX = "sex";
	public static final String PARAM_DEPARTMENT = "department";
	public static final String PARAM_ROLE = "role";
	public static final String RESULT_KEY = "subUserId";
	
	/**
	 * 获取api名称
	 * @return
	 */
	@Override
	protected String getApiName() {
		return "caigou.api.account.createSubAccount";
	}
	
	/**
	 * 获取api版本
	 * @return
	 */
	@Override
	protected int getApiVersion() {
		return 1;
	}
	
	/**
	 * 获取api异常处理器
	 * @return
	 */
	@Override
	protected ErrorHandler getApiErrorHandler() {
		return new CreateSubAccountExceptionHandler();
	}
	
	/**
	 * 参数准备
	 * @param srcParam
	 * @return
	 */
	@Override
	protected Map<String, Object> getApiParams() throws BaseException {
		UserInfo user = ctx.getSysParamInfo().getUserInfo();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_EMPLOYEEID, user.getId());
		params.put(PARAM_NAME, user.getName());
		if (StringUtils.isNotEmpty(user.getMobileNO())) {
			params.put(PARAM_MOBILENO, user.getMobileNO());
		}
		if (StringUtils.isNotEmpty(user.getEmail())) {
			params.put(PARAM_EMAIL, user.getEmail());
		}
		params.put(PARAM_SEX, String.valueOf(user.getSex().ordinal() + 1));
		if (StringUtils.isNotEmpty(user.getDepartment())) {
			params.put(PARAM_DEPARTMENT, user.getDepartment());
		}
		params.put(PARAM_ROLE, Role.getAlias(user.getRole()));
		
		return params;
	}

}
