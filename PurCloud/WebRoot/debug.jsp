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
			height: auto;
			padding: 10px;
			-webkit-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
		}
		.form-signin .form-control:focus {
			z-index: 2;
		}
		.form-signin input[type="text"] {
			margin-bottom: -1px;
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
	      <div id="myTabContent" class="tab-content">
	        <div class="tab-pane fade in active" id="userdiv">
		      <form class="form-signin" role="form" method="post" action="debug">
		        <input type="text" name="url" class="form-control" placeholder="url" value="" required autofocus />
		        <input type="text" name="method" class="form-control" placeholder="method" value="post" required/>
		        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
		      </form>
	        </div>
	      </div>
      </div>
   	<br>
      <footer style=" padding:30px 0;">
        <p>&copy; 金蝶软件（中国）有限公司 2014</p>
      </footer>
    </div>
  </body>
	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/bootstrap.js"></script>
</html>