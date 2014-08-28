package com.kingdee.purchase.platform.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.dao.IEnterpriseDao;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.info.EnterpriseInfo;
import com.kingdee.purchase.platform.service.IEnterpriseService;
import com.kingdee.purchase.platform.util.StringUtils;

@Service
public class EnterpriseServiceImpl implements IEnterpriseService {
	
	private final static Logger logger=LogManager.getLogger(EnterpriseServiceImpl.class);

	@Resource
	private IEnterpriseDao enterpriseDao;
	
	public EnterpriseInfo get(long id) throws BaseException {
		if(id<=0){
			throw new PurBizException("该企业资料不存在");
		}
		
		return enterpriseDao.get(id);
	}

	public int save(EnterpriseInfo info) throws BaseException {
		if(null==info){
			throw new PurBizException("企业资料不正确，请重新输入");
		}
		
		if(StringUtils.isEmpty(info.getName())){
			throw new PurBizException("企业名称不能为空");
		}
		if(null==info.getErpType()){
			throw new PurBizException("系统类型不能为空");
		}
		if(info.getAccountId()<=0){
			throw new PurBizException("企业关联的账号不能为空");
		}
		
		boolean rep = enterpriseDao.isExistName(info.getId(), info.getName());
		if(rep){
			throw new PurBizException("该企业名称已经被注册");
		}
		
		//校验
		if(info.getId()>0){
			info.setUpdateDate(new Date(System.currentTimeMillis()));
			return enterpriseDao.update(info);
		}else{
			info.setCreateDate(new Date(System.currentTimeMillis()));
			info.setUpdateDate(new Date(System.currentTimeMillis()));
			
			//生成企业ID，不可修改
			long enterpriseId = generateEnterpriseId();
			while(enterpriseDao.isExistEnterpriseId(enterpriseId)){
				enterpriseId = generateEnterpriseId();
			}
			info.setEnterpriseid(enterpriseId);
		
			return enterpriseDao.insert(info);
		}
	}
	
	private long generateEnterpriseId(){
		Random random = new Random();
		return random.nextInt(10000000)+10000000;
	}

	public List<EnterpriseInfo> query(String condition) throws BaseException {
		return enterpriseDao.query(condition);
	}

	public EnterpriseInfo getByAccountId(long accountId) throws PurBizException {
		if(accountId<=0){
			throw new PurBizException("用户未登录，无法获取其企业信息。请重新登录");
		}
		
		EnterpriseInfo info = null;
		try{
			info = enterpriseDao.getByAccountId(accountId);
		}catch(RuntimeException e){
			logger.error(e.getMessage(),e);
		}
		
		if(info==null){
			info = new EnterpriseInfo();
			info.setAccountId(accountId);
		}
		
		return info;
	}

	public EnterpriseInfo getByEnterpriseId(long enterpriseId) throws BaseException {
		if(enterpriseId>=100000000 || enterpriseId<10000000){
			throw new PurBizException("无效的企业ID");
		}
		
		return enterpriseDao.getByEnterpriseId(enterpriseId);
	}
}