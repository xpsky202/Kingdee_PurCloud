package com.kingdee.purchase.platform.service.message;

import org.apache.http.client.CookieStore;

import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.info.msg.MessageInfo;

/***
 * 消息对象
 * @author RD_jiangkun_zhu
 *
 */
public interface IMessagePushService {
	
	/***
	 * 登录成功，返回对应的cookie
	 * <br>登录失败，返回null
	 * @param enterpriseInfo
	 * @return
	 */
	public CookieStore login(EnterpriseInfo enterpriseInfo);
	
	/***
	 * 推送成功，返回true;
	 * 推送失败，返回false，系统要持久化消息
	 * 
	 * @param enterpriseInfo
	 * @param messageInfo
	 * @param cookie
	 * @return
	 */
	public boolean push(EnterpriseInfo enterpriseInfo,MessageInfo messageInfo,CookieStore cookie);
}