package com.kingdee.purchase.platform.service;

import com.kingdee.purchase.openapi.model.PurOrderInfo;
import com.kingdee.purchase.platform.exception.BaseException;

public interface IPurOrderService {
	
	/**
	 * 保存采购订单
	 * @param enterpriseId
	 * @param info
	 * @return
	 * @throws BaseException
	 */
	public int savePurOrderInfo(long enterpriseId, PurOrderInfo info) throws BaseException;
	
	/**
	 * 修改采购订单状态
	 * @param enterpriseId
	 * @param info
	 * @return
	 * @throws BaseException
	 */
	public int updatePurOrderStatus(long enterpriseId, PurOrderInfo info) throws BaseException;
	
	/**
	 * 根据系统订单ID获取阿里订单ID
	 * @param enterPriseID
	 * @param orderId
	 * @return
	 * @throws BaseException
	 */
	public String getAliOrderId(long enterPriseID, String orderId) throws BaseException;

}
