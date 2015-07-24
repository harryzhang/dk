<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/include/mobile/head.jsp"></jsp:include>
    <title>提示信息</title>
</head>
<body>
<h1 class="t-a title"><strong>提示信息</strong></h1>
<div class="wrap" id="wrap" style="display:none;">
    <div class="pd responsive" style="padding-top: 10%;">
        <div class="responsive-content"><span id="result"></span></div>
    </div>
</div>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script>
    <%--$("#result").html("${title}");--%>
    $("#result").html("您的手机号已经在合和年在线注册，不能使用彩富一号。");
</script>
</body>
</html>
