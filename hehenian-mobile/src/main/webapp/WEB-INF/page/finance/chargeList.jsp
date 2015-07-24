<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.hhn.util.DateUtil" %>
<% request.setAttribute("menuIndex", 2); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%@ include file="../common/head.jsp" %>
        <title>${channel_name}-充值记录</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=640,target-densitydpi=320,user-scalable=no">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/base.css">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/record.css">
		<script src="http://static.hehenian.com/m/js/finance/balance.js"></script>
	</head>
<body class="bg-2">
	<header class="top-bar">
		<a href="http://m.hehenian.com/balance/index.do">
		<span class="icon-back">
			</span>
		</a>
		<p>充值记录</p>
	</header>
      <div class="container">
        <table>
          <tr>
            <th>编号</th>
            <th>交易时间</th>
            <th>金额</th>
            <th>银行卡尾号</th>
            <th>状态</th>
          </tr>
          <tbody id="listBox">
          <c:choose>
          	<c:when test="${chargeList.size()>0 }">
          		<c:forEach var="item" items="${chargeList}">
          			<tr>
          			<td>${item.actual_account_log_id}
          			<c:if test="${ not empty item.agreementFileName}">
          			<br /><a href="http://m.hehenian.com/product/queryAgreement.do?fileName=${item.agreementFileName}" target="_blank" style="color:#08B2E6;">协议 </a>
          			</c:if>
          			</td>
          			<td>${item.third_trade_time}</td>
          			<td>${item.trade_amount}</td>
            		<td>${item.from_account}</td>
            		<td>
            		<c:choose>
          			<c:when test="${item.transfer_status=='TRANSFERRED'}">充值成功</c:when>
          			<c:when test="${item.transfer_status=='NOTRANSFER'}">银行处理中</c:when>
          			<c:when test="${item.transfer_status=='FAILURE'}">充值失败</c:when>
          			<c:otherwise>&nbsp;</c:otherwise>
          			</c:choose>
          			<td/>
          			</tr>
          		</c:forEach>
          	</c:when>
          	<c:otherwise>
          		<tr><td colspan="5" align="center">查无记录</td></tr>
          	</c:otherwise>
          </c:choose>
          </tbody>
        </table>
      </div>
      	<%@ include file="../common/tail.jsp" %>
</body>
</html>