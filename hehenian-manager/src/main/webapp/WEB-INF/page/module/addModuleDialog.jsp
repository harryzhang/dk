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
<input type="hidden" id="moduleId" value="${module.id }"/>
<div class="center">
    <label for="name">模　块:</label>
    <input id="name" class="easyui-validatebox" type="text" name="name"  value="${module.name }"/>
</div>
<div class="center">
    <label for="module">编　码:</label>
    <input id="module" class="easyui-validatebox" type="text" name="module"  value="${module.module }"/>
</div>
<div class="center">
    <label for="desc">备　注:</label>
    <input id="desc" class="easyui-validatebox" type="text" name="desc"  value="${module.moduleDesc }"/>
</div>
</body>
</html>