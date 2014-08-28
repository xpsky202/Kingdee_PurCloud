package com.kingdee.purchase.destapi.alibaba.apihandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.openapi.client.ErrorHandler;
import com.alibaba.openapi.client.util.DateUtil;
import com.kingdee.purchase.config.ConfigConstant;
import com.kingdee.purchase.destapi.alibaba.exceptionhandler.PostBuyOffExceptionHandler;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.exception.PurBizException;
import com.kingdee.purchase.platform.exception.PurExceptionDefine;

public class PostBuyOfferHandler extends AbstractDestApiHandler {

	@Override
	protected Map<String, Object> getApiParams() throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>(ctx.getBussinessParams());
		Object value = params.get("gmtQuotationExpire");
		if (value instanceof Date) {
			params.put("gmtQuotationExpire", value);
		} else {
			String gmtQuotationExpire = params.get("gmtQuotationExpire").toString();
			DateFormat format = new SimpleDateFormat(ConfigConstant.DateFormatPattern);
			try {
				value = format.parse(gmtQuotationExpire);
			} catch (ParseException e) {
				throw new PurBizException(PurExceptionDefine.ILLEGAL_DATE_FORMAT, new String[] { value.toString(),
						ConfigConstant.DateFormatPattern });
			}
			params.put("gmtQuotationExpire", DateUtil.format((Date) value));
		}

		return params;
	}

	/**
	 * 获取api名称
	 * 
	 * @return
	 */
	@Override
	protected String getApiName() {
		return "caigou.api.buyoffer.postBuyoffer";
	}

	/**
	 * 获取api版本
	 * 
	 * @return
	 */
	@Override
	protected int getApiVersion() {
		return 1;
	}
	
	/**
	 * 是否需要子账号，根据具体API要求重载
	 * @return
	 */
	@Override
	protected boolean isNeedSubAccount() {
		return true;
	}

	/**
	 * 获取api异常处理器
	 * 
	 * @return
	 */
	@Override
	protected ErrorHandler getApiErrorHandler() {
		return new PostBuyOffExceptionHandler();
	}

}
