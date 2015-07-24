<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 2); %>
	<title>${channel_name}-我的账户</title>
</head>
<body class="bg-2">
<header class="top-bar">
		<a href="javascript:history.go(-1);">
		<span class="icon-back">
			</span>
		</a>
		<p>账号与安全</p>
	</header>
	<section class="sign-area">
			<div class="sign-style br-3">
				<span class="sign-lable pr75">姓名</span>
				<span class="sign-ctn">${realName }</span>
			</div>
			<div id="phoneId" class="sign-style br-2">
			<span class="sign-lable">绑定手机</span>
				<span class="sign-ctn">${cellPhone}></span>
			</div>
			<div id="auditId" class="sign-style br-1 bb-1">
				<span class="sign-lable">实名认证</span>
				<span class="sign-ctn">
					<c:if test="${auditStatus == 1 }">待认证></c:if>
					<c:if test="${auditStatus == 2 }">认证不通过></c:if>
					<c:if test="${auditStatus == 3 }">已认证></c:if>
				</span>
			</div>
			<div id="setLoginPwd" class="sign-style br-5">
				<span class="sign-lable">重置登录密码</span>
				<span class="sign-ctn">></span>
			</div>
			<div id="setPayPwd" class="sign-style br-4 bb-1">
			<span class="sign-lable">重置支付密码</span>
				<span class="sign-ctn">></span>
			</div>
			<div class="sign-style br-5">
				<span class="sign-lable">昵称</span>
				<span class="sign-ctn">${nickName}</span>
			</div>
			<div id="emailId" class="sign-style br-4 bb-1">
			<span class="sign-lable">邮箱</span>
				<span class="sign-ctn">${email}></span>
			</div>
	</section>
	
	<script>
		$(function(){
			sbh();
			
			//实名认证
			$("#auditId").click(function(){
				location.href="http://m.hehenian.com/profile/userinfo.do?flag=auth";
			});
			
			//重置登录密码
			$("#setLoginPwd").click(function(){
				location.href="http://m.hehenian.com/account/resetPwdIndex.do?pwdFlag=login";
			});
			
			//重置手机号码
			$("#phoneId").click(function(){
				location.href="http://m.hehenian.com/account/resetMobile.do";
			});

			//重置手机号码
			$("#emailId").click(function(){
				location.href="http://m.hehenian.com/account/resetEmailIndex.do";
			});

			//重置支付密码
			$("#setPayPwd").click(function(){
				location.href="http://m.hehenian.com/account/resetPwdIndex.do?pwdFlag=pay";
			});
		});
	</script>
	<%@ include file="../common/tail.jsp" %>
</body>
</html>