package com.kingdee.purchase.platform.service.message;

import java.io.IOException;
import java.io.InputStream;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.info.msg.MessageInfo;

@Service("easMessageService")
public class EASMessagePushService implements IMessagePushService{
	
	private final static Logger logger = LogManager.getLogger(EASMessagePushService.class);
	
	public CookieStore login(EnterpriseInfo enterpriseInfo) {
//		HttpPost method = new HttpPost("http://172.19.76.62:6888/eassso/login");
		HttpPost method = new HttpPost(enterpriseInfo.getServiceUrl());
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
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, MessagePushFacade.proxy);
		try {
			HttpResponse resp = client.execute(method);
			if(resp.getStatusLine().getStatusCode()==200){
				return client.getCookieStore();
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		
		return null;
	}

	public boolean push(EnterpriseInfo enterpriseInfo, MessageInfo messageInfo,CookieStore cookieStore) {
		HttpPost method = new HttpPost(enterpriseInfo.getServiceUrl());
		method.getParams().setParameter("message", messageInfo.toJsonString());
		
		HttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);  
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		DefaultHttpClient client = new DefaultHttpClient(httpParams);
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, MessagePushFacade.proxy);
		try {
			HttpResponse resp = client.execute(method);
			HttpEntity entity = resp.getEntity();
			if(resp.getStatusLine().getStatusCode()==200 && entity.getContentLength()>0){
				InputStream input = entity.getContent();
				byte[] bys = new byte[(int)entity.getContentLength()];
				input.read(bys);
				JSONObject json = JSONObject.fromObject(new String(bys));
				if(json.getBoolean("success")){				//消息处理成功，返回
					return true;
				}
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}catch (IllegalStateException e) {
			logger.error(e.getMessage(),e);
		}
		
		return false;
	}
}