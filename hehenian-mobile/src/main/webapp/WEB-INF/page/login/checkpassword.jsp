<%@ page contentType="text/html; charset=utf-8"%>
<% request.setAttribute("menuIndex", 2); %>
<!doctype html>
<html>
	<head>
		<%@ include file="../common/head.jsp" %>
		<title>红包理财-用户绑定</title>
	</head>
	
	<body class="bg-2">
	<header class="top-bar">
		<a href="${remoteAddr }">
		<span class="icon-back">
			</span>
		</a>
		<p>用户绑定</p>
	</header>
	<div class="real-tips">
	</div>
	<section class="sign-area">
			<div class="sign-style br-2">
			<span class="sign-lable">手机号</span>
				<input type="text" id="mobilePhone" name="mobilePhone" value="${mobilePhone}" readonly="readonly">
			</div>
			<div class="sign-style br-4 bb-1">
				<span class="sign-lable">登录密码</span>
				<input type="password" id="password" name="password" value="${password}"  placeholder="请输入合和年密码">
			</div>
			<div class="sign-sub">
			<input type="hidden" id="remoteAddr" value="${remoteAddr}">
			<c:if test="${password==null || password=='' }">
				<input type="button" value="提交" id="authBtn">
			</c:if>
			<a class="forgetPassword" href="http://m.hehenian.com/account/resetPwdIndex.do">忘记密码</a>
			</div>
	</section>
	<%@ include file="../common/tail.jsp" %>
</body>
<script type="text/javascript">
$(function(){

	$("#authBtn").bind("click",function() {
		var password = $("#password").val();
		if (password == "") {
			 popWindow("请输入密码！");
			 return;
		}
			var param = {};
			param["password"] = password;
			$.post("http://m.hehenian.com/product/checkPassWord.do", param, function(data) {
				if (data.msg == "Y") {
					if(data.status == '3'){
						window.location.href="http://m.hehenian.com/product/plist.do";
					}else if(data.status == '2'){
						window.location.href="http://m.hehenian.com/account/realAuth.do";
					}
					
				}else{
					popWindow(data.msg);
				}
			},"json");
	});
	$(".pro-ft-unlog").hide();
})
</script>
</html>
