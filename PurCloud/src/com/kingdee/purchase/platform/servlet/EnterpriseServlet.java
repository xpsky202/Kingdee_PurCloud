package com.kingdee.purchase.platform.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.ERPType;
import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.service.IEnterpriseService;
import com.kingdee.purchase.platform.util.ParamParseUtil;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

public class EnterpriseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6213953060405376921L;

	private final static String EIDTUI = "/view/admin/enterprise/edit.jsp";
	private final static String LISTUI = "/view/admin/enterprise/list.jsp";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		if(url.endsWith("/admin/enterprise/save")){
			saveEnterpriseInfo(request,response);
		}else if(url.endsWith("/admin/enterprise/list")){
			listEnterpriseInfo(request,response);
		}else if(url.indexOf("/admin/enterprise/view/")!=-1){
			try{
				String idStr = url.substring(url.lastIndexOf("/")+1);
				int id = Integer.parseInt(idStr);
				viewEnterpriseInfo(request,response,id);
			}catch(Exception e){
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
				listEnterpriseInfo(request, response);
			}
		}
		
		return ;
	}

	private void viewEnterpriseInfo(HttpServletRequest request,	HttpServletResponse response,int id) throws ServletException, IOException {
		EnterpriseInfo info;
		try {
			info = SpringContextUtils.getBean(IEnterpriseService.class).get(id);
			request.setAttribute(WebConstant.PAGE_KEY_INFO, info);
		} catch (BaseException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		request.getRequestDispatcher(EIDTUI).forward(request, response);
		return ;		
	}

	private void listEnterpriseInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String condition = ParamParseUtil.getString(request, "condition");
		List<EnterpriseInfo> list;
		try {
			list = SpringContextUtils.getBean(IEnterpriseService.class).query(condition);
			request.setAttribute(WebConstant.PAGE_KEY_LIST, list);
		} catch (BaseException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		request.setAttribute("condition", condition);
		request.getRequestDispatcher(LISTUI).forward(request, response);
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
		
		int erpType = ParamParseUtil.getInt(request, "erptype");
		info.setErpType(ERPType.getErpType(erpType));

		long accountId = ParamParseUtil.getLong(request, "accountId");
		info.setAccountId(accountId);
		
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
		request.getRequestDispatcher(EIDTUI).forward(request, response);
	}
}