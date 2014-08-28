package com.kingdee.purchase.platform.dao.alibaba;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.platform.dao.BaseDao;
import com.kingdee.purchase.platform.dao.rowmapper.Company2AliAccountRowMapper;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.KeyValue;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;

@Repository
public class Company2AliAccountDaoImpl extends BaseDao<Company2AccountInfo> implements ICompany2AccountDao {

	@Autowired
	public Company2AliAccountDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, new Company2AliAccountRowMapper());
	}

	@Override
	protected String getTableName() {
		return "t_map_company_ali";
	}

	public int insert(Company2AccountInfo t) throws PurDBException {
		String sql = "insert into t_map_company_ali(enterpriseid,companyid,accountid) values(?,?,?)";
		int result = getJdbcTemplate().update(sql,
				new Object[] { t.getEnterpriseId(), t.getCompanyId(), t.getAccountId() });

		obtainInsertId(t);
		return result;
	}

	public int update(Company2AccountInfo t) throws PurDBException {
		return 0;
	}

	public Company2AccountInfo getCompany2AccountInfo(long enterpriseid, String companyid) throws PurDBException {
		String sql = "select * from t_map_company_ali where enterpriseid = ? and companyid = ?";
		try {
			List<Company2AccountInfo> list = getJdbcTemplate().query(sql, new Object[] { enterpriseid, companyid },
					getRowMapper());
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
		} catch (DataAccessException e) {
			throw new PurDBException(e.getMessage());
		}

		return null;
	}

	public List<Company2AccountInfo> getAllCompany2AccountList() throws PurDBException {
		String sql = "select * from t_map_company_ali";
		try {
			return getJdbcTemplate().query(sql, getRowMapper());
		} catch (DataAccessException e) {
			throw new PurDBException(e.getMessage());
		}
	}

	public List<Company2AccountInfo> getCompanyList(long enterpriseid) throws PurDBException {
		String sql = "select * from t_map_company_ali where enterpriseid = ?";
		try {
			return getJdbcTemplate().query(sql, new Object[] { enterpriseid }, getRowMapper());
		} catch (DataAccessException e) {
			throw new PurDBException(e.getMessage());
		}
	}

	public int saveCompanyInfo(long enterpriseId, List<KeyValue> companysList) throws PurDBException {
		final Set<String> companyIdSet = new HashSet<String>();
		StringBuilder ids = new StringBuilder();
		for (KeyValue kv : companysList) {
			ids.append("'").append(kv.getKey()).append("',");
		}
		ids.setLength(ids.length() - 1);
		String selectSql = "select companyid from t_map_company_ali where enterpriseid=? and companyid in (" + ids
				+ ")";
		getJdbcTemplate().query(selectSql, new Object[] { enterpriseId }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				companyIdSet.add(rs.getString("companyid"));
			}
		});

		List<Object[]> params = new ArrayList<Object[]>();
		for (KeyValue kv : companysList) {
			if (companyIdSet.contains(kv.getKey())) {
				continue;
			}

			Object[] paramObjects = new Object[] { enterpriseId, kv.getKey(), kv.getValue() };
			params.add(paramObjects);
		}

		if (params.size() > 0) {
			String sql = " insert into t_map_company_ali(enterpriseid,companyid,companyname) values(?,?,?)";
			int[] argTypes = new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR };
			return getJdbcTemplate().batchUpdate(sql, params, argTypes).length;
		}

		return 0;
	}

	public int updateCompany2AccountMapping(long enterpriseId, List<Company2AccountInfo> companysList)
			throws PurDBException {
		String sql = "update t_map_company_ali set accountid = ?, token = ? where id = ? and enterpriseid = ?";
		List<Object[]> paramsList = new ArrayList<Object[]>();
		Object[] paramsObjects;
		for (Company2AccountInfo kv : companysList) {
			paramsObjects = new Object[] { kv.getAccountId(), kv.getToken(), kv.getId(), enterpriseId };
			paramsList.add(paramsObjects);
		}

		int[] argTypes = new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.BIGINT,
				java.sql.Types.BIGINT };
		return getJdbcTemplate().batchUpdate(sql, paramsList, argTypes).length;
	}

	public int updateCompany2AccountMapping(long enterpriseId, String companyId, String memberId, String token,
			Date expiredDate) throws PurDBException {
		String sql = " update t_map_company_ali set accountid = ?, token = ?, expireddate = ? "
				+ " where companyId = ? and enterpriseid = ?";

		Object[] params = new Object[] { memberId, token, expiredDate, companyId, enterpriseId };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.BIGINT };
		return getJdbcTemplate().update(sql, params, types);
	}

	public Company2AccountInfo getCompany2AccountInfo(String accountId) throws PurDBException {
		String sql = "select * from t_map_company_ali where accountid = ?";
		Object[] params = new Object[] { accountId };
		int[] types = new int[] { Types.VARCHAR };

		try {
			List<Company2AccountInfo> list = getJdbcTemplate().query(sql, params, types, getRowMapper());
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (RuntimeException e) {
			throw new PurDBException(e);
		}

		return null;
	}

	public boolean hasAuthorized(String memberId,String companyId) throws PurDBException {
		String selectSql = "select * from t_map_company_ali where accountid = ? and companyId != ? ";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(selectSql, new Object[]{memberId,companyId});
		if(list!=null && list.size()>0){
			return true;
		}
		
		return false;
	}

	public boolean deleteCompany(long enterpriseId, String companyId) throws PurDBException {
		String deleteSql = "delete from t_map_company_ali where enterpriseid = ? and companyId = ? ";
		getJdbcTemplate().update(deleteSql, new Object[] {enterpriseId, companyId}, new int[] {Types.BIGINT, Types.VARCHAR});
		
		return true;
	}
}