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
		<title>${channel_name}-重置密码</title>
	</head>
	
	<body class="bg-2">
	<input type="hidden" id="pwdFlag" value="${pwdFlag}">
	<header class="top-bar">
		<a href="javascript:history.go(-1);">
			<span class="icon-back">
			</span>
		</a>
		<c:if test="${pwdFlag == 'pay'}">
			<p>重置支付密码</p>
		</c:if>
		<c:if test="${pwdFlag != 'pay'}">
			<p>重置登陆密码</p>
		</c:if>
	</header>
	<nav class="nav-title">
		<c:if test="${pwdFlag == 'pay'}">
			<p>请重设您的支付密码</p>
		</c:if>
		<c:if test="${pwdFlag != 'pay'}">
			<p>请重设您的登录密码</p>
		</c:if>
	</nav> 
	<section>
			<div class="input-area">
				<span class="input-flag icon-lock">
					<img src="http://static.hehenian.com/m/img/icon-lock.png">
				</span>
				<input type="password" class="res-input" id="pwd" name="pwd" placeholder="请输入新密码">
			</div>
			<div class="input-area">
				<span class="input-flag icon-lock">
					<img src="http://static.hehenian.com/m/img/icon-locka.png">
				</span>
				<input type="password" class="res-input" id="confirmPwd" name="confirmPwd" placeholder="请确认新密码">
			</div>
			<c:if test="${pwdFlag == 'pay'}">
				<p class="tips-p"><span class="icon-tips"></span>密码长度8~20位，由数字、字母组成</p>
			</c:if>
			<c:if test="${pwdFlag == 'login'}">
				<p class="tips-p"><span class="icon-tips"></span>密码长度6~20位，由数字、字母组成</p>
			</c:if>
			<div class="res-sub">
				<input type="button" value="提交" id="updPwdBtn">
			</div>
	</section>
	<script>
		$(function(){
			sbh();
		})
	</script>
</html>