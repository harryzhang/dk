<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>问卷管理</title>
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="/js/jquery-easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<form action="/questionnaire/add.html" method="POST">
<div class="tabelMod">
	<label>问卷号：</label>
	<input type="text" name="qnType"/>
</div>
<div class="tabelMod">
	<label>描述：</label>
	<input type="text" name="descript"/>
</div>
<input type="submit" value="添加"/>
</form>
</body>
</html>