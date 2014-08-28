package com.kingdee.purchase.platform.dao.alibaba;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.platform.dao.BaseDao;
import com.kingdee.purchase.platform.dao.rowmapper.User2AliSubAccountRowMapper;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.alibaba.User2SubAccountInfo;

@Repository
public class User2SubAccountAliDaoImpl extends BaseDao<User2SubAccountInfo> implements IUser2SubAccountDao{

	@Autowired
	public User2SubAccountAliDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, new User2AliSubAccountRowMapper());
	}

	@Override
	protected String getTableName() {
		return "t_map_user_ali";
	}

	public int insert(User2SubAccountInfo t) throws PurDBException {
		String sql = "insert into t_map_user_ali(enterpriseid,companyid,userid,subaccountid) values(?,?,?,?);";
		int result = 0;
		try {
			 result = getJdbcTemplate().update(sql,
					new Object[] { t.getEnterpriseId(), t.getCompanyId(), t.getUserId(), t.getSubAccountId() });
		} catch (DataAccessException e) {
			throw new PurDBException(e.getMessage());
		}

		obtainInsertId(t);
		return result;
	}

	public int update(User2SubAccountInfo t) throws PurDBException {
		String sql = "update t_map_user_ali set subaccountid = ? where id = ? and enterpriseid = ? and companyid = ? and userid = ? ";
		int result = 0;
		try {
			 result = getJdbcTemplate().update(sql,
					new Object[] {t.getSubAccountId(), t.getId(), t.getEnterpriseId(), t.getCompanyId(), t.getUserId()});
		} catch (DataAccessException e) {
			throw new PurDBException(e.getMessage());
		}
		return result;
	}

	public User2SubAccountInfo get(long enterpriseid, String companyid, String userid) throws PurDBException {
		String sql = "select * from t_map_user_ali where enterpriseid = ? and companyid = ? and userid = ? ";
		try {
			List<User2SubAccountInfo> list = getJdbcTemplate().query(sql, new Object[] { enterpriseid, companyid, userid },
					getRowMapper());
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
		} catch (DataAccessException e) {
			throw new PurDBException(e.getMessage());
		}

		return null;
	}

	public User2SubAccountInfo get(String subAccountId) throws PurDBException {
		String sql = "select * from t_map_user_ali where subaccountid = ? ";
		try {
			List<User2SubAccountInfo> list = getJdbcTemplate().query(sql, new Object[]{subAccountId},getRowMapper());
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
		} catch (DataAccessException e) {
			throw new PurDBException(e.getMessage());
		}

		return null;
	}
}
