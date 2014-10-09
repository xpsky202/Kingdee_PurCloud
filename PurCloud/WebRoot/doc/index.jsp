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
    <title>API文档</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
    	.bs-sidenav {
			padding-top:10px;
			padding-left:20px;
			padding-right:20px;
			text-shadow: 0 1px 0 #fff;
			background-color: #f7f5fa;
		}
		
		.clsTable {
			border: solid 1px #ccc;
			padding: 3px;
		}
		
		.clsTable tr th,td {
			border: solid 1px #ccc;
			line-height:25px;
			width:250px;
		}
    </style>
 </head>
  <body>
   <%@include file="../nav.jsp"%>

	<div class="container bs-docs-container" style="padding-top:80px;font-size: 12px;">	  
      <div class="row">
        <%@include file="module.jsp"%>
        <div class="col-md-9">
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
			  <li class="active">API文档</li>
		  </ol>
          
          <h3 id="pplatform-info"></h3>
	      <br/><br/>
     	  <div class="panel panel-info">
			  <div class="panel-heading">				    
     				<h5 class="panel-title">平台介绍</h5>
			  </div>
			  <div class="panel-body">
			  	<p>&nbsp;&nbsp;&nbsp;&nbsp;本平台为企业进行网络采购提供的开放服务平台，为金蝶ERP系统提供与互联网系统互联API服务，目前支持金蝶ERP通过本平台与阿里巴巴互联，提供企业注册、用户授权、发布询价、获取报价、上传订单等服务
			  	</p>
			  </div>
		  </div>
          
          <h3 id="pplatform-use"></h3>
	      <br/><br/>
     	  <div class="panel panel-info">
			  <div class="panel-heading">				    
     				<h5 class="panel-title">调用说明</h5>
			  </div>
			  <div class="panel-body">
			  	<h6><b>请求URL注解</b></h6>
			  	<p>http://192.168.204.131:8086/openapi/param2/1/com.kingdee.purchase.openapi/postBuyOffer</p>
			  	<table class="clsTable">
			  		<tr>
			  			<th>样例URL</th><th>说明</th><th>备注</th>
			  		</tr>
			  		<tr>
			  			<td>192.168.204.131:8086</td><td>开放平台公网地址</td><td></td>
			  		</tr>
			  		<tr>
			  			<td>param2</td><td>请求协议格式</td><td></td>
			  		</tr>
			  		<tr>
			  			<td>1/com.kingdee.purchase.openapi/postBuyOffer</td><td>/api version/api namespace/api name</td><td>/API版本/API命名空间/API接口名</td>
			  		</tr>
			  	</table>
			  	<p style="padding-top:10px"><b>数据提交方式：Post</b></p>
			  	<p><b>输出结果：</b></p>
			  	<div class="img-wrap">
            						<div style="background-color:#FBFBE7;color:#555;border: 1px dashed #2F6FAB;padding:10px;text-align:left">
            							<pre >
{
"result":{
	"total":1,
	"toReturn":[{
		"createTime":"20080806114526000+0800",
		"sex":"先生",
		"fax":"86  ",
		"status":"enabled",
		"sellerName":"tp4",
		"companyName":"标兵邮箱公司",
		"openJobTitle":"经理",
		"product":"服装 服装",
		"isEnterpriseTP":true,
		"mobilePhone":"13590254875",
		"isPersonalTP":false,
		"addressLocation":
                    {"address":"浙江宁波",
                    "province":"浙江",
                    "district":"宁波市",
                    "country":"中国",
                    "city":"宁波"
                    },
		"memberId":"tonytp4",
		"industry":"服装",
		"isTP":true,
		"telephone":"86 0571 89875463"
	}],
	"success":false
	}
}
            							</pre>
                                    </div>
                                    <p class="data-format"><b>错误输出示例:</b></p>
            					<div class="img-wrap">
            						<div style="background-color:#FBFBE7;color:#555;border: 1px dashed #2F6FAB;padding:10px;text-align:left">
            							<pre >
{
"error_code":"450",
"error_message":"Required argument memberId : expect [type: java.lang.String]",
"exception":"Required argument memberId : expect [type: java.lang.String]"
}
            							</pre>
            						</div>
            					</div>
			  </div>
		  </div>
		  
		  <h3 id="pplatform-permission"></h3>
	      <br/><br/>
     	  <div class="panel panel-info">
			  <div class="panel-heading">				    
     				<h5 class="panel-title">权限说明</h5>
			  </div>
			  <div class="panel-body">
			  	暂无权限控制 
			  </div>
		  </div>
		  
		   <h3 id="pplatform-errcode"></h3>
	      <br/><br/>
     	  <div class="panel panel-info">
			  <div class="panel-heading">				    
     				<h5 class="panel-title">全局错误码</h5>
			  </div>
			  <div class="panel-body">
			  	请联系 <a href="mailto:sky_lv@kingdee.com">吕飞</a> 
			  </div>
		  </div>
          <!-- API文档列表 -->
	      <c:forEach var="api" items="${PAGE_KEY_LIST}">
          	<h5 id="p${api.subSystem}-${api.apiNameVo}"></h5>
 		    <br/><br/>
      		<h3>${api.apiName} &nbsp;&nbsp;${api.apiAlias}</h3>
      		  <div class="panel panel-info">
				  <div class="panel-heading">				    
      				<h5 class="panel-title">基本信息</h5>
				  </div>
				  <div class="panel-body">
				  	版本：${api.version}<br/>
				  	模块：${api.subSystemAlias} (${api.subSystem})<br/>
				    <p>描述：${api.remark}</p>
				  </div>
			  </div>
		      <div class="panel panel-info">
				  <div class="panel-heading">
				    <h5 class="panel-title">输入参数</h5>
				  </div>
				  <div class="panel-body">
				     <p>输入参数包括两个部分：系统参数，业务参数</p>
				  </div>
				  <table class="table">
			      	<thead>
			      		<tr>
			      			<th class="col-lg-1">参数名</th>
			      			<th class="col-lg-1">类型</th>
			      			<th class="col-lg-1">系统参数</th>
			      			<th class="col-lg-1">是否必须</th>
			      			<th class="col-lg-3">示例值</th>
			      			<th class="col-lg-1">默认值</th>
			      			<th class="col-lg-4">描述</th>
			      		</tr>
			      	</thead>
			      	<tbody>
			      		<c:forEach var="inParam" items="${api.inputParamList}">
				      		<tr>
				      			<td>${inParam.field}</td>
				      			<td>${inParam.type}</td>
				      			<td>${inParam.isSysParam}</td>
				      			<td>${inParam.isNull}</td>
				      			<td>${inParam.example}</td>
				      			<td>${inParam.defaultValue}</td>
				      			<td>${inParam.description}</td>
				      		</tr>
			      		</c:forEach>
			      	</tbody>
			      </table>
			    </div>				
				<br/>
				<div class="panel panel-info">
				  <div class="panel-heading">
				    <h5 class="panel-title">返回结果</h5>
				  </div>
				  <div class="panel-body">
			    	<p>返回的结果封装在一个json串中，用户（程序）自己去解析。对于没有值的字段，在json字符串中不会包含其信息（字段名也没有）</p>
				  </div>
				  <table class="table">
				      	<thead>
				      		<tr>
				      			<th class="col-lg-1">参数名</th>
				      			<th class="col-lg-1">类型</th>
				      			<th class="col-lg-1">是否必须</th>
				      			<th class="col-lg-4">示例值</th>
				      			<th class="col-lg-5">描述</th>
				      		</tr>
				      	</thead>
				      	<tbody>
				      		<c:forEach var="retParam" items="${api.outputParamList}">
					      		<tr>
					      			<td>${retParam.field}</td>
					      			<td>${retParam.type}</td>
					      			<td>${retParam.isNull}</td>
					      			<td>${retParam.example}</td>
					      			<td>${retParam.description}</td>
					      		</tr>
				      		</c:forEach>
				      	</tbody>
				      </table>
				</div>
			    <br/>
			    <div class="panel panel-danger">
				  <div class="panel-heading">
				    <h5 class="panel-title">错误码</h5>
				  </div>
				  <div class="panel-body">
				    <p>错误码分为全局错误码和业务api错误码。全局错误码是系统级别的，如网络异常、用户无权限等；
				    	api错误码是api运行过程中返回的包含业务语义的错误码，如必选的参数值不能为空</p>
			      </div>
			      <table class="table">
				      	<thead>
				      		<tr>
				      			<th class="col-lg-3">代码</th>
				      			<th class="col-lg-6">描述</th>
				      			<th class="col-lg-3">解决方案</th>
				      		</tr>
				      	</thead>
				      	<tbody>
				      		<c:forEach var="errCode" items="${api.errorCodeList}">
					      		<tr>
					      			<td>${errCode.code}</td>
					      			<td>${errCode.description}</td>
					      			<td>${errCode.dealSolution}</td>
					      		</tr>
				      		</c:forEach>
				      	</tbody>
				      </table>
			   </div>
	      </c:forEach>
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