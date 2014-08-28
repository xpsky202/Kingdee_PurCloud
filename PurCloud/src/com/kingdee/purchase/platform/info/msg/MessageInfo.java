package com.kingdee.purchase.platform.info.msg;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kingdee.purchase.platform.info.BaseInfo;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;
import com.kingdee.purchase.platform.service.ISystemParamService;
import com.kingdee.purchase.platform.service.SystemParamsServiceFactory;

/***
 * 消息
 * @author RD_jiangkun_zhu
 *
 */
public abstract class MessageInfo extends BaseInfo{
	
	public final static String MSG_GMTBORN = "gmtBorn";
	public final static String MSG_USERINFO = "userInfo";
	public final static String MSG_TYPE = "type";
	public final static String MSG_DATA = "data";

	private final static Logger logger = LogManager.getLogger(MessageInfo.class);
	
	private long id;
	private long gmtBorn;
	private String companyId;
	private MsgTypeEnum type;
	private Map<String, String> data;
	private long enterpriseId;
	private int status;						//0-未消费、1-已消费
	
	public long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public MessageInfo(){
		super();
		this.data = new HashMap<String, String>();
	}
	public long getGmtBorn() {
		return gmtBorn;
	}
	public void setGmtBorn(long gmtBorn) {
		this.gmtBorn = gmtBorn;
	}
	public Timestamp getGmtTimestamp() {
		return new Timestamp(gmtBorn);
	}
	public MsgTypeEnum getType() {
		return type;
	}
	public void setType(MsgTypeEnum type) {
		this.type = type;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public Map<String, String> getData(){
		return this.data;
	}
	
	public static MessageInfo parse(String jsonStr) throws Exception{
		JSONObject json = JSONObject.fromObject(jsonStr);
		MsgTypeEnum msgType = MsgTypeEnum.getMsgTypeEnum(json.getString(MSG_TYPE));		//消息类型，暂时不是所有的消息都支持
		MessageInfo info = getMsgInfoByType(msgType);
		long id = json.optLong("id");
		if(id>0){
			info.setId(id);
		}
		
		if(null==info){
			logger.error("不支持的消息类型，msgType("+json.getString(MSG_TYPE)+")");
			return null;
		}
	
		ISystemParamService service = SystemParamsServiceFactory.getServiceInstance4Alibaba();
		String memberId = json.getString(MessageInfo.MSG_USERINFO);
		Company2AccountInfo c2aInfo = service.getCompanyInfoByAccountId(memberId);
		if(null==c2aInfo){
			logger.error("通过memberId（"+memberId+")无法获取到对应的财务组织");
			return null;
		}		
		
		info.setCompanyId(c2aInfo.getCompanyId());
		info.setEnterpriseId(c2aInfo.getEnterpriseId());
		info.setGmtBorn(json.getLong(MSG_GMTBORN));	

		//子类解析具体的业务data数据
		info.initData(json.getJSONObject(MSG_DATA));
		
		return info;
	}
	
	public static MessageInfo getMsgInfoByType(MsgTypeEnum msgType){
		//消息类型，暂时不是所有的消息都支持
		if(MsgTypeEnum.BUYER_PUBLISH_BUYOFFER.equals(msgType)){
			return new BuyerPublishBuyofferMsg();
		}else if(MsgTypeEnum.BUYER_MODIFY_BUYOFFER.equals(msgType)){
			return new BuyerModifyBuyofferMsg();
		}else if(MsgTypeEnum.BUYER_CANCEL_BUYOFFER.equals(msgType)){
			return new BuyerCancelBuyofferMsg();
		}else if(MsgTypeEnum.SUPPLIER_CANCEL_QUOTATION.equals(msgType)){
			return new SupplierCancelQuotationMsg();
		}else if(MsgTypeEnum.SUPPLIER_QUOTATION.equals(msgType)){
			return new SupplierQuotationMsg();
		}else if(MsgTypeEnum.CREATE_COOPERATEION.equals(msgType)){
			return new SupplierCooperateionMsg();
		}

		return null;
	}
	
	public abstract void initData(JSONObject json);
	
	public String toJsonString(){
		JSONObject json = JSONObject.fromObject(this);
		return json.toString();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}