package com.kingdee.purchase.openapi.impl;

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
import com.kingdee.purchase.platform.info.UserInfo;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;
import com.kingdee.purchase.platform.service.ISystemParamService;
import com.kingdee.purchase.platform.service.SystemParamsServiceFactory;
import com.kingdee.purchase.platform.service.alibaba.ICompany2AccountServcie;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

/**
 * 上传供应商API
 * @author RD_cary_lin
 *
 */
public class SupplierUpLoadService extends OpenApiServiceImpl {
	
	//供应商列表
	private static final String SUPPLIERLIST= "list";
	
	//采购商memberId
	//private static final String BUYERMEMBERID = "buyerMemberId";
	//供应商公司名
	private static final String SUPPLIERCOMPANYNAME = "supplierCompanyName";
	//供应商在外部系统中的ID
	private static final String EXTERNALID = "externalId";
	//供应商memberId 
	//private static final String SUPPLIERMEMBERID = "supplierMemberId";
	
	
	/**
	 * 处理特殊的场景参数
	 * @param context
	 * @throws BaseException 
	 */
	protected void initSpecialParam(RequestContext context) throws BaseException{
		//企业ID
		long enterpriseId = context.getSysParamInfo().getEnterPriseID();
		
		String companyId = "";
		Company2AccountInfo company2AccountInfo = null;
		List<Company2AccountInfo> companyList = SpringContextUtils.getBean(ICompany2AccountServcie.class).getCompanyList(enterpriseId);
		if (companyList!= null){
			for(int i = 0, size = companyList.size(); i< size; i++ ){
				if (companyList.get(0).getToken()!= null){
					company2AccountInfo = companyList.get(0);
					break;
				}
			}
			if (company2AccountInfo== null){
				throw new PurBizException(PurExceptionDefine.REQUIRE_COMPNAYTOKEN);
			}
			
			companyId = company2AccountInfo.getCompanyId();
			context.getSysParamInfo().setOrgUnitID(companyId);
			
			//查询B2B平台上的子账号ID
			UserInfo userInfo = context.getSysParamInfo().getUserInfo();
			ISystemParamService paramService = SystemParamsServiceFactory.getServiceInstance4Alibaba();
			String destId = paramService.getB2BSubAccount(enterpriseId, companyId, userInfo.getId());
			userInfo.setDestId(destId);
		}
	}

	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)
			throws BaseException {
		Object vaule = paramsMap.get(SUPPLIERLIST);
		if (ParamCheckUtil.isEmpty(vaule)){
			throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS,new String[]{SUPPLIERLIST,ParamCheckUtil.LISTTYPE});
		}
	}

	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)
		throws BaseException {
		
		StringBuilder error = new StringBuilder();
		
		//JSON:[{"subject":"苹果","unit":"个","purchaseAmount":12,"productCode":"123","desc":"描述","prItemId":"pr的item行号1"}]
		error.append(checkJsonString((String)paramsMap.get(SUPPLIERLIST), SUPPLIERLIST));
		
		if (error.toString()!= null && error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.ILLEGAL_ARGS, error.toString());
		}
	}
	
	
	protected String checkJsonString(String jsonField, String fieldName) throws PurBizException {
		StringBuilder error = new StringBuilder();
		if (StringUtils.isNotEmpty(jsonField)){
			//判断是否为空
			String[][] NOTNULLFIELD = new String[][]{
					//{BUYERMEMBERID,ParamCheckUtil.STRINGTYPE},
					{SUPPLIERCOMPANYNAME,ParamCheckUtil.STRINGTYPE},
					{EXTERNALID,ParamCheckUtil.STRINGTYPE}
					//{SUPPLIERMEMBERID,ParamCheckUtil.STRINGTYPE}
				};
			
			JSONObject obj = ParamCheckUtil.getJsonObject(fieldName,jsonField);
			JSONArray array = obj.getJSONArray(ParamCheckUtil.ITEMSLIST);
			JSONObject data = null;
			StringBuilder chkNullStr = new StringBuilder();
			for(int i = 0, size = array.size();i < size;i++){ 
				data = array.getJSONObject(i); 
				chkNullStr.append(ParamCheckUtil.checkIsNull(fieldName,data,NOTNULLFIELD,i));
			}
			error.append(chkNullStr);
		}
		return error.toString();
	}
}
