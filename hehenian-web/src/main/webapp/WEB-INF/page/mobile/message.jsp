<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Refresh" content="5; url=/webapp/webapp-index.do" />
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>提示信息</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t-a title"><strong>提示信息</strong></h1>
<div class="wrap" id="wrap" style="display:none;">
	<div class="pd responsive">
    	<div class="responsive-content"><span id="result"></span></div>
		<div class="responsive-content">5秒后自动跳转到首页</div>
        <div class="responsive-btn"><a href="/webapp/webapp-index.do" class="btn-a">点击返回首页</a></div>
    </div>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script>
$("#result").html(HHN.queryString("title"));
</script>
</body>
</html>
