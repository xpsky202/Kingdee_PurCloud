package com.kingdee.purchase.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.openapi.model.PurOrderInfo;
import com.kingdee.purchase.platform.dao.IPurOrderDao;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.service.IPurOrderService;

@Service
public class PurOrderServiceImpl implements IPurOrderService {
	
	@Autowired
	private IPurOrderDao orderDao;

	public int savePurOrderInfo(long enterPriseID,PurOrderInfo info)
			throws BaseException {
		return orderDao.savePurOrderInfo(enterPriseID, info);
	}

	public int updatePurOrderStatus(long enterPriseID, PurOrderInfo info)
			throws BaseException {
		return orderDao.updatePurOrderStatus(enterPriseID, info);
	}
	
	public String getAliOrderId(long enterPriseID, String orderId) throws BaseException {
		return orderDao.getAliOrderId(enterPriseID, orderId);
	}

}
 