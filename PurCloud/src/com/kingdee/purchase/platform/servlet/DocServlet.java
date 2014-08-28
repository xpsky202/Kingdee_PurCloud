package com.kingdee.purchase.platform.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.api.ApiBaseInfo;
import com.kingdee.purchase.platform.service.IDocService;
import com.kingdee.purchase.platform.util.SpringContextUtils;

public class DocServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1484492513694383901L;
	
	private final static Logger logger = LogManager.getLogger(DocServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		responseApiList(request,response);
	}

	/***
	 * 返回API列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void responseApiList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		IDocService docService = SpringContextUtils.getBean(IDocService.class);
		try {
			List<ApiBaseInfo> apiList = docService.getSortedAPIList();
			request.setAttribute(WebConstant.PAGE_KEY_LIST, apiList);
		}catch (BaseException e) {
			logger.error(e.getMessage(),e);
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		
		request.getRequestDispatcher("/doc/index.jsp").forward(request, response);
	}
}