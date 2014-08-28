package com.kingdee.purchase.platform.service.alibaba;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.info.UsedStatus;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;
import com.kingdee.purchase.platform.info.alibaba.User2SubAccountInfo;
import com.kingdee.purchase.platform.service.IEnterpriseService;
import com.kingdee.purchase.platform.service.ISystemParamService;

@Service("systemParamService4Alibaba")
public class SystemParamService4Alibaba implements ISystemParamService{
	
	private final static Logger logger = LogManager.getLogger(SystemParamService4Alibaba.class);
	
	@Autowired
	private IUser2SubAccountService iUser2SubAccountService;
	
	@Autowired
	private ICompany2AccountServcie iCompany2AccountServcie;
	
	@Autowired
	private IEnterpriseService iEnterpriseService;

	public String getB2BAccount(long enterpriseId, String companyId) throws BaseException {
		try {
			Company2AccountInfo info = iCompany2AccountServcie.getCompany2AccountInfo(enterpriseId, companyId);
			if(null!=info){
				return info.getAccountId();
			}
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

	public String getB2BSubAccount(long enterpriseId, String companyId, String userId) throws BaseException {
		try {
			User2SubAccountInfo info = iUser2SubAccountService.getUser2SubAccountInfo(enterpriseId, companyId, userId);
			if(null!=info){
				return info.getSubAccountId();
			}
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return null;
	}

	public UsedStatus getEnterpriseStatus(long enterpriseId) throws BaseException {
		try {
			EnterpriseInfo info = iEnterpriseService.getByEnterpriseId(enterpriseId);
			if(null!=info){
				return info.getUsedstatus();
			}
		} catch (PurBizException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return UsedStatus.UNREGISTERED;
	}

	public boolean saveUser2SubAccountInfo(long enterpriseId, String companyId, String userId, String subAccountId)
			throws BaseException {
		try {
			int result = iUser2SubAccountService.saveUser2SubAccount(enterpriseId, companyId, userId, subAccountId);
			return result > 0;
		} catch (PurBizException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public String getB2BToken(long enterpriseId, String companyId) throws BaseException {
		try {
			Company2AccountInfo info = iCompany2AccountServcie.getCompany2AccountInfo(enterpriseId, companyId);
			if(null!=info){
				return info.getToken();
			}
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

	public Company2AccountInfo getCompanyInfoByAccountId(String accountId) {
		try {
			return iCompany2AccountServcie.getCompany2AccountInfo(accountId);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

	public EnterpriseInfo getEnterpriseInfo(long enterpriseidId) {
		try {
			return iEnterpriseService.get(enterpriseidId);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

	public String getERPUserId(String subAccountId) {
		try {
			User2SubAccountInfo info = iUser2SubAccountService.getUser2SubAccountInfo(subAccountId);
			return info.getUserId();
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
}