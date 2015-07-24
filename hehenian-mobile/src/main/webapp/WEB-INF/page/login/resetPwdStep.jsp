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
		<p>请填写您的真实姓名与身份证号码：</p>
	</nav> 
	<section>
			<div class="input-area">
				<span class="input-flag icon-real">
					<img src="http://static.hehenian.com/m/img/icon-real.png">
				</span>
				<input type="text" class="res-input" id="realName" name="realName" placeholder="真实姓名">
			</div>
			<div class="input-area">
				<span class="input-flag icon-id">
					<img src="http://static.hehenian.com/m/img/icon-id.png">
				</span>
				<input type="text" class="res-input" id="idNo" name="idNo" placeholder="身份证号码">
			</div>
			<p class="tips-p"><span class="icon-tips"></span>实名校验，仅用于保障账户资金安全</p>
			<div class="res-sub">
				<input type="button" id="resetNextStep2" value="下一步">
			</div>
	</section>
	<script>
		$(function(){
			sbh();
		})
	</script>
</body>
</html>