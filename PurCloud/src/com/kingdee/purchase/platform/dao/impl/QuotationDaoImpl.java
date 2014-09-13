package com.kingdee.purchase.platform.dao.impl;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.openapi.model.ObjectCollection;
import com.kingdee.purchase.openapi.model.QuotationInfo;
import com.kingdee.purchase.openapi.model.QuotationItem;
import com.kingdee.purchase.platform.dao.IQuotationDao;
import com.kingdee.purchase.platform.dao.rowmapper.QuotationItemRowMapper;
import com.kingdee.purchase.platform.dao.rowmapper.QuotationRowMapper;
@Repository
public class QuotationDaoImpl extends JdbcDaoSupport implements IQuotationDao {
	
	@Autowired
	public QuotationDaoImpl(JdbcTemplate jdbcTemplate) {
		super();
		super.setJdbcTemplate(jdbcTemplate);
	}
	
	/**
	 * 批量保存报价单
	 */
	public int batchSaveQuotation(List<QuotationInfo> quoInfos){
		int result = 0;
		for(QuotationInfo quoInfo:quoInfos){
			result += saveQuotationInfo(quoInfo);
		}
		return result;
	}
	
	/**
	 * 保存报价单
	 */
	public int saveQuotationInfo(QuotationInfo quoInfo) {
		int result = saveQuotationHead(quoInfo);
		result = saveQuotationItem(quoInfo);
		return result;
	}
	/**
	 * 保存报价单表头
	 * @param quoInfo
	 * @return
	 */
	public int saveQuotationHead(QuotationInfo quoInfo) {
		String sql = "insert into t_bas_quotation " +
				"(id,expireDate,buyOfferId,supplierMemberId,contactName" +
				",mobile,phone,email,invoiceType,freight,totalPrice,specifications," +
				"payType,paySpecification,gmtCreate,prId) " +
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List<Object> args = new ArrayList<Object>();
		args.add(quoInfo.getId());
		args.add(quoInfo.getExpireDate());
		args.add(quoInfo.getBuyOfferId());
		args.add(quoInfo.getSupplierMemberId());
		args.add(quoInfo.getContactInfo().getContact());
		args.add(quoInfo.getContactInfo().getMobile());
		args.add(quoInfo.getContactInfo().getPhone());
		args.add(quoInfo.getContactInfo().getEmail());
		if(quoInfo.getInvoiceType()!=null){
			args.add(quoInfo.getInvoiceType().name());
		}else{
			args.add(null);
		}
		
		args.add(quoInfo.getFreight());
		args.add(quoInfo.getTotalPrice());
		args.add(quoInfo.getSpecifications());
		if(quoInfo.getPayType() != null){
			args.add(quoInfo.getPayType().name());
		}else{
			args.add(null);
		}
		args.add(quoInfo.getPaySpecification());
		args.add(quoInfo.getGmtCreate());
		args.add(quoInfo.getPrId());
				
		int[] argTypes = new int[]{
				Types.BIGINT,
				Types.DATE,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.BIGINT,
				Types.BIGINT,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.DATE,
				Types.VARCHAR
		};
		int result = getJdbcTemplate().update(sql, args.toArray(), argTypes);
		return result;
	}
	
	/**
	 * 保存报价分录
	 * @return
	 */
	private int saveQuotationItem(QuotationInfo quoInfo){
		String sql = "insert into t_bas_quotationItem " +
				"(id,parentId,subject,productId,amount,unit,price,prItemId,taxRate) " +
				" values(?,?,?,?,?,?,?,?,?)";
		long quoId = quoInfo.getId();
		List<Object[]> paramList = new ArrayList<Object[]>();
		Object[] paramObjects = null;
		for(QuotationItem item:quoInfo.getSupplyNoteItems().getItemsList()){
			paramObjects = new Object[]{
					item.getId(),quoId,item.getSubject(),item.getProductId(),item.getAmount(),
					item.getUnit(),item.getPrice(),item.getPrItemId(),item.getTaxRate()
			};
			paramList.add(paramObjects);
		}
		int[] argTypes = new int[]{
				Types.BIGINT,Types.BIGINT,Types.VARCHAR,Types.BIGINT,Types.INTEGER,
				Types.VARCHAR,Types.BIGINT,Types.VARCHAR,Types.VARCHAR
		};
		int result = this.getJdbcTemplate().batchUpdate(sql, paramList,argTypes).length;
		return result;
	}
	
	public QuotationInfo getQuotationById(long id){
		QuotationInfo quoInfo = getQuotationHeaderById(id);
		if(quoInfo == null){
			return null;
		}
		quoInfo.setSupplyNoteItems(getQuotationItemByParentIds(quoInfo.getId()));
		return quoInfo;
	}
	/**
	 * 根据根据报价单ID获取报价单头
	 * @param id
	 * @return
	 */
	private QuotationInfo getQuotationHeaderById(long id) {
		String sql = "select * from t_bas_quotation where id=?";
		Object[] args = new Object[]{id};
		int[] types = new int[] { Types.BIGINT };
		QuotationRowMapper rowMapper = new QuotationRowMapper();
		List<QuotationInfo> quoInfos = getJdbcTemplate().query(sql,args,types,rowMapper);
		if(quoInfos.isEmpty()){
			return null;
		}
		return quoInfos.get(0);
	}
	/**
	 * 根据报价单单据头Id查询单据分录
	 * @return
	 */
	private ObjectCollection<QuotationItem> getQuotationItemByParentIds(long parentId){
		ObjectCollection<QuotationItem> quoItems = new ObjectCollection<QuotationItem>();
		String sql = "select * from t_bas_quotationItem where parentId=?";
		Object[] args = new Object[]{parentId};
		int[] types = new int[]{Types.BIGINT};
		QuotationItemRowMapper mapper = new QuotationItemRowMapper();
		List<QuotationItem> items = getJdbcTemplate().query(sql, args,types,mapper);
		quoItems.setItemsList(items);
		return quoItems;
	}
	public ObjectCollection<QuotationInfo> getQuotationsByBuyOfferrrId(String buyOfferId) {
		ObjectCollection<QuotationInfo> quoInfos = new ObjectCollection<QuotationInfo>();
		List<QuotationInfo> quoList =	getQuotationHeadsByBuyOfferrrId(buyOfferId);
		quoInfos.setItemsList(quoList);
		if(quoList.isEmpty()){
			return quoInfos;
		}
		for(QuotationInfo info:quoList){
			info.setSupplyNoteItems(getQuotationItemByParentIds(info.getId()));
		}
		return quoInfos;
	}
	public List<QuotationInfo> getQuotationHeadsByBuyOfferrrId(String buyOfferId) {
		String sql = "select * from t_bas_quotation where buyOfferId=?";
		Object[] args = new Object[]{buyOfferId}; 
		int[] types = new int[]{Types.VARCHAR};
		QuotationRowMapper rowMapper = new QuotationRowMapper();
		List<QuotationInfo> quoInfos = getJdbcTemplate().query(sql,args,types,rowMapper);
		return quoInfos;
	}
}
