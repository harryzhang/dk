<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
<c:if test="${sessionScope.channel == '1'}">
<% request.setAttribute("menuIndex", 1); %>
	<title>${channel_name}-产品详情-${result.product_name}</title>
</c:if>
<c:if test="${sessionScope.channel == '2'}">
<% request.setAttribute("menuIndex", 0); %>
	<title>${channel_name}-产品详情-${result.product_name}</title>
</c:if>
<c:if test="${sessionScope.channel == '0'}">
	<title>合和年在线</title>
</c:if>
</head>
<body class="bg-2">
	<header class="top-bar bg-f">
		<a href="javascript:history.go(-1);">
			<span class="icon-back2">
			</span>
		</a>
		<p>产品详情</p>
	</header>
	<div class="prodetail-top">
		<span class="rate-num">${result.rateInfo * 100}%</span>
		<p class="prohead-name">${result.product_name}·${result.period}-M</p>
		<p class="prohead-feature">预期年化收益率${result.rateInfo * 100}%</p>
	</div>
	<article class="pro-detail">
	  <c:if  test="${!(sessionScope.channel == '1'  and ( result.sub_channel=='3' or result.sub_channel=='2')) }">
		<div class="dt-bar br-1">
			<p>${result.product_name}<span></span></p>
			<div class="d-detail">
				<p>${result.product_name}是合和年在线推出的对100%逾期本息代偿的借款项目进行自动投资的理财计划。投资期届满后，本金、收益将一次性返回到您的账户中。</p>
			</div>
		</div>
		<div class="dt-bar br-2">
			<p>概要<span></span></p>
			<div class="d-detail">
				<p>加入条件：<span>${result.channel eq 2 ? 500 : 1 }元起投</span></p>
				<p>购买期限：<span>${result.period}个月</span></p>
				<p>锁定期：<span>${result.channel eq 2 ? result.period : 3 }个月，锁定期内不可赎回</span></p>
				<p>计息日期：<span>购买后下一工作日</span></p>
				<p>收益方式：<span>到期收回投资及收益</span></p>
			</div>
		</div>
		<div class="dt-bar br-3">
			<p>费用<span></span></p>
			<div class="d-detail">
				<p>加入费率：<span>0.00%</span></p>
				<c:if test="${sessionScope.channel == '0' || sessionScope.channel == '2' }">
					<p>管理费率：<span>参见爱定宝服务协议</span></p>
					<p>提前赎回费率：<span>参见爱定宝服务协议</span></p>
				</c:if>
				<c:if test="${sessionScope.channel == '1'}">
					<p>管理费率：<span>参见+薪宝服务协议</span></p>
					<p>提前赎回费率：<span>参见+薪宝服务协议</span></p>
				</c:if>
			</div>
		</div>
		<div class="dt-bar br-4">
			<p>赎回<span></span></p>
			<div class="d-detail">
				<p>到期赎回方式：<span>债权自动优先转让</span></p>
				<p>提前赎回方式：<span>${result.channel eq 2 ? "不支持提前赎回" : "锁定期后，支持提前赎回" }</span></p>
			</div>
		</div>
		</c:if>
		
		<c:if  test="${sessionScope.channel == '1'  and result.sub_channel=='3' }">
			<div class="dt-bar br-1">
			<p>${result.product_name}<span></span></p>
			<div class="d-detail">
				<p>+車宝是国际物业与花样年集团联合推出的感恩回馈活动，业主只需预存一定金额的存款，即可冲抵一年加一个月的停车费，同时享受3%的高于银行定存年化收益。</p>
			</div>
		</div>
		<div class="dt-bar br-2">
			<p>概要<span></span></p>
			<div class="d-detail">
				<p>加入条件：<span>国际物业社区车主</span></p>
				<p>购买期限：<span>12个月</span></p>
				<p>锁定期：<span>3个月，锁定期内不可赎回</span></p>
				<p>冲抵期限：<span>13个月</span></p>
				<p>预期收益：<span>年化收益率3%</span></p>
				<p>计息日期：<span>投资成功后的第一个工作日</span></p>
				<p>收益方式：<span>到期收回投资及收益</span></p>
			</div>
		</div>
		
		</c:if>
		<c:if  test="${sessionScope.channel == '1'  and result.sub_channel=='2' }">
			<div class="dt-bar br-1">
			<p>${result.product_name}<span></span></p>
			<div class="d-detail">
				<p>+多宝是国际物业与花样年集团联合推出的感恩回馈活动，业主只需预存一定金额的存款，即可冲抵一年的物业费，同时享受3%的高于银行定存年化收益。</p>
			</div>
		</div>
		<div class="dt-bar br-2">
			<p>概要<span></span></p>
			<div class="d-detail">
				<p>加入条件：<span>国际物业社区业主</span></p>
				<p>购买期限：<span>12个月</span></p>
				<p>锁定期：<span>3个月，锁定期内不可赎回</span></p>
				<p>冲抵期限：<span>12个月</span></p>
				<p>预期收益：<span>年化收益率3%</span></p>
				<p>计息日期：<span>投资成功后的第一个工作日</span></p>
				<p>收益方式：<span>到期收回投资及收益</span></p>
			</div>
		</div>
		
		</c:if>
		
		<c:if test="${not empty recentList }">
		<div class="dt-bar br-6">
			<p>交易记录<span></span></p>
			<div class="d-detail">
				<div class="clear-flow">
				<p></p>
				</div>
				<c:forEach var="row" items="${recentList}">
				<div class="clear-flow">
					<p>${row.cellPhone}<span>${row.trade_time}</span></p>
					<p><span>￥${row.tradeAmountInfo}</span></p>
				</div>
				</c:forEach>
			</div>
		</div>
		</c:if>
		</article>
		<div class="ph-3">
			<div>
				<footer class="buy-now">
				<c:if test="${ loginId gt 0}">
					<c:choose >
						<c:when test="${sessionScope.channel == '2'}">
							<a href="http://m.hehenian.com/finance/prepayHb.do?pid=${result.product_rate_id}">立即购买</a>
						</c:when>
						<c:when  test="${sessionScope.channel == '1'  and ( result.sub_channel=='3' or result.sub_channel=='2') }">
							<a href="http://m.hehenian.com/product/queryDefaultOffset.do?pid=${result.product_rate_id}">立即购买</a>
						</c:when>
						<c:otherwise>
					<a href="http://m.hehenian.com/finance/prepay.do?pid=${result.product_rate_id}">立即购买</a>
						</c:otherwise>
					</c:choose>
						
					
					</c:if>
				<c:if test="${ empty loginId or loginId le 0}">
				<a href="http://m.hehenian.com/login/index.do">立即登录</a>
				</c:if>
				</footer>
				
				<script>
					$(function(){
						sbh();
					})
				</script>
</body>
</html>