package com.kingdee.purchase.openapi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.info.KeyValue;
import com.kingdee.purchase.platform.service.alibaba.ICompany2AccountServcie;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 上传财务组织API
 * @author RD_cary_lin
 *
 */
public class CompanyOrgUpLoadService extends OpenApiServiceImpl {
	
	private static final String COMPANYORGS = "companyOrgs";
	
	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
			throws BaseException {
		// TODO Auto-generated method stub
		String jsonField = (String) paramsMap.get(COMPANYORGS);
		if (StringUtils.isEmpty(jsonField)){
			throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{COMPANYORGS,ParamCheckUtil.LISTTYPE});
		}
	}
	
	
	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)
			throws BaseException {
		// TODO Auto-generated method stub
		String jsonField = (String) paramsMap.get(COMPANYORGS);
		//paramsMap:companyOrgs = [{"id":"xsdiddikg=sjk","name":"深圳金蝶"}]
		checkJsonStr(jsonField);
	}
	
	
	//JSON:[{"id":"xsdiddikg=sjk","name":"深圳金蝶"}]
	protected void  checkJsonStr(String jsonField) throws PurBizException {
		if (StringUtils.isNotEmpty(jsonField)){
			
			JSONObject obj = ParamCheckUtil.getJsonObject(COMPANYORGS,jsonField);
			JSONArray array = obj.getJSONArray(ParamCheckUtil.ITEMSLIST);
			JSONObject data = null;
			String id = null,name = null;
			for(int i = 0, size = array.size();i < size;i++){ 
				data = array.getJSONObject(i); 
				id = data.optString("key"); 
				name = data.optString("value"); 
				if (StringUtils.isEmpty(id) 
						|| StringUtils.isEmpty(name)){
					throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{"companyOrgs:key || value",ParamCheckUtil.STRINGTYPE});
				}
			}
		}
	}

	
	@Override
	protected JSONObject invokeDestApi(RequestContext context) throws BaseException {
		Long enterpriseId = context.getSysParamInfo().getEnterPriseID();
		
		Map<String, Object> paramsMap = context.getBussinessParams();
		String jsonField = (String) paramsMap.get(COMPANYORGS);
		JSONObject obj = ParamCheckUtil.getJsonObject(COMPANYORGS,jsonField);
		
		JSONArray array = obj.getJSONArray(ParamCheckUtil.ITEMSLIST);
		JSONObject data = null;
		KeyValue companyInfo = null;
		List<KeyValue> companysList = new ArrayList<KeyValue>();
		for(int i = 0, size = array.size();i < size;i++){ 
			data = array.getJSONObject(i); 
			companyInfo = (KeyValue) JSONObject.toBean(data, KeyValue.class);
			companysList.add(companyInfo);
		}
		
		int state = SpringContextUtils.getBean(ICompany2AccountServcie.class).saveCompanyInfo(enterpriseId, companysList);
		//拼装jsonobject
		JSONObject jsonObject  = new JSONObject();
		jsonObject.element("State", state);
		return jsonObject;
	}
	
	
	

}
