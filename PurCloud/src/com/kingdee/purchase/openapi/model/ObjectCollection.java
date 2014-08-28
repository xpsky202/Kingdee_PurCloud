package com.kingdee.purchase.openapi.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

/**
 * 对象集合类
 * @author RD_sky_lv
 *
 * @param <T>
 */
public class ObjectCollection<T extends IJSONTransfer> {
	
	protected List<T> objectList = new ArrayList<T>();

	public List<T> getItemsList() {
		return objectList;
	}

	public void setItemsList(List<T> itemsList) {
		this.objectList = itemsList;
	}
	
	public T getItem(int index) {
		if (index >= 0 && index < size()) {
			return objectList.get(index);
		}
		
		return null;
	}
	
	public boolean addItem(T item) {
		if (item == null || objectList.indexOf(item) > -1) {
			return false;
		}
		return objectList.add(item);
	}
	
	public int size() {
		return objectList.size();
	}
	
	public JSONArray toJSONArray() {
		JSONArray result = new JSONArray();
		for (int i = 0; i < size(); i++) {
			result.add(getItem(i).toJSONObject());
		}
		
		return result;
	}

}
