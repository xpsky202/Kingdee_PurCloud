package com.kingdee.purchase.destapi.alibaba.apihandler;

import java.util.Calendar;
import java.util.Map;

import net.sf.json.JSONObject;

import com.alibaba.openapi.client.ErrorHandler;
import com.kingdee.purchase.destapi.IDestApiHandler;
import com.kingdee.purchase.destapi.alibaba.AlibabaApiCallService;
import com.kingdee.purchase.destapi.alibaba.CreateSubAccountService;
import com.kingdee.purchase.destapi.alibaba.InvokeContext;
import com.kingdee.purchase.destapi.alibaba.exceptionhandler.BaseExceptionHandler;
import com.kingdee.purchase.destapi.alibaba.exceptionhandler.IAliExceptionHandler;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.exception.PurSysException;
import com.kingdee.purchase.platform.info.UserInfo;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;
import com.kingdee.purchase.platform.service.alibaba.ICompany2AccountServcie;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

public abstract class AbstractDestApiHandler implements IDestApiHandler {
	
	protected RequestContext ctx;
	
	public JSONObject invokeHandle(RequestContext ctx) throws BaseException {
		this.ctx = ctx;
		UserInfo user = ctx.getSysParamInfo().getUserInfo();
		//如果用户没有子账号，而调用API又需要时，创建子账号
		if (isNeedSubAccount() && StringUtils.isEmpty(user.getDestId())) {
			CreateSubAccountService.createSubAccount(ctx);
		}
		//获取调用上下文
		InvokeContext invokeCtx = getInvokeContext();
		//调用阿里API
		JSONObject value = invokeApi(invokeCtx);
		//处理异常
		IAliExceptionHandler exHandler = (IAliExceptionHandler)invokeCtx.getHandler();
		if (exHandler != null && exHandler.isHasException()) {
			throw exHandler.buildException(value);
		}
		//返回处理结果
		return handleResult(value);
	}
	
	/**
	 * 获取访问令牌
	 * @param ctx
	 * @return
	 * @throws BaseException 
	 */
	protected String getRefreshToken() throws BaseException {
		Long enterpriseId = ctx.getSysParamInfo().getEnterPriseID();
		String companyId = ctx.getSysParamInfo().getOrgUnitID();
		
		ICompany2AccountServcie service = SpringContextUtils.getBean(ICompany2AccountServcie.class);
		Company2AccountInfo companyInfo = service.getCompany2AccountInfo(enterpriseId, companyId);
		if (companyInfo == null) {
			throw new PurBizException(PurExceptionDefine.COMPANY_NOT_EXIST, new String [] {companyId});
		}
		
		String refreshToken = companyInfo.getToken();
		if (refreshToken == null || refreshToken.length() == 0) {
			throw new PurBizException(PurExceptionDefine.ALI_NO_TOKEN);
		}
		
		//如果refreshToken已过期，则要求重新授权
		if (companyInfo.getExpiredDate() == null
				|| companyInfo.getExpiredDate().before(Calendar.getInstance().getTime())) {
			throw new PurBizException(PurExceptionDefine.ALI_TOKEN_OUTOFDATE);
		}
		
		return refreshToken;
	}
	
	/**
	 * 获取api名称，根据具体API要求重载
	 * @return
	 */
	protected abstract String getApiName();
	
	/**
	 * 获取api版本，根据具体API要求重载
	 * @return
	 */
	protected int getApiVersion() {
		return 1;
	}
	
	/**
	 * 获取api异常处理器，根据具体API要求重载
	 * @return
	 */
	protected ErrorHandler getApiErrorHandler() {
		return new BaseExceptionHandler();
	}
	
	/**
	 * 参数准备，根据具体API要求重载
	 * @param srcParam
	 * @return
	 */
	protected Map<String, Object> getApiParams() throws BaseException {
		return ctx.getBussinessParams();
	}
	
	/**
	 * 是否需要认证，根据具体API要求重载
	 * @return
	 */
	protected boolean isNeedAuthorization() {
		return true;
	}
	
	/**
	 * 是否需要签名，根据具体API要求重载
	 * @return
	 */
	protected boolean isNeedUseSignture() {
		return true;
	}
	
	/**
	 * 是否需要发送时间戳，根据具体API要求重载
	 * @return
	 */
	protected boolean isNeedSendTimestamp() {
		return false;
	}
	
	/**
	 * 是否需要子账号，根据具体API要求重载
	 * @return
	 */
	protected boolean isNeedSubAccount() {
		return false;
	}
	
	/**
	 * 获取调用目标API上下文
	 * @return
	 * @throws BaseException 
	 */
	protected InvokeContext getInvokeContext() throws BaseException {
		InvokeContext invokeCtx = new InvokeContext();
		invokeCtx.setApiName(getApiName());
		invokeCtx.setApiVersion(getApiVersion());
		invokeCtx.setParams(getApiParams());
		invokeCtx.setRefreshToken(getRefreshToken());
		invokeCtx.setHandler(getApiErrorHandler());
		
		invokeCtx.setNeedAuth(isNeedAuthorization());
		invokeCtx.setNeedSign(isNeedUseSignture());
		invokeCtx.setNeedTimestamp(isNeedSendTimestamp());
		
		return invokeCtx;
	}
	
	/**
	 * 目标API调用
	 * @param refreshToken
	 * @param param
	 * @return
	 * @throws PurSysException
	 */
	protected JSONObject invokeApi(InvokeContext ctx) throws BaseException {
		return AlibabaApiCallService.callApi(ctx);
	}
	
	/**
	 * 返回结果处理
	 * @param value
	 * @return
	 */
	protected JSONObject handleResult(JSONObject value) throws BaseException {
		return value;
	}

}
