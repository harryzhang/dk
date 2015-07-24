<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 0); %>
	<title>${channel_name}-产品展示</title>
</head>
<body class="bg-2">
	<header class="top-bar">
		<p>+理财</p>
		<c:if test="${ param.channel=='1' and param.subChannel=='0' }">
		<a href="http://m.hehenian.com/product/plist.do?channel=1&subChannel=1" class="staff-entry">
			+族宝
		</a>
		</c:if>
	</header>
	<c:forEach var="row" items="${result}">
	<article class="product-bar">
		<p class="product-name">${row.product_name}·${row.period}-M</p>
		<p class="product-feature">收益稳健，安全快捷</p>
		<section class="product-detail">
			<p>预期年化收益率</p>
			<p class="pro-num">${row.rateInfo * 100}%</p>
			<a href="http://m.hehenian.com/product/detail.do?pid=${row.product_rate_id}" class="pro-more">
				查看详情 >>
			</a>
		</section>
		<a href="http://m.hehenian.com/finance/prepay.do?pid=${row.product_rate_id}">
			<div class="pro-bn">
				立即购买
			</div>
		</a>
	</article>
	</c:forEach>
	<%@ include file="../common/tail.jsp" %>
	<script>
		$(function(){
			sbh();
		})
	</script>
</body>
</html>