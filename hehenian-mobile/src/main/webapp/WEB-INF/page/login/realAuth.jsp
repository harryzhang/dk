<%@ page contentType="text/html; charset=utf-8"%>

<!doctype html>
<html>
	<head>
		<%@ include file="../common/head.jsp" %>
		<c:if test="${sessionScope.channel != '2'}">
		<% request.setAttribute("menuIndex", 2); %>
		</c:if>
		<script src="http://static.hehenian.com/m/js/account/realAuth.js"></script>
		<title>${channel_name}-实名认证</title>
	</head>
	
	<body class="bg-2">
	<header class="top-bar">
		<a href="${remoteAddr }">
		<span class="icon-back">
			</span>
		</a>
		<p>实名认证</p>
	</header>
	<div class="real-tips">
	<p>温馨提示：仅用于保障交易安全，请务必如实填写</p>
	</div>
	<section class="sign-area">
			<div class="sign-style br-2">
			<span class="sign-lable">真实姓名</span>
				<input type="text" id="realName" name="realName" value="${realName }" placeholder="请输入您的真实姓名">
			</div>
			<div class="sign-style br-4 bb-1">
				<span class="sign-lable">身份证号</span>
				<input type="text" id="idNo" name="idNo" value="${idNO }" placeholder="请输入您的身份证号码">
			</div>
			<p class="tips-p"><span class="icon-tips"></span>身份证信息一经保存，无法修改</p>
			<div class="sign-sub">
			<input type="hidden" id="remoteAddr" value="${remoteAddr}">
			<!--<c:if test="${realName==null || realName=='' }">
				<input type="button" value="提交" id="authBtn">
			</c:if>-->
			<input type="button" value="提交" id="authBtn">
			</div>
	</section>
	<c:if test="${sessionScope.channel != '2'}">
	<%@ include file="../common/tail.jsp" %>
	</c:if>
</body>
</html>