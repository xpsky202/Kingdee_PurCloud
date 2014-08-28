package com.kingdee.purchase.platform.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.AccountInfo;
import com.kingdee.purchase.platform.service.ILoginService;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7599974081474122439L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if("logout".equals(type)){
			doPost(request, response);
		}else{
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		
		if("logout".equalsIgnoreCase(type)){
			request.getSession(true).removeAttribute(WebConstant.SESSION_KEY_USER);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return ;
		}else if("userlogin".equalsIgnoreCase(type)){
			String username = request.getParameter("username");
			ILoginService authService = SpringContextUtils.getBean(ILoginService.class);
			try {
				AccountInfo info = authService.loginByUsername(username, password);
				request.getSession(true).setAttribute(WebConstant.SESSION_KEY_USER, info);
				String url=request.getParameter("url");
				if(StringUtils.isEmpty(url)){
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}else{
					response.sendRedirect(url);
				}
			} catch (BaseException e) {
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
				request.setAttribute("username", username);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}else if("emaillogin".equalsIgnoreCase(type)){
			String email = request.getParameter("email");
			ILoginService authService = SpringContextUtils.getBean(ILoginService.class);
			try {
				AccountInfo info = authService.loginByEmail(email, password);
				request.getSession(true).setAttribute(WebConstant.SESSION_KEY_USER, info);
				String url=request.getParameter("url");
				if(StringUtils.isEmpty(url)){
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}else{
					request.getRequestDispatcher(url).forward(request, response);
				}
			} catch (PurBizException e) {
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
				request.setAttribute("email", email);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}else{
			//默认跳转到首页
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return ;			
		}	
	}
}