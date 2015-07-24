<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
</head>
<body>
<style type="text/css">
.center{
  text-align: center;
  margin-top:5px;
}
</style>
    <input id="roleId" value="${role.id }" type="hidden"/>
    <div class="center">
        <label for="name">角色名:</label>
        <input id="name" class="easyui-validatebox" type="text" name="name" value="${role.name }" />
    </div>
    <div class="center">
        <label for="desc">描　述:</label>
        <input id="desc" class="easyui-validatebox" type="text" name="desc"  value="${role.roleDesc }"/>
    </div>
</body>
</html>