package com.kingdee.purchase.platform.service;

import java.util.List;

import com.kingdee.purchase.platform.info.msg.MessageInfo;

public interface IMessageService {

	/**
	 * 
	 * @param enterpriseId
	 * @param size
	 * @return
	 */
	public List<MessageInfo> getList(long enterpriseId,int size);
	
	public boolean save(MessageInfo info);
	
	/**
	 * 0-未消费；
	 * 1-已消费；
	 * 
	 * @param  t
	 * @return
	 */
	public boolean updateStatus(MessageInfo t);
	
	
}