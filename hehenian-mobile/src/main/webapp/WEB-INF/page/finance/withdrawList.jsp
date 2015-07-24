<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.hhn.util.DateUtil" %>
<% request.setAttribute("menuIndex", 2); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="../common/head.jsp" %>
        <title>${channel_name}-提现记录</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=640,target-densitydpi=320,user-scalable=no">
		<link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/base.css">
		<link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/record.css">
		<script src="http://static.hehenian.com/m/js/finance/balance.js"></script>
	</head>
	<body class="bg-2">
	<header class="top-bar">
		<a href="javascript:history.go(-1);">
		<span class="icon-back">
			</span>
		</a>
		<p>提现记录</p>
	</header>
	<div class="container">
        <table>
          <tr>
            <th>流水号</th>
            <th width="22%">提现时间</th>
            <th>金额(元)</th>
            <th>资金渠道</th>
            <th>状态</th>
          </tr>
          <tbody id="listBox">
          <c:choose>
          	<c:when test="${drawList.size()>0 }">
          		<c:forEach var="item" items="${drawList}">
          			<tr>
          			<td>${item.withdraw_id}</td>
          			<td>${item.create_time}</td>
          			<td>￥ ${item.amount}</td>
            		<td>${item.bank_name}</td>
            		<td>
            		<c:choose>
          			<c:when test="${item.status==1}">审核中</c:when>
          			<c:when test="${item.status==2}">提现中</c:when>
          			<c:when test="${item.status==3}">提现成功</c:when>
          			<c:otherwise>提现失败</c:otherwise>
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