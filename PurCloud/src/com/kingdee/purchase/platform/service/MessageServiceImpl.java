package com.kingdee.purchase.platform.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.dao.IMessageDao;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.msg.MessageInfo;

@Service("messageService")
public class MessageServiceImpl implements IMessageService {
	
	private final static Logger logger = LogManager.getLogger(MessageServiceImpl.class);

	@Autowired
	private IMessageDao messageDao;
	
	public boolean save(MessageInfo info) {
		if(null==info){
			return false;
		}
		
		try {
			if(info.getId()<=0){
				messageDao.insert(info);
			}else {
				messageDao.update(info);
			}
			return true;
		} catch (PurDBException e) {
			logger.error(e.getMessage(),e);
		}
		return false;
	}

	public boolean updateStatus(MessageInfo t) {
		try {
			return messageDao.updateStatus(t.getId(),t.getStatus())>0;
		} catch (PurDBException e) {
			logger.error(e.getMessage(),e);
		}
		
		return false;
	}

	public List<MessageInfo> getList(long enterpriseId, int size) {
		if(enterpriseId<=0){
			return new ArrayList<MessageInfo>();
		}
		
		if(size<=0){
			size = 10;
		}
		
		return messageDao.getList(enterpriseId, size);
	}
}