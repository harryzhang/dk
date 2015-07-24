<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>通知明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />
<meta name="format-detection" content="telephone=no" />
<meta name="MobileOptimized" content="320" />
<link href="${fileServerUrl }/app_res/css/elend/eLoan.css?v=${jsversion}" rel="stylesheet" type="text/css">
</head>
<body>
	<article class="tong-list p1" style="color:#999;">
			<h4 style="font-size:18px;">${notifyDo.subject}</h4>
			<time style="padding:5px 0;">${notifyDo.createTimeString}</time>
			<p>${notifyDo.message}</p>
	</article>
</body>
</html>