package com.kingdee.purchase.platform.service.alibaba;

import java.util.Date;
import java.util.List;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.KeyValue;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;

public interface ICompany2AccountServcie {

	/****
	 * 根据企业号和公司id，获取对应的公司--1688映射信息
	 * @param enterpriseId
	 * @param companyId
	 * @return				企业id、公司id、主账号id、token
	 * @throws PurBizException
	 */
	public Company2AccountInfo getCompany2AccountInfo(long enterpriseId,String companyId) throws BaseException;
	
	/**
	 * 获取所有企业和公司--1688映射信息
	 * @return
	 * @throws BaseException
	 */
	public List<Company2AccountInfo> getAllCompany2AccountList() throws BaseException;
	
	/***
	 * 通过主账号id，获取对应的公司信息
	 * @param accountId
	 * @return				企业id、公司id、主账号id、token
	 * @throws BaseException
	 */
	public Company2AccountInfo getCompany2AccountInfo(String accountId) throws BaseException;
	
	/**
	 * 获取某个企业的所有映射公司列表
	 * @param enterpriseId
	 * @return
	 * @throws PurBizException
	 */
	public List<Company2AccountInfo> getCompanyList(long enterpriseId) throws BaseException;
	
	/***
	 * 更新财务组织、主账号、token的映射
	 * @param enterpriseId			企业id
	 * @param companysList			id\accountid\token
	 * @return						更新成功的记录数
	 * @throws PurBizException 
	 */
	public int updateCompany2AccountMapping(long enterpriseId,List<Company2AccountInfo> companysList) throws BaseException;

	/***
	 * 更新财务组织、主账号、token的映射关系
	 * @param enterpriseId
	 * @param companyId
	 * @param memberId
	 * @param token
	 * @param expiredDate
	 * @return
	 * @throws BaseException
	 */
	public int updateCompany2AccountMapping(long enterpriseId,String companyId,String memberId,String token,Date expiredDate) throws BaseException;
	
	/***
	 * 保存财务组织<id\name>
	 * 
	 * @param enterpriseId			企业id
	 * @param companysList			公司列表		<key-valu> --> <财务组织id，财务组织名称>
	 * @return 						插入成功的数量
	 * @throws PurBizException 
	 */
	public int saveCompanyInfo(long enterpriseId,List<KeyValue> companysList) throws BaseException;
	
	/***
	 * 是否已经做过授权
	 * @param companyId
	 * @param memberId
	 * @return
	 * @throws BaseException
	 */
	public boolean hasAuthorized(String companyId, String memberId) throws BaseException;
	
	/**
	 * 删除组织
	 * @param enterpriseId
	 * @param companyId
	 * @return
	 * @throws BaseException
	 */
	public boolean deleteCompanyInfo(long enterpriseId, String companyId) throws BaseException;
}