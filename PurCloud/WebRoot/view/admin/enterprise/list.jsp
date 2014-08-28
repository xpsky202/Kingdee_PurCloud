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
    <link href="css/bootstrap.css" rel="stylesheet"/>
    <script type="text/javascript">
    	function addnew(){
    		window.location.href="view/admin/enterprise/edit.jsp";
    	}
    	function view(){
    		var ret = null;
    		$("input[type='checkbox']").each(function(i,val){
    			if(val.checked==true){
    				ret = val.value;
    				return false;
    			}
    		});
    		
    		if(ret==null){
    			alert("请选择要查看的企业");
    		}else{
    			window.location.href="admin/enterprise/view/"+ret;
    		}
    	}
    	function query(){
    		var v = $("#condition").val(); 
    		if(v==""){
    			$("#condition").focus();
    			return false;
    		}
			window.location.href="admin/enterprise/list?condition="+v;   	
    	}
    </script>
 </head>
  <body>
   <%@include file="../../../nav.jsp"%>
	
	<div class="container" style="padding-top:80px;">
  	<div class="row row-offcanvas row-offcanvas-right">
        <%@include file="../menu.html"%>
        <div class="col-xs-12 col-sm-9 sidebar-offcanvas">
        	<ol class="breadcrumb">
			  <li><a href="#">首页</a></li>
			  <li class="active">企业列表</li>
			</ol>
          	
          	<div class="row">
			  <div class="col-lg-7">
			    <div class="btn-group">
		          	<button  class="btn btn-primary" type="button" onclick="addnew();">新增</button>
		          	<button  class="btn btn-primary" type="button" onclick="view();">查看</button>
			    </div>
			  </div>
			  <div class="col-lg-5">
			    <div class="input-group">
			      <input type="text" name="condition" id="condition" class="form-control" placeholder="名称" value="${condition}">
			      <span class="input-group-btn">
			        <button class="btn btn-default" type="button" onclick="query();">Go!</button>
			      </span>
			    </div>
			  </div>
			</div>          	
          	
          	<br>
			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th>选择</th>
						<th>企业ID</th>
						<th>名称</th>
						<th>注册邮箱</th>
						<th>联系人</th>
						<th>电话</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="info" items="${PAGE_KEY_LIST}" varStatus="s">
						<tr>
							<td><input type="checkbox" name="select" value="${info.id}"/></td>
							<td>${info.enterpriseid}</td>
							<td>${info.name}</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>						
					</c:forEach>
					
					<!-- class="success" -->
				</tbody>
			</table>
        </div>
      </div>
      
      <hr>
      <footer>
        <p>&copy; 金蝶软件（中国）有限公司 2014</p>
      </footer>
    </div>
	<script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>