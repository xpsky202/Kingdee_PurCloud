package com.kingdee.purchase.platform.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;

public class Company2AliAccountRowMapper implements RowMapper<Company2AccountInfo> {

	public Company2AccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company2AccountInfo info = new Company2AccountInfo();
		info.setAccountId(rs.getString("accountid"));
		info.setCompanyId(rs.getString("companyid"));
		info.setCompanyName(rs.getString("companyname"));
		info.setToken(rs.getString("token"));
		info.setEnterpriseId(rs.getLong("enterpriseid"));
		info.setId(rs.getLong("id"));
		info.setExpiredDate(rs.getDate("expireddate"));
		
		return info;
	}
}