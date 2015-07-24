<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>删除缓存</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
</head>
<body>
	<div class="m-search">
		<input id="search" class="easyui-searchbox" searcher="" prompt="清除缓存专用" menu="#mm" style="width:310px" />
		<div id="mm" style="width:120px">
			<div name="redis" iconCls="icon-ok">Redis Key</div>
			<div name="memcached" iconCls="icon-ok">Memcache</div>
		</div>
	</div>
	<div>
		<div id="tableGrid">
		
		</div>
	</div>
	<script type="text/javascript"
	src="${basePath}/js/cache/cacheIndex.js"></script>
</body>
</html>