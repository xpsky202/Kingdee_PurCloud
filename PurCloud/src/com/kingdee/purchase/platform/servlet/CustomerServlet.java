package com.kingdee.purchase.platform.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kingdee.purchase.destapi.alibaba.AlibabaConfiguration;
import com.kingdee.purchase.destapi.alibaba.util.CommonUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.AccountInfo;
import com.kingdee.purchase.platform.info.ERPType;
import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;
import com.kingdee.purchase.platform.service.IAccountService;
import com.kingdee.purchase.platform.service.IEnterpriseService;
import com.kingdee.purchase.platform.service.ILoginService;
import com.kingdee.purchase.platform.service.alibaba.ICompany2AccountServcie;
import com.kingdee.purchase.platform.util.ContextUtils;
import com.kingdee.purchase.platform.util.ParamParseUtil;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

public class CustomerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7633183351421867644L;
	
	private final static Logger logger = LogManager.getLogger(CustomerServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		if(url.endsWith("/customer/enterprise/edit")){
			editEnterpriseInfo(request,response);
		}else if(url.endsWith("/customer/enterprise/save")){
			saveEnterpriseInfo(request,response);
		}else if(url.endsWith("/customer/account/edit")){
			editAccountInfo(request,response);
		}else if(url.indexOf("/customer/account/save")!=-1){
			saveAccountInfo(request,response);
		}else if(url.endsWith("/customer/account/chgpwd")){
			changePassword(request,response);
		}else if(url.endsWith("/customer/company/edit")){
			editCompanyInfo(request,response);
		}else if(url.endsWith("/customer/company/save")){
			saveCompanyInfo(request,response);
		}else if (url.endsWith("/customer/company/delete")) {
			deleteCompanyInfo(request, response);
		}else{
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}		
	}

	/***
	 * 修改密码
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void changePassword(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String oldPassword = ParamParseUtil.getString(request, "oldPassword");
		String password = ParamParseUtil.getString(request, "password");
		String rePassword = ParamParseUtil.getString(request, "rePassword");
		
		AccountInfo info = ContextUtils.getCurrentAccount(request);
		ILoginService service = SpringContextUtils.getBean(ILoginService.class);
		
		try {
			boolean result = service.chgPassword(info.getId(), oldPassword,password,rePassword);
			if(result){
				request.setAttribute(WebConstant.PAGE_KEY_SUCCESS, "修改密码成功");	
			}else{
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, "修改密码失败");	
			}
		} catch (PurBizException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());		
		}
		
		request.getRequestDispatcher("/view/admin/account/password.jsp").forward(request, response);
		return ;
	}

	/***
	 * 保存公司和1688的账号映射信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void saveCompanyInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		AccountInfo accountInfo = (AccountInfo) request.getSession(true).getAttribute(WebConstant.SESSION_KEY_USER);
		EnterpriseInfo enterpriseInfo=null;
		try {
			enterpriseInfo = SpringContextUtils.getBean(IEnterpriseService.class).getByAccountId(accountInfo.getId());
		} catch (BaseException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		
		if(enterpriseInfo==null){
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, "请先维护企业信息");
			request.getRequestDispatcher("/view/admin/enterprise/edit.jsp").forward(request, response);
		}else{
			String[] accountid = request.getParameterValues("accountid");
			String[] token = request.getParameterValues("token");
			String[] ids = request.getParameterValues("id");
			long enterpriseid = enterpriseInfo.getId();
	
			List<Company2AccountInfo> companyList = new ArrayList<Company2AccountInfo>();
			Company2AccountInfo info;
			for(int i=0,len=ids.length; i<len; i++){
				info = new Company2AccountInfo();
				info.setAccountId(accountid[i]);
				info.setId(Long.parseLong(ids[i]));
				info.setToken(token[i]);
				companyList.add(info);
			}
			
			try {
				SpringContextUtils.getBean(ICompany2AccountServcie.class).updateCompany2AccountMapping(enterpriseid,companyList);
				request.setAttribute(WebConstant.PAGE_KEY_SUCCESS, "数据保存成功");
			} catch (BaseException e) {
				logger.error(e.getMessage(), e);
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
			}
			
			editCompanyInfo(request, response);
		}
	}
	
	/**
	 * 删除组织
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteCompanyInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountInfo accountInfo = (AccountInfo) request.getSession(true).getAttribute(WebConstant.SESSION_KEY_USER);		
		EnterpriseInfo enterpriseInfo=null;
		try {
			enterpriseInfo = SpringContextUtils.getBean(IEnterpriseService.class).getByAccountId(accountInfo.getId());
		} catch (BaseException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		
		if(enterpriseInfo==null){
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, "请先维护企业信息");
			request.getRequestDispatcher("/view/admin/enterprise/edit.jsp").forward(request, response);
		}else{
			long enterpriseId = enterpriseInfo.getEnterpriseid();
			String paramStr = request.getQueryString();
			Map<String, Object> params = ParamParseUtil.getRequestParams(paramStr);
			String companyId = params.get("companyId").toString();
			try {
				SpringContextUtils.getBean(ICompany2AccountServcie.class).deleteCompanyInfo(enterpriseId, companyId);
			} catch (BaseException e) {
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
			}
			request.setAttribute(WebConstant.PAGE_KEY_SUCCESS, true);
		}
	}

	/***
	 * 账号信息编辑
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void editCompanyInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		AccountInfo accountInfo = (AccountInfo) request.getSession(true).getAttribute(WebConstant.SESSION_KEY_USER);		
		EnterpriseInfo enterpriseInfo=null;
		try {
			enterpriseInfo = SpringContextUtils.getBean(IEnterpriseService.class).getByAccountId(accountInfo.getId());
		} catch (BaseException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		
		if(enterpriseInfo==null){
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, "请先维护企业信息");
			request.getRequestDispatcher("/view/admin/enterprise/edit.jsp").forward(request, response);
		}else{
			long enterpriseId = enterpriseInfo.getEnterpriseid();
			try {
				List<Company2AccountInfo> list = SpringContextUtils.getBean(ICompany2AccountServcie.class).getCompanyList(enterpriseId);
				generateSignatureUrl(list);		//生成签名url
				request.setAttribute(WebConstant.PAGE_KEY_LIST, list);
			} catch (BaseException e) {
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
			}
			request.getRequestDispatcher("/view/admin/company/edit.jsp").forward(request, response);
		}
	}
	
	/***
	 * 产生认证的url
	 * @param list
	 */
	private void generateSignatureUrl(List<Company2AccountInfo> list){
		if(null==list || list.size()==0){
			return ;
		}
		
		String url = AlibabaConfiguration.URL_AUTHORIZE
							+ "?client_id="+AlibabaConfiguration.getAppKey()
							+ "&site="+AlibabaConfiguration.SITE
							+ "&redirect_uri="+AlibabaConfiguration.getRedirectUriCode();
		String state;
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("client_id", AlibabaConfiguration.getAppKey());
		paramsMap.put("site", AlibabaConfiguration.SITE);
		paramsMap.put("redirect_uri", AlibabaConfiguration.getRedirectUriCode());
		for(Company2AccountInfo info:list){
			state=info.getEnterpriseId()+"_"+info.getCompanyId();
			paramsMap.put("state", state);
			String sign = CommonUtil.signatureWithParamsOnly(paramsMap, AlibabaConfiguration.getAppSecret());
			String tempUrl = url+"&state="+state+"&_aop_signature="+sign;
			info.setSignUrl(tempUrl);
		}
	}

	/***
	 * 个人账号信息保存
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void saveAccountInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		AccountInfo accountInfo = (AccountInfo) request.getSession(true).getAttribute(WebConstant.SESSION_KEY_USER);
		
		//注册
		AccountInfo info = new AccountInfo();
		info.setDisplayname(ParamParseUtil.getString(request, "displayname"));
		info.setEmail(ParamParseUtil.getString(request, "email"));
		info.setId(ParamParseUtil.getLong(request, "id"));
		info.setMobile(ParamParseUtil.getString(request, "mobile"));
		info.setUsername(ParamParseUtil.getString(request, "username"));
		if(accountInfo.getId()!=info.getId()){
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, "不能编辑其他人的资料");
		}else{
			try {
				SpringContextUtils.getBean(IAccountService.class).updateAccountInfo(info);
				request.setAttribute(WebConstant.PAGE_KEY_SUCCESS, "保存成功");
			} catch (BaseException e) {
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
			}
		}

		request.setAttribute(WebConstant.PAGE_KEY_INFO, info);
		request.getRequestDispatcher("/view/admin/account/edit.jsp").forward(request, response);
	}

	/***
	 * 个人账号信息编辑
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void editAccountInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		AccountInfo accountInfo = (AccountInfo) request.getSession(true).getAttribute(WebConstant.SESSION_KEY_USER);
		try {
			AccountInfo info = SpringContextUtils.getBean(IAccountService.class).getAccountInfo(accountInfo.getId());
			request.setAttribute(WebConstant.PAGE_KEY_INFO, info);
		} catch (BaseException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		request.getRequestDispatcher("/view/admin/account/edit.jsp").forward(request, response);
	}

	/***
	 * 保存企业信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void saveEnterpriseInfo(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		StringBuilder errorStr = new StringBuilder();
		EnterpriseInfo info = new EnterpriseInfo();
		info.setId(ParamParseUtil.getLong(request, "id"));		
//		String contact = ParamParseUtil.getString(request, "contact");		
//		if(StringUtils.isEmpty(contact)){
//			errorStr.append("联系人不能为空").append("<br/>");
//		}else{
//			info.setContact(contact);
//		}
		
		String name = ParamParseUtil.getString(request, "name");		
		if(StringUtils.isEmpty(name)){
			errorStr.append("企业名称不能为空").append("<br/>");
		}else{
			info.setName(name);
		}
		
		long enterpriseid = ParamParseUtil.getLong(request, "enterpriseid");
		info.setEnterpriseid(enterpriseid);
		
//		String email = ParamParseUtil.getString(request, "email");		
//		if(StringUtils.isEmpty(email)){
//			errorStr.append("企业名称不能为空").append("<br/>");
//		}else{
//			info.setEmail(email);
//		}
//		
//		String mobile = ParamParseUtil.getString(request, "mobile");		
//		if(StringUtils.isEmpty(mobile)){
//			errorStr.append("企业名称不能为空").append("<br/>");
//		}else{
//			info.setMobile(mobile);
//		}
		
		String serviceUrl = ParamParseUtil.getString(request, "serviceUrl","http://www.kingdee.com");		
		if(StringUtils.isEmpty(serviceUrl)){
			errorStr.append("服务网址不能为空").append("<br/>");
		}else{
			info.setServiceUrl(serviceUrl);
		}
		
		int erpType = ParamParseUtil.getInt(request, "erpType");
		info.setErpType(ERPType.getErpType(erpType));

		AccountInfo accountInfo = (AccountInfo) request.getSession(true).getAttribute(WebConstant.SESSION_KEY_USER); 
		info.setAccountId(accountInfo.getId());
		
		if(errorStr.length()>0){
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, errorStr);
		}else{
			try {
				SpringContextUtils.getBean(IEnterpriseService.class).save(info);
				request.setAttribute(WebConstant.PAGE_KEY_SUCCESS, "企业资料保存成功");
			} catch (BaseException e) {
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
			}
		}
		
		request.setAttribute(WebConstant.PAGE_KEY_INFO, info);
		request.getRequestDispatcher("/view/admin/enterprise/edit.jsp").forward(request, response);
	}

	/***
	 * 编辑企业信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void editEnterpriseInfo(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		AccountInfo accountInfo = (AccountInfo) request.getSession(true).getAttribute(WebConstant.SESSION_KEY_USER);
		try {
			EnterpriseInfo info = SpringContextUtils.getBean(IEnterpriseService.class).getByAccountId(accountInfo.getId());
			request.setAttribute(WebConstant.PAGE_KEY_INFO, info);
		} catch (BaseException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		request.getRequestDispatcher("/view/admin/enterprise/edit.jsp").forward(request, response);
	}
}