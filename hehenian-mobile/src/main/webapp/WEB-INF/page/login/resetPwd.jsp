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
		<p>请输入您在合和年注册绑定的手机号码：</p>
	</nav> 
	<section>
			<div class="input-area">
				<span class="input-flag icon-phone">
					<img src="http://static.hehenian.com/m/img/icon-phone.png">
				</span>
				<input type="text" class="res-input" id="mobilePhone" name="mobilePhone" value="${mobile}">
			</div>
			<div class="input-area">
				<span class="input-flag icon-text">
					<img src="http://static.hehenian.com/m/img/icon-text.png">
				</span>
				<input type="text" class="res-input" id="identifyCode" name="identifyCode" placeholder="请输入短信验证码">
				<span class="getc-btn" disable="no">
				<a id="reqCode" href="javascript:;">获取验证码</a>
				</span>
			</div>
			<!-- <div class="input-area">
				<span class="input-flag icon-pcheck">
					<img src="http://static.hehenian.com/m/img/icon-pcheck.png">
				</span>
				<input type="text" class="res-input" placeholder="请输入验证码">
				<span class="checkcode">
				<img src="">
				</span>
			</div> -->
			<div class="res-sub">
				<input type="button" class="res-input" id="resetNextStep" value="下一步">
			</div>
	</section>
	<script>
		$(function(){
			sbh();
			
			//下一步
			$("#resetNextStep").bind("click",function(){
				var mobilePhone = $("#mobilePhone").val();
				var identifyCode = $("#identifyCode").val();
				var pwdFlag = $("#pwdFlag").val();
				if(mobilePhone==null || mobilePhone.length==0){
					popWindow("请输入手机号");
					return;
				}
				if(identifyCode==null || identifyCode.length==0){
					popWindow("请输入手机验证码");
					return;
				}
				var options = {type:"GET",url:"http://m.hehenian.com/account/verifyPwdStep1.do",data:{mobilePhone:mobilePhone,identifyCode:identifyCode}};
				ajaxRequest(options,function(data){
					if(data.result==1){
						popWindow("手机验证码不正确");
					}else if(data.result==2){
						popWindow("获取手机号信息失败");
					}else{
						window.location.href="http://m.hehenian.com/account/resetJumpPage.do?pwdFlag="+pwdFlag;
					}
				});
			});
			
			$('#reqCode').bind("click",function(){
			   if ($(this).attr("disable") == null) {
				 var param = {checkPhone:false};
				 sendPhoneVirifyCode(param);
			   }
			});
		});
		
			function sendPhoneVirifyCode(param) {
			var mobilePhone = $("#mobilePhone");
			if(mobilePhone.val()==null || mobilePhone.val().length==0){
				popWindow("请输入手机号码");
				return;
			}
			var verifyR = verifyPhone_(mobilePhone.val());
			if(verifyR!=""){
				popWindow(verifyR);
				return;
			}
			param["mobilePhone"]=mobilePhone.val();
			$.post("http://m.hehenian.com/common/sendPhoneVirifyCode.do", param, function(data) {
				if(data.ret==1){
					popWindow("发送验证码失败");
				}else if(data.ret==2){
					popWindow("手机号码不存在");
				}else if(data.ret==3){
					popWindow("手机号码已存在");
				}else{
					$("#reqCode").attr("disable", true).text("60s重获");
					telephoneTimer_();
				}
			},"json");
	}
	//异步请求
	function ajaxRequest(options,callback){
		$.ajax({ 
			type:options.type,
			dataType: "json",			
			url:options.url, 
			data:options.data,
			success:callback,
			error:function(data){
				popWindow("网络异常，稍后重试");
			}
		});
	}
	//倒计时
	function telephoneTimer_() {
		var count = 60;
		var timer=setInterval(function(){
			if(count <= 0){
				clearInterval(timer);
				$("#reqCode").removeAttr("disable").text("获取验证码");
			}else{
				count--;
				$("#reqCode").text(count+"s重获");
			}
		},1000);
	}

	function verifyPhone_(telephone){
	    if (telephone == "") {
	        return "请输入手机号";
	    } else if (!/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(telephone)) {
	        return "输入手机号的格式有误";
	    }else if(telephone.length!=11){
	        return "输入手机号的格式有误";
	    }else{
	        return "";
	    }
	}
	</script>
</body>
</html>