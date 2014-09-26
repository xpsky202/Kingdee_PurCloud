package com.kingdee.purchase.platform.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kingdee.purchase.openapi.model.BusinessInfo;
import com.kingdee.purchase.openapi.model.SupplierCompanyInfo;
import com.kingdee.purchase.openapi.model.SupplierContactInfo;
import com.kingdee.purchase.openapi.model.SupplierInfo;

public class SupplierRowMapper implements RowMapper<SupplierInfo>{
	public SupplierInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		SupplierInfo supplierInfo = new SupplierInfo();
		supplierInfo.setId(rs.getString("id"));
		supplierInfo.setDestId(rs.getString("destId"));
		
		SupplierCompanyInfo companyInfo = new SupplierCompanyInfo();
		companyInfo.setName(rs.getString("companyName"));
		companyInfo.setPrincipal(rs.getString("principal"));
		companyInfo.setCompanyAddress(rs.getString("companyAddress"));
		companyInfo.setRegistrationId(rs.getString("registrationId"));
		companyInfo.setEnterpriseType(rs.getString("enterpriseType"));
		companyInfo.setDateOfEstablishment(rs.getString("dateOfEstablishment"));
		companyInfo.setRegisteredCapital(rs.getString("registeredCapital"));
		companyInfo.setBusinessTerm(rs.getString("businessTerm"));
		companyInfo.setBusinessScope(rs.getString("businessScope"));
		companyInfo.setBank(rs.getString("bank"));
		companyInfo.setBankAccount(rs.getString("bankAccount"));
		companyInfo.setCompanySummary(rs.getString("companySummary"));
		supplierInfo.setCompanyInfo(companyInfo);
		
		SupplierContactInfo contactInfo = new SupplierContactInfo();
		contactInfo.setName(rs.getString("contactName"));
		contactInfo.setMobile(rs.getString("mobile"));
		contactInfo.setGender(rs.getString("gender"));
		contactInfo.setPhone(rs.getString("phone"));
		contactInfo.setFax(rs.getString("fax"));
		contactInfo.setZipCode(rs.getString("zipCode"));
		contactInfo.setContactAddress(rs.getString("contactAddress"));
		supplierInfo.setContactInfo(contactInfo);
		
		BusinessInfo businessInfo = new BusinessInfo();
		businessInfo.setMainIndustries(rs.getString("mainIndustries"));
		businessInfo.setBusinessAddress(rs.getString("businessAddress"));
		supplierInfo.setBusinessInfo(businessInfo);
		
		return supplierInfo;
	}
}
