<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 3); %>
	<title>${channel_name}-产品展示</title>
</head>
<body class="bg-2">
	<article class="pro-detail">
		<div class="dt-bar br-1">
			<a href="http://m.hehenian.com/about/introduce.do"><p>+平台介绍<a href="http://m.hehenian.com/about/introduce.do" class="a-next">></a></p></a>
		</div>
		<div class="dt-bar br-3">
		   <c:if test="${sessionScope.channel != '2'}">
			<a href="tel://4008-303-737"><p>+客服电话&nbsp;&nbsp;4008-303-737<a href="tel://4008-303-737" id="call" class="a-next">></a></p></a>
			</c:if>
			<c:if test="${sessionScope.channel == '2'}">
			<a href="tel://4008-893-893"><p>+客服电话&nbsp;&nbsp;4008-893-893<a href="tel://4008-893-893" id="call" class="a-next">></a></p></a>
			</c:if>
		</div>
		<c:if test="${ loginId gt 0}">
			<c:if test="${sessionScope.channel != '2'}">
			<div class="dt-bar br-3">
			<a href="http://m.hehenian.com/login/loginout.do"><p>+退出登录<a href="http://m.hehenian.com/login/loginout.do" class="a-next">></a></p></a>
			</div>
			</c:if>
		</c:if>
	</article>
	<div class="ph-3">
	</div>
	<%@ include file="../common/tail.jsp" %> 
</body>
</html>
