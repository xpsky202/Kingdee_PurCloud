package com.kingdee.purchase.platform.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.kingdee.purchase.config.ConfigConstant;
import com.kingdee.purchase.destapi.alibaba.AlibabaApiCallService;
import com.kingdee.purchase.destapi.alibaba.AlibabaConfiguration;
import com.kingdee.purchase.destapi.alibaba.CreateSubAccountService;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.info.UrlRequestInfo;
import com.kingdee.purchase.platform.info.UserInfo;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;
import com.kingdee.purchase.platform.processor.RequestParamsConversionProcessor;
import com.kingdee.purchase.platform.processor.RequestParserProcessor;
import com.kingdee.purchase.platform.processor.ValidateEnterPriseProcessor;
import com.kingdee.purchase.platform.service.alibaba.ICompany2AccountServcie;
import com.kingdee.purchase.platform.util.ParamParseUtil;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

public class AutoLoginServlet extends HttpServlet {

	private static final long serialVersionUID = -4881707962572973213L;

	private static final String ACCESS_TOKEN = "access_token";
	private static final String SUBUSERID = "subUserId";
	private static final String TARGETURLENUM = "targetUrlEnum";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		//unsupported
		return ;
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		String urlPath = "";
		int index = url.indexOf("?");
		if(index>0){
			urlPath = url.substring(0,index-1);
		}else{
			urlPath = url;
		}
		
		//解析getParameterMap
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//parameterMap
		Map<String, Object> reqMap = ParamParseUtil.getParameters(request);
		for(Map.Entry<String, Object> entry: reqMap.entrySet()){
			if (!paramMap.containsKey(entry.getKey())){
				paramMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		response.setContentType(ConfigConstant.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstant.ENCODE);
		try {
			RequestContext context = getRequestContext(urlPath, paramMap);
			
			HttpClient httpClient = new HttpClient();
			PostMethod method = getMethod(context);
			setParameter(context, method);
			int status = httpClient.executeMethod(method);
			if (status != 302) {
				String message = "Alibaba Auto Login failed, Status: " + status + " response:" + method.getResponseBodyAsString();
				response.getOutputStream().write(message.getBytes(ConfigConstant.ENCODE));
			} else {
				Header[] headerArray = method.getResponseHeaders();
				for (Header header : headerArray) {
					response.setHeader(header.getName(), header.getValue());
				}
				response.setStatus(method.getStatusCode());
			}
		} catch (BaseException e) {
			response.getOutputStream().write(e.getMessage().getBytes(ConfigConstant.ENCODE));
		}
	}
	
	/**
	 * 创建post方法
	 * @param ctx
	 * @return
	 */
	private PostMethod getMethod(RequestContext ctx) {
		String autoLoginUrl = AlibabaConfiguration.getAutoLoginUrl() + AlibabaConfiguration.getAppKey();
		PostMethod method = new PostMethod(autoLoginUrl);
		method.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		
		return method;
	}
	
	/**
	 * 设置post参数
	 * @param ctx
	 * @param method
	 * @throws BaseException
	 */
	private void setParameter(RequestContext ctx, PostMethod method) throws BaseException {
		method.setParameter(ACCESS_TOKEN, getAccessToken(ctx));
		UserInfo user = ctx.getSysParamInfo().getUserInfo();
		if (StringUtils.isEmpty(user.getDestId())) {
			CreateSubAccountService.createSubAccount(ctx);
		}
		method.setParameter(SUBUSERID, user.getDestId());
		Map<String, Object> paramMap = ctx.getBussinessParams();
		if (paramMap == null || !paramMap.containsKey(TARGETURLENUM)) {
			throw new PurBizException(PurExceptionDefine.REQUIRE_TARGETURL);
		}
		
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			method.setParameter(entry.getKey(), entry.getValue().toString());
		}
	}
	
	/**
	 * 获取访问令牌
	 * @param ctx
	 * @return
	 * @throws BaseException 
	 */
	private String getAccessToken(RequestContext ctx) throws BaseException {
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
		AuthorizationToken token = AlibabaApiCallService.getAuthorizationToken(refreshToken);
		
		return token.getAccess_token();
	}
	
	/**
	 * 解析url和参数，构造请求上下文
	 * @param urlPath
	 * @param paramMap
	 * @return
	 * @throws BaseException
	 */
	private RequestContext getRequestContext(String urlPath, Map<String, Object> paramMap) throws BaseException {
		//解析URL处理器
		RequestParserProcessor parserProcessor = new RequestParserProcessor(urlPath,  paramMap);
		UrlRequestInfo requestInfo = parserProcessor.process();
		
		//URL对象转换处理器
		RequestParamsConversionProcessor conversionProcessor = new RequestParamsConversionProcessor(requestInfo);
		RequestContext context = conversionProcessor.process();
		
		//企业注册校验处理器
		ValidateEnterPriseProcessor validateProcessor = new ValidateEnterPriseProcessor(context);
		validateProcessor.process();
		
		return context;
	}
}
