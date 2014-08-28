package com.kingdee.purchase.platform.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.AccountInfo;
import com.kingdee.purchase.platform.info.ERPType;
import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.service.IAccountService;
import com.kingdee.purchase.platform.util.ParamParseUtil;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

public class AccountServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3747956822885894771L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request, response);		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String type = ParamParseUtil.getString(request, "type","create");
		if("create".equalsIgnoreCase(type)){
			String repassword = ParamParseUtil.getString(request, "repassword");
			String password = ParamParseUtil.getString(request, "password");
			
			//企业号注册
			EnterpriseInfo enterPriseInfo = new EnterpriseInfo();
			enterPriseInfo.setName(ParamParseUtil.getString(request, "enterpriseName"));
			enterPriseInfo.setErpType(ERPType.getErpType(ParamParseUtil.getInt(request, "erpType")));
			enterPriseInfo.setServiceUrl("http://www.kingdee.com");
			
			//账号注册
			AccountInfo info = new AccountInfo();
			info.setDisplayname(ParamParseUtil.getString(request, "displayname"));
			info.setEmail(ParamParseUtil.getString(request, "email"));
			info.setId(ParamParseUtil.getLong(request, "id"));
			info.setMobile(ParamParseUtil.getString(request, "mobile"));
			info.setPassword(password);
			info.setUsername(ParamParseUtil.getString(request, "username"));
			
			boolean error = false;
			if(StringUtils.isEmpty(password)){
				error = true;
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, "两次输入的密码不一致");
			}else if(!password.equals(repassword)){
				error = true;
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, "两次输入的密码不一致");
			}
			if(error){
				request.setAttribute("enterpriseName", enterPriseInfo.getName());
				request.setAttribute("erpType", enterPriseInfo.getErpType()==null?1:enterPriseInfo.getErpType().getValue());
				request.setAttribute(WebConstant.PAGE_KEY_INFO, info);
				request.getRequestDispatcher("signup.jsp").forward(request, response);
				return ;
			}
			
			try {
				SpringContextUtils.getBean(IAccountService.class).createAccountInfo(info,enterPriseInfo);
				request.setAttribute(WebConstant.PAGE_KEY_SUCCESS, "注册成功");
			} catch (BaseException e) {
				request.setAttribute("enterpriseName", enterPriseInfo.getName());
				request.setAttribute("erpType", enterPriseInfo.getErpType()==null?1:enterPriseInfo.getErpType().getValue());
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
				request.setAttribute(WebConstant.PAGE_KEY_INFO, info);
				request.getRequestDispatcher("signup.jsp").forward(request, response);
				return ;
			}

			request.getSession(true).setAttribute(WebConstant.SESSION_KEY_USER, info);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return ;
		}else if("edit".equalsIgnoreCase(type)){
			//修改
		}
	}
}