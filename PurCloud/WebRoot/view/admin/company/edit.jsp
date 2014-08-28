<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<html lang="zh-cn">
  <head>
    <meta charset="UTF-8"/>    
  	<base href="<%=basePath%>"/>    
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>公司授权</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
    	.required{
    		color:red;
    		font-size:16px;
    	}
    </style>
     <script type="text/javascript">
    	function submitForm(){
    		//校验
    		var error = false;
    		$("input[name='accountid']").each(
    			function(i,obj){
    				if(obj.value==""){alert("第"+(i+1)+"行的1688主账号为空，请重新输入");obj.focus();error = true;return false;}
    			}
    		);    		
    		if(error){return false;}
    		$("input[name='token']").each(
    			function(i,obj){
    				if(obj.value==""){alert("第"+(i+1)+"行的1688授权token为空，请重新输入");obj.focus();error = true;return false;}
    			}
    		);
    		if(error){return false;}
    		$("#formId").submit();
    	}
    	function refresh(){
    		window.location.href="customer/company/edit";
    	}
    	function deleteCompany(obj) {
    		event.preventDefault();
    		if(confirm("确认要删除该公司吗？")) {
    			var companyId = $(obj).val();
    			$.get("customer/company/delete?companyId=" + companyId, 
    				function(data,status){
    					if (status == 'success') {
    						$(obj).parent().parent().eq(0).remove();
    					}
    				}
    			);
    		}
    	}
    </script>
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
			  <li class="active">公司授权</li>
			</ol>
          	<form action="customer/company/save" method="post" id="formId">
				<table class="table">
					<thead>
						<tr align="center">
							<th>编号</th>
							<th>公司名称</th>
							<th align="center">1688令牌</th>
							<th>有效期</th>
							<th>授权</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="info" items="${PAGE_KEY_LIST}" varStatus="s">
							<tr>
								<td style="width:7%;">${s.count}</td>
								<td style="width:20%;">${info.companyName}</td>
								<td style="width:35%;">${info.token}</td>
								<td style="width:15%;">${info.expiredDate}</td>
								<td style="width:15%;"><a href="${info.signUrl}" target="blank">去1688授权</a></td>
								<td style="width:8%;"><button type="" onclick="deleteCompany(this);" value="${info.companyId}">删除</button>
							</tr>						
						</c:forEach>
					</tbody>
					<tr>
						<td colspan="5" align="center" style="height:100px;" valign="middle">
							<div class="form-group">
						      	<button type="button" class="btn btn-primary" onclick="refresh();">刷新</button>
					      	</div>
						</td>	
					</tr>
				</table>
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