package com.kingdee.purchase.platform.service.alibaba;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.dao.alibaba.ICompany2AccountDao;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.KeyValue;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;
import com.kingdee.purchase.platform.util.StringUtils;

@Service
public class Company2AccountServiceImpl implements ICompany2AccountServcie {

	@Autowired
	private ICompany2AccountDao iCompany2AccountDao;
	
	public Company2AccountInfo getCompany2AccountInfo(long enterpriseId,String companyId) throws BaseException {
		if(enterpriseId<=0){
			throw new PurBizException("企业ID不能为空");
		}
		
		if(StringUtils.isEmpty(companyId)){
			throw new PurBizException("公司ID不能为空");
		}
		
		return iCompany2AccountDao.getCompany2AccountInfo(enterpriseId, companyId);
	}

	public List<Company2AccountInfo> getCompanyList(long enterpriseId)	throws BaseException {
		return iCompany2AccountDao.getCompanyList(enterpriseId);
	}

	public int saveCompanyInfo(long enterpriseId, List<KeyValue> companysList) throws BaseException{
		if(enterpriseId<=0){
			throw new PurBizException("企业ID不能为空");
		}
		
		if(companysList==null || companysList.size()==0){
			throw new PurBizException("上传的公司信息为空");
		}
		
		return iCompany2AccountDao.saveCompanyInfo(enterpriseId, companysList);
	}

	public int updateCompany2AccountMapping(long enterpriseId,List<Company2AccountInfo> companysList) throws BaseException {
		if(enterpriseId<=0){
			throw new PurBizException("企业ID不能为空");
		}
		
		if(companysList==null || companysList.size()==0){
			throw new PurBizException("上传的公司信息为空");
		}
		
		return iCompany2AccountDao.updateCompany2AccountMapping(enterpriseId, companysList);
	}

	public int updateCompany2AccountMapping(long enterpriseId,	String companyId, String memberId, String token,Date expiredDate) throws BaseException {
		if(enterpriseId<=0){
			throw new PurBizException("企业ID不能为空");
		}
		
		if(StringUtils.isEmpty(companyId)){
			throw new PurBizException("公司ID不能为空");
		}
		
		if(StringUtils.isEmpty(memberId)){
			throw new PurBizException("1688主账号不能为空");
		}
		
		if(StringUtils.isEmpty(token)){
			throw new PurBizException("1688的令牌（token）不能为空");
		}
		
		return iCompany2AccountDao.updateCompany2AccountMapping(enterpriseId, companyId,memberId,token,expiredDate);
	}

	public Company2AccountInfo getCompany2AccountInfo(String accountId)	throws BaseException {
		if(StringUtils.isEmpty(accountId)){
			throw new PurBizException("主账号ID不能为空");
		}
		
		return iCompany2AccountDao.getCompany2AccountInfo(accountId);
	}
	
	public List<Company2AccountInfo> getAllCompany2AccountList() throws BaseException {
		return iCompany2AccountDao.getAllCompany2AccountList();
	}

	public boolean hasAuthorized(String companyId, String memberId) throws BaseException {
		if(StringUtils.isEmpty(companyId)){
			throw new PurBizException("公司ID不能为空");
		}
		
		if(StringUtils.isEmpty(memberId)){
			throw new PurBizException("1688主账号不能为空");
		}
		
		return iCompany2AccountDao.hasAuthorized(memberId, companyId);
	}

	public boolean deleteCompanyInfo(long enterpriseId, String companyId) throws BaseException {
		if(enterpriseId <= 0 || StringUtils.isEmpty(companyId)){
			return false;
		}
		
		return iCompany2AccountDao.deleteCompany(enterpriseId, companyId);
	}
}