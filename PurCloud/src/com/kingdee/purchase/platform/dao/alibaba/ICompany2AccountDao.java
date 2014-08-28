package com.kingdee.purchase.platform.dao.alibaba;

import java.util.Date;
import java.util.List;

import com.kingdee.purchase.platform.dao.IBaseDao;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.KeyValue;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;

public interface ICompany2AccountDao extends IBaseDao<Company2AccountInfo> {

	public Company2AccountInfo getCompany2AccountInfo(long enterpriseid,String companyid) throws PurDBException;
	
	public Company2AccountInfo getCompany2AccountInfo(String accountId) throws PurDBException;
	
	public List<Company2AccountInfo> getCompanyList(long enterpriseid) throws PurDBException;
	
	public int saveCompanyInfo(long enterpriseId, List<KeyValue> companysList) throws PurDBException;
	
	public int updateCompany2AccountMapping(long enterpriseId, List<Company2AccountInfo> companysList) throws PurDBException;
	
	public int updateCompany2AccountMapping(long enterpriseId,	String companyId, String memberId, String token,Date expiredDate) throws PurDBException;
	
	public List<Company2AccountInfo> getAllCompany2AccountList() throws PurDBException;
	
	/***
	 * 此主账号是否已经做过授权
	 * @param memberId				主账号
	 * @param companyId				公司
	 * @return
	 * @throws PurDBException
	 */
	public boolean hasAuthorized(String memberId,String companyId) throws PurDBException;
	
	public boolean deleteCompany(long enterpriseId, String companyId) throws PurDBException;
}