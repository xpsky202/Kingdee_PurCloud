package com.kingdee.purchase.platform.exception;

import java.util.HashMap;
import java.util.Map;

public class PurExceptionDefine {
	
	private static final String NO_DEFINE_ERROR = "No defined error";
	public static Map<Integer, String> errorCode2MessageMap = new HashMap<Integer, String>();
	
	//Sys Exception 400 - 499
	public static final int KD_PLATFORM_SYSEXCEPTION = 400;
	public static final int API_INVOKE_INTERRUPTED = 401;
	public static final int API_INVOKE_EXCUTEERROR = 402;
	public static final int API_INVOKE_TIMEOUT = 403;
	public static final int NOT_FOUND_DESTHANDLER = 404;
	public static final int CREATE_DESTHANDLER_ERROR = 405;
	public static final int UNSUPPORT_API = 406;
	
	//DB Exception 500 - 599
	public static final int KD_PLATFORM_DBEXCEPTION = 500;
	
	//Biz Exception (平台级异常 600 - 699)
	public static final int KD_PLATFORM_BIZEXCEPTION = 600;
	public static final int URL_INVALID = 601;
	public static final int ENTERPRISE_UNREGISTED = 602;
	public static final int ENTERPRISE_FORBIDDEN = 603;
	public static final int NOT_FOUND_OPENAPIPROCESSOR = 604;
	public static final int CREATE_OPENPROCESSOR_ERROR = 605;
	public static final int COMPANY_NOT_EXIST = 606;
	
	
	//Biz Exception (API级异常)
	//API级通用异常 700 - 799
	public static final int REQUIRED_ARGS = 700;
	public static final int ILLEGAL_ARGS = 701;
	public static final int INVALIDATE_JSON_ARGS = 702;
	public static final int ILLEGAL_DATE_FORMAT = 703;
	public static final int DATA_NOT_EXIST = 704;
	
	public static final int ALI_INVALID_SIGN = 710;
	public static final int ALI_NEED_AUTH = 711;
	public static final int ALI_EXCEED_APP_TIME = 712;
	public static final int ALI_UNSUPPORT_API = 713;
	public static final int ALI_CONNECT_ERROR = 714;
	public static final int ALI_INVOKE_TIMEOUT = 715;
	public static final int ALI_INVOKE_TARGET = 716;
	public static final int ALI_APIDEF_ERROR = 717;
	public static final int ALI_NO_TOKEN = 718;
	public static final int ALI_TOKEN_OUTOFDATE = 719;
	
	public static final int ALI_NO_PRIVILEGE = 720;
	public static final int ALI_SYSTEM_ERROR = 721;
	public static final int ALI_INVALID_PARAM = 722;
	public static final int ALI_UNKNOW_RESULT = 723;
	public static final int ALI_NULL_RESULT = 724;
	public static final int ALI_UNKNOW_ERROR = 725;
	
	//创建子账号异常 800 - 819
	public static final int ALI_ACCOUNT_CONFLICT = 800;
	
	//发布询价单异常 820 - 839
	public static final int EXCEED_BUYOFFER_TIMELIMIT = 820;
	public static final int USER_IN_BLACK_LIST = 821;
	
	//保存采购订单异常 880 - 899
	public static final int DUPLICATE_INSERT = 880;
	
	//免登异常  900 - 919
	public static final int REQUIRE_TARGETURL = 901;
	
	public static final int REQUIRE_COMPNAYTOKEN = 902;
	
	
	static {
		//Sys Exception 400 - 499
		errorCode2MessageMap.put(KD_PLATFORM_SYSEXCEPTION, "Kingdee purchase platform system exception");
		errorCode2MessageMap.put(API_INVOKE_INTERRUPTED, "Alibaba invoke excetpion: Send request interrupt");
		errorCode2MessageMap.put(API_INVOKE_EXCUTEERROR, "Alibaba invoke excetpion: {0}");
		errorCode2MessageMap.put(API_INVOKE_TIMEOUT, "Alibaba invoke excetpion: Send request timeout");
		errorCode2MessageMap.put(NOT_FOUND_DESTHANDLER, "Not found Dest's API handler: {0}");
		errorCode2MessageMap.put(CREATE_DESTHANDLER_ERROR, "Create Dest's API handler error: {0}");
		errorCode2MessageMap.put(UNSUPPORT_API, "Unsupport API: {0}");
		
		//DB Exception 500 - 599
		errorCode2MessageMap.put(KD_PLATFORM_DBEXCEPTION, "Kingdee purchase platform DataBase exception");
		
		//Biz Exception (平台级异常 600 - 699)
		errorCode2MessageMap.put(KD_PLATFORM_BIZEXCEPTION, "Kingdee purchase platform biz exception");
		errorCode2MessageMap.put(URL_INVALID, "Request URL is invalidate");
		errorCode2MessageMap.put(ENTERPRISE_UNREGISTED, "Enterprise is unRegistered");
		errorCode2MessageMap.put(ENTERPRISE_FORBIDDEN, "Enterprise is forbidden");
		errorCode2MessageMap.put(NOT_FOUND_OPENAPIPROCESSOR, "Not found open api processor: {0}");
		errorCode2MessageMap.put(CREATE_OPENPROCESSOR_ERROR, "Create open api processor error: {0}");
		errorCode2MessageMap.put(COMPANY_NOT_EXIST, "Company: {0} not exist, please upload company and authorize it first");
		
		//Biz Exception (API级异常)
		//API级通用异常 700 - 799
		errorCode2MessageMap.put(REQUIRED_ARGS, "Required argument {0} : expect [type: {1}]");  //缺少必录参数
		errorCode2MessageMap.put(ILLEGAL_ARGS, "Illegal argument {0} : expect [type: {1}] but [type: {2}]");  //参数类型错误
		errorCode2MessageMap.put(INVALIDATE_JSON_ARGS, "Invalidate argument {0} : can not to jsonObject");  //无效的JSON参数
		errorCode2MessageMap.put(ILLEGAL_DATE_FORMAT, "Invalidate date format: {0}, required format: {1}");  //不合法的日期格式
		errorCode2MessageMap.put(DATA_NOT_EXIST, "Alibaba invoke excetpion: order info doesn't exsited");
		
		errorCode2MessageMap.put(ALI_INVALID_SIGN, "Alibaba invoke excetpion: Invalid signature");
		errorCode2MessageMap.put(ALI_NEED_AUTH, "Alibaba invoke excetpion: Request need user authenticated");
		errorCode2MessageMap.put(ALI_EXCEED_APP_TIME, "Alibaba invoke excetpion: Exceed the app call time limit");
		errorCode2MessageMap.put(ALI_UNSUPPORT_API, "Alibaba invoke excetpion: Unsupport API");
		errorCode2MessageMap.put(ALI_CONNECT_ERROR, "Alibaba invoke excetpion: Invoke connect error");
		errorCode2MessageMap.put(ALI_INVOKE_TIMEOUT, "Alibaba invoke excetpion: Invoke time out");
		errorCode2MessageMap.put(ALI_INVOKE_TARGET, "Alibaba invoke excetpion: Invoke target error");
		errorCode2MessageMap.put(ALI_APIDEF_ERROR, "Alibaba invoke excetpion: API definition error");
		errorCode2MessageMap.put(ALI_NO_TOKEN, "Alibaba invoke excetpion: Not found access_token, please login on purchase cloud server to authorize");
		errorCode2MessageMap.put(ALI_TOKEN_OUTOFDATE, "Alibaba invoke excetpion: access_token is out of date, please login on purchase cloud server to authorize");
		
		errorCode2MessageMap.put(ALI_NO_PRIVILEGE, "Alibaba invoke excetpion: You have no privilege");  //没有权限
		errorCode2MessageMap.put(ALI_SYSTEM_ERROR, "Alibaba invoke excetpion: Alibaba system error");  //系统错误
		errorCode2MessageMap.put(ALI_INVALID_PARAM, "Alibaba invoke excetpion: Invalid param: {0}");  //非法参数
		errorCode2MessageMap.put(ALI_UNKNOW_RESULT, "Alibaba invoke excetpion: Unknow result: {0}");  //未知结果
		errorCode2MessageMap.put(ALI_NULL_RESULT, "Alibaba invoke excetpion: Null result");  //未知结果
		errorCode2MessageMap.put(ALI_UNKNOW_ERROR, "Alibaba invoke excetpion: errorCode: {0}, errorMessage: {1}");  //未知结果
		
		//创建子账号异常 800 - 819
		errorCode2MessageMap.put(ALI_ACCOUNT_CONFLICT, "Alibaba invoke excetpion: Main account conflict"); //主账号冲突
		
		//发布询价单异常 820 - 839
		errorCode2MessageMap.put(EXCEED_BUYOFFER_TIMELIMIT, "Alibaba invoke excetpion: Exceed buyoff time limit");
		errorCode2MessageMap.put(USER_IN_BLACK_LIST, "Alibaba invoke excetpion: User is in black list");
		
		//获取报价单异常 840 - 859
		
		//获取供应商异常 860 - 879
		
		//上传采购订单异常 880 - 899
		errorCode2MessageMap.put(DUPLICATE_INSERT, "Alibaba invoke excetpion: order info already exsited");
		
		//上传供应商异常 900 - 919
		
		//免登异常  900 - 919
		errorCode2MessageMap.put(REQUIRE_TARGETURL, "Auto login required argument: TargetUrlEnum");
		errorCode2MessageMap.put(REQUIRE_COMPNAYTOKEN, "Upload supplier excetpion: No Auth OrgUnit");
		
	}
	
	/**
	 * 根据错误代码获取错误消息
	 * @param errorCode
	 * @return
	 */
	public static String getErrorMessage(int errorCode) {
		if (!errorCode2MessageMap.containsKey(Integer.valueOf(errorCode))) {
			return NO_DEFINE_ERROR;
		}
		
		return errorCode2MessageMap.get(errorCode);
	}

}
