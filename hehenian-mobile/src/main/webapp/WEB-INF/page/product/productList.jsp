<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 1); %>
	<title>${channel_name}-产品展示</title>
</head>
<body class="bg-2">
	<header class="top-bar">
		<p>理财产品</p>
		<c:if test="${ channel=='1' }">
		<a href="http://m.hehenian.com/product/plist.do?channel=1&subChannel=1" class="staff-entry">
			+族宝
		</a>
		</c:if>
	</header>

	<c:if test="${ (channel=='1'  and param.subChannel=='0') or (channel=='1' and empty param.subChannel)}">
	<nav class="pro-li">
		<ul>
			<li class="li-act">
				<a href="http://m.hehenian.com/product/plist.do?channel=1&subChannel=0">+薪宝</a>
			</li>
			<li>
				<a href="http://m.hehenian.com/product/plist.do?channel=1&subChannel=2">+多宝</a>
			</li>
			<li>
				<a href="http://m.hehenian.com/product/plist.do?channel=1&subChannel=3">+車宝</a>
			</li>
		</ul>
	</nav>
	</c:if>
	<c:if test="${ (param.channel=='0' and param.subChannel=='0') }">
	<nav class="pro-li">
		<ul>
			<li class="li-act">
				<a href="http://m.hehenian.com/product/plist.do?channel=0&subChannel=0">爱定宝</a>
			</li>
			<li>
				<a href="http://m.hehenian.com/product/.do">项目投资</a>
			</li>
		</ul>
	</nav>
	</c:if>
	
	<c:forEach var="row" items="${result}">
	<article class="product-bar2">
		<p>${row.product_name}·${row.period}-M</p> 
		<a href="http://m.hehenian.com/product/detail.do?pid=${row.product_rate_id}">
		<div class="ps-bar">
			<span>${row.rateInfo * 100}%</span>
			${row.comment}
		</div>
		</a>
	</article>
	
	<div>
		<a href="http://m.hehenian.com/finance/prepay.do?pid=${row.product_rate_id}">
			<div class="pro-bn2">
				立即购买
			</div>
		</a>
	</div>
	</c:forEach>
	
	<%@ include file="../common/tail.jsp" %>
</body>
</html>