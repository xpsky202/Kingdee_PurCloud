package com.kingdee.purchase.platform.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kingdee.purchase.platform.info.api.ApiBaseInfo;

public class APIBaseInfoRowMapper implements RowMapper<ApiBaseInfo> {

	public ApiBaseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ApiBaseInfo info = new ApiBaseInfo();
		info.setId(rs.getLong("id"));
		info.setSubSystem(rs.getString("subSystem"));
		info.setSubSystemAlias(rs.getString("subSystemAlias"));
		info.setApiName(rs.getString("apiName"));
		info.setApiAlias(rs.getString("apiAlias"));
		info.setRemark(rs.getString("remark"));
		info.setState(rs.getString("state"));
		info.setVersion(rs.getInt("version"));
		return info;
	}

}
