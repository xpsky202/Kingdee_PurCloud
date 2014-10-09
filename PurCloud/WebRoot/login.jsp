<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String url = request.getParameter("url");
url=url==null?"":url;
%> 
<html lang="zh-cn">
  <head>
    <meta charset="utf-8"/>
  	<base href="<%=basePath%>"/>  
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>企业登录</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
		body {
		  padding-top: 100px;
		  padding-bottom: 40px;
		  background-color: #eee;
		  background-color: #fafafa;
		  box-shadow: inset 0 3px 6px rgba(0,0,0,.05);
		  border-color: #e5e5e5 #eee #eee;
		  border-width: 1px 0;		
		 }
		.form-signin {
			max-width: 360px;
			padding-top: 20px;
			padding-left:10px;
			padding-right:10px;
			padding-bottom:10px;
			margin: 0 auto;
		}
		.form-signin .form-control {
			position: relative;
			font-size: 16px;
			height: 35px;
			padding: 10px;
			-webkit-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
		}
		.form-signin .form-control:focus {
			z-index: 2;
		}
		.form-signin input[type="text"] {
			margin-bottom: 5px;
			border-bottom-left-radius: 0;
			border-bottom-right-radius: 0;
		}
		.form-signin input[type="password"] {
			margin-bottom: 10px;
			border-top-left-radius: 0;
			border-top-right-radius: 0;
		}
	</style>
  </head>
  <body>
   <%@include file="nav.jsp"%>
    <div class="container">
      <div class="form-signin">
		  <c:if test="${PAGE_KEY_ERROR!=null}">
		  	<div class="alert alert-danger alert-dismissable">
  				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		  		${PAGE_KEY_ERROR}
		  	</div>
		  </c:if>
	      <ul id="myTab" class="nav nav-tabs">
	        <li class="active"><a href="#userdiv" data-toggle="tab">用户名登录</a></li>
	        <li><a href="#emaildiv" data-toggle="tab">邮箱登录</a></li>
	      </ul>
	      <div id="myTabContent" class="tab-content">
	        <div class="tab-pane fade in active" id="userdiv">
		      <form class="form-signin" role="form" method="post" action="login">
		        <input type="text" name="username" class="form-control" placeholder="用户名" value="${username}" required autofocus />
		        <input type="password" name="password" class="form-control" placeholder="密码" value="${password}" required id="upasswordid"/>
		        <input type="hidden" name="type" value="userlogin"/>
		        <input type="hidden" name="url" value="<%=url%>"/>
		        <button class="btn btn-primary btn-block" type="submit">登录</button>
		      </form>
	        </div>
	        <div class="tab-pane fade" id="emaildiv">
	          <form class="form-signin" role="form" method="post" action="login">
		        <input type="text" name="email" class="form-control" placeholder="邮箱" value="${email}" required autofocus>
		        <input type="password" name="password" class="form-control" placeholder="密码" value="${password}" required id="epassword">
		        <input type="hidden" name="type" value="emaillogin"/>
		        <input type="hidden" name="url" value="<%=url%>"/>
		        <button class="btn btn-primary btn-block" type="submit">登录</button>
		      </form>
	        </div>
	      </div>
      
       <div class="form-signin">
	      &nbsp;&nbsp;<a href="signup.jsp"><b>注 册</b></a>&nbsp;&nbsp;&nbsp;&nbsp;
	      &nbsp;&nbsp;<a>忘记密码?</a>
      </div>
     </div>
   	<br>
	<hr>
      <footer style="position:fixed;top:95%;left:1%">
        <p>&copy; 金蝶软件（中国）有限公司 2014</p>
      </footer>
    </div>
  </body>
	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var email = "${email}";
			var username = "${username}";
			if(username!=""){
				$('#myTab a[href="#userdiv"]').tab('show');
				$("#upasswordid").attr("autofocus","autofocus");
				return false;
			}else if(email!=""){
				$('#myTab a[href="#emaildiv"]').tab('show');
				$("#epassword").attr("autofocus","autofocus");
				return false;
			}
		});
	</script>
</html>