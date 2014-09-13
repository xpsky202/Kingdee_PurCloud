package com.kingdee.purchase.platform.dao;

import java.util.List;

import com.kingdee.purchase.openapi.model.ObjectCollection;
import com.kingdee.purchase.openapi.model.QuotationInfo;


public interface IQuotationDao{
	/**
	 * 保存报价单
	 * @param quoInfo
	 * @return
	 */
	public int saveQuotationInfo(QuotationInfo quoInfo);
	/**
	 * 批量保存报价单
	 * @param quoInfos
	 * @return
	 */
	public int batchSaveQuotation(List<QuotationInfo> quoInfos);
	/**
	 * 根据询价单查询报价单
	 * @param buyOfferId
	 * @return
	 */
	public ObjectCollection<QuotationInfo> getQuotationsByBuyOfferrrId(String buyOfferId);
	/**
	 * 根据报价单ID查询报价单
	 * @param id
	 * @return
	 */
	public QuotationInfo getQuotationById(long id);
}
