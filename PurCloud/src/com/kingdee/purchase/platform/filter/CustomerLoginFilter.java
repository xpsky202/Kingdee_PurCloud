package com.kingdee.purchase.platform.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingdee.purchase.platform.info.AccountInfo;
import com.kingdee.purchase.platform.servlet.WebConstant;

/***
 * 客户（企业）登录校验
 * @author RD_jiangkun_zhu
 *
 */
public class CustomerLoginFilter implements javax.servlet.Filter{

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		AccountInfo accountInfo = (AccountInfo) request.getSession(true).getAttribute(WebConstant.SESSION_KEY_USER);
		if(null==accountInfo){
			//重新登录到主页
			String urlString = request.getRequestURI();
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			basePath += "login.jsp?url="+urlString;
			((HttpServletResponse)resp).sendRedirect(basePath);
		}else{
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
}