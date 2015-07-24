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
		<p>请填写您的真实姓名与身份证号码：</p>
	</nav> 
	<section>
			<div class="input-area">
				<span class="input-flag icon-real">
					<img src="${loginServerUrl }/login_res/img/icon-real.png">
				</span>
				<input type="text" class="res-input" id="realName" name="realName" placeholder="真实姓名">
			</div>
			<div class="input-area">
				<span class="input-flag icon-id">
					<img src="${loginServerUrl }/login_res/img/icon-id.png">
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
			$("#resetNextStep2").bind("click",function(){
				var realName = $("#realName").val();
				var idNo = $("#idNo").val();
				var pwdFlag = $("#pwdFlag").val();
				if(realName==null || realName.length==0){
					popWindow("请输入真实姓名");
					return;
				}
				if(idNo==null || idNo.length==0){
					popWindow("请输入身份证号码");
					return;
				}
				var options = {type:"POST",url:"${loginServerUrl }/account/verifyPwdStep2.do",data:{realName:realName,idNo:idNo}};
				ajaxRequest(options,function(data){
					if(data.result==1){
						popWindow("认证超时，请返回上一步");
					}else if(data.result==2){
						popWindow("认证失败");
					}else{
						window.location.href="${loginServerUrl }/account/reInputPwd.do?pwdFlag="+pwdFlag;
					}
				});
			});
		})
	</script>
</body>
</html>