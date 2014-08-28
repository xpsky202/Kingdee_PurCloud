package com.kingdee.purchase.platform.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.kingdee.purchase.config.ConfigConstant;
import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.OutputResult;
import com.kingdee.purchase.platform.info.SucessEnum;
import com.kingdee.purchase.platform.processor.AbstractApiProcessor;
import com.kingdee.purchase.platform.util.ParamParseUtil;

public class URLRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public URLRequestServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = request.getRequestURL().toString();
		String urlPath = "", paramStr = "";
		int index = url.indexOf("?");
		if(index>0){
			urlPath = url.substring(0,index-1);
		}else{
			urlPath = url;
		}
		
		//解析queryString 和getParameterMap
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//queryString
		paramStr = request.getQueryString();
		Map<String, Object> params = ParamParseUtil.getRequestParams(paramStr);
		for(Map.Entry<String, Object> entry: params.entrySet()){
			if (!paramMap.containsKey(entry.getKey())){
				paramMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		//parameterMap
		Map<String, Object> reqMap = ParamParseUtil.getParameters(request);
		for(Map.Entry<String, Object> entry: reqMap.entrySet()){
			if (!paramMap.containsKey(entry.getKey())){
				paramMap.put(entry.getKey(), entry.getValue());
			}
		}

		String result;
		try {
			AbstractApiProcessor processor = new AbstractApiProcessor(urlPath, paramMap);
			JSONObject output = processor.execute();
			result = handleResult(output);
		} catch (BaseException e) {
			//返回异常结果
			result = handleException(e);
		}
		
		//utf-8
		response.setContentType(ConfigConstant.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstant.ENCODE);
		response.getOutputStream().write(result.getBytes(ConfigConstant.ENCODE));
	}
	
	/**
	 * 异常处理把异常信息包装为jsonString
	 * @param e
	 * @return
	 */
	public String handleException(BaseException e){
		OutputResult output = new OutputResult();
		output.setSuccess(SucessEnum.FAIL);
		output.setData(e.toJSONObject());
        return output.toJSONString();
	}
	
	/**
	 * 返回结果把Alibaba的返回结果包装为jsonString
	 * @param result
	 * @return
	 */
	public String handleResult(JSONObject result){
		OutputResult output = new OutputResult();
		output.setSuccess(SucessEnum.SUCCESS);
		output.setData(result);
		return output.toJSONString();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		
	}

}
