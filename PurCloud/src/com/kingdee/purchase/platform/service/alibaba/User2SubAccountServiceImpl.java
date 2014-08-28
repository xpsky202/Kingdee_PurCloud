package com.kingdee.purchase.platform.service.alibaba;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.dao.alibaba.IUser2SubAccountDao;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.alibaba.User2SubAccountInfo;
import com.kingdee.purchase.platform.util.StringUtils;

@Service
public class User2SubAccountServiceImpl implements IUser2SubAccountService {

	@Resource
	private IUser2SubAccountDao user2SubAccountDao;

	public User2SubAccountInfo getUser2SubAccountInfo(long enterpriseId, String companyId, String userId)
			throws BaseException {
		if (enterpriseId <= 0) {
			throw new PurBizException("企业ID不能为空");
		}
		if (StringUtils.isEmpty(userId)) {
			throw new PurBizException("用户ID不能为空");
		}

		return user2SubAccountDao.get(enterpriseId, companyId, userId);
	}

	public int saveUser2SubAccount(long enterpriseId, String companyId, String userId, String subAccountId)
			throws BaseException {
		if (enterpriseId <= 0) {
			throw new PurBizException("企业ID不能为空");
		}
		if (StringUtils.isEmpty(companyId)) {
			throw new PurBizException("组织ID不能为空");
		}
		if (StringUtils.isEmpty(userId)) {
			throw new PurBizException("用户ID不能为空");
		}
		if (StringUtils.isEmpty(subAccountId)) {
			throw new PurBizException("子账号不能为空");
		}

		User2SubAccountInfo old = user2SubAccountDao.get(enterpriseId, companyId, userId);
		if (null == old) {
			User2SubAccountInfo info = new User2SubAccountInfo();
			info.setEnterpriseId(enterpriseId);
			info.setCompanyId(companyId);
			info.setSubAccountId(subAccountId);
			info.setUserId(userId);
			return user2SubAccountDao.insert(info);
		} else {
			User2SubAccountInfo info = new User2SubAccountInfo();
			info.setEnterpriseId(enterpriseId);
			info.setCompanyId(companyId);
			info.setSubAccountId(subAccountId);
			info.setUserId(userId);
			info.setId(old.getId());
			return user2SubAccountDao.update(info);
		}
	}

	public User2SubAccountInfo getUser2SubAccountInfo(String subAccountId) throws BaseException {
		if (StringUtils.isEmpty(subAccountId)) {
			throw new PurBizException("子账号不能为空");
		}

		return user2SubAccountDao.get(subAccountId);
	}
}
