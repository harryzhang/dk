<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>提示</title>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<!--顶部底部浮动层-->

<jsp:include page="/include/app-comm.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap">
  <div id="container">
    <div class="content">
      <div class="section-title" style=" padding-bottom:10px;">
       <!--  <h4>提示</h4> -->
      </div>
      <div class="one-half-responsive" style=" height:450px; text-align:center; padding-top:150px;">
        <p style=" padding:20px 20px 0px 20px;"><span style=" font-size:28px;" id="result"></span></p>
        <p style=" padding:20px 20px 0px 20px;"><span>5秒后自动跳转到首页，<a href="/appcomm/index.do">您也可以点击立即跳转>></a></span></p>
      </div>
    </div>
  </div>
</div>
</body>
<script>
setTimeout(function(){window.location.href="/appcomm/index.do"},5000);
</script>
<script>
        var url = window.location+"";
        url = decodeURI(url);
        var start = url.indexOf("title=") + "title=".length;
        var ret = url.substring(start);
        $("#result").text(ret);
</script>

</html>
