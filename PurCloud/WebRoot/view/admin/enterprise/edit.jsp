<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<html lang="zh-cn">
  <head>
    <meta charset="UTF-8">    
  	<base href="<%=basePath%>"/>    
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>企业列表</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
    	.required{
    		color:red;
    		font-size:16px;
    	}
    </style>
 </head>
  <body>
   <%@include file="../../../nav.jsp"%>
	
	<div class="container" style="padding-top:80px;">	  
      <div class="row row-offcanvas row-offcanvas-right">
        <%@include file="../menu.html"%>
        <div class="col-xs-12 col-sm-9 sidebar-offcanvas">
		  <c:if test="${PAGE_KEY_ERROR!=null}">
		  	<div class="alert alert-danger alert-dismissable">
  				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		  		${PAGE_KEY_ERROR}
		  	</div>
		  </c:if>
		  <c:if test="${PAGE_KEY_SUCCESS!=null}">
		  	<div class="alert alert-success  alert-dismissable">
  				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		  		${PAGE_KEY_SUCCESS}
		  	</div>
		  </c:if>
	  
		  	<ol class="breadcrumb">
			  <li><a href="#">首页</a></li>
			  <li class="active">企业编辑</li>
			</ol>

          	<form class="form-horizontal" role="form" method="post" 
          	<c:choose>
          		<c:when test='${SESSION_KEY_USER.username=="administrator"}'>action="admin/enterprise/save"</c:when>
          		<c:otherwise>action="customer/enterprise/save"</c:otherwise>
          	</c:choose>
          	>
          		<div class="form-group">
				    <label for="email" class="col-sm-2 control-label">企业ID<span class="required">*</span></label>
				    <div class="col-sm-6">
				      <input type="text" class="form-control" name="enterpriseid" id="enterpriseid" readonly value="${PAGE_KEY_INFO.enterpriseid}">
				    </div>
				    <span class="col-sm-4">用于唯一标识企业</span>
			   </div>
			   <div class="form-group">
			    <label for="name" class="col-sm-2 control-label">企业名称<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" name="name" id="name" placeholder="金蝶软件" value="${PAGE_KEY_INFO.name}">
			    </div>
			    <span class="col-sm-4">企业的注册名称</span>
			  </div>
			  <div class="form-group">
			    <label for="mobile" class="col-sm-2 control-label">系统类型<span class="required">*</span></label>
			    <div class="col-sm-6">
					 <select class="form-control" id="erpType" name="erpType">
						<c:choose>
							<c:when test="${PAGE_KEY_INFO.erpType.value==2}">
					    	  <option value="1">EAS</option>					  
							  <option value="2" selected="selected">K3 Wise</option>
							  <option value="3">K3 Cloud</option>							
							</c:when>
							<c:when test="${PAGE_KEY_INFO.erpType.value==3}">
					    	  <option value="1">EAS</option>					  
							  <option value="2">K3 Wise</option>
							  <option value="3" selected="selected">K3 Cloud</option>							
							</c:when>
							<c:otherwise>
					    	  <option value="1" selected="selected">EAS</option>					  
							  <option value="2">K3 Wise</option>
							  <option value="3">K3 Cloud</option>							
							</c:otherwise>
						</c:choose>
					</select>
			    </div>
			    <span class="col-sm-4">接入的ERP系统类型</span>
			  </div>			  
			  <div class="form-group" style="display:none;">
			    <label for="mobile" class="col-sm-2 control-label">服务网址<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="phone" class="form-control" id="serviceUrl" name="serviceUrl" placeholder="http://ip:port/service" value="${PAGE_KEY_INFO.serviceUrl}">
			    </div>
			    <span class="col-sm-4">用于处理各种业务消息</span>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			    	<input type="hidden" id="id" name="id" value="${PAGE_KEY_INFO.id}"/>
			    	<input type="hidden" id="accountId" name="accountId" value="${PAGE_KEY_INFO.accountId}"/>
			      	<button type="submit" class="btn btn-primary">提交</button>
			      	<button type="reset" class="btn btn-default">重置</button>
			    </div>
			  </div>
			</form>
        </div>
      </div>
      
      <hr>
      <footer style=" padding:30px 0;">
        <p>&copy; 金蝶软件（中国）有限公司 2014</p>
      </footer>
    </div>
	
	<script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>