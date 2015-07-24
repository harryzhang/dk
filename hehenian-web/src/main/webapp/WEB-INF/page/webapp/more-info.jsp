<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
<title>E理财</title>
</head>
<body>
<jsp:include page="/include/wap-bottom.jsp"></jsp:include>
<div class="wrap" id="wrap" style="display:none;">
  <div class="t"><span>更多</span></div>
  <ul class="items">
    <li><a href="introduce.do"><i class="more1-icon"></i><span>平台介绍</span></a></li>
    <li><a href="qa.do"><i class="more2-icon"></i><span>常见问题(Q&amp;A)</span></a></li>
    <li><a href="feedback.do"><i class="more3-icon"></i><span>建意反馈</span></a></li>
    <li><a href="tel://4008-303-737" id="call"><i class="more4-icon"></i><span>客服电话&nbsp;&nbsp;4008-303-737</span></a></li>
  </ul>
</div>
<jsp:include page="/include/wap-js.jsp"></jsp:include>
<script src="/wap/scripts/module/more.js" ></script>
</body>
</html>