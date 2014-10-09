<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html lang="zh-cn">
  <head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>采购开放平台</title>
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
		.starter-template {
		  padding: 40px 15px;
		  text-align: center;
		}
		
    </style>
 </head>
  <body>
   <%@include file="nav.jsp"%>

    <div class="container"> 
	    <div class="row" style="height:650px"> 
	        <div class="span12"> 
	            <div class="thumbnail center well well-small text-center" style="height:500px"> 
	                 <h2>欢迎访问金蝶网络采购开放平台</h2>
	                 <img src="res/ali_1.jpg" alt="图片无法显示"></img>
	                 <img src="res/ali_2.jpg" alt="图片无法显示"></img>
	                 <img src="res/ali_3.jpg" alt="图片无法显示" usemap="#Map"></img>
 				 	 
	            </div>
	            <div class="center well well-small text-center">
	            	<img src="res/ali_4.jpg" alt="图片无法显示"></img>
	            </div>
	        </div>
	    </div>
	</div>
	<map name="Map" id="Map">
  		<area shape="rect" coords="780,50,959,102" href="login.jsp" target="_self"/>
	</map>
	
	<hr>
	<footer style="position:fixed;top:95%;left:1%">
        <p>&copy; 金蝶软件（中国）有限公司 2014</p>
    </footer>
    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
 
</html>