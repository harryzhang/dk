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
<form action="/question/qnModify.html" method="POST">
问题：<input type="text" name="questionId"/><br/>
问卷：<input type="text" name="qnType"/><br/>
阶段：<input type="text" name="qnId"/><br/>
<input type="radio" name="pos" value="first"/>第一道<input type="radio" name="pos" value="last"/>最后一道<br/>
<input type="text" name="previous"/>与<input type="text" name="next"/>之间<br/>
<input type="submit" value="确定"/>
</form>
</body>
</html>