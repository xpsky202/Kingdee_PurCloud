package com.kingdee.purchase.platform.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kingdee.purchase.platform.info.msg.MessageInfo;
import com.kingdee.purchase.platform.service.IMessageService;
import com.kingdee.purchase.platform.util.SpringContextUtils;

public class DebugServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4799141806697642064L;
	
	private final static Logger logger = LogManager.getLogger(DebugServlet.class);

	private final static HttpHost proxy = new HttpHost("192.168.1.17",8080,"http");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		boolean isdebug = true;
		if(isdebug){
			List<MessageInfo> list = SpringContextUtils.getBean(IMessageService.class).getList(1000, 30);
			JSONObject json = new JSONObject();
			JSONArray array = JSONArray.fromObject(list);
			json.put("messages", array);
			System.out.println(json.toString());
		}
		
		try{
			getEASSessionID(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			pushEASMessage(request,response);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void testNetwork(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String url = request.getParameter("url");
		String action = request.getParameter("method");
		try{
			DefaultHttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			
			if ("get".equals(action)) {
				HttpGet method = new HttpGet(url);
				client.execute(method);
			}else{
				HttpPost method = new HttpPost(url);
				client.execute(method);
			}

			response.getWriter().write("没有报错");
		}catch(Exception e){
			response.getWriter().write(e.getMessage());
		}
	}
	
	private void getEASSessionID(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
//		String url = enterpriseInfo.getServiceUrl();
		
//		String msgJson = JSONObject.fromObject(msgInfo).toString();
		HttpPost method = new HttpPost("http://172.19.76.62:6888/eassso/login");
		method.getParams().setParameter("Locale","L2");
		method.getParams().setParameter("dataCenter","e750no6_main");
		method.getParams().setParameter("username","user");
		method.getParams().setParameter("password","");
		method.getParams().setParameter("userAuthPattern","purCloud");
		method.getParams().setParameter("message", "test");
		
		HttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);  
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		DefaultHttpClient client = new DefaultHttpClient(httpParams);
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		HttpResponse resp = client.execute(method);
		HttpEntity entity = resp.getEntity();
		InputStream inputStream = entity.getContent();
		
		byte[] bys = new byte[1024];
		while(inputStream.read(bys)>0){
			response.getOutputStream().write(bys);
		}
		CookieStore cookie = client.getCookieStore();
//		cookieCache.put("asdf", new CookieHolder(cookie));
	}
	
	private void pushEASMessage(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpPost method = new HttpPost("http://172.19.76.62:6888/scm/TestServlet");
		method.getParams().setParameter("Locale","L2");
		method.getParams().setParameter("dataCenter","e750no6_main");
		method.getParams().setParameter("username","user");
		method.getParams().setParameter("password","");
		method.getParams().setParameter("userAuthPattern","purCloud");
		method.getParams().setParameter("message", "test");
		
		HttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);  
		HttpConnectionParams.setSoTimeout(httpParams, 10000);  
		DefaultHttpClient client = new DefaultHttpClient(httpParams);
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		
		HttpResponse resp = client.execute(method);
		HttpEntity entity = resp.getEntity();
		InputStream inputStream = entity.getContent();
		
		byte[] bys = new byte[1024];
		while(inputStream.read(bys)>0){
			response.getOutputStream().write(bys);
		}
		CookieStore cookie = client.getCookieStore();
//		cookieCache.put("asdf", new CookieHolder(cookie));
	}
	
//	private final static Map<String, CookieHolder> cookieCache = new HashMap<String, CookieHolder>();
	
//	private 
}