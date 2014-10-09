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
		.form-signup {
			max-width: 780px;
			padding-top: 20px;
			padding-left:10px;
			padding-right:10px;
			padding-bottom:10px;
			margin: 0 auto;
		}
    </style>
 </head>
  <body>
    <%@include file="nav.jsp"%>
	<div class="container" style="padding-top:80px;">	  
      <div class="form-signup">
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
			  <li class="active">用户注册</li>
			</ol>

          	<form class="form-horizontal" role="form" method="post" action="account">
          	<div class="form-group">
			    <label for="name" class="col-sm-2 control-label">用户名<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" required name="username" id="username" 
			      		 placeholder="san_zhang" value="${PAGE_KEY_INFO.username}">
			    </div>
			    <span class="col-sm-4">数字、字母、下划线，6-20位字符</span>
			  </div>
			  <div class="form-group">
			    <label for="email" class="col-sm-2 control-label">邮箱<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="email" class="form-control" id="email" required name="email" placeholder="san_zhang@kingdee.com" value="${PAGE_KEY_INFO.email}">
			    </div>
			    <span class="col-sm-4">管理员邮箱，用于系统登录和消息接收</span>
			  </div>
			  <div class="form-group">
			    <label for="contact" class="col-sm-2 control-label">昵称<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" id="displayname" required name="displayname" placeholder="张三" value="${PAGE_KEY_INFO.displayname}">
			    </div>
			    <span class="col-sm-4">2-20位字符</span>
			  </div>
			  <div class="form-group">
			    <label for="mobile" class="col-sm-2 control-label">密码<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="password" required name="password" placeholder="字母、数字、下划线">
			    </div>
			    <span class="col-sm-4">6-20位字符</span>
			  </div>
			  <div class="form-group">
			    <label for="mobile" class="col-sm-2 control-label">密码确认<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="repassword" required name="repassword" placeholder="同上">
			    </div>
			    <span class="col-sm-4">与密码相同</span>
			  </div>
			  <div class="form-group">
			    <label for="mobile" class="col-sm-2 control-label">联系电话</label>
			    <div class="col-sm-6">
			      <input type="phone" class="form-control" id="mobile" name="mobile" placeholder="13800138000" value="${PAGE_KEY_INFO.mobile}">
			    </div>
			    <span class="col-sm-4">联系方式</span>
			  </div>
			  <div class="form-group">
			    <label for="name" class="col-sm-2 control-label">企业名称<span class="required">*</span></label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" name="enterpriseName" id="enterpriseName" placeholder="金蝶软件" value="${enterpriseName}">
			    </div>
			    <span class="col-sm-4">企业的注册名称</span>
			  </div>
			  <div class="form-group">
			    <label for="mobile" class="col-sm-2 control-label">系统类型<span class="required">*</span></label>
			    <div class="col-sm-6">
			    	 <select class="form-control" id="erpType" name="erpType">
						<c:choose>
							<c:when test="${erpType==2}">
					    	  <option value="1">EAS</option>					  
							  <option value="2" selected="selected">K3 Wise</option>
							  <option value="3">K3 Cloud</option>							
							</c:when>
							<c:when test="${erpType==3}">
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
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			    	<input type="hidden" id="type" name="type" value="create"/>
			    	<input type="hidden" id="id" name="id" value="${PAGE_KEY_INFO.id}"/>
			      	<button type="submit" class="btn btn-primary">提交</button>&nbsp;&nbsp;
			      	<button type="reset" class="btn btn-default">重置</button>&nbsp;&nbsp;
			      	<a href="login.jsp">登录</a>
			    </div>
			  </div>
			</form>
      </div>
      
      <br>
      <footer style="position:fixed;top:95%;left:1%">
        <p>&copy; 金蝶软件（中国）有限公司 2014</p>
      </footer>
    </div>
	
	<script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>