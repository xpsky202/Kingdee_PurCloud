package com.kingdee.purchase.platform.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.BaseInfo;

/***
 * 
 * @author RD_jiangkun_zhu
 *
 * @param <T>			对象类型
 * @param <R>			对象映射类型
 */
@Repository
public abstract class BaseDao<T extends BaseInfo> extends JdbcDaoSupport implements IBaseDao<T>{
	
	protected BaseDao(JdbcTemplate jdbcTemplate,RowMapper<T> rowMapper){
		super();
		setJdbcTemplate(jdbcTemplate);
		this.rowMapper = rowMapper;
	}
	
	protected RowMapper<T> getRowMapper(){
		return this.rowMapper;
	}
	
	protected BaseDao(DataSource dataSource,RowMapper<T> rowMapper){
		super();
		setDataSource(dataSource);
		this.rowMapper = rowMapper;
	}
	
	private RowMapper<T> rowMapper;
	
	public List<T> query(String sql,Object[] params) throws PurDBException {
		return getJdbcTemplate().query(sql, params, rowMapper);
	}
	
	public T get(long id) throws PurDBException {
		String sql = "select * from " + getTableName() + " where id = ?";
		try{
			return getJdbcTemplate().queryForObject(sql, rowMapper, new Object[]{id});
		}catch(RuntimeException e){
			logger.error(e.getMessage(),e);
			throw new PurDBException("获取对象失败，指定的ID找不到数据。(table:"+getTableName()+";id:"+id+")");
		}
	}
	
	public int delete(long id) throws PurDBException {
		String sql = "delete from " + getTableName() + " where id = ?";
		return getJdbcTemplate().update(sql, new Object[]{id});
	}
	
	protected abstract String getTableName();
	
	/***
	 * 获取插入后的id
	 * @param t
	 */
	protected void obtainInsertId(T t){
		String sql = "select last_insert_id()";
		long id = getJdbcTemplate().queryForObject(sql, Long.class);
		t.setId(id);		
	}
}