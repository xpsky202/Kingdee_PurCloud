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
    <title>OpenApi列表</title>
    <link href="css/bootstrap.css" rel="stylesheet"/>
    <script type="text/javascript">
    	function addnew(){
    		window.location.href="view/admin/doc/register/apiedit.jsp";
    	}
    	function view(id){
    		window.location.href="admin/doc/register/view/"+id;
    	}
    	function query(){
    		var v = $("#condition").val(); 
    		if(v==""){
    			$("#condition").focus();
    			return false;
    		}
			window.location.href="admin/doc/register/apilist?condition="+v;   	
    	}
    	function del(id){
    		window.location.href="admin/doc/register/delete/"+id;
    	}
    </script>
 </head>
  <body>
  	 <%@include file="../../../../nav.jsp"%>
	
	<div class="container" style="padding-top:80px;">
  	<div class="row row-offcanvas row-offcanvas-right">
     	<%@include file="../../menu.html"%>
        <div class="col-xs-12 col-sm-9 sidebar-offcanvas">
        
      			<c:if test="${PAGE_KEY_ERROR!=null}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">
						&times;
					</button>
					${PAGE_KEY_ERROR}
				</div>
			</c:if>
			<c:if test="${PAGE_KEY_SUCCESS!=null}">
				<div class="alert alert-success  alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">
						&times;
					</button>
					${PAGE_KEY_SUCCESS}
				</div>
			</c:if>
					
        	<ol class="breadcrumb">
			  <li><a href="#">首页</a></li>
			  <li class="active">OpenApi列表</li>
			</ol>
          	
          	<div class="row">
			  <div class="col-lg-7">
			    <div class="btn-group">
		          	<button  class="btn btn-primary" type="button" onclick="addnew();">新增</button>
		          	<button  class="btn btn-primary disabled" type="button">禁用</button>
		          	<button  class="btn btn-primary disabled" type="button">启用</button>
			    </div>
			  </div>
			  
			  <div class="col-lg-5">
			    <div class="input-group">
			      <input type="text" name="condition" id="condition" class="form-control" placeholder="api名称、别名" value="${condition}">
			      <span class="input-group-btn">
			        <button class="btn" type="button" onclick="query();">Go!</button>
			      </span>
			    </div>
			  </div>
			</div>          	
          	
          	<br>
			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th class="col-lg-1">选择</th>
						<th class="col-lg-1">序号</th>
						<th class="col-lg-1">子系统</th>
						<th class="col-lg-3">api名称</th>
						<th class="col-lg-3">别名</th>
						<th class="col-lg-1">版本</th>
						<th class="col-lg-1">状态</th>
						<th class="col-lg-1">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="info" items="${PAGE_KEY_LIST}" varStatus="s">
						<tr>
							<td><input type="checkbox" name="select" value="${info.id}"/></td>
							<td>${s.count}</td>
							<td>${info.subSystem}</td>
							<td>${info.apiName}</td>
							<td>${info.apiAlias}</td>
							<td>${info.version}</td>
							<td>${info.state}</td>
							<td><a href="javascript:view(${info.id});"><span class="label label-warning">Edit</span></a></td>
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