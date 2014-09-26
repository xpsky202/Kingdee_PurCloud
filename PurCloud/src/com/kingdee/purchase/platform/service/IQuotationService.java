package com.kingdee.purchase.platform.service;

import java.util.List;

import com.kingdee.purchase.openapi.model.ObjectCollection;
import com.kingdee.purchase.openapi.model.QuotationInfo;

public interface IQuotationService {
	public int batchSaveQuotationsInfo(List<QuotationInfo> quoInfos);
	public int saveQuotationInfo(QuotationInfo quoInfo);
	public ObjectCollection<QuotationInfo> getQuotationsByBuyOfferId(String buyOfferId);
}
