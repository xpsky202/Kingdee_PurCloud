package com.kingdee.purchase.openapi.util;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kingdee.purchase.config.ConfigConstant;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;
import com.kingdee.purchase.platform.util.StringUtils;


/**
 * 校验参数工具类
 * @author RD_cary_lin
 *
 */
public class ParamCheckUtil {
	
	public static final String STRINGTYPE = "java.lang.String";
	public static final String LONGTYPE   = "java.lang.Long";
	public static final String INTEGERTYPE= "java.lang.Integer";
	public static final String DATETYPE   = "java.util.Date";
	public static final String LISTTYPE   = "java.util.List";
	
	public static final String ITEMSLIST = "itemsList";
	
	
	/** 
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty 
     * @param obj 
     * @return 
     */  
    public static boolean isEmpty(Object obj) {  
        if (obj == null){ 
            return true;  
        }
        
        if (obj instanceof CharSequence){  
            return ((CharSequence) obj).length() == 0;  
        }  
         
        if (obj instanceof Collection){  
            return ((Collection) obj).isEmpty();
        }
        
        if (obj instanceof Map){  
            return ((Map) obj).isEmpty();  
        }
 
        if (obj instanceof String) {  
        	return ((String)obj).isEmpty();
        }
        
        if (obj.getClass().isArray()){
        	return Array.getLength(obj) == 0;
        }
        return false;  
    }  

	
    /**
     * 判断对象或对象数组中每一个对象是否为空
     * @param obj
     * @return
     */
	public final static boolean isNotEmpty(Object obj){
		if (obj!=null){
	        if (obj instanceof CharSequence)  
	            return ((CharSequence) obj).length() > 0;  
	  
	        if (obj instanceof Collection)  
	            return ((Collection) obj).size()>0;
	  
	        if (obj instanceof Map)  
	            return ((Map) obj).size()>0;
	 
	        if (obj instanceof String) {  
	        	return ((String)obj).trim().length()!=0;
	        }
	        
	        if (obj.getClass().isArray()){
	        	return Array.getLength(obj) > 0;
	        }
	        return true;
		}
        return false;  
		
	}
	
	public final static boolean isLong(String s){
		try {
			Long.parseLong(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public final static boolean isDate(String s,DateFormat format){    
		try {   
		    format.parse(s);  
		} catch (ParseException e) {   
			return false;
		}

		return true;
	}
	
	public final static boolean isInteger(String s){
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static String checkIsNull(Map<String, Object> paramsMap,String fields[][]){
		
		StringBuilder error = new StringBuilder();
		String field = null,type = null;
		for (int i = 0,size = fields.length; i< size; i++) {
			field = fields[i][0];
			type = fields[i][1];
			if (isEmpty(paramsMap.get(field))){
				error.append("Required argument '"+ field  +"' : expect [type: "+ type +"]").append("\n");
			}
		}
		return error.toString();
	}
	
	/**
	 * item:[{"subject":"苹果","unit":"个","purchaseAmount":12,"productCode":"123","desc":"描述","prItemId":"pr的item行号1"},{xxxx},{xxxx}]
	 * 校验json中的子项是否必录
	 * @param fieldName 字段名称item
	 * @param jsonObject json中解析的对象
	 * @param fields {{subject,ParamCheckUtil.STRINGTYPE},{purchaseAmount,ParamCheckUtil.LONGTYPE}}
	 * @param rowindex 行数
	 * @return
	 */
	public static String checkIsNull(String fieldName,JSONObject jsonObject,String fields[][],int rowindex){
		
		StringBuilder error = new StringBuilder();
		String field = null,type = null;
		for (int i = 0,size = fields.length; i< size; i++) {
			field = fields[i][0];
			type = fields[i][1];
			if (isEmpty(jsonObject.opt(field))){
				error.append("num:" + rowindex +" Required argument '"+ fieldName+"."+ field +"' : expect [type: "+ type +"]").append("\n");
			}
		}
		return error.toString();
	}
	
	/**
	 * item:[{"subject":"苹果","unit":"个","purchaseAmount":12,"productCode":"123","desc":"描述","prItemId":"pr的item行号1"},{xxxx},{xxxx}]
	 * 校验json中的子项是否必录
	 * @param fieldName 字段名称item
	 * @param jsonObject json中解析的对象
	 * @param fields {"purchaseAmount"}
	 * @param rowindex 行数
	 * @return
	 */
	public static String checkLong(String fieldName,JSONObject jsonObject,String fields[],int rowindex){
		
		StringBuilder error = new StringBuilder();
		String field = null; Object value = null;
		for (int i = 0,size = fields.length; i< size; i++) {
			field = fields[i];
			value = jsonObject.opt(field);
			if (isNotEmpty(value)){  
				if (!isLong(value.toString())){
					error.append("num:" + rowindex +" Illegal argument "+fieldName+"."+ field+" : expect [type: "+LONGTYPE+"] but [type: "+STRINGTYPE+"]" + "\n");  //参数类型错误
				}
			}
		}
		return error.toString();
	}
	
	
	
	/**
	 * 校验是否是Long:123
	 * @param value
	 * @param fieldName
	 * @return
	 * @throws PurBizException
	 */
	public static String checkLong(Object value, String fieldName) throws PurBizException {
		String error = "";
		if (isNotEmpty(value)){  
			if (!isLong(value.toString())){
				error = "Illegal argument "+fieldName+" : expect [type: "+LONGTYPE+"] but [type: "+STRINGTYPE+"]" + "\n";  //参数类型错误
			}
		}
		return error;
	}
	
	
	/**
	 * 校验日期DATE:201408011
	 * @param value
	 * @param fieldName
	 * @return
	 * @throws PurBizException
	 */
	public static String checkDate(Object value, String fieldName) throws PurBizException {
		DateFormat format = new SimpleDateFormat(ConfigConstant.DateFormatPattern);
		String error = "";
		if (isNotEmpty(value)){  
			if (!isDate(value.toString(), format)){
				error = "Illegal argument "+fieldName+" : expect [type: "+DATETYPE+"] but [type: "+STRINGTYPE+"]" + "\n";  //参数类型错误
			}
		}
		return error;
	}
	
	
	/**
	 * 校验json like  List:["331100","610300"]
	 * @param listField
	 * @param type
	 * @param fieldName
	 * @return
	 * @throws PurBizException
	 */
	public static String checkList(String listField, String type, String fieldName) throws PurBizException {
		
		StringBuilder error = new StringBuilder();
		StringBuilder jsonStr = new StringBuilder();
		if (StringUtils.isNotEmpty(listField)) {
			String itemStr = listField.replaceAll("\"", "\\\"");
			jsonStr.append("{\"list\":").append(itemStr).append("}");

			JSONObject object = JSONObject.fromObject(jsonStr);
			if (object!= null){
				JSONArray jarray= object.getJSONArray("list");
				Object[] objects = (Object[]) jarray.toArray();
				for(Object obj:objects){
					if (type.equals(LONGTYPE)) {
						if (!isLong(obj.toString())){
							error.append("Illegal argument "+fieldName+" : expect [type: "+LONGTYPE+"] but [type: "+STRINGTYPE+"]").append("\n");
							break;
						}
					}
					
					if (type.equals(INTEGERTYPE)) {
						if (!isInteger(obj.toString())){
							error.append("Illegal argument "+fieldName+" : expect [type: "+INTEGERTYPE+"] but [type: "+STRINGTYPE+"]").append("\n");
							break;
						}
					}
				}
			}
		}
		return error.toString();
	}
	
	
	/**
	 * 通过json字符串转换为jsonObject
	 * jsonField:"[{\"id\":\"1\",\"name\":\"aa\"},{\"id\":\"2\",\"name\":\"bb\"}]";
	 * @param jsonValue
	 * @return
	 * @throws PurBizException 
	 */
	public static JSONObject getJsonObject(String field,String jsonValue) throws PurBizException {
		StringBuilder jsonStr = new StringBuilder();
		String itemStr = jsonValue.replaceAll("\"", "\\\""); 
		jsonStr.append("{\""+ITEMSLIST+"\":").append(itemStr).append("}");
		
		JSONObject obj = null;
		try{
			obj = JSONObject.fromObject(jsonStr.toString());
		}catch (Exception e) {
			throw new PurBizException(PurExceptionDefine.INVALIDATE_JSON_ARGS,new String[]{field});
		}
		
		return obj;
	}
	

}
