<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>摇一摇抢流量红包大派送</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<link type="text/css" rel="stylesheet" href="${application.basePath}/llhb/css/llhb.css">
	<script src="${application.basePath}/llhb/js/llhb.js"></script>
</head>
<body>
    <div class="act-over getHeight">
      <div class="act-again"> 
      <form method="get">
        <a href="${application.basePath}/llhb/know.jsp">
          <span class="hhn-link">了解合和年</span>
        </a>
        <a href="http://mp.weixin.qq.com/s?__biz=MzA4ODExMjAzMA==&mid=206676550&idx=1&sn=5cfef6d1f5db871846a953dd45c7184d#rd">
          <span class="hhn-link">关注合和年</span>
        </a>
        </form>
      </div>
    <div class="act-rules">
      <p>
        <a href="${application.basePath}/llhb/act-rule.jsp">
          了解活动规则<br />
          ︿
        </a>
      </p>
    </div>
  </div>
</body>
</html>