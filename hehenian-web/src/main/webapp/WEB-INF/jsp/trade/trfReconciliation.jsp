<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head></head>
<body>
	<h1>商户扣款对账</h1>
	<table border="1">
		<tr>
			<td>订单号</td>
			<td>商户号</td>
			<td>用户汇付编号</td>
			<td>交易金额</td>
			<td>交易状态</td>
			<td>交易日期</td>
			<td>汇付交易流水号</td>
		</tr>
		<c:forEach items="${params['TrfReconciliationDtoList']}" var="rrfReconciliationDto">
		<tr>
			<td>${rrfReconciliationDto['OrdId']}</td>
			<td>${rrfReconciliationDto['MerCustId']}</td>
			<td>${rrfReconciliationDto['UsrCustId']}</td>
			<td>${rrfReconciliationDto['TransAmt']}</td>
			<td>${rrfReconciliationDto['TransStat']}</td>
			<td>${rrfReconciliationDto['PnrDate']}</td>
			<td>${rrfReconciliationDto['PnrSeqId']}</td>
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