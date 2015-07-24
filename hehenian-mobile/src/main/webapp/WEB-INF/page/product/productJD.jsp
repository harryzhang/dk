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
		<a href="http://m.hehenian.com/product/plist.do?channel=1&subChannel=1" class="staff-entry">
			+族宝
		</a>
	</header>
	<nav class="pro-li">
		<ul>
			<li>
				<a href="http://m.hehenian.com/product/plist.do?channel=1&subChannel=0">+薪宝</a>
			</li>
			<li class="li-act">
				<a href="http://m.hehenian.com/product/plist.do?channel=1&subChannel=2">+多宝</a>
			</li>
			<li>
				<a href="http://m.hehenian.com/product/plist.do?channel=1&subChannel=3">+車宝</a>
			</li>
		</ul>
	</nav>
	<c:forEach var="row" items="${result}">
	<article class="product-bar">
		<p class="product-name">${row.product_name}</p>
		<p class="product-feature">${row.comment}</p>
		<a href="http://m.hehenian.com/product/detail.do?pid=${row.product_rate_id}">
			<section class="product-detail2">
			</section>
			</a>
			<a href="http://m.hehenian.com/product/queryDefaultOffset.do?pid=${row.product_rate_id}">
			<div class="pro-bn">
				立即购买
			</div>
			</a>
	</article>
	</c:forEach>
	<%@ include file="../common/tail.jsp" %>
</body>
</html>