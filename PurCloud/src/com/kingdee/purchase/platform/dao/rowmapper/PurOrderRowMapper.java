package com.kingdee.purchase.platform.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kingdee.purchase.openapi.model.PurOrderInfo;


public class PurOrderRowMapper implements RowMapper<PurOrderInfo> {

	public PurOrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		PurOrderInfo info = new PurOrderInfo();
		info.setOrderId(rs.getString("orderId"));
		info.setOrderId(rs.getString("aliOrderId"));
		info.setTotalAmount(rs.getLong("totalAmount"));
		info.setFreightAmount(rs.getLong("freightAmount"));
		info.setOrderAmount(rs.getLong("orderAmount"));
		info.setSourceType(rs.getString("sourceType"));
		info.setOrderStatus(rs.getString("orderStatus"));
		info.setFailReason(rs.getString("failReason"));
		info.setBuyerCompanyName(rs.getString("buyerCompanyName"));
		info.setBuyerMobile(rs.getString("buyerMobile"));
		info.setBuyerEmail(rs.getString("buyerEmail"));
		info.setSupplierCode(rs.getString("supplierCode"));
		info.setSupplierCompanyName(rs.getString("supplierCompanyName"));
		info.setSupplierMemberId(rs.getString("supplierMemberId"));
		info.setSupplierEmail(rs.getString("supplierEmail"));
		info.setSupplierMobile(rs.getString("supplierMobile"));
		
		return info;
	}

}
