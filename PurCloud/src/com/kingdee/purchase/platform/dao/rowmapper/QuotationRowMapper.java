package com.kingdee.purchase.platform.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.kingdee.purchase.openapi.model.ContactInfo;
import com.kingdee.purchase.openapi.model.QuotationInfo;
import com.kingdee.purchase.openapi.model.QuotationInfo.InvoiceType;
import com.kingdee.purchase.openapi.model.QuotationInfo.PayType;

public class QuotationRowMapper implements RowMapper<QuotationInfo>  {
	public QuotationInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuotationInfo info = new QuotationInfo();
		info.setId(rs.getLong("id"));
		info.setExpireDate(rs.getDate("expireDate"));
		info.setBuyOfferId(rs.getString("buyOfferId"));
		info.setSupplierMemberId(rs.getString("supplierMemberId"));
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setContact(rs.getString("contactName"));
		contactInfo.setEmail(rs.getString("email"));
		contactInfo.setMobile(rs.getString("mobile"));
		contactInfo.setPhone(rs.getString("phone"));
		info.setContactInfo(contactInfo);
		info.setInvoiceType(InvoiceType.getInvoiceType(rs.getString("invoiceType")));
		info.setFreight(rs.getLong("freight"));
		info.setTotalPrice(rs.getLong("totalPrice"));
		info.setSpecifications(rs.getString("specifications"));
		info.setPayType(PayType.getPayType(rs.getString("payType")));
		info.setPaySpecification(rs.getString("paySpecification"));
		info.setGmtCreate(rs.getDate("gmtCreate"));
		info.setPrId(rs.getString("prId"));
		return info;
	}
}
