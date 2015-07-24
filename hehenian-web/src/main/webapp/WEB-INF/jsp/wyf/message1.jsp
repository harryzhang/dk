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
    $("#result").html("${title}");
//    $("#result").html("客官莫急，经小二查询，您的账户已注册e理财，为保证您的资金安全，重新注册彩之云账户才是最明智的选择啦~省钱更安全，请客官返回彩之云更换账号后再来参加活动。");
</script>
</body>
</html>
