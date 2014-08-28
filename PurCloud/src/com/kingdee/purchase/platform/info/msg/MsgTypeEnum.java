package com.kingdee.purchase.platform.info.msg;

/***
 * 消息类型
 * @author RD_jiangkun_zhu
 *
 */
public enum MsgTypeEnum {

	/***
	 * 采购商发布询价单
	 */
	BUYER_PUBLISH_BUYOFFER("CAIGOU_MSG_BUYER_PUBLISH_BUYOFFER"),
	
	/***
	 * 采购商关闭询价单
	 */
	BUYER_CANCEL_BUYOFFER("CAIGOU_MSG_BUYER_CANCEL_BUYOFFER"),
	
	/***
	 * 采购商修改询价单
	 */
	BUYER_MODIFY_BUYOFFER("CAIGOU_MSG_BUYER_MODIFY_BUYOFFER"),
	
	/***
	 * 供应商报价消息
	 */
	SUPPLIER_QUOTATION ("CAIGOU_MSG_SUPPLIER_QUOTATION"),
	
	/**
	 * 供应商撤销报价消息
	 */
	SUPPLIER_CANCEL_QUOTATION("CAIGOU_MSG_SUPPLIER_CANCEL_QUOTATION"),
	
	/***
	 * 供应商选择备选报价消息
	 */
	BUYER_MARK_QUOTATION("CAIGOU_MSG_BUYER_MARK_QUOTATION"),
	
	/***
	 * 供应商撤销备选报价消息
	 */
	BUYER_UNMARK_QUOTATION("CAIGOU_MSG_BUYER_UNMARK_QUOTATION"),
	
	/***
	 * 供应商认证
	 */
	CREATE_COOPERATEION("CAIGOU_MSG_CREATE_COOPERATEION"),
	
	/***
	 * 未知的消息类型
	 */
	UNKNOWN("UNKNOWN");
	
	

	private String value;
	MsgTypeEnum(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	/**
	 * 获取消息类型枚举值
	 * @param value
	 * @return		永远不返回null，未知的类型，返回UNKNOWN类型
	 */
	public static MsgTypeEnum getMsgTypeEnum(String value){
		MsgTypeEnum[] vals = MsgTypeEnum.values();
		for(MsgTypeEnum e: vals){
			if(e.getValue().equalsIgnoreCase(value)){
				return e;
			}
		}
		
		return UNKNOWN;
	}
}