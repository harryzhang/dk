﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>个人中心</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<link type="text/css" rel="stylesheet"
	href="http://static.hehenian.com/m/css/main.min.css">
<script src="http://static.hehenian.com/m/common/zepto.min.js"></script>
<script src="http://static.hehenian.com/m/js/main.min.js"></script>
</head>

<body class="bg-2">
	<header class="top-bar bg-f">
		<a href="javascript:history.go(-1);"> <span class="icon-back2">
		</span>
		</a>
		<p>项目投资</p>
	</header>
	<nav class="nav-tip">
		<p>温馨提示：手机暂不支持项目投资相关操作，请登录www.hehenian.com会员中心进行操作。</p>
	</nav>
	<div class="order-panel">
		<p class="tips">
			<i></i>资产估值（元）
		</p>
		<span class="maney"><fmt:formatNumber pattern="#0.00"
				value="${userIncomeDo.assetValue}"></fmt:formatNumber></span>
		<div class="income">
			<i class="i2"></i>昨日增值（元）：<span class="cOrange"><fmt:formatNumber
					pattern="#0.00" value="${userIncomeDo.dailyIncome}"></fmt:formatNumber></span>
		</div>
	</div>
	<ul class="invest-list clearfix">
		<li class="center">
			<div>
				<span> 待收本金（元）</span>
				<p class="cOrange">
					<fmt:formatNumber pattern="#0.00"
						value="${userIncomeDo.recivedPrincipal - userIncomeDo.hasPrincipal}"></fmt:formatNumber>
				</p>
			</div>
		</li>
		<li class="center">
			<div>
				<span> 冻结金额（元）</span>
				<p class="cOrange">
					<fmt:formatNumber pattern="#0.00"
						value="${userIncomeDo.freezeAmount}"></fmt:formatNumber>
				</p>
			</div>
		</li>
		<li class="center">
			<div>
				<span> 累计收益（元）</span>
				<p class="cOrange">
					<fmt:formatNumber pattern="#0.00" value="${userIncomeDo.earnSum}"></fmt:formatNumber>
				</p>
			</div>
		</li>
		<li class="center">
			<div>
				<span> 可用余额（元）</span>
				<p class="cOrange">
					<fmt:formatNumber pattern="#0.00"
						value="${userIncomeDo.withdrawalAmount}"></fmt:formatNumber>
				</p>
			</div>
		</li>
	</ul>
	<%@ include file="../common/tail.jsp"%>
	<script>
		$(function() {
			sbh();
		})
	</script>
	<!-- <script>
			$.get("/updateUserUsableSum.do?_=" + new Date().getTime());
		</script> -->
	<%-- </c:if> --%>
</body>
</html>