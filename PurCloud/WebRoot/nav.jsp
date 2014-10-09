<%@ page language="java" pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#"><b>金蝶采购开放平台</b></a>
    </div>
    <div class="navbar-collapse">
      <ul class="nav navbar-nav">
        <li><a href="/purchase">首页</a></li>
        <li><a href="doc">开发者文档</a></li>
        <li><a href="contact.jsp">联系我们</a></li>
      </ul>
      	  <ul class="nav navbar-nav navbar-right">
      <c:choose>
      	<c:when test='${SESSION_KEY_USER.username=="administrator"}'>
      	  <li><a href="customer/account/edit">${SESSION_KEY_USER.displayname}，您好！</a></li>
          <li><a href="admin/enterprise/list">管理</a></li>
          <li><a href="login?type=logout">退出登录</a></li>
      	</c:when>
      	<c:when test='${SESSION_KEY_USER!=null}'>
      	  <li><a href="customer/account/edit">${SESSION_KEY_USER.displayname}，您好！</a></li>
          <li><a href="customer/enterprise/edit">管理</a></li>
          <li><a href="login?type=logout">退出登录</a></li>
      	</c:when>
      	<c:otherwise>          
      	   <li><a href="signup.jsp">注册</a></li>
      	   <li><a href="login.jsp">登录</a></li>
      	</c:otherwise>
      </c:choose>
       </ul>
    </div>
  </div>
</nav>