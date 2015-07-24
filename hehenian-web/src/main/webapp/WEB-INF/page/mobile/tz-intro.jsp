<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<style>
.tabs-content1 {
	width: 200%;
	padding-top: 0px;
	float: left;
}

.wrap1 {
	width: 100%;
	min-width: 320px;
	margin: 0 auto;
	font-size: 1em;
}
</style>
<title>投资列表</title>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/wap/mobile/styles/hhn.common.css?t=1'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/wap/mobile/styles/hhn.layout.css?t=1'/>" />
<%
	if (session.getAttribute("appstyle") != null) {
%>
<link rel="stylesheet" type="text/css"
	href='/wap/mobile/styles/hhn.<%=session.getAttribute("appstyle")%>.css?t=2' />
<%
	}
%>
</head>
<body>
	<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
	<div class="wrap1" id="wrap" style="display: none;">
		<div class="tabs">
			<div class="tabs-top" id="ss">
				<ul class="tabs-nav">
					<li class="on" style="font-size: 16px"><strong>投资列表</strong></li>
					<li style="font-size: 16px"><strong>债权转让</strong></li>
				</ul>
				<span class="tabs-line"></span>
			</div>
			<div id="redirect" style="display: none;"></div>
			<div class="tabs-content1">
				<div class="tabs-box">
					<iframe id="iframe1" src="webapp/webapp-finance.do?noBottom=true"
						style="border-bottom: none;" height="900px" width="100%"></iframe>
				</div>
				<div class="tabs-box">
					<iframe id="iframe2"
						src="webapp/queryFrontAllDebt.do?noBottom=true"
						style="border-bottom: none;" height="900px" width="100%"></iframe>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
	<script>
		var noIScroller = true;
	</script>
	<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
	<script src="/wap/mobile/scripts/module/details.js?1=1"></script>
	<script>
		$(function() {
			$("#iframe1").attr("height", 940);
			$("#iframe2").attr("height", 940);
		})
	</script>
</body>
</html>