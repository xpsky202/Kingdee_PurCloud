package com.kingdee.purchase.platform.service;

import java.util.List;

import com.kingdee.purchase.openapi.model.SupplierInfo;

public interface ISupplierService {
	
	/**
	 * 根据destId获取供应商信息
	 * @param id
	 * @return
	 */
	public SupplierInfo getSupplierInfoByDestId(String destId);

	/**
	 * 保存供应商
	 * @param info
	 */
	public int saveSupplier(SupplierInfo supplier);
	
	/**
	 * 保存多个供应商
	 * @param suppliers
	 */
	public int saveSuppliers(List<SupplierInfo> suppliers);

}
