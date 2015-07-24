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
			<!-- <p>+薪宝<a href="javascript:void()" class="a-next">></a></p> -->
			<p>爱定宝<a href="javascript:void()" class="a-next"><div class="ct">&gt;</div></a></p>
		</div>
		<!-- <div id="zubao" class="dt-bar br-3">
			<p>+族宝<a href="" class="a-next">></a></p>
			<p>+族宝<a href="" class="a-next"><div class="ct">&gt;</div></a></p>
		</div> -->
		<div id="projectInvest" class="dt-bar br-4">
			<!-- <p>项目投资<a href="" class="a-next">></a></p> -->
			<p>项目投资<a href="" class="a-next"><div class="ct">&gt;</div></a></p>
		</div>
<!-- 		<div class="dt-bar br-6"> -->
<!-- 			<p>彩富人生<a href="" class="a-next">></a></p> -->
<!-- 		</div>	 -->
	</article>
	
	<p class="gray-tips">
        第三方合作
    </p>

    <ul class="pic-text-list">
        <li class="br-3">
            <a href="http://m.hehenian.com/profile/thirdparty.do?channel=0">
                <div class="img" style="background-image: url(http://static.hehenian.com/m/img/m-logo1.jpg);"></div>
                <div class="content">
                    <p>• <b>E理财</b> -- 爱定宝、项目投资</p>
                    <p>• 彩富人生</p>
                </div>
                <div class="ct">&gt;</div>
            </a>
        </li>
        <li class="br-4">
            <a href="http://m.hehenian.com/profile/thirdparty.do?channel=1">
                <div class="img" style="background-image: url(http://static.hehenian.com/m/img/m-logo2.jpg);"></div>
                <div class="content">
                    <p>+<b>理财</b></p>
                    <p>+薪宝</p>
                    <p>+族宝</p>
                </div>
                <div class="ct">&gt;</div>
            </a>
        </li>
    </ul>

	
	
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
				location.href="http://m.hehenian.com/profile/salaryDesc.do?flag=adb";
			});
			
			//+族宝
			$("#zubao").click(function(){
				location.href="http://m.hehenian.com/profile/salaryDesc.do?flag=zu";
			});
		
			// 项目投资
			$("#projectInvest").click(function(){
				location.href="http://m.hehenian.com/finance/projectInvest.do";
			});
			
			var phone = "${mobile}";//to do
			$(".self-num").text(phone.substring(0,3) + "****" + phone.substring(7,11));
		});
	</script>
</body>
</html>
