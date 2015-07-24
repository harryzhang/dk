<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <html>
	<head>
		<%@ include file="../include/top.jsp"%>
		<meta charset="utf-8" />
		<title>业绩查询</title>
	</head>
	<body>
		<section>
			<article>
				<ul class="performance_nav">
					<li>
						<a href="<c:url value='/app/mhk/getPerform.do?status=PENDING'/>" class="db_f">
						<label>新订单</label>
						<p class="bf1 t_ar"><em class="fcl dib">${pendingOrder}</em>笔</p>
						</a>
					</li>
					<li>
						<a href="<c:url value='/app/mhk/getPerform.do?status=BORROWING'/>" class="db_f">
						<label>还款中</label>
						<p class="bf1 t_ar"><em class="fcl dib">${borrowingOrder }</em>笔</p>
					</a>
					</li>
					<li>
						<a href="<c:url value='/app/mhk/getPerform.do?status=BORROWED'/>" class="db_f">
						<label>已还清</label>
						<p class="bf1 t_ar"><em class="fcl dib">${borrowedOrder }</em>笔</p>
					</a>
					</li>
					<li>
						<a href="<c:url value='/app/mhk/getPerform.do?status=NOPASS'/>" class="db_f">
						<label>已拒绝</label>
						<p class="bf1 t_ar"><em class="fcl dib">${nopassOrder }</em>笔</p>
						</a>
					</li>
				</ul>
			</article>	
		</section>
		<%@ include file="../include/foot.jsp"%>
		<script>	
				$( function() {
					 sessionStorage.setItem("index", 2);
				} )
		</script>
	</body>
</html>