package com.kingdee.purchase.platform.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.platform.dao.BaseDao;
import com.kingdee.purchase.platform.dao.IAPIRegisterDao;
import com.kingdee.purchase.platform.dao.rowmapper.APIBaseInfoRowMapper;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.api.ApiBaseInfo;
import com.kingdee.purchase.platform.info.api.ApiErrorCodeInfo;
import com.kingdee.purchase.platform.info.api.ApiInputParamInfo;
import com.kingdee.purchase.platform.info.api.ApiOutputParamInfo;
import com.kingdee.purchase.platform.util.StringUtils;

@Repository
public class APIRegisterDaoImpl extends BaseDao<ApiBaseInfo> implements IAPIRegisterDao {

	@Autowired
	public APIRegisterDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate,new APIBaseInfoRowMapper());
	}
	
	protected APIRegisterDaoImpl(DataSource dataSource,
			RowMapper<ApiBaseInfo> rowMapper) {
		super(dataSource, rowMapper);
	}
	
	@Override
	protected String getTableName() {
		return "t_pm_apibase";
	}

	/**
	 * list界面查询列表
	 */
	public List<ApiBaseInfo> getOpenApiInfoList() throws PurDBException {
		final String sql = "SELECT * FROM t_pm_apibase ";
		
		List<ApiBaseInfo> list = getJdbcTemplate().query(sql, getRowMapper());
		if(null==list||list.size()==0){
			return null;
		}
		return list;
	}
	
	/**
	 * edit界面注册
	 */
	public void registerService(ApiBaseInfo apiInfo) throws PurDBException{
		int id = isExistApi(apiInfo);
		if(id==-1){
			insert(apiInfo);
		}else{
			apiInfo.setId(id);
			deleteParams(id);
			update(apiInfo);
		}
	}
	
	
	public void publishAPIDoc() {
		
	}

	/**
	 * list界面点击删除
	 * 
	 */
	public int delete(long id) throws PurDBException {
		final String sql = "DELETE FROM t_pm_apierrorcode WHERE parentid = " + id;
		getJdbcTemplate().execute(sql);

		
		final String sql1 = "DELETE FROM t_pm_apiinput WHERE parentid = " + id;
		getJdbcTemplate().execute(sql1);

		
		final String sql2 = "DELETE FROM t_pm_apioutput WHERE parentid = " + id;
		getJdbcTemplate().execute(sql2);

		
		
		final String sql3 = "DELETE FROM t_pm_apibase WHERE id = " + id;
		getJdbcTemplate().execute(sql3);
		return 0;
	}
	
	
	/**
	 * edit注册，重新提交时
	 * 
	 */
	public int deleteParams(long id) throws PurDBException {
		final String sql = "DELETE FROM t_pm_apierrorcode WHERE parentid = " + id;
		getJdbcTemplate().execute(sql);

		
		final String sql1 = "DELETE FROM t_pm_apiinput WHERE parentid = " + id;
		getJdbcTemplate().execute(sql1);

		
		final String sql2 = "DELETE FROM t_pm_apioutput WHERE parentid = " + id;
		getJdbcTemplate().execute(sql2);

		return 0;
	}

	/**
	 * list点击编辑查看
	 */
	public ApiBaseInfo get(long id) throws PurDBException {
		String sql = "SELECT * FROM t_pm_apibase WHERE id = ?";
		List<ApiBaseInfo> list = getJdbcTemplate().query(sql, new Object[]{id},getRowMapper());
		if(null==list||list.size()==0){
			return null;
		}
		
		ApiBaseInfo baseInfo = list.get(0);
		List<ApiInputParamInfo> inputList = queryApiInputParamListByParentId(id);
		baseInfo.getInputParamList().addAll(inputList);
		
		List<ApiOutputParamInfo> outputList = queryApiOutputParamListByParentId(id);
		baseInfo.getOutputParamList().addAll(outputList);
		
		
		List<ApiErrorCodeInfo> errorCoList = queryApiErrorCodeListByParentId(id);
		baseInfo.getErrorCodeList().addAll(errorCoList);
		
		return baseInfo;
	}
	
	
	/**
	 * 判断是否存在
	 * @param info
	 * @return
	 */
	public int isExistApi(ApiBaseInfo info) {
		
		String sql = "select id from t_pm_apibase where subSystem = ? and apiName = ? and version = ? ";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,new Object[]{info.getSubSystem(),info.getApiName(),info.getVersion()});
		if (rs!=null&&rs.next()){
			return rs.getInt("id");
		}
		return -1;
	}
	

	public int insert(ApiBaseInfo info) throws PurDBException {
		
		insertBaseInfo(info);
		obtainInsertId(info);
		
		//父ID
		long parentid = info.getId();
		
		if (info.getInputParamList().size()>0){
			insertInputParamInfo(info, parentid);
		}
		
		if (info.getOutputParamList().size()>0){
			insertOutputParamInfo(info, parentid);
		}
		
		if (info.getErrorCodeList().size()>0){
			insertErrorInfo(info, parentid);
		}
		
		return 0;
	}

	private void insertBaseInfo(ApiBaseInfo info) {
		String sql = "insert into t_pm_apibase(subSystem,subSystemAlias,apiName,apiAlias,version,remark,state) values(?,?,?,?,?,?,?)";
		Object[] args = new Object[]{
									info.getSubSystem(),
									info.getSubSystemAlias(),
									info.getApiName(),
									info.getApiAlias(),
									info.getVersion(),
									info.getRemark(),
									info.getState()};
		int[] argTypes = new int[]{
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.BIGINT,
					Types.VARCHAR,
					Types.VARCHAR};
		
		getJdbcTemplate().update(sql, args, argTypes);
	}
	
	
	private void updateBaseInfo(ApiBaseInfo info) {
		String sql = "update t_pm_apibase set subSystemAlias =? ,apiAlias = ? ,remark = ? ,state = ? where id = ? ";
		Object[] args = new Object[]{
									info.getSubSystemAlias(),
									info.getApiAlias(),
									info.getRemark(),
									info.getState(),
									info.getId()};
		int[] argTypes = new int[]{
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.BIGINT};
		
		getJdbcTemplate().update(sql, args, argTypes);
	}

	/**
	 * 新增api错误码
	 * @param info
	 * @param parentid
	 */
	private void insertErrorInfo(ApiBaseInfo info, long parentid) {
		
		String sql = "insert into t_pm_apierrorcode(parentid,code,description,dealSolution) values(?,?,?,?)";
		
		List<Object[]> paramsList = new ArrayList<Object[]>();
		Object[] objects ;
		for(ApiErrorCodeInfo errorInfo:info.getErrorCodeList()){
			objects = new Object[]{parentid,errorInfo.getCode(),
								   errorInfo.getDescription(),errorInfo.getDealSolution()};
			paramsList.add(objects);
		}
		
		int[] argTypes = new int[]{
					Types.BIGINT,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR};
		
		getJdbcTemplate().batchUpdate(sql, paramsList, argTypes);
	}

	/**
	 * 新增api处参
	 * @param info
	 * @param parentid
	 */
	private void insertOutputParamInfo(ApiBaseInfo info, long parentid) {

		String sql = "insert into t_pm_apioutput(parentid,field,type,description,isNull,example) values(?,?,?,?,?,?)";
		
		List<Object[]> paramsList = new ArrayList<Object[]>();
		Object[] objects ;
		for(ApiOutputParamInfo outputInfo:info.getOutputParamList()){
			objects = new Object[]{
									parentid,
									outputInfo.getField(),
									outputInfo.getType(),
									outputInfo.getDescription(),
									outputInfo.isIsNull(),
									outputInfo.getExample()};
			paramsList.add(objects);
		}

		int[] argTypes = new int[]{
					Types.BIGINT,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.BOOLEAN,
					Types.VARCHAR,
					Types.BOOLEAN,
					Types.VARCHAR};
		
		getJdbcTemplate().batchUpdate(sql, paramsList, argTypes);
	}

	/**
	 * 新增api入参
	 * @param info
	 * @param parentid
	 */
	private void insertInputParamInfo(ApiBaseInfo info, long parentid) {
		String sql = "insert into t_pm_apiinput(parentid,field,type,description,isNull,example,isSysParam,defaultValue) values(?,?,?,?,?,?,?,?)";
		
		List<Object[]> paramsList = new ArrayList<Object[]>();
		Object[] objects ;
		for(ApiInputParamInfo inputInfo:info.getInputParamList()){
			objects = new Object[]{
									parentid,
									inputInfo.getField(),
									inputInfo.getType(),
									inputInfo.getDescription(),
									inputInfo.isIsNull(),
									inputInfo.getExample(),
									inputInfo.isIsSysParam(),
									inputInfo.getDefaultValue()};
			paramsList.add(objects);
		}

		int[] argTypes = new int[]{
					Types.BIGINT,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.BOOLEAN,
					Types.VARCHAR,
					Types.BOOLEAN,
					Types.VARCHAR};
		
		getJdbcTemplate().batchUpdate(sql, paramsList, argTypes);
	}

	/**
	 * 更新注册信息，表头只更新subSystemAlias  ,apiAlias  ,remark  ,state 
	 * 参数进行先删除重新插入的方式
	 */
	public int update(ApiBaseInfo info) throws PurDBException {
		
		updateBaseInfo(info);
		//父ID
		long parentid = info.getId();
		
		if (info.getInputParamList().size()>0){
			insertInputParamInfo(info, parentid);
		}
		
		if (info.getOutputParamList().size()>0){
			insertOutputParamInfo(info, parentid);
		}
		
		if (info.getErrorCodeList().size()>0){
			insertErrorInfo(info, parentid);
		}
		return 0;
	}

	/**
	 * list条件查询
	 */
	public List<ApiBaseInfo> query(String condition) throws PurDBException {
		String sqlString = "select * from t_pm_apibase ";
		if(!StringUtils.isEmpty(condition)){
			sqlString += " where subSystem like ?  or apiName like ? or apiAlias like ? ";
			condition = "%" + condition.trim() + "%";
			Object[] paramsObjects = new Object[]{condition,condition,condition};
			return getJdbcTemplate().query(sqlString, paramsObjects, getRowMapper());
		}else {
			return getJdbcTemplate().query(sqlString, getRowMapper());
		}
	}
	
	public List<ApiErrorCodeInfo> queryApiErrorCodeListByParentId(long baseID) throws PurDBException {
		String sql = "select * from t_pm_apierrorcode where parentid = " + baseID;
		return getJdbcTemplate().query(sql,new RowMapper<ApiErrorCodeInfo>(){
			public ApiErrorCodeInfo mapRow(ResultSet rs, int rowNum)throws SQLException {
				ApiErrorCodeInfo info = new ApiErrorCodeInfo();
				info.setCode(rs.getString("code"));
				info.setDealSolution(rs.getString("dealSolution"));
				info.setDescription(rs.getString("description"));
				info.setParentid(rs.getInt("parentid"));
				
				return info;
			}			
		});
	}

	public List<ApiInputParamInfo> queryApiInputParamListByParentId(long baseID)	throws PurDBException {
		String sql = "select * from t_pm_apiinput where parentid = " + baseID;
		return getJdbcTemplate().query(sql,new RowMapper<ApiInputParamInfo>(){
			public ApiInputParamInfo mapRow(ResultSet rs, int rowNum)throws SQLException {
				ApiInputParamInfo info = new ApiInputParamInfo();
				info.setSeq(rs.getInt("seq"));
				info.setField(rs.getString("field"));
				info.setType(rs.getString("type"));
				info.setDescription(rs.getString("description"));
				info.setParentid(rs.getInt("parentid"));
				info.setIsNull(rs.getBoolean("isNull"));
				info.setIsSysParam(rs.getBoolean("isSysParam"));
				info.setExample(rs.getString("example"));
				info.setDefaultValue(rs.getString("defaultValue"));
				
				return info;
			}			
		});
	}

	public List<ApiOutputParamInfo> queryApiOutputParamListByParentId(long baseID)throws PurDBException {
		String sql = "select * from t_pm_apioutput where parentid = " + baseID;
		return getJdbcTemplate().query(sql,new RowMapper<ApiOutputParamInfo>(){
			public ApiOutputParamInfo mapRow(ResultSet rs, int rowNum)throws SQLException {
				ApiOutputParamInfo info = new ApiOutputParamInfo();
				info.setSeq(rs.getInt("seq"));
				info.setField(rs.getString("field"));
				info.setType(rs.getString("type"));
				info.setDescription(rs.getString("description"));
				info.setParentid(rs.getInt("parentid"));
				info.setIsNull(rs.getBoolean("isNull"));
				info.setExample(rs.getString("example"));
				
				return info;
			}			
		});
	}

	public List<ApiErrorCodeInfo> getApiErrorCodeList() throws PurDBException {
		String sql = "select * from t_pm_apierrorcode";
		return getJdbcTemplate().query(sql,new RowMapper<ApiErrorCodeInfo>(){
			public ApiErrorCodeInfo mapRow(ResultSet rs, int rowNum)throws SQLException {
				ApiErrorCodeInfo info = new ApiErrorCodeInfo();
				info.setCode(rs.getString("code"));
				info.setDealSolution(rs.getString("dealSolution"));
				info.setDescription(rs.getString("description"));
				info.setParentid(rs.getInt("parentid"));
				
				return info;
			}			
		});
	}

	public List<ApiInputParamInfo> getApiInputParamList()	throws PurDBException {
		String sql = "select * from t_pm_apiinput";
		return getJdbcTemplate().query(sql,new RowMapper<ApiInputParamInfo>(){
			public ApiInputParamInfo mapRow(ResultSet rs, int rowNum)throws SQLException {
				ApiInputParamInfo info = new ApiInputParamInfo();
				info.setSeq(rs.getInt("seq"));
				info.setField(rs.getString("field"));
				info.setType(rs.getString("type"));
				info.setDescription(rs.getString("description"));
				info.setParentid(rs.getInt("parentid"));
				info.setIsSysParam(rs.getBoolean("isSysParam"));
				info.setIsNull(rs.getBoolean("isNull"));
				info.setExample(rs.getString("example"));
				info.setDefaultValue(rs.getString("defaultValue"));
				
				return info;
			}			
		});
	}

	public List<ApiOutputParamInfo> getApiOutputParamList()throws PurDBException {
		String sql = "select * from t_pm_apioutput";
		return getJdbcTemplate().query(sql,new RowMapper<ApiOutputParamInfo>(){
			public ApiOutputParamInfo mapRow(ResultSet rs, int rowNum)throws SQLException {
				ApiOutputParamInfo info = new ApiOutputParamInfo();
				info.setSeq(rs.getInt("seq"));
				info.setField(rs.getString("field"));
				info.setType(rs.getString("type"));
				info.setDescription(rs.getString("description"));
				info.setParentid(rs.getInt("parentid"));
				info.setIsNull(rs.getBoolean("isNull"));
				info.setExample(rs.getString("example"));
				
				return info;
			}			
		});
	}
}