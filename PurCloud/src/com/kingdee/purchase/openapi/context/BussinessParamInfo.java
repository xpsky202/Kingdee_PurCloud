package com.kingdee.purchase.openapi.context;

import java.util.Map;

public class BussinessParamInfo {
	
	protected Map<String,Object> params;
	
	public BussinessParamInfo () {
		
	}
	
	public BussinessParamInfo (Map<String, Object> businessParams) {
		params = businessParams;
	}


}
