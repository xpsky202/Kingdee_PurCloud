package com.kingdee.purchase.platform.service.impl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.dao.IAccountDao;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.AccountInfo;
import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.service.IAccountService;
import com.kingdee.purchase.platform.service.IEnterpriseService;
import com.kingdee.purchase.platform.util.SecurityUtil;
import com.kingdee.purchase.platform.util.StringUtils;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private IAccountDao iAccountDao;
	
	public void updateAccountInfo(AccountInfo info) throws PurBizException,PurDBException {
		if(StringUtils.isEmpty(info.getDisplayname())){
			throw new PurBizException("显示名称不能为空");
		}
		
		if(StringUtils.isEmpty(info.getEmail())){
			throw new PurBizException("邮箱不能为空");
		}
		
		boolean result = iAccountDao.isExistEmail(info.getId(), info.getEmail());
		if(result){
			throw new PurBizException("此邮箱已被占用，请修改邮箱");
		}
		
		iAccountDao.update(info);
	}

	public AccountInfo getAccountInfo(long id) throws BaseException {
		if(id<=0){
			throw new PurBizException("用户暂未登录，请登录后再重试");
		}
		
		return iAccountDao.get(id);
	}

	public void createAccountInfo(AccountInfo info,EnterpriseInfo enterpriseInfo) throws BaseException {
		if(StringUtils.isEmpty(info.getUsername())){
			throw new PurBizException("用户名不能为空");
		}
		
		//非常用操作，不必要用静态常量
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]+$");
		if((info.getUsername().length()<6 || info.getUsername().length()>20)
			|| !pattern.matcher(info.getUsername()).find()){
			throw new PurBizException("非法的用户名（由数字、字母、下划线组成，6-20位字符）");
		}
		
		if(StringUtils.isEmpty(info.getDisplayname())){
			throw new PurBizException("显示名称不能为空");
		}
		if(info.getDisplayname().length()<2 || info.getDisplayname().length()>20){
			throw new PurBizException("非法的显示名称（2-20位字符）");
		}		
		
		if(StringUtils.isEmpty(info.getEmail())){
			throw new PurBizException("邮箱不能为空");
		}
		
		if(StringUtils.isEmpty(info.getPassword())){
			throw new PurBizException("密码不能为空");
		}
		
		if(info.getPassword().length()<6 || info.getPassword().length()>20){
			throw new PurBizException("非法的密码（6-20位字符）");
		}
		
		if(iAccountDao.isExistEmail(info.getId(), info.getEmail())){
			throw new PurBizException("该邮箱名已被注册");
		}
		
		if(iAccountDao.isExistName(info.getId(), info.getUsername())){
			throw new PurBizException("该用户名已被注册");
		}
		
		//账号注册
		String  pwd = SecurityUtil.getMD5String(info.getPassword());
		info.setPassword(pwd);
		iAccountDao.insert(info);
		
		//企业注册
		enterpriseInfo.setAccountId(info.getId());
		enterpriseService.save(enterpriseInfo);
	}
	
	@Autowired
	private IEnterpriseService enterpriseService;
}