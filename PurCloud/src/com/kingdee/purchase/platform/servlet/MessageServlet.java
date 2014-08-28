package com.kingdee.purchase.platform.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kingdee.purchase.platform.info.msg.MessageInfo;
import com.kingdee.purchase.platform.service.message.MessagePushFacade;
import com.kingdee.purchase.platform.util.ParamParseUtil;
import com.kingdee.purchase.platform.util.StringUtils;

/***
 * 消息中心
 * @author RD_jiangkun_zhu
 */
public class MessageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7086691835893248293L;
	private final static Logger logger = LogManager.getLogger(MessageServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		//unsupported
		return ;
	}

	/**
	 * 暂时只是透传，没有做任何业务
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message = ParamParseUtil.getString(request, "message");
		if(logger.isErrorEnabled()){
			logger.error("message:" +message);
		}
		if(StringUtils.isEmpty(message)){
			return ;
		}
		
		MessageInfo msgInfo = null;
		try {
			msgInfo = MessageInfo.parse(message);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ;
		}
		
		MessagePushFacade.process(msgInfo);
	}
}