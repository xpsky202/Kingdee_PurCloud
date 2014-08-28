package com.kingdee.purchase.openapi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kingdee.purchase.openapi.OpenApiServiceImpl;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.msg.MessageInfo;
import com.kingdee.purchase.platform.service.IMessageService;
import com.kingdee.purchase.platform.util.SpringContextUtils;

public class MessageLoopService extends OpenApiServiceImpl {

	@Override
	protected void checkParamFormat(Map<String, Object> paramsMap)throws BaseException {

	}

	@Override
	protected void checkParamIsNull(Map<String, Object> paramsMap)throws BaseException {

	}
	
	/**
	 * 执行服务
	 * @param context
	 */
	protected JSONObject invokeDestApi(RequestContext context) throws BaseException{
		long enterpriseId = context.getSysParamInfo().getEnterPriseID();
		IMessageService service = SpringContextUtils.getBean("messageService",IMessageService.class);
		List<MessageInfo> list = service.getList(enterpriseId, 15);
		List<MessageInfo> retList = new ArrayList<MessageInfo>();
		for(MessageInfo msg:list){
			msg.setStatus(1);		//已消费
			if(service.updateStatus(msg)){
				retList.add(msg);
			}
		}

		JSONObject json = new JSONObject();
		JSONArray array = JSONArray.fromObject(retList);
		json.put("messages", array);
		
		return json;
	}
	 
	
	/**
	 * 转换结果集
	 * @param alibabaResult
	 * @return
	 */
	protected JSONObject buildOutPutResult(JSONObject outputResult) {
		return outputResult;
	}
}