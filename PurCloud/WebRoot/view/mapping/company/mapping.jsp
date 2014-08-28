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
    <title>公司主账号设置</title>
    <link href="css/bootstrap.css" rel="stylesheet"/>
    <script type="text/javascript">    	
    	function submitForm(){
    		//校验
    		var error = false;
    		$("input[name='accountid']").each(
    			function(i,obj){
    				if(obj.value==""){
    					alert("第"+(i+1)+"行的1688主账号为空，请重新输入");
    					obj.focus();
    					error = true;
    					return false;
    				}
    			}
    		);
    		
    		if(error){
    			return false;
    		}
    		
    		$("input[name='token']").each(
    			function(i,obj){
    				if(obj.value==""){
    					alert("第"+(i+1)+"行的1688授权token为空，请重新输入");
    					obj.focus();
    					error = true;
    					return false;
    				}
    			}
    		);
    		
    		if(error){
    			return false;
    		}
    		
    		$("#formId").submit();
    	}
    </script>
 </head>
  <body>	
	<div class="container" style="padding-top:80px;">
      <div class="row row-offcanvas row-offcanvas-right">
      	<form action="servlet/company?type=save" method="post" id="formId">
			<table class="table">
				<thead>
					<tr>
						<th>编号</th>
						<th>公司名称</th>
						<th>1688主账号<span style="color:red;">*</span></th>
						<th>1688授权token<span style="color:red;">*</span></th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="info" items="${PAGE_KEY_LIST}" varStatus="s">
						<tr>
							<td style="width:10%;">${s.count}</td>
							<td style="width:25%;">${info.companyName}</td>
							<td style="width:25%;">
								<input type="text" name="accountid" placeholder="1688主账号" value="${info.accountId}" />
							</td>
							<td style="width:25%;">
								<input type="text" name="token" placeholder="1688访问token" value="${info.token}" />
							</td>
							<td style="width:15%;">
								<input type="hidden" name="id" value="${info.id}" />
								<a href="http://www.1688.com" target="blank">去1688授权</a>
							</td>							
						</tr>						
					</c:forEach>
				</tbody>
				<tr>
					<td colspan="5" align="center" style="height:100px;" valign="middle">
						<div class="form-group">
							<input type="hidden" name="enterpriseId" value="${enterpriseId}"/>
					      	<button type="button" class="btn btn-primary" onclick="submitForm();">提交</button>
					      	<button type="reset" class="btn btn-default">重置</button>
				      	</div>
					</td>	
				</tr>
			</table>
		</form>
      </div>
      <footer>
        <p>&copy; 金蝶软件（中国）有限公司 2014</p>
      </footer>
    </div>	
	<script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>