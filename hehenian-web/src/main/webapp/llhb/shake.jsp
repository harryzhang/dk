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
  <div class="shake getHeight">
    <div class="phone-shake">
    </div>
    <div class="phone-num">
    <input type="hidden" name="mobile">
      <span>${mobile}</span>
      <p>当前参加活动的手机号码</p>
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
  <script> 
    var shakeThreshold = 7000;
    var lastUpdate = 0;
    var x, y, z, lastX, lastY, lastZ; 
     var shakeIcon = document.getElementsByClassName("phone-shake")[0];
    shakeIcon.style.webkitAnimationPlayState = "paused";
    window.addEventListener('devicemotion', deviceMotionHandler, false);
    function deviceMotionHandler(eventData) {
      var acceleration = eventData.accelerationIncludingGravity; 
      var curTime = new Date().getTime();
      if ((curTime - lastUpdate) > 100) {
        var diffTime = curTime - lastUpdate;
        lastUpdate = curTime;
        x = acceleration.x;
        y = acceleration.y;
        z = acceleration.z;
        var speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;
        if (speed > shakeThreshold) {
          //跳转到成功或者失败页面
          shakeIcon.style.webkitAnimationPlayState = "running";
          setTimeout(placeOrder(),1000);
        }
        lastX = x;
        lastY = y;
        lastZ = z;
      }
    }
    function placeOrder(){
      location.href = "/webapp/placeOrder.do?mobile="+${mobile};
    }
    </script>
  </body>
  </html>