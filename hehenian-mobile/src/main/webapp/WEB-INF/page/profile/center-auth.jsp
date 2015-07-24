<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 2); %>
	<title>${channel_name}-我的账户</title>
</head>
<body class="bg-2">
<header class="top-bar bg-f">
		<a href="javascript:history.go(-1);">
		<span class="icon-back2">
			</span>
		</a>
		<p>账号与安全</p>
	</header>
	<div class="bg-auth">
	</div>
	<section class="sign-area margin-none">
			<div class="sign-style br-3">
				<span class="sign-lable pr75">真实姓名</span>
				<span class="sign-ctn">${realName }</span>
			</div>
			<div class="sign-style br-2">
			<span class="sign-lable">身份证号</span>
				<span class="sign-ctn">${idNo }</span>
			</div>
			<div class="sign-style br-1 bb-1">
				<span class="sign-lable">实名认证</span>
				<span class="sign-ctn">
					<c:if test="${auditStatus == 1 }"><a href="http://m.hehenian.com/account/realAuth.do">待认证></a></c:if>
					<c:if test="${auditStatus == 2 }">认证不通过</c:if>
					<c:if test="${auditStatus == 3 }">已认证</c:if>
				</span>
			</div>
	</section>
	<%@ include file="../common/tail.jsp" %>
</body>
</html>