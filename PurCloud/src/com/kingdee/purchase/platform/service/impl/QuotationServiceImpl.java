package com.kingdee.purchase.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.purchase.openapi.model.ObjectCollection;
import com.kingdee.purchase.openapi.model.QuotationInfo;
import com.kingdee.purchase.platform.dao.IQuotationDao;
import com.kingdee.purchase.platform.service.IQuotationService;

@Service
public class QuotationServiceImpl implements IQuotationService{
	
	@Autowired
	private IQuotationDao quotationDao;
	
	public int batchSaveQuotationsInfo(List<QuotationInfo> quoInfos) {
		return quotationDao.batchSaveQuotation(quoInfos);
	}
	public ObjectCollection<QuotationInfo> getQuotationsByBuyOfferId(String buyOfferId) {
		return quotationDao.getQuotationsByBuyOfferrrId(buyOfferId);
	}
	public int saveQuotationInfo(QuotationInfo quoInfo) {
		return quotationDao.saveQuotationInfo(quoInfo);
	}
}
