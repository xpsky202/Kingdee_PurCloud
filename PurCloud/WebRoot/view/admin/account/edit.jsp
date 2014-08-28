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
    <title>账号编辑</title>
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
			  <li class="active">账号编辑</li>
			</ol>
          	<form class="form-horizontal" role="form" method="post" action="customer/account/save">
          	<div class="form-group">
			    <label for="name" class="col-sm-2 control-label">用户名<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" required name="username" id="username" placeholder="san_zhang" value="${PAGE_KEY_INFO.username}" readonly>
			    </div>
			    <span class="col-sm-4">用于系统登录</span>
			  </div>
			  <div class="form-group">
			    <label for="email" class="col-sm-2 control-label">注册邮箱<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="email" class="form-control" id="email" required name="email" placeholder="san_zhang@kingdee.com" value="${PAGE_KEY_INFO.email}">
			    </div>
			    <span class="col-sm-4">用于系统登录和接收云平台消息</span>
			  </div>
			  <div class="form-group">
			    <label for="contact" class="col-sm-2 control-label">昵称<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" id="displayname" required name="displayname" placeholder="张三" value="${PAGE_KEY_INFO.displayname}">
			    </div>
			    <span class="col-sm-4">显示名称</span>
			  </div>
			  <div class="form-group">
			    <label for="mobile" class="col-sm-2 control-label">联系电话</label>
			    <div class="col-sm-6">
			      <input type="phone" class="form-control" id="mobile" name="mobile" placeholder="13800138000" value="${PAGE_KEY_INFO.mobile}">
			    </div>
			    <span class="col-sm-4">联系方式</span>
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
      <footer style="padding:30px 0;">
        <p>&copy; 金蝶软件（中国）有限公司 2014</p>
      </footer>
    </div>
	
	<script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>