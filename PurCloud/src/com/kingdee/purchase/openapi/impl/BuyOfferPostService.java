package com.kingdee.purchase.openapi.impl;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.util.ParamCheckUtil;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.util.StringUtils;


/**
 * 发布询价单API
 * @author RD_cary_lin
 *
 */
public class BuyOfferPostService extends OpenApiServiceImpl{
	
	private final static String subject = "subject";
	private final static String buyerUserId = "buyerUserId";
	private final static String sourceType = "sourceType";
	private final static String items = "items";
	private final static String contact = "contact";
	private final static String phone = "phone";
	//private final static String description = "description";
	private final static String gmtQuotationExpire = "gmtQuotationExpire";
	private final static String expectedSupplierAreas = "expectedSupplierAreas";
	private final static String certificateIds = "certificateIds";
	private final static String regCapital = "regCapital";
	//private final static String prId = "prId";

	/**
	 * 校验是否必录项
	 * @param paramsMap
	 * @return
	 * @throws PurBizException 
	 */
	protected void checkParamIsNull(Map<String, Object> paramsMap) throws BaseException {
		
		String[][] NOTNULLFIELD = new String[][]{
				{subject,ParamCheckUtil.STRINGTYPE},
				{sourceType,ParamCheckUtil.STRINGTYPE},
				{items,ParamCheckUtil.LISTTYPE},
				{contact,ParamCheckUtil.STRINGTYPE},
				{phone,ParamCheckUtil.STRINGTYPE},
				{gmtQuotationExpire,ParamCheckUtil.DATETYPE}
			};
		
		StringBuilder error = new StringBuilder();
		error.append(ParamCheckUtil.checkIsNull(paramsMap,NOTNULLFIELD));
		if (error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.REQUIRED_ARGS, error.toString());
		}
	}
	
	/**
	 * 校验应用参数格式
	 * @param paramsMap
	 * @return
	 */
	protected void checkParamFormat(Map<String, Object> paramsMap) throws BaseException  {
		StringBuilder error = new StringBuilder();
		//Long:123
		error.append(ParamCheckUtil.checkLong(paramsMap.get(buyerUserId), buyerUserId));
		error.append(ParamCheckUtil.checkLong(paramsMap.get(regCapital), regCapital));
		
		//DATE:201408011
		error.append(ParamCheckUtil.checkDate(paramsMap.get(gmtQuotationExpire), gmtQuotationExpire));
		
		//List:["331100","610300"]
		error.append(ParamCheckUtil.checkList((String)paramsMap.get(expectedSupplierAreas), ParamCheckUtil.LONGTYPE, expectedSupplierAreas));
		error.append(ParamCheckUtil.checkList((String)paramsMap.get(certificateIds), ParamCheckUtil.INTEGERTYPE, certificateIds));
		
		
		//JSON:[{"subject":"苹果","unit":"个","purchaseAmount":12,"productCode":"123","desc":"描述","prItemId":"pr的item行号1"}]
		error.append(checkJsonString((String)paramsMap.get(items), items));
		
		if (error.toString()!= null && error.toString().length()>0){
			throw new PurBizException(PurExceptionDefine.ILLEGAL_ARGS, error.toString());
		}
	}
	
	
	//JSON:[{"subject":"苹果","unit":"个","purchaseAmount":12,"productCode":"123","desc":"描述","prItemId":"pr的item行号1"}]
	private static final String purchaseAmount = "purchaseAmount";
	private static final String productCode = "productCode";
	private static final String prItemId = "prItemId";
	
	
	protected String checkJsonString(String jsonField, String fieldName) throws PurBizException {
		StringBuilder error = new StringBuilder();
		if (StringUtils.isNotEmpty(jsonField)){
			//判断是否为空
			String[][] NOTNULLFIELD = new String[][]{
					{subject,ParamCheckUtil.STRINGTYPE},
					{purchaseAmount,ParamCheckUtil.LONGTYPE},
					{productCode,ParamCheckUtil.STRINGTYPE},
					{prItemId,ParamCheckUtil.STRINGTYPE}
				};
			//校验格式是否正确
			String[] LONGFIELD = new String[]{purchaseAmount};
			
			JSONObject obj = ParamCheckUtil.getJsonObject(fieldName,jsonField);
			JSONArray array = obj.getJSONArray(ParamCheckUtil.ITEMSLIST);
			JSONObject data = null;
			StringBuilder chkNullStr = new StringBuilder();
			StringBuilder chkFormatStr = new StringBuilder();
			for(int i = 0, size = array.size();i < size;i++){ 
				data = array.getJSONObject(i); 
				chkNullStr.append(ParamCheckUtil.checkIsNull(fieldName,data,NOTNULLFIELD,i));

				chkFormatStr.append(ParamCheckUtil.checkLong(fieldName, data, LONGFIELD, i));
			}
			error.append(chkNullStr).append(chkFormatStr);
		}
		return error.toString();
	}
	
	
}
