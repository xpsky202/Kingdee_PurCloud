package com.kingdee.purchase.platform.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.platform.dao.BaseDao;
import com.kingdee.purchase.platform.dao.IEnterpriseDao;
import com.kingdee.purchase.platform.dao.rowmapper.EnterpriceRowMapper;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.util.StringUtils;

@Repository
public class EnterpriseDaoImpl extends BaseDao<EnterpriseInfo> implements IEnterpriseDao{
	
	@Autowired
	public EnterpriseDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate,new EnterpriceRowMapper());
	}

	public int insert(EnterpriseInfo info) {
		String sqlString = "insert into t_pm_enterprise(username,createtime,updatetime,serviceurl,accountid,erptype,enterpriseid) values(?,?,?,?,?,?,?)";
		Object[] args = new Object[]{
									info.getName(),
									info.getCreateDate(),
									info.getUpdateDate(),
									info.getServiceUrl(),
									info.getAccountId(),
									info.getErpType()==null?1:info.getErpType().getValue(),
									info.getEnterpriseid()
								};
		int[] argTypes = new int[]{
					Types.VARCHAR,
					Types.DATE,
					Types.DATE,
					Types.VARCHAR,
					Types.BIGINT,
					Types.INTEGER,
					Types.BIGINT
				};
		
		int result = getJdbcTemplate().update(sqlString, args, argTypes);
		
		obtainInsertId(info);
		return result;
	}

	public int update(EnterpriseInfo info) {
		String sqlString = "update t_pm_enterprise set username=?,updatetime=?,serviceurl=? where id=?";
		Object[] args = new Object[] { info.getName(), info.getUpdateDate(),info.getServiceUrl(), info.getId() };
		int[] argTypes = new int[] { Types.VARCHAR,Types.DATE,Types.VARCHAR, Types.BIGINT };
		return getJdbcTemplate().update(sqlString, args,argTypes);
	}

	@Override
	protected String getTableName() {
		return "t_pm_enterprise";
	}

	public List<EnterpriseInfo> query() {
		String sql = "select * from t_pm_enterprise";
		return getJdbcTemplate().query(sql, getRowMapper());
	}

	public List<EnterpriseInfo> query(String condition) {
		String sqlString = "select * from t_pm_enterprise ";
		if(!StringUtils.isEmpty(condition)){
			sqlString += " where username like ? ";
			condition = "%" + condition.trim() + "%";
			Object[] paramsObjects = new Object[]{condition};
			return getJdbcTemplate().query(sqlString, paramsObjects, getRowMapper());
		}else {
			return getJdbcTemplate().query(sqlString, getRowMapper());
		}
	}

	public EnterpriseInfo getByAccountId(long accountId) {
		String sql = "select * from t_pm_enterprise where accountid = ?";
		List<EnterpriseInfo> list = getJdbcTemplate().query(sql, new Object[]{accountId},getRowMapper());
		if(null==list||list.size()==0){
			return null;
		}
		
		return list.get(0);
	}

	public boolean isExistEnterpriseId(long enterpriseId) {
		String sql = "select 1 from t_pm_enterprise where enterpriseid = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,new Object[]{enterpriseId});
		return rs!=null&&rs.next();
	}

	public boolean isExistName(long id, String name) {
		String sql = "select 1 from t_pm_enterprise where id <> ? and username = ?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,new Object[]{id,name});
		return rs!=null&&rs.next();
	}

	public EnterpriseInfo getByEnterpriseId(long enterpriseId)throws PurDBException {
		String sql = "select * from t_pm_enterprise where enterpriseid = ?";
		try{
			List<EnterpriseInfo> list = getJdbcTemplate().query(sql, new Object[]{enterpriseId},getRowMapper());
			if(null==list||list.size()==0){
				return null;
			}		
			return list.get(0);
		}catch(RuntimeException e){
			throw new PurDBException("获取企业资料资料失败，请确认企业ID是否正确",e);
		}
	}
}