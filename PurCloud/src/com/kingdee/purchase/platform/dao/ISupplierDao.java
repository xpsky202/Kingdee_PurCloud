package com.kingdee.purchase.platform.dao;

import java.util.List;

import com.kingdee.purchase.openapi.model.SupplierInfo;

public interface ISupplierDao {
	/**
	 * 保存供应商信息
	 * @param supplierInfo
	 */
	public int saveSupplier(SupplierInfo supplierInfo);
	
	/**
	 * 批量保存供应商
	 * @param suppliers
	 */
	public int batchSaveSupplier(List<SupplierInfo> suppliers);
	
	/**
	 * 根据ID获取供应商信息
	 * @param id
	 * @return
	 */
	public SupplierInfo getSupplierInfoByDestId(String id);
}
