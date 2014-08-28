package com.kingdee.purchase.platform.service;

import java.util.List;

import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurDBException;
import com.kingdee.purchase.platform.info.api.ApiBaseInfo;

public interface IDocService {

	public List<ApiBaseInfo> getSortedAPIList() throws PurBizException,PurDBException;
}