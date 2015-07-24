<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="stylesheet" href="http://static.hehenian.com/m/css/main.min.css">
		<script src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
		<script src="http://static.hehenian.com/m/common/zepto.min.js"></script>
		<script src="http://static.hehenian.com/m/js/main.min.js"></script>
		<script src="http://static.hehenian.com/m/js/account/account.js"></script>
		<title>${channel_name}-注册</title>
	</head>
<body class="bg-2">
<header class="top-bar">
		<a href="http://m.hehenian.com/login/index.do">
		<span class="icon-back">
			</span>
		</a>
		<p>注册理财账号</p>
	</header>
	<section class="sign-area">
		<form action="http://m.hehenian.com/account/register.do" id="regForm" method="post">
			<div class="sign-style br-3">
				<span class="sign-lable pr75">用户名</span>
				<input type="text" name="userName" id="userName" placeholder="由数字和字母组成，5-25个字符">
			</div>
			<div class="sign-style br-2">
			<span class="sign-lable">登录密码</span>
				<input type="password" name="password" id="password" placeholder="6-20个字符">
			</div>
			<div class="sign-style br-1 bb-1">
				<span class="sign-lable">确认密码</span>
				<input type="password" id="confirmPassword" name="confirmPassword" placeholder="6-20个字符">
			</div>
			<div class="sign-style br-5">
				<span class="sign-lable">手机号码</span>
				<input type="text" id="mobilePhone" name="mobilePhone" placeholder="请输入注册手机号码">
			</div>
			<div class="sign-style br-4 bb-1">
				<input type="text" id="identifyCode" name="identifyCode" placeholder="请输入短信验证码" class="ml-15">
				<!-- <span class="sign-get-ck" id="aaaaaaa">获取验证码</span> -->
				<a id="ida" class="sign-get-ck" href="javascript:;">获取验证码</a>
			</div>
			<div class="sign-sub">
			<input type="button" id="regBtn" value="立即注册">
			</div>
		</form>
		<p><span></span>点击“立即注册”，即表示您同意并愿意遵守<a href="http://m.hehenian.com/account/zcxy.do">《合和在线注册协议》</a></p>
	</section>
	<script>
		$(function(){
			sbh();
		})
	</script>
</body>
</html>