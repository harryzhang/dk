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
		<title>${channel_name}-重置密码成功</title>
	</head>
	
<body class="bg-3">
	<header class="top-bar bg-f">
		<a href="javascript:history.go(-1);">
		<span class="icon-back2">
			</span>
		</a>
		<p>重置支付密码</p>
	</header>
	
	<section class="succ-area">
	<div class="succ-logo">
	</div>
	
	<div class="anch-launch">
	<a href="http://m.hehenian.com/product/plist.do?channel=${param.channel}&subChannel=${param.subChannel}">
		立即投资
	</a>
	</div>
	</section>
	<script>
	$(function(){
		sbh();
	})
	</script>
</body>
</html>