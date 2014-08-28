package com.kingdee.purchase.platform.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kingdee.purchase.platform.info.ERPType;
import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.info.UsedStatus;

public class EnterpriceRowMapper implements RowMapper<EnterpriseInfo> {

	public EnterpriseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		EnterpriseInfo info = new EnterpriseInfo();
		info.setId(rs.getLong("id"));
//		info.setEmail(rs.getString("email"));
//		info.setMobile(rs.getString("mobile"));
		info.setName(rs.getString("username"));
//		info.setContact(rs.getString("contact"));
		info.setCreateDate(rs.getDate("createtime"));
		info.setUpdateDate(rs.getDate("updatetime"));
		info.setServiceUrl(rs.getString("serviceurl"));
		info.setUsedstatus(UsedStatus.ENABLED);
		info.setErpType(ERPType.getErpType(rs.getInt("erptype")));
		info.setEnterpriseid(rs.getLong("enterpriseid"));
		
		return info;
	}
}
