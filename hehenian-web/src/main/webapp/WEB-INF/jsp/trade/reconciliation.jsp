<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head></head>
<body>
	<h1>投标对账(放款和还款对账)</h1>
	<table border="1">
		<tr>
			<td>订单号</td>
			<td>下单日期</td>
			<td>商户号</td>
			<td>InvestCustId</td>
			<td>BorrCustId</td>
			<td>交易金额</td>
			<td>交易状态</td>
			<td>交易日期</td>
			<td>汇付交易流水号</td>
		</tr>
		<c:forEach items="${params['reconciliationDtoList']}" var="reconciliationDto">
		<tr>
			<td>${reconciliationDto['OrdId']}</td>
			<td>${reconciliationDto['OrdDate']}</td>
			<td>${reconciliationDto['MerCustId']}</td>
			<td>${reconciliationDto['InvestCustId']}</td>
			<td>${reconciliationDto['BorrCustId']}</td>
			<td>${reconciliationDto['TransAmt']}</td>
			<td>${reconciliationDto['TransStat']}</td>
			<td>${reconciliationDto['PnrDate']}</td>
			<td>${reconciliationDto['PnrSeqId']}</td>
		</tr>
		</c:forEach>
	</table>
	
	<br/> <br/>
	查询条件：开始日期：${params['BeginDate']}，结束日期：${params['EndDate']}，页码：${params['PageNum']}，每页条数：${params['PageSize']}
	<br/>
	返回状态：${params['RespDesc']}
	
	<br/>
	交易状态说明：S 成功,F 失败,I 初始,P 部分成功,H 经办,R 拒绝
</body>
</html>