<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>个人信息</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><span>个人信息</span></h1>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <ul class="items user">
    <li><strong><i>用户名</i></strong><em>${session.user.username}</em></li>
    <li><strong><i>姓名</i></strong><em>${map.realName}</em></li>
    <li><strong><i>身份证号</i></strong><em>${map.idNo}</em></li>
    <li><strong><i>电子邮箱</i></strong><em>${map.email}</em></li>
    <li><strong><i>手机号码</i></strong><em>${map.mobilePhone}</em></li>
    <li><strong><i>汇付帐号</i></strong><em>
    <s:if test="#request.map.usrCustId>0">${map.usrCustId }</s:if>
    <s:else><a href="/registerChinaPnr.do" class="btc-a"  onclick="regChinaPnr();">注册汇付天下</a></s:else>
    </em></li>
  </ul>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
</body>
</html>