package com.kingdee.purchase.platform.service.message;

import java.util.Date;

import org.apache.http.client.CookieStore;

/***
 * 缓存的cookie，有效期30分钟
 * @author RD_jiangkun_zhu
 *
 */
class CookieHolder {

	private CookieStore cookiestore;
	private Date refreshDate;
	
	CookieHolder(){
		this(null);
	}
	
	CookieHolder(CookieStore cookiestore) {
		this(cookiestore,new Date());
	}
	
	CookieHolder(CookieStore cookiestore,Date refreshDate) {
		super();
		this.cookiestore = cookiestore;
		this.refreshDate = refreshDate;
	}
	
	CookieStore getCookiestore() {
		return cookiestore;
	}
	void setCookiestore(CookieStore cookiestore) {
		this.cookiestore = cookiestore;
	}
	Date getRefreshDate() {
		return refreshDate;
	}
	void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}
	
	public boolean isExpire(){
		return isExpire(30);
	}
	/***
	 * 是否过期
	 * @mins 多久
	 * @return
	 */
	public boolean isExpire(int mins){
		if(null==refreshDate){
			return true;
		}
		
		return System.currentTimeMillis()>refreshDate.getTime()+mins*60*1000;
	}
}