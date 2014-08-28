package com.kingdee.purchase.platform.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;
import com.kingdee.purchase.platform.service.alibaba.ICompany2AccountServcie;
import com.kingdee.purchase.platform.util.ParamParseUtil;
import com.kingdee.purchase.platform.util.SpringContextUtils;

public class CompanyServlet extends HttpServlet {
	
	private final static Logger logger = LogManager.getLogger(CompanyServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7999713263949114050L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = ParamParseUtil.getString(request, "type");
		if("save".equals(type)){
			saveCompany2AccountInfo(request,response);
		}else {
			listCompany2AccountInfo(request,response);
		}
	}

	/**
	 * 保存公司到b2b主账号的映射
	 * @param request
	 * @param response
	 * @throws PurBizException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void saveCompany2AccountInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String[] accountid = request.getParameterValues("accountid");
		String[] token = request.getParameterValues("token");
		String[] ids = request.getParameterValues("id");
		long enterpriseid = ParamParseUtil.getLong(request, "enterpriseid");

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
		
		listCompany2AccountInfo(request, response);
	}

	/***
	 * 列出财务组织到主账号的映射
	 * @param request
	 * @param response
	 * @param enterpriseId
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void listCompany2AccountInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		long enterpriseId = ParamParseUtil.getLong(request, "enterpriseId");
		if(enterpriseId<=0){
			enterpriseId = ParamParseUtil.getLong(request, "enterpriseid");
		}
		
		List<Company2AccountInfo> result;
		if(enterpriseId<=0){
			result = new ArrayList<Company2AccountInfo>();
		}else{
			ICompany2AccountServcie service = SpringContextUtils.getBean(ICompany2AccountServcie.class);
			try {
				result = service.getCompanyList(enterpriseId);
				request.setAttribute(WebConstant.PAGE_KEY_LIST, result);
			} catch (BaseException e) {
				logger.error(e.getMessage(), e);
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
			}
		}
		
		request.setAttribute("enterpriseId", enterpriseId);
		request.getRequestDispatcher("/view/mapping/company/mapping.jsp").forward(request, response);
	}
}