<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setAttribute("menuIndex", 2); %>
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
	<header class="self-bar">
		<p id="auth_info"><span class="icon-self"></span><span class="self-num"> </span>
		<a href="javascript:void(0)">></a></p>
	</header>
	<article class="pro-detail">
		<div class="self-money">
		<input type="hidden" id="balance" value="${balance}"/>
		<input type="hidden" id="AvlBal" value="${AvlBal}"/>
			<span id="availableBalance" class="self-balance">
				<span class="self-tips">
					可用余额
				</span>
				<span id="totalAM" class="sm-num">0</span>
			</span>
			<span id="cardMan" class="self-card">
				<span class="icon-card"></span>
				银行卡
				<span>${cardNum}张</span>
			</span>
		</div>
		<div id="salary" class="dt-bar br-1">
			<p>+薪宝<a href="javascript:void()" class="a-next">></a></p>
		</div>
		<div id="zubao" class="dt-bar br-3">
			<p>+族宝<a href="" class="a-next">></a></p>
		</div>
		<div id="duobao" class="dt-bar br-4"> 
 			<p>+多宝<a href="" class="a-next">></a></p> 
 		</div> 
		<div id="chebao" class="dt-bar br-6"> 
			<p>+車宝<a href="" class="a-next">></a></p> 
		</div>	
	</article>
	<div class="ph-3">
	</div>
	<%@ include file="../common/tail.jsp" %>
	<script>
		$(function(){
			sbh();
			
			//可用余额=汇通+汇付
			var balance = $("#balance").val();
			var AvlBal = $("#AvlBal").val();
			$("#totalAM").text(parseInt(balance)+parseInt(AvlBal));
			 var accountType = "${sessionScope.user.accountType}";
			//银行卡管理
			$("#cardMan").click(function(){
				if(accountType == '1'){
					popWindow("无需绑定银行卡");
				}else{
					location.href="http://m.hehenian.com/profile/managerCard.do";
				}
			});
			
			//点击进入个人信息详情页面
			$("#auth_info").click(function(){
				location.href="http://m.hehenian.com/profile/userinfo.do?flag=q";
			});
			
			//点击进入可用余额
			$("#availableBalance").click(function(){
// 				location.href="http://m.hehenian.com/profile/totalBalance.do";
				location.href="http://m.hehenian.com/balance/index.do";
			});
			
			// +薪宝
			$("#salary").click(function(){
				location.href="http://m.hehenian.com/profile/salaryDesc.do?flag=xin";
			});
			
			//+族宝
			$("#zubao").click(function(){
				location.href="http://m.hehenian.com/profile/salaryDesc.do?flag=zu";
			});
			
			//+多宝
			$("#duobao").click(function(){
				location.href="http://m.hehenian.com/profile/salaryDesc.do?flag=duo";
			});
			
			//+车宝
			$("#chebao").click(function(){
				location.href="http://m.hehenian.com/profile/salaryDesc.do?flag=che";
			});
			var phone = "${mobile}";//to do
			$(".self-num").text(phone.substring(0,3) + "****" + phone.substring(7,11));
		});
	</script>
</body>
</html>
