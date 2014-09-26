package com.kingdee.purchase.platform.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kingdee.purchase.openapi.model.QuotationItem;

public class QuotationItemRowMapper implements RowMapper<QuotationItem>{
	public QuotationItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuotationItem item = new QuotationItem();
		item.setId(rs.getLong("id"));
		item.setSubject(rs.getString("subject"));
		item.setProductId(rs.getLong("productId"));
		item.setAmount(rs.getInt("amount"));
		item.setUnit(rs.getString("unit"));
		item.setPrice(rs.getLong("price"));
		item.setPrItemId(rs.getString("prItemId"));
		item.setTaxRate(rs.getString("taxRate"));
		return item;
	}
}
