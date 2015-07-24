<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>删除手机号码</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
</head>
<body>
	<div class="m-search">
		<input id="search" class="easyui-searchbox" searcher="" prompt="请输入邮箱或者手机号码" menu="#mm" />
		<div id="mm" style="width:120px">
			<div name="1" iconCls="icon-ok">手机号码/邮箱</div>
			<div name="2" iconCls="icon-ok">MEMBERID</div>
		</div>
	</div>
	<div>
		<div id="tableGrid"></div>
	</div>
	<script type="text/javascript"
	src="${basePath}/js/websiteLogin/phoneSearchIndex.js"></script>
</body>
</html>