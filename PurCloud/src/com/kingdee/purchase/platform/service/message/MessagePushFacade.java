package com.kingdee.purchase.platform.service.message;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;

import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.info.msg.MessageInfo;
import com.kingdee.purchase.platform.service.IMessageService;
import com.kingdee.purchase.platform.util.SpringContextUtils;

/**
 * 消息处理
 * @author RD_jiangkun_zhu
 *
 */
public class MessagePushFacade {

	private final static Map<Long, CookieHolder> cache = Collections.synchronizedMap(new HashMap<Long, CookieHolder>());
	final static HttpHost proxy = new HttpHost("192.168.1.17",8080,"http");
	

	public static void process(MessageInfo messageInfo){
		if(messageInfo==null){
			return ;
		}
		store(messageInfo);
		
//		EnterpriseInfo enterpriseInfo = SystemParamsServiceFactory.getServiceInstance4Alibaba().getEnterpriseInfo(messageInfo.getEnterpriseId());
//		boolean isEas = true;
//		boolean result = false;
//		if(isEas){
//			result = pushEASMessage(enterpriseInfo,messageInfo);
//		}	
//		
//		if(!result){
//			store(messageInfo);
//		}
	}
	
	@SuppressWarnings("unused")
	private static boolean pushEASMessage(EnterpriseInfo enterpriseInfo,MessageInfo messageInfo){		
		CookieStore cookieStore = getCookieStore(enterpriseInfo);
		if(null!=cookieStore){
			IMessagePushService service = SpringContextUtils.getBean("easMessageService",IMessagePushService.class);
			boolean result = service.push(enterpriseInfo, messageInfo,cookieStore);
			if(result){
				return true;
			}
		}
		
		return false;
	}
	
	/***
	 * 获取cookie缓存
	 * @param enterpriseInfo
	 * @return
	 */	
	private static CookieStore getCookieStore(EnterpriseInfo enterpriseInfo){
		synchronized (cache) {
			CookieHolder holder = cache.get(enterpriseInfo.getEnterpriseid());
			if(holder!=null&&!holder.isExpire(30)){
				return holder.getCookiestore();
			}
		}
		IMessagePushService service = SpringContextUtils.getBean("easMessageService",IMessagePushService.class);
		CookieStore cookieStore = service.login(enterpriseInfo);
		if(null!=cookieStore){
			synchronized (cache) {
				cache.put(enterpriseInfo.getEnterpriseid(), new CookieHolder(cookieStore));
			}
		}
		
		return cookieStore;
	}
	
	/**
	 * 持久化
	 * @param msgInfo
	 */
	private static void store(MessageInfo msgInfo){
		msgInfo.setStatus(0);
		//保存到数据库中
		SpringContextUtils.getBean("messageService",IMessageService.class).save(msgInfo);
	}
}