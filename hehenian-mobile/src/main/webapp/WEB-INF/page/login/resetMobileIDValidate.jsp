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
		<title>${channel_name}-重置手机号</title>
	</head>
	
	<body class="bg-2">
	<header class="top-bar">
		<a href="javascript:history.go(-1);">
			<span class="icon-back">
			</span>
		</a>
		<p>重置登录密码</p>
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
	
		$(function(){
			sbh();
			
			$("#resetNextStep2").bind("click",function(){
				var realName = $("#realName").val();
				var idNo = $("#idNo").val();
				if(realName==null || realName.length==0){
					popWindow("请输入真实姓名");
					return;
				}
				if(idNo==null || idNo.length==0){
					popWindow("请输入身份证号码");
					return;
				}
				var options = {type:"POST",url:"http://m.hehenian.com/account/verifyPwdStep2.do",data:{realName:realName,idNo:idNo}};
				ajaxRequest(options,function(data){
					if(data.result==1){
						popWindow("认证超时，请返回上一步");
					}else if(data.result==2){
						popWindow("认证失败");
					}else{
						window.location.href="http://m.hehenian.com/account/resetMobileValidateStep3.do";
					}
				});
			});
		})
	</script>
</body>
</html>