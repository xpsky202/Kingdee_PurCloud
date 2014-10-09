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
    <title>密码重置</title>
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
			  <li class="active">密码重置</li>
			</ol>
          	<form class="form-horizontal" role="form" method="post" action="customer/account/chgpwd">
			  <div class="form-group">
			    <label for="email" class="col-sm-2 control-label">原密码<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="oldPassword" required name="oldPassword">
			    </div>
			    <span class="col-sm-4">账号原来的密码</span>
			  </div>
			  <div class="form-group">
			    <label for="contact" class="col-sm-2 control-label">新密码<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="password" required name="password">
			    </div>
			    <span class="col-sm-4">账号新密码</span>
			  </div>
			  <div class="form-group">
			    <label for="contact" class="col-sm-2 control-label">确认密码<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="rePassword" required name="rePassword">
			    </div>
			    <span class="col-sm-4">与新密码一致</span>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			    	<input type="hidden" id="type" name="type" value="save"/>
			    	<input type="hidden" id="id" name="id" value="${PAGE_KEY_INFO.id}"/>
			      	<button type="submit" class="btn btn-primary">提交</button>&nbsp;&nbsp;
			      	<button type="reset" class="btn btn-default">重置</button>&nbsp;&nbsp;
			    </div>
			  </div>
			</form>
        </div>
      </div>
      
      <hr>
      <footer style="position:fixed;top:95%;left:1%">
        <p>&copy; 金蝶软件（中国）有限公司 2014</p>
      </footer>
    </div>
	
	<script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>