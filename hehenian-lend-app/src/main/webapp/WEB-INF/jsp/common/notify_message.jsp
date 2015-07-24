<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>消息提示界面</title>
		<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=0" />
		<style>
			*{margin: 0;padding:0;}
			.alt{width:180px;height: 40px;line-height:40px; text-align: center;font-size: 25px;position: absolute;left:50%;top: 50%;margin: -20px 0 0 -90px;color: #7F53EA;}
		</style>
	</head>
	<body>
		<section>
			<p class="alt">${message }</p>
		</section>
	</body>
</html>