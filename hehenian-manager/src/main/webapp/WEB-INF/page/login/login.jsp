<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>合和年管理后台登录页</title>
<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
<link type="text/css" rel="stylesheet" href="${basePath }/css/login.css" />
</head>
<body onload='document.f.j_username.focus();' class="login">
	
	<div class="login_top">
	<div class="login_width">
		<div class="login_top_l">
			<div class="login_logo"></div>
			<div class="login_name">合和年管理后台</div>
		</div>
		<div class="login_top_c"></div>
		<div class="login_top_r">
			<p id="xianshi"></p>
		</div>
	</div>
</div>
<form name='f' action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
<div class="login_main">
	<div class="login_border">
		<div class="login_border_h"></div>
		<div class="login_border_c">
			<div><input id="username" class="username" name='j_username'  type="text" size="15" /></div>
			<div><input id="password" class="password" name='j_password'  type="password" size="15" /></div>
		</div>
		<div class="login_border_b">
			<p id="errorInfo" class="errorInfo"></p>
        	<a id="login" class="button1" href="javascript:void(0)" title="登录"></a>
		</div>
	</div>
</div>
</form>
<div class="login_bottom">
	<P>Copyright &copy; 2015 版权所有：深圳市合和年</P>
</div>
<script type="text/javascript" src="${basePath}/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript">

$(function(){
	var errorTemp = '${error}';
	if(errorTemp && errorTemp=='true'){
		$('#errorInfo').show().text('用户名或密码错误！');
	}
});

window.onload = function(){
	(function(){
		document.getElementById("xianshi").innerHTML="现在的时间是"+new Date().toLocaleString();
		setTimeout(arguments.callee,1000);
	})();
	
}
window.onkeydown=function(e){
	var ev = (typeof event!= 'undefined') ? window.event : e;
	if(ev.keyCode == 13) {
       checkLogin();
    }
}
document.getElementById("login").onclick=checkLogin;

function checkLogin(e){
	var name=document.getElementById("username").value;
	var password=document.getElementById("password").value;
	if(!name){
		alert("请输入用户名");
		return false;
	}
	if(!password){
		alert("请输入密码");
		return false;
	}
	document.f.submit();
}
</script>

</body>
</html>