package com.kingdee.purchase.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.dao.IAccountDao;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.AccountInfo;
import com.kingdee.purchase.platform.service.IAuthService;
import com.kingdee.purchase.platform.util.SecurityUtil;
import com.kingdee.purchase.platform.util.StringUtils;

@Service
public class AuthServiceImpl implements IAuthService {
	
	@Autowired
	private IAccountDao accountDao;

	public AccountInfo loginByUsername(String username, String password)throws PurBizException {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			throw new PurBizException("用户名或密码不能为空");
		}
		
		AccountInfo info;
		try {
			info = accountDao.getByUsername(username);
		} catch (PurDBException e) {
			throw new PurBizException(e.getMessage());
		}
		if(null==info){
			throw new PurBizException("用户名未注册");
		}
		
		//MD5加密
		password = SecurityUtil.getMD5String(password);
		if(!password.equals(info.getPassword())){
			throw new PurBizException("用户名或密码不正确");
		}
		
		info.setPassword(null);
		
		return info;
	}

	public AccountInfo loginByEmail(String email, String password) throws PurBizException {
		if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
			throw new PurBizException("邮箱或密码不能为空");
		}
		
		AccountInfo info;
		try {
			info = accountDao.getByEmail(email);
		} catch (PurDBException e) {
			throw new PurBizException(e.getMessage());
		}
		if(null==info){
			throw new PurBizException("邮箱未注册");
		}

		//MD5加密
		password = SecurityUtil.getMD5String(password);
		if(!password.equals(info.getPassword())){
			throw new PurBizException("邮箱或密码不正确");
		}
		
		info.setPassword(null);		
		return info;
	}
}