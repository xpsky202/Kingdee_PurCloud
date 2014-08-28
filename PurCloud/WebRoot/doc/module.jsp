<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.kingdee.purchase.platform.servlet.WebConstant"%>
<%@page import="com.kingdee.purchase.platform.info.api.ApiBaseInfo"%>
<%@page import="com.kingdee.purchase.platform.util.StringUtils"%>

<div class="col-md-3" id="sidebar" role="navigation">
	<div class="bs-sidebar hidden-print affix well" role="complementary">
		<ul class="nav bs-sidenav">
			<li>
				<b>平台概述</b>
				<ul class="nav">
					<li><a href="#pplatform-info">• 平台介绍</a></li>
					<li><a href="#pplatform-use">• 调用说明</a></li>
					<li><a href="#pplatform-permission">• 权限说明</a></li>
					<li><a href="#pplatform-errcode">• 全局错误码</a></li>
				</ul>
			</li>
			<%
			//因为jstl的语法不支持相邻两个元素的比较(判断是不是同一个子系统[模块])，因此直接用java写了，不友好
			List<ApiBaseInfo> apiList = (List<ApiBaseInfo>)request.getAttribute(WebConstant.PAGE_KEY_LIST);
			if(apiList!=null && apiList.size()>0){
				String preSubSystem = null;
				StringBuilder sb = new StringBuilder();
				for(ApiBaseInfo api:apiList){
					String subSystem = "#p"+api.getSubSystem();
					if(StringUtils.isEmpty(preSubSystem)){
						//第一个模块
						preSubSystem = subSystem;
						sb.append("<li>")
						  .append("<b>").append(api.getSubSystemAlias()).append("</b>")
						  .append("<ul class=\"nav\">");
					}else if(!preSubSystem.equals(subSystem)){
						//新模块
						preSubSystem = subSystem;					
						
						sb.append("</ul></li>");
												
						sb.append("<li>")
						  .append("<b>").append(api.getSubSystemAlias()).append("</b>")
						  .append("<ul class=\"nav\">");
					}
					
					String apiPanel = subSystem+"-"+api.getApiNameVo();
					sb.append("<li><a href=\"").append(apiPanel).append("\">• ").append(api.getApiAlias()).append("</a></li>");
				}
				
				sb.append("</ul></li>");
				out.write(sb.toString());
			}
			%>	
		</ul>
	</div>
</div>