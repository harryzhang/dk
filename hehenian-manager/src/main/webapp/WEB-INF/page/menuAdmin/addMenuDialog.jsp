<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
</head>
<body>
<style type="text/css">
.center{
  text-align: center;
  margin-top:5px;
}
</style>
<input type="hidden" id="rid" value="${resource.id }"/>
<div class="center">
    <label for="name">菜  单 项:</label>
    <input id="name" class="easyui-validatebox" type="text" name="name"  value="${resource.name }"/>
</div>
<div class="center">
<label for="name">所属模块:</label>
<select id="modules" class="easyui-combobox" name="modules" style="width:150px;">
<c:choose>
<c:when test="${!empty resource }">
  <c:forEach var="item" items="${modules }" >
   <option value="${item.module }" <c:if test="${item.module==resource.module }">selected="selected"</c:if>>${item.name }</option>
  </c:forEach>
</c:when>
<c:otherwise>
  <c:forEach var="item" items="${modules}" varStatus="status">
  <option value="${item.module}">${item.name}</option>
  </c:forEach>
</c:otherwise>
</c:choose>
</select>
</div>
<div class="center">
    <label for="url">链　　接:</label>
    <input id="url" class="easyui-validatebox" type="text" name="url" value="${resource.resourceStr }" />
</div>

<div class="center">
    <label for="resouceType">资源类型:</label>
    <select id="resouceType" class="easyui-combobox" name="resouceType" style="width:150px;">
		<c:choose>
		<c:when test="${!empty resource }">
		   <option value="menu" <c:if test="${1==resource.resouceType }">selected="selected"</c:if>>菜单</option>
		   <option value="button" <c:if test="${0==resource.resouceType }">selected="selected"</c:if>>按钮</option>
		</c:when>
		<c:otherwise>
		  	<option value="menu">菜单</option>
		    <option value="button">按钮</option>
		</c:otherwise>
		</c:choose>
	</select>    
</div>

<div class="center">
    <label for="desc">备　　注:</label>
    <input id="desc" class="easyui-validatebox" type="text" name="desc" value="${resource.resourceDesc }"/>
</div>
</body>