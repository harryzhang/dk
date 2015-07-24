<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../common/head.jsp" %>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
					<img src="${loginServerUrl }/login_res/img/icon-lock.png">
				</span>
				<input type="password" class="res-input" id="pwd" name="pwd" placeholder="请输入新密码">
			</div>
			<div class="input-area">
				<span class="input-flag icon-lock">
					<img src="${loginServerUrl }/login_res/img/icon-locka.png">
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
			$("#updPwdBtn").bind("click",function(){
				var password = $("#pwd").val();
				var confirmPwd = $("#confirmPwd").val();
				var pwdFlag = $("#pwdFlag").val();
				var p = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/;
				if(pwdFlag == 'pay'){//支付密码
					if (password == "" || confirmPwd == "") {
						popWindow("请完整填写密码信息");
						return;
					} else if (password.length < 8 || password.length > 20) {
						popWindow("密码长度为8-20位,由数字与字母组成");
						return;
					}
					else if(!p.test(password)){
						popWindow("密码长度为8-20位,由数字与字母组成");
						return;
					}
					else if (password != confirmPwd) {
						popWindow("两次密码不一致");
						return;
					}
					var options = {type:"POST",url:"${loginServerUrl }/account/updateLoginPwd.do",data:{pwd:password,confirmPwd:confirmPwd,pwdFlag:pwdFlag}};
					ajaxRequest(options,function(data){
						if(data.result == 1){
							popWindow("填写的密码信息格式有误");
						}else if (data.result == 2) {
							popWindow("两次密码输入不一致");
						} else if (data.result == 3) {
							popWindow("新密码不能与原密码相同");
						} else if (data.result == 4) {
							popWindow("修改密码失败");
						} else {//密码修改成功，跳到成功页面
								window.location.href="${loginServerUrl }/account/resetPaySucc.do";
						}
					});
				}else{//登陆密码
					if (password == "" || confirmPwd == "") {
						popWindow("请完整填写密码信息");
						return;
					} else if (password.length < 6 || password.length > 20) {
						popWindow("密码长度必须为6-20个字符");
						return;
					} else if (password != confirmPwd) {
						popWindow("两次密码不一致");
						return;
					}
					var options = {type:"POST",url:"${loginServerUrl }/account/updateLoginPwd.do",data:{pwd:password,confirmPwd:confirmPwd,pwdFlag:pwdFlag}};
					ajaxRequest(options,function(data){
						if(data.result == 1){
							popWindow("填写的密码信息格式有误");
						} else if (data.result == 2) {
							popWindow("两次密码输入不一致");
						} else if (data.result == 3) {
							popWindow("新密码不能与原密码相同");
						} else if (data.result == 4 || data.result == 5) {
							popWindow("修改密码失败");
						} else {//密码修改成功，跳到登录页面
							popWindowCab("修改密码成功,请重新登录",function(){
								window.location.href="${loginServerUrl }/login/index.do";
							});
						}
					});
				}
			});
		})
	</script>
</html>