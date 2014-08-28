package com.kingdee.purchase.destapi.alibaba;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.alibaba.Company2AccountInfo;
import com.kingdee.purchase.platform.service.alibaba.ICompany2AccountServcie;
import com.kingdee.purchase.platform.util.SpringContextUtils;

/**
 * 轮询refreshToken是否过期任务处理器
 * @author RD_sky_lv
 *
 */
public class RefreshTokenTimerTask extends TimerTask {
	
	private static final Logger logger = LogManager.getLogger(RefreshTokenTimerTask.class);
	
	private static final ICompany2AccountServcie service = SpringContextUtils.getBean(ICompany2AccountServcie.class);
	private static final long Delay = 60 * 1000;
	private static final long Period = 12 * 60 * 60 * 1000;
	private static boolean isInit = false;
	
	/**
	 * 开始轮询refreshToken是否过期
	 */
	public static synchronized void beginTimer() {
		if (isInit) return;
		
		Timer timer = new Timer();
		timer.schedule(new RefreshTokenTimerTask(), Delay, Period);
		isInit = true;
	}
	
	@Override
	public void run() {
		try {
			logger.info("begin RefreshTokenTimerTask");
			List<Company2AccountInfo> companyList = service.getAllCompany2AccountList();
			for (Company2AccountInfo info : companyList) {
				String refreshToken = info.getToken();
				if (refreshToken == null || refreshToken.length() == 0) {
					continue;
				}
				if (!isOutOfDate(info.getExpiredDate())) {
					continue;
				}
				logger.info("company:" + info.getCompanyName() + "'s token is out of date");
				//重新刷新refreshToken
				AuthorizationToken token = AlibabaApiCallService.getAuthorizationToken(refreshToken);
				Date refresh_token_timeout = token.getRefresh_token_timeout();
				if (refresh_token_timeout == null) {
					Calendar cd = Calendar.getInstance();
					cd.add(Calendar.MONTH, 6);
					refresh_token_timeout = cd.getTime();
				}
				service.updateCompany2AccountMapping(info.getEnterpriseId(), info.getCompanyId(), token
						.getMemberId(), token.getRefresh_token(), refresh_token_timeout);
				logger.info("company:" + info.getCompanyName() + "'s token is refreshed");
			}
		} catch (BaseException e) {
			logger.error(e.toJSONString());
		}
	}
	
	/**
	 * 判断refreshToken是否快要过期
	 * @param expiredDate
	 * @return
	 */
	private boolean isOutOfDate(Date expiredDate) {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, 2);
		//如果refreshToken还差2天过期，重新取refreshToken
		if (cd.getTime().after(expiredDate)) {
			return true;
		}
		
		return false;
	}

}
