package com.kingdee.purchase.platform.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingdee.purchase.platform.exception.BaseException;
import com.kingdee.purchase.platform.info.api.ApiBaseInfo;
import com.kingdee.purchase.platform.info.api.ApiErrorCodeInfo;
import com.kingdee.purchase.platform.info.api.ApiInputParamInfo;
import com.kingdee.purchase.platform.info.api.ApiOutputParamInfo;
import com.kingdee.purchase.platform.service.IAPIRegisterService;
import com.kingdee.purchase.platform.util.ParamParseUtil;
import com.kingdee.purchase.platform.util.SpringContextUtils;
import com.kingdee.purchase.platform.util.StringUtils;

public class OpenApiRegisterServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	
	private final static String EIDTUI = "/view/admin/doc/register/apiedit.jsp";
	private final static String LISTUI = "/view/admin/doc/register/apilist.jsp";
	
	/**
	 * Constructor of the object.
	 */
	public OpenApiRegisterServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		if(url.endsWith("/admin/doc/register/save")){
			saveApiRegisterInfo(request,response);
		}else if(url.endsWith("/admin/doc/register/apilist")){
			listApiRegisterInfo(request,response);
		}else if(url.indexOf("/admin/doc/register/view/")!=-1){
			try{
				String idStr = url.substring(url.lastIndexOf("/")+1);
				int id = Integer.parseInt(idStr);
				viewApiRegisterInfo(request,response,id);
			}catch(Exception e){
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
				listApiRegisterInfo(request, response);
			}
		}else if(url.indexOf("/admin/doc/register/delete/")!=-1){
			try{
				String idStr = url.substring(url.lastIndexOf("/")+1);
				int id = Integer.parseInt(idStr);
				SpringContextUtils.getBean(IAPIRegisterService.class).deleteById(id);
				listApiRegisterInfo(request,response);
			}catch(Exception e){
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
				listApiRegisterInfo(request, response);
			}
		}
		
		return ;

	}
	
	private void viewApiRegisterInfo(HttpServletRequest request,HttpServletResponse response,int id) throws ServletException, IOException {
		ApiBaseInfo info;
		try {
			info = SpringContextUtils.getBean(IAPIRegisterService.class).get(id);
			
			request.setAttribute(WebConstant.PAGE_KEY_INFO, info);
			request.setAttribute(WebConstant.PAGE_KEY_INPUTLIST, info.getInputParamList());
			request.setAttribute(WebConstant.PAGE_KEY_OUTPUTLIST, info.getOutputParamList());
			request.setAttribute(WebConstant.PAGE_KEY_ERRORLIST, info.getErrorCodeList());
		} catch (BaseException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		
		request.getRequestDispatcher(EIDTUI).forward(request, response);
		return ;		
	}

	private void listApiRegisterInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String condition = ParamParseUtil.getString(request, "condition");
		List<ApiBaseInfo> list;
		try {
			list = SpringContextUtils.getBean(IAPIRegisterService.class).query(condition);
			request.setAttribute(WebConstant.PAGE_KEY_LIST, list);
		} catch (BaseException e) {
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
		}
		request.setAttribute("condition", condition);
		request.getRequestDispatcher(LISTUI).forward(request, response);
	}

	/***
	 * 注册API信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void saveApiRegisterInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		ApiBaseInfo apiInfo = new ApiBaseInfo();
		
		StringBuilder errorStr = createApiBaseInfo(request, apiInfo);
		
		
		createApiInputInfo(request, apiInfo);
		createApiOutputInfo(request,apiInfo);
		createApiErrorInfo(request, apiInfo);
		
		if(errorStr.length()>0){
			request.setAttribute(WebConstant.PAGE_KEY_ERROR, errorStr);
		}else{
			try {
				SpringContextUtils.getBean(IAPIRegisterService.class).registerService(apiInfo);
				request.setAttribute(WebConstant.PAGE_KEY_SUCCESS, "api注册成功");
			} catch (BaseException e) {
				request.setAttribute(WebConstant.PAGE_KEY_ERROR, e.getMessage());
			}
		}
		
		request.setAttribute(WebConstant.PAGE_KEY_INFO, apiInfo);
		request.setAttribute(WebConstant.PAGE_KEY_INPUTLIST, apiInfo.getInputParamList());
		request.setAttribute(WebConstant.PAGE_KEY_OUTPUTLIST, apiInfo.getOutputParamList());
		request.setAttribute(WebConstant.PAGE_KEY_ERRORLIST, apiInfo.getErrorCodeList());
		request.getRequestDispatcher(EIDTUI).forward(request, response);
	}

	/**
	 * 创建apibaseInfo
	 * @param request
	 * @param apiInfo
	 * @return
	 */
	private StringBuilder createApiBaseInfo(HttpServletRequest request,
			ApiBaseInfo apiInfo) {
		StringBuilder errorStr = new StringBuilder();
		apiInfo.setId(ParamParseUtil.getLong(request, "id",1));		
		
		String subSystem = ParamParseUtil.getString(request, "subSystem");		
		if(StringUtils.isEmpty(subSystem)){
			errorStr.append("子系统不能为空").append("<br/>");
		}else{
			apiInfo.setSubSystem(StringUtils.urlDecode(subSystem));
		}
		
		String subSystemAlias = ParamParseUtil.getString(request, "subSystemAlias");		
		if(!StringUtils.isEmpty(subSystemAlias)){
			apiInfo.setSubSystemAlias(StringUtils.urlDecode(subSystemAlias));
		}
		
		String apiName = ParamParseUtil.getString(request, "apiName");		
		if(StringUtils.isEmpty(apiName)){
			errorStr.append("api名称不能为空").append("<br/>");
		}else{
			apiInfo.setApiName(apiName);
		}
		
		String apiAlias = ParamParseUtil.getString(request, "apiAlias");		
		if(StringUtils.isEmpty(apiAlias)){
			errorStr.append("api别名不能为空").append("<br/>");
		}else{
			apiInfo.setApiAlias(StringUtils.urlDecode(apiAlias));
		}
		
		int version = ParamParseUtil.getInt(request, "version",1);		
		apiInfo.setVersion(version);
		
		String remark = ParamParseUtil.getString(request, "remark");		
		if(StringUtils.isEmpty(remark)){
			errorStr.append("api业务描述不能为空").append("<br/>");
		}else{
			apiInfo.setRemark(StringUtils.urlDecode(remark));
		}
		apiInfo.setState("Enable");
		return errorStr;
	}
	
	
	
	private StringBuilder createApiInputInfo(HttpServletRequest request,ApiBaseInfo apiInfo){
		List<ApiInputParamInfo> inputCol = new ArrayList<ApiInputParamInfo>();
		ApiInputParamInfo inputInfo = null;
		Map<String, String[]> map = ParamParseUtil.getParameters4Register(request);
		if (map.containsKey("infield")) {
			String[] fields = map.get("infield");
			for(int i= 0, size = fields.length; i < size ; i++){
				inputInfo = new ApiInputParamInfo();
				
				inputInfo.setField(fields[i]);
				
				if (map.containsKey("intype")){
					inputInfo.setType(map.get("intype")[i]);
				}else{
					inputInfo.setType("String");
				}
				
				if (map.containsKey("indescription")){
					inputInfo.setDescription(map.get("indescription")[i]);
				}
				
				if (map.containsKey("inisNull")){
					inputInfo.setIsNull("on".equalsIgnoreCase((String)map.get("inisNull")[i]));
				}
				
				if (map.containsKey("inisSysParam")){
					inputInfo.setIsSysParam("on".equalsIgnoreCase((String)map.get("inisSysParam")[i]));
				}
				
				
				if (map.containsKey("inexample")){
					inputInfo.setExample(map.get("inexample")[i]);
				}
				
				if (map.containsKey("indefaultValue")){
					inputInfo.setDefaultValue(map.get("indefaultValue")[i]);
				}
				
				inputCol.add(inputInfo);
			}
		}
		apiInfo.getInputParamList().addAll(inputCol);
		
		
		return null;
	}
	
	
	private StringBuilder createApiOutputInfo(HttpServletRequest request,
			ApiBaseInfo apiInfo){
		
		List<ApiOutputParamInfo> outputCol = new ArrayList<ApiOutputParamInfo>();
		ApiOutputParamInfo outputInfo = null;
		Map<String, String[]> map = ParamParseUtil.getParameters4Register(request);
		if (map.containsKey("outfield")) {
			String[] fields = map.get("outfield");
			for(int i= 0, size = fields.length; i < size ; i++){
				outputInfo = new ApiOutputParamInfo();
				
				outputInfo.setField(fields[i]);
				
				if (map.containsKey("outtype")){
					outputInfo.setType(map.get("outtype")[i]);
				}else{
					outputInfo.setType("String");
				}
				
				if (map.containsKey("outdescription")){
					outputInfo.setDescription(map.get("outdescription")[i]);
				}
				
				if (map.containsKey("outisNull")){
					outputInfo.setIsNull(true);
				}else {
					outputInfo.setIsNull(false);
				}

				if (map.containsKey("outexample")){
					outputInfo.setExample(map.get("outexample")[i]);
				}
				
				outputCol.add(outputInfo);
			}
		}
		apiInfo.getOutputParamList().addAll(outputCol);
		
		return null;
	}
	
	
	private StringBuilder createApiErrorInfo(HttpServletRequest request,ApiBaseInfo apiInfo){
		List<ApiErrorCodeInfo> errorCol = new ArrayList<ApiErrorCodeInfo>();
		ApiErrorCodeInfo errorInfo = null;
		Map<String, String[]> map = ParamParseUtil.getParameters4Register(request);
		
		if (map.containsKey("ecode")) {
			String[] codes = map.get("ecode");
			for(int i= 0, size = codes.length; i < size ; i++){
				errorInfo = new ApiErrorCodeInfo();
				
				errorInfo.setCode(codes[i]);
				if (map.containsKey("edescription")){
					errorInfo.setDescription(map.get("edescription")[i]);
				}

				if (map.containsKey("edealSolution")){
					errorInfo.setDealSolution(map.get("edealSolution")[i]);
				}
				
				errorCol.add(errorInfo);
			}
		}
		apiInfo.getErrorCodeList().addAll(errorCol);
		return null;
	}
	

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {}
}