<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>更多</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><span>更多</span></h1>
<div class="wrap" id="wrap" style="display:none;">
  <ul class="items">
    <li><a href="introduce.do"><i class="more1-icon"></i><span>平台介绍</span></a></li>
    <li><a href="qa.do"><i class="more2-icon"></i><span>常见问题(Q&amp;A)</span></a></li>
    <li class="online"><a href="#" id="qq"><i class="more3-icon"></i><span style="background:none;">在线客服</span><span class="qq"><script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNDI4MV8xNzM0NDVfNDAwODMwMzczN18"></script></span></a></li>
    <li class="tel"><a href="tel://4008-303-737" id="call"><i class="more4-icon"></i><span>客服电话&nbsp;&nbsp;4008-303-737</span></a></li>
    <s:if test="#session.appstyle!='cf'">
    <li><a href="/webapp/webapp-logout.do" load-tip="登出中..."><i class="account5-icon"></i><span>登出</span></a></li>
    </s:if>
  </ul>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/more.js" ></script>
</body>
</html>