<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head></head>
<body>
	<h1>充值对账</h1>
	<table border="1">
		<tr>
			<td>商户号</td>
			<td>用户汇付编号</td>
			<td>订单号</td>
			<td>下单日期</td>
			<td>交易金额</td>
			<td>交易状态</td>
			<td>GateBusiId</td>
			<td>OpenBankId</td>
			<td>OpenAcctId</td>
			<td>FeeAmt</td>
			<td>FeeCustId</td>
			<td>FeeAcctId</td>
		</tr>
		<c:forEach items="${params['SaveReconciliationDtoList']}" var="saveReconciliationDto">
		<tr>
			<td>${saveReconciliationDto['MerCustId']}</td>
			<td>${saveReconciliationDto['UsrCustId']}</td>
			<td>${saveReconciliationDto['OrdId']}</td>
			<td>${saveReconciliationDto['OrdDate']}</td>
			<td>${saveReconciliationDto['TransAmt']}</td>
			<td>${saveReconciliationDto['TransStat']}</td>
			<td>${saveReconciliationDto['GateBusiId']}</td>
			<td>${saveReconciliationDto['OpenBankId']}</td>
			<td>${saveReconciliationDto['OpenAcctId']}</td>
			<td>${saveReconciliationDto['FeeAmt']}</td>
			<td>${saveReconciliationDto['FeeCustId']}</td>
			<td>${saveReconciliationDto['FeeAcctId']}</td>
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