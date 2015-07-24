<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${channel_name}</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<link type="text/css" rel="stylesheet" href="http://static.hehenian.com/m/css/main.min.css">
	<script src="http://static.hehenian.com/m/common/zepto.min.js"></script>
	<script src="http://static.hehenian.com/m/js/main.min.js"></script>
</head>
<body class="bg-2">
<header class="top-bar">
<input type="hidden" id="avlB" value="${AvlBalFlag}"/>
		<a href="">
		<span class="icon-back">
			</span>
		</a>
		<p>账户与安全</p>
	</header>
	<div class="io-bar">
	<div class="ib-top">
	<p><span>账户余额</span>￥${balance}</p>
	</div>
	<div class="ib-bot">
	<span class="in-btn">充值</span>
	<span class="out-btn">提现</span>
	</div>
	</div>
	<section class="sign-area margin-none">
			<div class="sign-style br-3">
				<span class="sign-lable pr75">充值记录</span>
				<span class="sign-ctn">></span>
			</div>
			<div class="sign-style br-2">
			<span class="sign-lable">提现记录</span>
				<span class="sign-ctn">></span>
			</div>
			<div id="huifu" class="sign-style3 br-5 bb-1">
				<span class="sign-lable">汇付余额</span>
				<span class="sign-ctn">￥${AvlBal}</span>
				<p>温馨提示：仅用于项目投资、彩富人生产品，手机暂不支持汇付提现，请登录www.hehenian.com的会员中心进行操作。 </p>
			</div>
	</section>
	
	<script>
		$(function(){
			//汇付可用余额为0时，则隐藏汇付显示框
			var flag = $("#avlB").val();
			if(flag == "0"){
				$("#huifu").hide();	
			}
			
		});
	</script>
	<%@ include file="../common/tail.jsp" %>
</body>
</html>