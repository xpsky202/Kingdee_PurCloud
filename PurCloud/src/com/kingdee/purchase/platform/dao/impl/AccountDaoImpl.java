package com.kingdee.purchase.platform.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.platform.dao.BaseDao;
import com.kingdee.purchase.platform.dao.IAccountDao;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.AccountInfo;

@Repository
public class AccountDaoImpl extends BaseDao<AccountInfo> implements IAccountDao {

	@Autowired
	protected AccountDaoImpl(DataSource dataSource) {
		super(dataSource, new AccountRowMapper());
	}

	public AccountInfo getByUsername(String username) throws PurDBException {
		String sqlString = "select * from t_pm_account where username = ?";
		try{
			List<AccountInfo> list = query(sqlString, new Object[]{username});
			if(null!=list && list.size()>0){
				return list.get(0);
			}
		}catch(RuntimeException e){
			throw new PurDBException("数据库异常",e);
		}
		
		return null;
	}

	private static class AccountRowMapper implements RowMapper<AccountInfo>{
		public AccountInfo mapRow(ResultSet rs, int rowNum)	throws SQLException {
			AccountInfo info = new AccountInfo();
			info.setId(rs.getLong("id"));
			info.setDisplayname(rs.getString("displayname"));
			info.setPassword(rs.getString("password"));
			info.setUsername(rs.getString("username"));
			info.setEmail(rs.getString("email"));
			info.setMobile(rs.getString("mobile"));
			
			return info;
		}
	}

	public int insert(AccountInfo t) throws PurDBException {
		String sql = "insert into t_pm_account(username,displayname,password,email,mobile) values(?,?,?,?,?)";
		int[] argTypes = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		Object[] args = new Object[]{t.getUsername(),t.getDisplayname(),t.getPassword(),t.getEmail(),t.getMobile()};
		int result = getJdbcTemplate().update(sql, args, argTypes);
		obtainInsertId(t);
		return result;
	}

	public int update(AccountInfo t) throws PurDBException {
		String sql = "update t_pm_account set displayname=?,email=?,mobile=? where id = ?";
		int[] argTypes = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BIGINT};
		Object[] args = new Object[]{t.getDisplayname(),t.getEmail(),t.getMobile(),t.getId()};
		return getJdbcTemplate().update(sql, args, argTypes);
	}

	@Override
	protected String getTableName() {
		return "t_pm_account";
	}

	public AccountInfo getByEmail(String email) throws PurDBException {
		String sqlString = "select * from t_pm_account where email = ?";
		try{
			List<AccountInfo> list = query(sqlString, new Object[]{email});
			if(null!=list && list.size()>0){
				return list.get(0);
			}
		}catch(RuntimeException e){
			throw new PurDBException("数据库异常",e);
		}
		
		return null;
	}

	public boolean isExistEmail(long id, String email) {
		String sql = "select 1 from t_pm_account where id <> ? and email = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,new Object[]{id,email});
		return rs!=null && rs.next();
	}

	public boolean isExistName(long id, String username) {
		String sql = "select 1 from t_pm_account where id <> ? and username = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,new Object[]{id,username});
		return rs!=null && rs.next();
	}

	public boolean changePassword(long id, String password) {
		String sql = "update t_pm_account set password = ? where id = ?";
		Object[] params = new Object[]{password,id};
		return getJdbcTemplate().update(sql, params)>0;
	}
}