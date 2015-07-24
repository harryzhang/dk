<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setAttribute("menuIndex", 1); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="stylesheet" href="http://static.hehenian.com/m/css/main.min.css">
		<script src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
		<script src="http://static.hehenian.com/m/common/zepto.min.js"></script>
		<script src="http://static.hehenian.com/m/js/main.min.js"></script>
		<script src="http://static.hehenian.com/m/js/account/account.js"></script>
		<title>${channel_name}-邀请码快速通道</title>
	</head>
<body class="bg-2">
	<header class="top-bar">
		<a href="javascript:history.go(-1);">
			<span class="icon-back">
			</span>
		</a>
		<p>邀请码快速通道</p>
	</header>
	<section>
		<div class="invite-in">
				<input type="text" name="code" id="code" placeholder="请输入您的邀请码">
			</div>
			<div class="invite-sub">
				<input id="invite-btn" type="button" value="提交">
			</div>
		<div class="invite-cancel">
			<a href="http://m.hehenian.com/product/plist.do">跳过</a>
		</div>
			<p class="invite-tips"><span class="icon-tips"></span>该通道仅适用于物业国际员工，邀请码可咨询公司理财顾问</p>
	</section>
	<script>
		$(function(){
			sbh();
		})
	</script>
</body>

</html>