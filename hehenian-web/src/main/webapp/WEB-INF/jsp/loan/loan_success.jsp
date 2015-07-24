<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<style>
a:HOVER {
	color: #f07a05;
}
</style>
</head>
<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="center">
		<div class="wytz_center">
			<div
				style="text-align: center; margin-top: 130px; margin-bottom: 130px;">
				<span style="font-size: 24px; color: #eb6100;"> 
					<span id="result">
                       ${message}
                    </span>
				</span>
				<br/><br/><br/><br/>
				<input type="button" onclick="window.location.href='/account/reg.do'" value="免费注册" style=" width:238px; height:40px; border:0px; background:#F60; font-size:16px;color:#FFF; margin-left:0px;"/>
				<br/><br/>已有账号，<a href="/account/login-index.do">立即登录</a>
			</div>
		</div>
	</div>
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
