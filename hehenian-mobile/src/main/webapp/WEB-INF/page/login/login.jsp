<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
		<script src="http://static.hehenian.com/m/js/account/account.js"></script>
		<title>${channel_name}</title>
</head>
<body class="bg-1">
	<header class="index-h">
		<div class="index-logo">
		</div>
		<p>您的随身理财管家</p>
	</header>
	<section class="log-area">
			<div>
				<input type="text" class="lis" id="userName" name="userName" placeholder="手机号码/用户名">
			</div>
			<div>
				<input type="password" class="lis" id="password" id="password" placeholder="登录密码">
			</div>
			<div>
				<input type="text" class="lis lis-check" id="code" name="code" placeholder="图片验证码">
				<span>
					 <img src="http://m.hehenian.com/common/imageCode.do?pageId=userlogin" title="点击更换验证码" id="codeNum" />
				</span>
			</div>
			<div>
		<!-- 		<input type="button" class="lis bg-s" id="login_btn" value="立即登录"> -->
		<a class="lis bg-s" id="login_btn" style="display: block;margin: auto;">立即登录</a>
			</div>
	</section>
	<section class="fnr">
	<p style="margin-top: .5rem;">
	<span><a href="http://m.hehenian.com/account/resetPwdIndex.do">忘记密码？</a></span>
	|
	<span><a href="http://m.hehenian.com/account/regIndex.do">注册理财账号</a></span>
	</p>
	</section>
	<script>
	$(function(){
		sbh();
	});
	
	document.onkeydown = function(e) {
		if (!e)
			e = window.event;
		if ((e.keyCode || e.which) == 13) {
			login();
		}
	}
	</script>
</body>
</html>