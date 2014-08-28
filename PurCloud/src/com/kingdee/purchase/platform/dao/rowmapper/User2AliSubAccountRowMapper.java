package com.kingdee.purchase.platform.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kingdee.purchase.platform.info.alibaba.User2SubAccountInfo;

public class User2AliSubAccountRowMapper implements RowMapper<User2SubAccountInfo>{

	public User2SubAccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		User2SubAccountInfo info = new User2SubAccountInfo();
		info.setId(rs.getLong("id"));
		info.setEnterpriseId(rs.getLong("enterpriseid"));
		info.setCompanyId(rs.getString("companyid"));
		info.setSubAccountId(rs.getString("subaccountid"));
		info.setUserId(rs.getString("userid"));
		
		return info;
	}
}
