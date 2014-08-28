package com.kingdee.purchase.platform.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.platform.dao.IAPIRegisterDao;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.api.ApiBaseInfo;
import com.kingdee.purchase.platform.info.api.ApiErrorCodeInfo;
import com.kingdee.purchase.platform.info.api.ApiInputParamInfo;
import com.kingdee.purchase.platform.info.api.ApiOutputParamInfo;

@Service
public class DocServiceImpl implements IDocService {
	
	@Autowired
	private IAPIRegisterDao apiDao;

	public List<ApiBaseInfo> getSortedAPIList() throws PurBizException,PurDBException {
		List<ApiBaseInfo> apiList = apiDao.getOpenApiInfoList();
		List<ApiInputParamInfo> inputParamList = apiDao.getApiInputParamList();
		List<ApiOutputParamInfo> outputParamList = apiDao.getApiOutputParamList();
		List<ApiErrorCodeInfo> errorCodeList = apiDao.getApiErrorCodeList();

		for(ApiBaseInfo api:apiList){
			api.setApiNameVo(api.getApiName().replaceAll("\\.", ""));
		}
		
		//按子系统排名
		Collections.sort(apiList, new Comparator<ApiBaseInfo>(){
			public int compare(ApiBaseInfo o1, ApiBaseInfo o2) {
				if(o1==null || o2 == null){
					return 0;
				}
				
				if(o1.getSubSystem()==null || o2.getSubSystem()==null){
					return 0;
				}
				
				return o1.getSubSystem().compareTo(o2.getSubSystem());
			}
		});
		
		Map<Long, ApiBaseInfo> apiMap = new HashMap<Long, ApiBaseInfo>();
		for(ApiBaseInfo api : apiList){
			apiMap.put(api.getId(), api);
		}
		
		for(ApiInputParamInfo inputParamInfo:inputParamList){
			ApiBaseInfo api = apiMap.get(inputParamInfo.getParentid());
			if(api!=null){
				api.getInputParamList().add(inputParamInfo);
			}
		}
		
		for(ApiOutputParamInfo outputParamInfo:outputParamList){
			ApiBaseInfo api = apiMap.get(outputParamInfo.getParentid());
			if(api!=null){
				api.getOutputParamList().add(outputParamInfo);
			}
		}
		
		for(ApiErrorCodeInfo errorCodeInfo:errorCodeList){
			ApiBaseInfo api = apiMap.get(errorCodeInfo.getParentid());
			if(api!=null){
				api.getErrorCodeList().add(errorCodeInfo);
			}
		}
		
		return apiList;
	}
}