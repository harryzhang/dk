<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.hhn.util.DateUtil"%>
<%
	request.setAttribute("menuIndex", 2);
%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp"%>
<meta name="viewport"
	content="width=640,target-densitydpi=320,user-scalable=no">
<link rel="stylesheet" href="http://static.hehenian.com/m/css/base.css">
<link rel="stylesheet"
	href="http://static.hehenian.com/m/css/bindBank.css">
<title>${channel_name}-设置邮箱</title>
</head>
<body class="bg-2">
	<header class="top-bar">
		<a href="javascript:history.go(-1);">设置邮箱 <span
			class="icon-back"> </span>
		</a>
	</header>
	<c:if test='${not empty email}'>
		<div class="inputDiv">
				<span>原邮箱地址</span>
				<span id="realSpan"> ${email} </span>
		</div>
	</c:if>
	<a class="maimeng">为保障您的账户安全，请输入登录密码后再输入新的邮箱地址</a>
	<div class="inputDiv">
		<span style="color:#20abad;">登录密码</span> <input type="password" placeholder="请输入登录密码"
			id="loginPassword" />
	</div>
	<div class="inputDiv">
		<span style="color:#20abad;">邮箱地址</span> <input type="text" placeholder="请输入您的常用邮邮箱"
			id="email" />
	</div>
	<br />
	<div class="res-sub">
		<input type="button" class="res-input" id="submit" value="提交">
	</div>
	<div class="cover" id="cover"></div>
	<%@ include file="../common/tail.jsp"%>
	<script type="text/javascript"
		src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript"
		src="http://static.hehenian.com/m/js/common.js"></script>
	<script type="text/javascript"
		src="http://static.hehenian.com/m/js/module.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#submit").click(function() {
				var loginPassword = $("#loginPassword").val();
				var newEmail = $("#email").val();
				if(loginPassword==null || loginPassword.length==0){
					popWindow("请输入登录密码");
					return;
				}
				if(newEmail==null || newEmail.length==0){
					popWindow("请输入新邮箱地址");
					return;
				}
				var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
				if(!reg.test(newEmail)) {
					popWindow("请输正确的邮箱地址");
					return;
				}
				var params = {email:newEmail, password:loginPassword};
				$.post("http://m.hehenian.com/account/resetEmail.do", params, function(data){
					var result;
					if (data.length > 0) {
						result = jQuery.parseJSON(data);
					}
					if(result.msg != null && data.length != 0){
						popWindow(result.msg);
					} else if(result.code==0){
						window.location.href="http://m.hehenian.com/account/resetEmailSuccess.do";
					}
				});
			});
		})
	</script>
</body>
</html>