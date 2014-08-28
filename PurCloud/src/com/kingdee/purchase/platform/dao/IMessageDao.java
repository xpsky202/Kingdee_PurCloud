package com.kingdee.purchase.platform.dao;

import java.util.List;

import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.msg.MessageInfo;

public interface IMessageDao extends IBaseDao<MessageInfo> {

	public List<MessageInfo> getList(long enterpriseId,int size);
	
	public int updateStatus(long id,int status) throws PurDBException;
}