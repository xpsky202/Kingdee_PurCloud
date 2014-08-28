package com.kingdee.purchase.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.dao.IAccountDao;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.AccountInfo;
import com.kingdee.purchase.platform.service.ILoginService;
import com.kingdee.purchase.platform.util.SecurityUtil;
import com.kingdee.purchase.platform.util.StringUtils;

@Service
public class LoginServiceImpl implements ILoginService {
	
	@Autowired
	private IAccountDao accountDao;

	public AccountInfo loginByUsername(String username, String password)	throws PurBizException {
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

	public boolean chgPassword(long id, String oldPassword,String password,String rePassword) throws PurBizException {
		if(StringUtils.isEmpty(oldPassword)){
			throw new PurBizException("原密码不能为空");
		}
		
		if(StringUtils.isEmpty(password) || password.length()<6 || password.length()>20){
			throw new PurBizException("新密码不能为空，且长度必须在6-20个字符内");
		}
		
		if(!password.equals(rePassword)){
			throw new PurBizException("确认密码和新密码不一致");
		}

		//MD5加密
		oldPassword = SecurityUtil.getMD5String(oldPassword);
		password = SecurityUtil.getMD5String(password);
		
		try {
			AccountInfo info = accountDao.get(id);
			if(info.getPassword()!=null && !info.getPassword().equals(oldPassword)){
				throw new PurBizException("原密码不正确");
			}
		} catch (PurDBException e) {
			throw new PurBizException("获取用户资料失败");		
		}

		return accountDao.changePassword(id, password);
	}
}