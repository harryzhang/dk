<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 0); %>
	<title>${channel_name}-产品展示</title>
</head>
<body class="bg-2">
	<header class="top-bar">
		<p>理财产品</p>
	</header>
	<c:if test="${ loginId gt 0}">
	<section>
		<table class="product-result">
			<tr>
				<td width="50%"><em>可用红包</em><span>￥<fmt:formatNumber value="${balance}" pattern="#0.0"/></span></td>
				<td width="50%"><em>投资金额</em><span>￥${principal}</span></td>
			</tr>
			<tr>
				<td><em>待收收益</em><span>￥<fmt:formatNumber value="${interested}" pattern="#0.0"/></span></td>
				<td><em>累计收益</em><span>￥${interest}</span></td>
			</tr>
		</table>
		<div class="product-photo border_radio">
			<div class="photo-quan border_radio">
				<p>
					<span class="photo-head border_radio"></span>
					<em class="photo-body border_radio"></em>
				</p>
			</div>
		</div>
	</section>
	</c:if>
	<c:if test="${ empty loginId or loginId le 0}">
		<br />
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
		<a href="http://m.hehenian.com/finance/prepayHb.do?pid=${row.product_rate_id}">
			<div class="pro-bn2">
				立即购买
			</div>
		</a>
	</div>
	</c:forEach>
	<%@ include file="../common/tail.jsp" %>
</body>
</html>