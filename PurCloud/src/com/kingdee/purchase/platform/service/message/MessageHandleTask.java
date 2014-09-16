package com.kingdee.purchase.platform.service.message;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.kingdee.purchase.destapi.alibaba.util.JSONUtil;
import com.kingdee.purchase.destapi.alibaba.util.JSONUtil2;
import com.kingdee.purchase.openapi.context.RequestContext;
import com.kingdee.purchase.openapi.model.QuotationInfo;
import com.kingdee.purchase.openapi.model.SupplierInfo;
import com.kingdee.purchase.platform.info.msg.MessageInfo;
import com.kingdee.purchase.platform.info.msg.MsgTypeEnum;
import com.kingdee.purchase.platform.processor.AbstractApiProcessor;
import com.kingdee.purchase.platform.service.IQuotationService;
import com.kingdee.purchase.platform.service.ISupplierService;
import com.kingdee.purchase.platform.util.SpringContextUtils;

/**
 * 消息处理器
 * @author RD_daniel_liu
 *
 */
public class MessageHandleTask {

	/**
	 * 处理来自1688的消息
	 * 根据消息的类型分别处理
	 * @param messageInfo
	 */
	protected static void process(MessageInfo messageInfo){
	
		//报价消息
		if(MsgTypeEnum.SUPPLIER_QUOTATION.equals(messageInfo.getType())){
			handleQuotationMessage(messageInfo);
		}
	}
	
	private static void handleQuotationMessage(MessageInfo messageInfo){
		JSONObject jsonObject = null;
		RequestContext quoRequestContext = MessageUtils.getRequestContext(messageInfo,
				"com.kingdee.purchase.openapi","quotation.getQuotationInfoById",  "1");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("quotationId", messageInfo.getData().get("quotationId"));
		quoRequestContext.setBussinessParams(params);
		AbstractApiProcessor apiProcressor = new AbstractApiProcessor(null,null);
		try{
			jsonObject = apiProcressor.processApiService(quoRequestContext);
			QuotationInfo quoInfo = JSONUtil2.json2QuotationInfo(jsonObject, true);
			quoInfo.setId(new Long(messageInfo.getData().get("quotationId")));
			quoInfo.getSupplier().setDestId(quoInfo.getSupplierMemberId());
			SpringContextUtils.getBean(ISupplierService.class).saveSupplier(quoInfo.getSupplier());
			SpringContextUtils.getBean(IQuotationService.class).saveQuotationInfo(quoInfo);
		}catch(Exception e){
			e.printStackTrace();
		};
		
	}        
	
}
