package com.kingdee.purchase.platform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.msg.MessageInfo;
import com.kingdee.purchase.platform.info.msg.MsgTypeEnum;
import com.kingdee.purchase.platform.util.StringUtils;

@Repository
public class MessageDao extends BaseDao<MessageInfo> implements IMessageDao {

	@Autowired
	protected MessageDao(DataSource dataSource) {
		super(dataSource, new MessageRowMapper());
	}

	@Override
	protected String getTableName() {
		return "t_data_msg";
	}

	public int insert(MessageInfo t) throws PurDBException {
		String sql = "insert into t_data_msg(gmtDate,companyId,type,enterpriseId,data,status) values (?,?,?,?,?,?)";
		String data = null;
		if(t.getData()!=null){
			JSONObject json = JSONObject.fromObject(t.getData());
			data = json.toString();
		}
		Object[] args = new Object[]{t.getGmtTimestamp(),t.getCompanyId(),t.getType().getValue(),t.getEnterpriseId(),data,t.getStatus()};
		int[] argTypes = new int[]{Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.BIGINT,Types.VARCHAR,Types.INTEGER};
		return getJdbcTemplate().update(sql, args, argTypes);
	}

	public int update(MessageInfo t) throws PurDBException {
		String sql = "update t_data_msg set status = ? where id = ?";
		Object[] args = new Object[]{t.getStatus(),t.getId()};
		int[] argTypes = new int[]{Types.INTEGER,Types.BIGINT};
		return getJdbcTemplate().update(sql, args, argTypes);
	}

	private static class MessageRowMapper implements RowMapper<MessageInfo>{
		public MessageInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			String type = rs.getString("type");
			MsgTypeEnum typeEnum = MsgTypeEnum.getMsgTypeEnum(type);			
			MessageInfo msgInfo = MessageInfo.getMsgInfoByType(typeEnum);
			if(null!=msgInfo){
				msgInfo.setCompanyId(rs.getString("companyid"));
				msgInfo.setEnterpriseId(rs.getLong("enterpriseid"));
				msgInfo.setGmtBorn(rs.getLong("gmtborn"));
				msgInfo.setStatus(rs.getInt("status"));
				msgInfo.setId(rs.getLong("id"));
				String dataString = rs.getString("data");
				if(StringUtils.isNotEmpty(dataString)){
					JSONObject json = JSONObject.fromObject(dataString);
					msgInfo.initData(json);
				}
			}
			
			return msgInfo;
		}
	}

	public List<MessageInfo> getList(long enterpriseId, int size) {
		String sql = "select * from t_data_msg where status = 0 and enterpriseId = ? limit 0," + size;
		return getJdbcTemplate().query(sql, getRowMapper(), new Object[]{enterpriseId});
	}

	public int updateStatus(long id, int status) throws PurDBException {
		String sql = "update t_data_msg set status = ? where id = ? and status = 0";
		Object[] args = new Object[]{status,id};
		int[] argTypes = new int[]{Types.INTEGER,Types.BIGINT};
		return getJdbcTemplate().update(sql, args, argTypes);
	}
}