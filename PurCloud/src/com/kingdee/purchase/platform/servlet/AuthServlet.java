package com.kingdee.purchase.platform.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.openapi.client.AlibabaClient;
import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.alibaba.openapi.client.policy.ClientPolicy;
import com.kingdee.purchase.destapi.alibaba.AlibabaConfiguration;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.service.alibaba.ICompany2AccountServcie;
import com.kingdee.purchase.platform.util.ParamParseUtil;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

/***
 * 权限认证
 * @author RD_jiangkun_zhu
 *
 */
public class AuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5492008470661059333L;
	private final static Logger logger = LogManager.getLogger(AuthServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		if(url.endsWith("/auth/code")){
			codeCallback(request, response);
		}else if(url.endsWith("/auth/token")){
			tokenCallback(request, response);
		}
	}
	
	/***
	 * 根据code，获取refresh token；
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void codeCallback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = ParamParseUtil.getString(request, "code");
		String state = ParamParseUtil.getString(request, "state");
		if(StringUtils.isEmpty(code) || StringUtils.isEmpty(state)){
			logger.error("获取到的code或者state参数为空。","code:"+code + ";state"+state);
			request.setAttribute(WebConstant.PAGE_KEY_ERROR,"获取签名授权失败，请重试。如果再出现此问题，请联系管理员。");
			request.getRequestDispatcher("/authResult.jsp").forward(request, response);
			return ;
		}
		
		long enterpriseId = 0;
		String companyId = null;
		try {
			enterpriseId = Long.parseLong(state.substring(0, state.indexOf("_")));
			companyId = state.substring(state.indexOf("_")+1);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		if(StringUtils.isEmpty(companyId) || enterpriseId<=0){
			logger.error("获取到的companyId或者enterpriseId参数无效。","companyId:"+companyId + ";enterpriseId"+enterpriseId);
			request.setAttribute(WebConstant.PAGE_KEY_ERROR,"获取签名授权失败，请重试。如果再出现此问题，请联系管理员。");
			request.getRequestDispatcher("/authResult.jsp").forward(request, response);
			return ;
		}
		
//		https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/YOUR_APPKEY?
//		grant_type=authorization_code
//		&need_refresh_token=true
//		&client_id= YOUR_APPKEY
//		&client_secret= YOUR_APPSECRET
//		&redirect_uri=YOUR_REDIRECT_URI
//		&code=CODE
		
/*		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(AppCfgInfo.URL_GET_TOKEN);
		post.addParameter("grant_type", "authorization_code");
		post.addParameter("need_refresh_token", "true");
		post.addParameter("client_id", AppCfgInfo.APP_KEY);
		post.addParameter("client_secret", AppCfgInfo.APP_SECRET);
		post.addParameter("redirect_uri", AppCfgInfo.REDIRECT_URI_TOKEN);
		post.addParameter("code", code);
		
		String result = null;
		try{
			if(client.executeMethod(post)==200){
				result = post.getResponseBodyAsString();				
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}*/
		
		AuthorizationToken token = null;
		try{
			token = client.getToken(code,AlibabaConfiguration.TIMEOUT);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		if(token!=null){		
//			{
//			 "uid":"xx",
//			 "aliId":"8888888888",
//			 "resource_owner":"xxx",										//resource_owner为登录id
//			 "memberId":"xxxxxxx",											//memberId为会员接口id
//			 "expires_in":"36000",											//aliId为阿里巴巴集团统一的id
//			 "refresh_token":"479f9564-1049-456e-ab62-29d3e82277d9",
//			 "refresh_token_timeout":"20121222222222+0800",
//			 "access_token":"f14da3b8-b0b1-4f73-a5de-9bed637e0188"
//			}
			Date date = token.getRefresh_token_timeout();
			String memberId = token.getMemberId();
			String refresh_token = token.getRefresh_token();
			if(StringUtils.isNotEmpty(memberId) && StringUtils.isNotEmpty(refresh_token)){
				//持久化到数据库
				ICompany2AccountServcie service = SpringContextUtils.getBean(ICompany2AccountServcie.class);
				try {
					int result = 0;
					if(!service.hasAuthorized(companyId, memberId)){
						result = service.updateCompany2AccountMapping(enterpriseId, companyId,memberId,refresh_token,date);
					}
					
					if(result==1){
						request.setAttribute(WebConstant.PAGE_KEY_SUCCESS,"授权成功");
					}else{
						request.setAttribute(WebConstant.PAGE_KEY_ERROR, "授权失败：该账号已和其他公司关联。");
					}
					
					request.getRequestDispatcher("/authResult.jsp").forward(request, response);
					return ;
				} catch (BaseException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		
		request.setAttribute(WebConstant.PAGE_KEY_ERROR,"获取签名授权失败，请重试。如果再出现此问题，请联系管理员。");
		request.getRequestDispatcher("/authResult.jsp").forward(request, response);
		return ;
	}
	

	private static AlibabaClient client = null;
	static{
		//使用默认的client策略，包括使用域名gw.open.1688.com,端口http 80，https443等
		ClientPolicy policy = new ClientPolicy(AlibabaConfiguration.HOST);
		//设置app的appKey以及对应的密钥，信息由注册app时生成
		policy = policy.setAppKey(AlibabaConfiguration.getAppKey()).setSigningKey(AlibabaConfiguration.getAppSecret());
		//使用client策略来初始化AlibabaClient,建议保持单例
		client = new AlibabaClient(policy);
		//启动AlibabaClient实例
		client.start();
	}
	
	/***
	 * 获取到token，和现有的公司绑定
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void tokenCallback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}