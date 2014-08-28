package com.kingdee.purchase.openapi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.kingdee.purchase.openapi.client.exception.ExceptionParser;
import com.kingdee.purchase.openapi.client.exception.InvokeHttpException;
import com.kingdee.purchase.openapi.client.exception.InvokeIOException;
import com.kingdee.purchase.openapi.client.exception.PurCloudException;
import com.kingdee.purchase.openapi.client.policy.ClientPolicy;
import com.kingdee.purchase.openapi.client.policy.RequestPolicy;

/**
 * 采购电商云 SDK 客户端
 * 
 * 使用方式如下：
 * ClientPolicy policy = new ClientPolicy(host).setHttpPort(port);  //创建客户端策略，设置服务地址
 * policy.setEnterpriseId("entrypriseId").setOrgUnitId("orgUnitId").setUserId("userId");  //设置系统参数
 * client = new PurCloudClient(policy);  //创建访问服务客户端
 * RequestPolicy basePolicy = new RequestPolicy().setContentCharset(ENCODE).setTimeout(TIMEOUT); //设置字符集和超时时间
 * Request request = new Request(nameSpace, apiName, apiVersion);  //创建访问请求
 * request.setParam("paramName", "paramValue");  //设置访问api参数
 * String result = client.send(request, basePolicy);  //调用服务
 * 
 * @author RD_sky_lv
 */
public class PurCloudClient {

	private static final String ID_ENTERPRISE = "enterpriseid";
	private static final String ID_ORGUNIT = "orgunitid";
	private static final String ID_USER = "userid";
	private static final String TARGETURLENUM = "targetUrlEnum";

	private ClientPolicy policy;
	private HttpClient httpClient;

	private static final ThreadLocal<PurCloudClient> clientHolder = new ThreadLocal<PurCloudClient>();

	/**
	 * 通过client策略初始化一个AlibabaClient
	 * 
	 * @param policy client策略
	 */
	public PurCloudClient(ClientPolicy policy) {
		init(policy);
		clientHolder.set(this);
	}

	private void init(ClientPolicy policy) {
		this.policy = policy;
		httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost(policy.getServerHost(), policy.getHttpPort());
		if (policy.isProxy()) {
			httpClient.getHostConfiguration().setProxy(policy.getProxyHost(), policy.getProxyPort());
		}
	}

	public static final PurCloudClient getClient() {
		return clientHolder.get();
	}

	public APIId getAPI(String namespace, String api, int version) {
		return new APIId(namespace, api, version);
	}
	
	/**
	 * 发送请求
	 * @param req  api调用请求
	 * @return
	 * @throws PurCloudException
	 */
	public Object send(Request req) throws PurCloudException {
		return send(req, this.policy);
	}
	
	/**
	 *  发送请求
	 * @param request api调用请求
	 * @param policy  请求调用策略
	 * @return
	 * @throws PurCloudException
	 */
	public String send(Request request, RequestPolicy policy) throws PurCloudException {
		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(policy.getTimeout());
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(policy.getTimeout());
		
		PostMethod method = getMethod(request, policy);
		setSystemParams(method);
		if (request.getParams() != null) {
			for (Map.Entry<String, Object> entry : request.getParams().entrySet()) {
				method.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}
		
		String value = "";
		try {
			int status = httpClient.executeMethod(method);
			if (status != HttpStatus.SC_OK) {
				throw new RuntimeException("invoke api failed, urlPath:" + request.getApiId().toString() + " status:"
						+ status + " response:" + method.getResponseBodyAsString());
			}
			Response res = parseResult(method);
			if (res.isSuccess) {
				value = res.data;
			} else {
				throw ExceptionParser.buildException(res.data);
			}
		} catch (HttpException e) {
			LoggerHelper.getClientLogger().fine(e.getMessage());
			throw new InvokeHttpException(e.getMessage());
		} catch (IOException e) {
			LoggerHelper.getClientLogger().fine(e.getMessage());
			throw new InvokeIOException(e.getMessage());
		} finally {
			method.releaseConnection();
		}
		
		return value;
	}
	
	/**
	 * 免登目的地址枚举
	 * @author RD_sky_lv
	 */
	public static enum TargetUrlEnum {
		POST_BUYOFFER(0, "发布询价单"),
		BUYOFFER_MANAGEMENT(1, "询价管理"),
		BUY_OFFER_DETAIL(2, "查看询价详情"),
		RECEIVE_QUOTATION_LIST(3, "查看报价列表"),
		SUPPLIER_MANAGEMENT(4, "供应商管理");
		
		private int val;
		private String alias;
		TargetUrlEnum(int value, String alias){
			this.val = value;
			this.alias = alias;
		}
		
		public int getValue(){
			return this.val;
		}
		
		public String getAlias(){
			return this.alias;
		}
	}
	
	/**
	 * 获取免登动态页面内容
	 * @param request
	 * @return
	 */
	public String getAutoLoginWebContent(Request request, TargetUrlEnum targetUrl) {
		StringBuilder html = new StringBuilder();
		html.append("<html>")
			.append("<script>")
			.append("function submitForm(){document.getElementById('formId').submit();}")
			.append("</script>")
			.append("<body onload='submitForm();'>")
			.append("<form id='formId' method='post' action='").append(getUrlPath(request, policy)).append("'>")
			.append("<input type='hidden' name='").append(ID_ENTERPRISE).append("' value='").append(policy.getEnterpriseId()).append("'/>")
			.append("<input type='hidden' name='").append(ID_ORGUNIT).append("' value='").append(policy.getOrgUnitId()).append("'/>")
			.append("<input type='hidden' name='").append(ID_USER).append("' value='").append(policy.getUserId()).append("'/>")
			.append("<input type='hidden' name='").append(TARGETURLENUM).append("' value='").append(targetUrl.toString()).append("'/>");
		if (request.getParams() != null) {
			for (Map.Entry<String, Object> entry : request.getParams().entrySet()) {
				html.append("<input type='hidden' name='").append(entry.getKey()).append("' value='")
					.append(urlEncode(entry.getValue().toString())).append("'/>");
			}
		}
		html.append("</form>")
			.append("</body>")
		    .append("</html>");
		
		return html.toString();
	}
	
	/**
	 * 对字符编码，防止中文乱码
	 * @param s
	 * @return
	 */
	private String urlEncode(String s){
		String encodeStr = null;
		try {
			encodeStr = URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
		return encodeStr;
	}

	/**
	 * 解析http请求的response
	 * @param method
	 * @return 请求结果
	 * @throws IOException
	 */
	private String parserResponse(PostMethod method) throws IOException {
		StringBuffer contentBuffer = new StringBuffer();
		InputStream in = method.getResponseBodyAsStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, method.getResponseCharSet()));
		String inputLine = null;
		while ((inputLine = reader.readLine()) != null) {
			contentBuffer.append(inputLine);
			contentBuffer.append("/n");
		}
		//去掉结尾的换行符
		contentBuffer.delete(contentBuffer.length() - 2, contentBuffer.length());
		in.close();
		return contentBuffer.toString();
	}
	
	private Response parseResult(PostMethod method) throws IOException {
		String response = parserResponse(method);
		JSONObject json = JSONObject.fromObject(response);
		Response res = new Response();
		int flag = json.optInt("isSuccess");
		if (flag == 1) {
			res.isSuccess = true;
		} else {
			res.isSuccess = false;
		}
		
		res.data = json.optString("data");
		
		return res;
	}
	
	private class Response {
		boolean isSuccess;
		String data;
	}

	private PostMethod getMethod(Request request, RequestPolicy policy) {
		//		PostMethod method = null;
		//		switch (policy.getHttpMethod()) {
		//		case POST:
		//			method = new PostMethod();
		//			break;
		//		case GET:
		//			method = new GetMethod();
		//			break;
		//		case AUTO:
		//			if (hasParameters(request.getParams()) || hasAttachments(request)) {
		//				method = new PostMethod();
		//			} else {
		//				method = new GetMethod();
		//			}
		//			break;
		//		default:
		//			method = new PostMethod();
		//		}
		PostMethod method = new PostMethod(getUrlPath(request, policy));
		method.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

		return method;
	}

	private PostMethod setSystemParams(PostMethod method) {
		method.setParameter(ID_ENTERPRISE, policy.getEnterpriseId());
		method.setParameter(ID_ORGUNIT, policy.getOrgUnitId());
		method.setParameter(ID_USER, policy.getUserId());

		return method;
	}
	
	/**
     * url format:
     * (http|https)://(192.168.204.131:8086)/(purchase/openapi)[/protocol[/defaultVersion[/defaultNamespace[/apiName[/appKey]]]]][?queryString]
     * @param context
     * @return {@link StringBuilder}
     */
	private String getUrlPath(Request request, RequestPolicy policy) {
		StringBuilder path = new StringBuilder();
		path.append("/purchase/openapi/");
		path.append(policy.getRequestProtocol().toString());
		path.append("/");
		path.append(this.policy.getDefaultVersion());
		path.append("/");
		path.append(request.getApiId().getNamespace());
		path.append("/");
		path.append(request.getApiId().getName());

		return path.toString();
	}

}
