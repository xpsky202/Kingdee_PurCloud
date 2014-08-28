package com.kingdee.purchase.platform.dao;

import com.kingdee.purchase.openapi.model.PurOrderInfo;
import com.kingdee.purchase.platform.exception.PurDBException;

public interface IPurOrderDao {
	
	public int savePurOrderInfo(long enterPriseID, PurOrderInfo info) throws PurDBException;
	
	public int updatePurOrderStatus(long enterPriseID, PurOrderInfo info) throws PurDBException;
	
	public String getAliOrderId(long enterPriseID, String orderId) throws PurDBException;

}
