package com.kingdee.purchase.platform.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.openapi.model.PurOrderInfo;
import com.kingdee.purchase.openapi.model.PurOrderItem;
import com.kingdee.purchase.platform.dao.IPurOrderDao;
import com.kingdee.purchase.platform.exception.PurDBException;

@Repository
public class PurOrderDaoImpl extends JdbcDaoSupport implements IPurOrderDao {
	
	@Autowired
	public PurOrderDaoImpl(JdbcTemplate jdbcTemplate) {
		super();
		super.setJdbcTemplate(jdbcTemplate);
	}
	/**
	 * 保存订单
	 * @param enterPriseID
	 * @param info
	 * @return
	 */
	public int savePurOrderInfo(long enterPriseID, PurOrderInfo info) throws PurDBException{
		
		int result = saveOrderHead(enterPriseID, info);
		result = saveOrderItem(info);
		return result;
	}

	/**
	 * 保存订单头
	 * @param enterPriseID
	 * @param info
	 * @return
	 */
	private int saveOrderHead(long enterPriseID, PurOrderInfo info) throws PurDBException{
		String sqlString = "insert into t_bas_purorder" +
				"(enterPriseID,orderId,totalAmount,freightAmount,orderAmount,sourceType," +
				" orderStatus,failReason,buyerCompanyName,buyerMobile,buyerEmail,payType,aliOrderId," +
				" supplierMemberId,supplierCode,supplierCompanyName,supplierMobile,supplierEmail)"+
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] args = new Object[]{
				enterPriseID,
				info.getOrderId(),
				info.getTotalAmount(),
				info.getFreightAmount(),
				info.getOrderAmount(),
				info.getSourceType(),
				info.getOrderStatus(),
				info.getFailReason(),
				info.getBuyerCompanyName(),
				info.getBuyerMobile(),
				info.getBuyerEmail(),
				info.getPayType(),
				info.getAliOrderId(),
				info.getSupplierMemberId(),
				info.getSupplierCode(),
				info.getSupplierCompanyName(),
				info.getSupplierMobile(),
				info.getSupplierEmail()};
		int[] argTypes = new int[]{
					Types.BIGINT,Types.VARCHAR,Types.BIGINT,Types.BIGINT,Types.BIGINT,Types.VARCHAR,
					Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BIGINT,
					Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		int result = getJdbcTemplate().update(sqlString, args, argTypes);
		return result;
	}
	
	/**
	 * 保存订单分录
	 * @param info
	 * @return
	 */
	private int saveOrderItem(PurOrderInfo info) throws PurDBException {
		String sqlStr = "insert into t_bas_purorderitem" +
				"(orderItemId,orderId,productName,price,count,otherInfo,receiverPhone,receiverMobile,receiverPostCode,receiverAddress,receiverName) " +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		
		String orderId = info.getOrderId();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		Object[] paramsObjects ;
		for(PurOrderItem item:info.getOrderItemList().getItemsList()){
			paramsObjects = new Object[]{item.getOrderItemId(),orderId, item.getProductName(),item.getPrice(),
										 item.getCount(),item.getOtherInfo(),item.getReceiverPhone(),item.getReceiverMobile(),
										 item.getReceiverPostCode(),item.getReceiverAddress(),item.getReceiverName()};
			paramsList.add(paramsObjects);
		}

		int[] argTypes = new int[]{
					Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BIGINT,Types.BIGINT,Types.VARCHAR,
					Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		int result = getJdbcTemplate().batchUpdate(sqlStr, paramsList, argTypes).length;
		return result;
	}

	/**
	 * 更新订单状态
	 * @param enterPriseID
	 * @param info
	 * @return
	 */
	public int updatePurOrderStatus(long enterPriseID, PurOrderInfo info) throws PurDBException {
		String sqlString = "delete from t_bas_purorder where enterPriseID = ? and orderId = ?";
		Object[] args = new Object[]{enterPriseID, info.getOrderId() };
		int[] argTypes = new int[]{Types.BIGINT, Types.VARCHAR};
		int result = getJdbcTemplate().update(sqlString, args, argTypes);
		
		sqlString = "delete from t_bas_purorderitem where orderId = ?";
		args = new Object[]{info.getOrderId() };
		argTypes = new int[]{Types.VARCHAR};
		result = getJdbcTemplate().update(sqlString, args, argTypes);
		
		return result;
	}
	
	/**
	 * 根据系统订单ID获取阿里订单ID
	 * @param enterPriseID
	 * @param orderId
	 * @return
	 * @throws PurDBException
	 */
	public String getAliOrderId(long enterPriseID, String orderId) throws PurDBException {
		final String sqlString = "select aliOrderId from t_bas_purorder where enterPriseID = ? and orderId = ?";
		Object[] args = new Object[]{enterPriseID, orderId };
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sqlString, args);
		if (rs.next()) {
			return rs.getString("aliOrderId");
		}
		
		return "-1";
	}

}
