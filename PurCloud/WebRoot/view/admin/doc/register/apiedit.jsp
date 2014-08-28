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
		<base href="<%=basePath%>" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="description" content="" />
		<meta name="author" content="" />
		<title>OpenApi注册</title>
		<link href="css/bootstrap.css" rel="stylesheet">
		<link href="css/bootstrap.table.css" rel="stylesheet">
		<link href="css/bootstrap.icon.css" rel="stylesheet">
		<style type="text/css">
			.required {
				color: red;
				font-size: 16px;
			}
		</style>
		<script type="text/javascript">
    	function addRow(table){
			var oTable = document.getElementById(table);
			var tBodies = oTable.tBodies;
			var tbody = tBodies[0];
			var count = tbody.rows.length
			var tr = tbody.insertRow(count);
			
			if(table=="inputtable"){
				tr.innerHTML = "<td><a class=\"btn\" href=\"\" onclick=\"removeRow(this);return false\"><i class=\"go-ico-del\"></i></a></td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"infield\" id=\"infield"+count+"\" required></td>"
				+"<td>"
				+"	<select name=\"intype\" id=\"intype"+count+"\" class=\"form-control\">"
				+"	<option value=\"String\" selected=\"selected\">String</option>"
				+"	<option value=\"Long\">Long</option>"
				+"	<option value=\"Date\">Date</option>"
				+"  <option value=\"List\">List</option>"
				+"	</select>"
				+"</td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"indescription\" id=\"indescription"+count+"\"/></td>"
				+"<td><input type=\"checkbox\" class=\"form-control\" name=\"inisNull\" id=\"inisNull"+count+"\" value=\"on\" checked/></td>"
				+"<td><input type=\"checkbox\" class=\"form-control\" name=\"inisSysParam\" id=\"inisSysParam"+count+"\" value=\"on\"/></td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"inexample\" id=\"inexample"+count+"\"/></td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"indefaultValue\" id=\"indefaultValue"+count+"\"/></td>";
			}else if (table=="outputtable"){
				tr.innerHTML = "<td><a class=\"btn\" href=\"\" onclick=\"removeRow(this);return false\"><i class=\"go-ico-del\"></i></a></td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"outfield\" id=\"outfield"+count+"\" required></td>"
				+"<td>"
				+"	<select class=\"form-control\" name=\"outtype\" id=\"outtype"+count+"\">"
				+"	<option value=\"String\" selected=\"selected\">String</option>"
				+"	<option value=\"Long\">Long</option>"
				+"	<option value=\"Date\">Date</option>"
				+"  <option value=\"List\">List</option>"
				+"	</select>"
				+"</td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"outdescription\" id=\"outdescription"+count+"\"/></td>"
				+"<td><input type=\"checkbox\" class=\"form-control\" name=\"outisNull\" id=\"outisNull"+count+"\" value=\"on\" checked/></td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"outexample\" id=\"outexample"+count+"\"/></td>";
			}else{
				tr.innerHTML = "<td><a class=\"btn\" href=\"\" onclick=\"removeRow(this);return false\"><i class=\"go-ico-del\"></i></a></td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"ecode\" id=\"ecode"+count+"\" required></td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"edescription\" id=\"edescription"+count+"\" required></td>"
				+"<td><input type=\"text\" class=\"form-control\" name=\"edealSolution\" id=\"edealSolution"+count+"\"/></td>";
			}
		}
		function removeRow(tdID){
			if(tdID==null) return;  
		    var parentTD = tdID.parentNode;  
		    var parentTR = parentTD.parentNode;  
		    var parentTBODY = parentTR.parentNode;  
		    parentTBODY.removeChild(parentTR); 
		}
		function checkForm(){
	       var t1 = document.getElementById("apiregisterform").getElementsByTagName("input");
	       for(i=0;i<t1.length;i++)
	       {
	           if(t1[i].type == "checkbox"){
	               if(!(t1[i].checked)){
	                   t1[i].checked = true;
	                   t1[i].value = "0ff";
	               }
	           }
	       }
	       return true;
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
						<li>
							<a href="#">首页</a>
						</li>
						<li class="active">
							OpenApi注册
						</li>
					</ol>

					<form id="apiregisterform" class="form-horizontal" role="form" method="post"
						<c:choose>
			          		<c:when test='${SESSION_KEY_USER.username=="administrator"}'>action="admin/doc/register/save"</c:when>
			          		<c:otherwise>action=""</c:otherwise>
			          	</c:choose>>
						<div class="form-group">
							<label for="email" class="col-sm-2 control-label">
								子系统简称
								<span class="required">*</span>
							</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="subSystem"
									id="subSystem" placeholder="sm"
									value="${PAGE_KEY_INFO.subSystem}" autofocus required>
							</div>
							<span class="col-sm-4">采购电商</span>
						</div>

						<div class="form-group">
							<label for="email" class="col-sm-2 control-label">
								子系统别名
								<span class="required">*</span>
							</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="subSystemAlias"
									id="subSystemAlias" placeholder="采购"
									value="${PAGE_KEY_INFO.subSystemAlias}">
							</div>
							<span class="col-sm-4">采购电商</span>
						</div>

						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">
								api名称
								<span class="required">*</span>
							</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="apiName"
									id="apiName" value="${PAGE_KEY_INFO.apiName}"
									placeholder="buyoffer.postbuyoffer" required>
							</div>
							<span class="col-sm-4">api名称</span>
						</div>

						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">
								api别名
								<span class="required">*</span>
							</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="apiAlias"
									id="apiAlias" placeholder="发布询价单"
									value="${PAGE_KEY_INFO.apiAlias}">
							</div>
							<span class="col-sm-4">api中文名称</span>
						</div>

						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">
								api版本
								<span class="required">*</span>
							</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="version"
									id="version" placeholder="1" value="${PAGE_KEY_INFO.version}" required>
							</div>
							<span class="col-sm-4">api的修订版本</span>
						</div>

						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">
								api业务描述
								<span class="required">*</span>
							</label>
							<div class="col-sm-6">
								<div class="textarea">
									<textarea class="form-control" name="remark" id="remark">${PAGE_KEY_INFO.remark}</textarea>
								</div>
							</div>
							<span class="col-sm-4">可以保留500个字符</span>
						</div>


						<ol class="breadcrumb">
							<li class="active">
								OpenApi具体信息
							</li>
						</ol>

						<div class="container-fluid">
							<div class="row-fluid">
								<div class="span12">
									<div class="tabbable" id="tabs-916305">
										<ul class="nav nav-tabs">
											<li class="active">
												<a href="#panelParam" data-toggle="tab">api应用参数</a>
											</li>
											<li>
												<a href="#panelRetrun" data-toggle="tab">api返回结果</a>
											</li>
											<li>
												<a href="#panelError" data-toggle="tab">错误码</a>
											</li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane active" id="panelParam">
												<div class="table-list">
													<table class="table table-hover table-condensed"
														id="inputtable">
														<thead>
															<tr>
																<th class="col-lg-1">
																	操作
																</th>
																<th class="col-lg-1">
																	<span class="required">*</span>名称
																</th>
																<th class="col-lg-1">
																	<span class="required">*</span>类型
																</th>
																<th class="col-lg-3">
																	描述
																</th>
																<th class="col-lg-1">
																	<span class="required">*</span>是否必须
																</th>
																<th class="col-lg-1">
																	<span class="required">*</span>系统参数
																</th>
																<th class="col-lg-2">
																	示例
																</th>
																<th class="col-lg-1">
																	默认值
																</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="inputInfo" items="${PAGE_KEY_INPUTLIST}">
																<tr>
																	<td>
																		<a class="btn" href="" onclick="removeRow(this);return false"><i class="go-ico-del"></i></a>
																	</td>

																	<td>
																		<input type="text" class="form-control" name="infield"
																			value="${inputInfo.field}" />
																	</td>

																	<td>
																		<input type="text" class="form-control" name="intype"
																			value="${inputInfo.type}" />
																	</td>

																	<td>
																		<input type="text" class="form-control"
																			name="indescription" value="${inputInfo.description}" />
																	</td>

																	<td>
																		<c:if test="${inputInfo.isNull}">
																			<input type="checkbox" class="form-control"
																				name="inisNull" checked />
																		</c:if>
																		<c:if test="${not inputInfo.isNull}">
																			<input type="checkbox" class="form-control"
																				name="inisNull" />
																		</c:if>
																	</td>

																	<td>
																		<c:if test="${inputInfo.isSysParam}">
																			<input type="checkbox" class="form-control"
																				name="inisSysParam" checked />
																		</c:if>
																		<c:if test="${not inputInfo.isSysParam}">
																			<input type="checkbox" class="form-control"
																				name="inisSysParam" />
																		</c:if>
																	</td>

																	<td>
																		<c:if test="${empty inputInfo.example}">
																			<input type="text" class="form-control"
																			name="inexample" value="${inputInfo.example}" />
																		</c:if>
																		<c:if test="${!empty inputInfo.example}">
																			<input type="text" class="form-control"
																			name="inexample" value=${inputInfo.example} />
																		</c:if>
																	</td>

																	<td>
																		<c:if test="${empty inputInfo.defaultValue}">
																			<input type="text" class="form-control"
																			name="indefaultValue"
																			value="${inputInfo.defaultValue}" />
																		</c:if>
																		<c:if test="${!empty inputInfo.defaultValue}">
																			<input type="text" class="form-control"
																			name="indefaultValue"
																			value=${inputInfo.defaultValue} />
																		</c:if>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<div class="action-cell">
														<a href="javascript:addRow('inputtable');"><span
															class="go-lnk add-row"><i class="go-ico-focus"></i>新增分录</span>
														</a>
													</div>
												</div>
											</div>



											<div class="tab-pane" id="panelRetrun">

												<div class="table-list">
													<table class="table table-hover table-condensed"
														id="outputtable">
														<thead>
															<tr>
																<th class="col-lg-1">
																	操作
																</th>
																<th class="col-lg-1">
																	<span class="required">*</span>名称
																</th>
																<th class="col-lg-2">
																	<span class="required">*</span>类型
																</th>
																<th class="col-lg-4">
																	描述
																</th>
																<th class="col-lg-1">
																	是否必须
																</th>
																<th class="col-lg-3">
																	示例
																</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="outputInfo"
																items="${PAGE_KEY_OUTPUTLIST}">
																<tr>
																	<td>
																		<a class="btn" href="" onclick="removeRow(this);return false"><i class="go-ico-del"></i></a>
																	</td>

																	<td>
																		<input type='text' class="form-control"
																			name="outfield" value="${outputInfo.field}" />
																	</td>

																	<td>
																		<input type="text" class="form-control" name="outtype"
																			value="${outputInfo.type}" />
																	</td>

																	<td>
																		<input type="text" class="form-control"
																			name="outdescription"
																			value="${outputInfo.description}" />
																	</td>

																	<td>
																		<c:if test="${outputInfo.isNull}">
																			<input type="checkbox" class="form-control"
																				name="outisNull" checked />
																		</c:if>
																		<c:if test="${not outputInfo.isNull}">
																			<input type="checkbox" class="form-control"
																				name="outisNull" />
																		</c:if>
																	</td>

																	<td>
																		<c:if test="${empty outputInfo.example}">
																			<input type="text" class="form-control"
																			name="outexample" value="${outputInfo.example}" />
																		</c:if>
																		<c:if test="${!empty outputInfo.example}">
																			<input type="text" class="form-control"
																			name="outexample" value=${outputInfo.example} />
																		</c:if>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>

													<div class="action-cell">
														<a href="javascript:addRow('outputtable');"><span
															class="go-lnk add-row"><i class="go-ico-focus"></i>新增分录</span>
														</a>
													</div>

												</div>
											</div>


											<div class="tab-pane" id="panelError">

												<div class="table-list">
													<table class="table table-hover table-condensed"
														id="errortable">
														<thead>
															<tr>
																<th class="col-lg-1">
																	操作
																</th>
																<th class="col-lg-3">
																	<span class="required">*</span>错误码
																</th>
																<th class="col-lg-4">
																	<span class="required">*</span>错误描述
																</th>
																<th class="col-lg-4">
																	解决方案
																</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="errorInfo" items="${PAGE_KEY_ERRORLIST}"
																varStatus="s">
																<tr>
																	<td>
																		<a class="btn" href="" onclick="removeRow(this);return false"><i class="go-ico-del"></i></a>
																	</td>

																	<td>
																		<input type="text" class="form-control" name="ecode"
																			value="${errorInfo.code}" />
																	</td>

																	<td>
																		<input type="text" class="form-control"
																			name="edescription" value="${errorInfo.description}" />
																	</td>

																	<td>
																		<input type="text" class="form-control"
																			name="edealSolution"
																			value="${errorInfo.dealSolution}" />
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>

													<div class="action-cell">
														<a href="javascript:addRow('errortable');"><span
															class="go-lnk add-row"><i class="go-ico-focus"></i>新增分录</span>
														</a>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>


						<div class="form-group">
							<div class="col-sm-offset-5 col-sm-7">
								<input type="hidden" id="id" name="id" value="${PAGE_KEY_INFO.id}" />
								<button type="submit" class="btn btn-large btn-primary" onclick="return checkForm();">
									注册
								</button>
								<button type="reset" class="btn btn-large btn-default">
									重置
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>

		</div>

		<script src="js/jquery-1.11.0.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>