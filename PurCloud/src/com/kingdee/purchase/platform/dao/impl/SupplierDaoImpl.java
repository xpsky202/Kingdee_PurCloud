package com.kingdee.purchase.platform.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.kingdee.purchase.openapi.model.SupplierInfo;
import com.kingdee.purchase.platform.dao.ISupplierDao;
import com.kingdee.purchase.platform.dao.rowmapper.SupplierRowMapper;
@Repository
public class SupplierDaoImpl  extends JdbcDaoSupport implements ISupplierDao{
	
	@Autowired
	public SupplierDaoImpl(JdbcTemplate jdbcTemplate){
		super();
		super.setJdbcTemplate(jdbcTemplate);
	}
	
	/**
	 * 批量保存供应商
	 */
	public int batchSaveSupplier(List<SupplierInfo> suppliers) {
		int result = 0; 
		for(SupplierInfo info:suppliers){
			result += saveSupplier(info);
		}
		return result;
	}
	
	/**
	 * 保存供应商信息
	 */
	public int saveSupplier(SupplierInfo supplierInfo) {
		String sql = "insert into t_bas_supplier (" +
				"id,destId,companyName,principal,companyAddress,registrationId,enterpriseType," +
				"dateOfEstablishment,registeredCapital,businessTerm,businessScope,bank,bankAccount," +
				"companySummary,contactName,mobile,gender,phone,fax,zipCode,contactAddress,mainIndustries," +
				"businessAddress) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(supplierInfo.getId());
		paramsList.add(supplierInfo.getDestId());
		if(supplierInfo.getCompanyInfo() != null){
			paramsList.add(supplierInfo.getCompanyInfo().getName());
			paramsList.add(supplierInfo.getCompanyInfo().getPrincipal());
			paramsList.add(supplierInfo.getCompanyInfo().getCompanyAddress());
			paramsList.add(supplierInfo.getCompanyInfo().getRegistrationId());
			paramsList.add(supplierInfo.getCompanyInfo().getEnterpriseType());
			paramsList.add(supplierInfo.getCompanyInfo().getDateOfEstablishment());
			paramsList.add(supplierInfo.getCompanyInfo().getRegisteredCapital());
			paramsList.add(supplierInfo.getCompanyInfo().getBusinessTerm());
			paramsList.add(supplierInfo.getCompanyInfo().getBusinessScope());
			paramsList.add(supplierInfo.getCompanyInfo().getBank());
			paramsList.add(supplierInfo.getCompanyInfo().getBankAccount());
			paramsList.add(supplierInfo.getCompanyInfo().getCompanySummary());
		}else{
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
		}
		
		if(supplierInfo.getContactInfo() != null){
			paramsList.add(supplierInfo.getContactInfo().getName());
			paramsList.add(supplierInfo.getContactInfo().getMobile());
			paramsList.add(supplierInfo.getContactInfo().getGender());
			paramsList.add(supplierInfo.getContactInfo().getPhone());
			paramsList.add(supplierInfo.getContactInfo().getFax());
			paramsList.add(supplierInfo.getContactInfo().getZipCode());
			paramsList.add(supplierInfo.getContactInfo().getContactAddress());
		}else{
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
			paramsList.add(null);
		}
		
		if(supplierInfo.getBusinessInfo() != null){
			paramsList.add(supplierInfo.getBusinessInfo().getMainIndustries());
			paramsList.add(supplierInfo.getBusinessInfo().getBusinessAddress());
		}else{
			paramsList.add(null);
			paramsList.add(null);
		}
		
		int[] types = new int[23];
		for(int i = 0; i < types.length; i++){
			types[i] = Types.VARCHAR;
		}
		
		int result = getJdbcTemplate().update(sql,paramsList.toArray(),types);
		return result;
	}
	
	/**
	 * 根据DestID获取供应商信息
	 */
	public SupplierInfo getSupplierInfoByDestId(String destId) {
		String sql = "select * from t_bas_supplier where destId=?";
		Object[] args = new Object[]{destId};
		int[] types = new int[]{Types.VARCHAR};
		SupplierRowMapper mapper = new SupplierRowMapper();
		
		List<SupplierInfo> suppliers = getJdbcTemplate().query(sql,args,types,mapper);
		if(suppliers.isEmpty()){
			return null;
		}
		return suppliers.get(0);
	}
}
