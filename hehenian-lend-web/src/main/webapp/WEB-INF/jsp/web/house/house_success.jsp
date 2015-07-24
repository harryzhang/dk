<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../include/head.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
	.wytz_center{
		width: 340px !important;
		padding: 40px;
	}
	.btn{
			height: 50px;
			line-height: 50px;
			width: 300px;
			display: inline-block;
			text-align: center;
			color: #fff;
			font-size: 22px;
			margin: 30px 20px 10px;
			background: #ff5a00;
		}
	.account_login{
		width: 300px;
		margin:0 20px 20px;
		text-align: right;
	}
</style>
</head>
<body>
	<jsp:include page="../include/top.jsp"></jsp:include>
	<div class="center">
		<div class="wytz_center">
			<img src="<c:url value='web_res/img/success.png'/>" />
	<%-- 		<a class="btn" href="${hhnServerUrl}/account/reg.do?fromUrl=http://house.hehenian.cn/house.do&source=100">免费注册</a>
			<p class="account_login">已有账号，<a href="${hhnServerUrl}/account/login-index.do?fromUrl=http://house.hehenian.cn/house.do&source=100">立即登录</a></p> --%>
		</div>
	</div>
	<jsp:include page="../include/footer.jsp"></jsp:include>
</body>
</html>
