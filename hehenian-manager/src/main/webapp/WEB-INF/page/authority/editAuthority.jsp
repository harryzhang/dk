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
<input type="hidden" id="authId" value="${auth.id }"/>
<div class="center">
    <label for="name">权  限  名:</label>
    <input id="name" class="easyui-validatebox" type="text" name="name"  value="${auth.name }"/>
</div>
<div class="center">
    <label for="desc">备　　注:</label>
    <input id="desc" class="easyui-validatebox" type="text" name="desc"  value="${auth.authDesc }"/>
</div>
<body>
</body>