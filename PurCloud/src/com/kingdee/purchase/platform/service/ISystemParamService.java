package com.kingdee.purchase.platform.service;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.info.UsedStatus;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;

/***
 * 系统参数服务
 * @author RD_jiangkun_zhu
 *
 */
public interface ISystemParamService {

	/***
	 * 获取云平台上指定企业的状态
	 * 
	 * @param enterpriseId			企业ID
	 * @return				三种状态：未注册、启用、禁用
	 */
	public UsedStatus getEnterpriseStatus(long enterpriseId) throws BaseException;
	
	/****
	 * 查询B2B平台上的主账号ID
	 * 
	 * @param enterpriseId			企业ID
	 * @param companyId				财务组织ID （ERP系统中的公司[财务组织]id）
	 * 
	 * @return		如果无映射，则返回null
	 */
	public String getB2BAccount(long enterpriseId,String companyId) throws BaseException;
	
	/****
	 * 查询B2B平台上的访问令牌
	 * 
	 * @param enterpriseId			企业ID
	 * @param companyId				财务组织ID （ERP系统中的公司[财务组织]id）
	 * 
	 * @return		如果无映射，则返回null
	 */
	public String getB2BToken(long enterpriseId,String companyId) throws BaseException;
	
	/****
	 * 查询B2B平台上的子账号ID
	 * 
	 * @param enterpriseId			企业ID
	 * @param userId				用户ID （ERP系统中的用户id）
	 * @return		如果无映射，则返回null
	 */
	public String getB2BSubAccount(long enterpriseId, String companyId, String userId) throws BaseException;
	
	/***
	 * 插入用户id到子账号的映射信息
	 * 
	 * @param enterpriseId			企业id
	 * @param userId				用户id	（ERP系统中的用户id）
	 * @param subAccountId			子账号	（B2B平台中的子账号）
	 * @return
	 */
	public boolean saveUser2SubAccountInfo(long enterpriseId, String companyId, String userId, String subAccountId)
			throws BaseException;
	
	/***
	 * 获取ERP系统中的用户id
	 * @param subAccountId
	 * @return
	 */
	public String getERPUserId(String subAccountId);
	
	/***
	 * 通过memberid，获取对应的公司id和企业号
	 * @param accountId
	 * @return
	 */
	public Company2AccountInfo getCompanyInfoByAccountId(String accountId);
	
	/***
	 * 根据企业ID，获取对应的企业消息url
	 * @param enterpriseidId
	 * @return
	 */
	public EnterpriseInfo getEnterpriseInfo(long enterpriseidId);
}
