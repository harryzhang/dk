<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.hhn.util.DateUtil" %>
<% request.setAttribute("menuIndex", 2); %>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
        <meta name="viewport" content="width=640,target-densitydpi=320,user-scalable=no">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/base.css">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/record.css">
		<script src="http://static.hehenian.com/m/js/finance/balance.js"></script>
		<title>${channel_name}-冲抵明细</title>
	</head>
<body class="bg-2">
<header class="top-bar">
		<a href="javascript:history.go(-1);">冲抵明细
		<span class="icon-back">
			</span>
		</a>
	</header>
      <div class="container">
        <table>
          <tr>
            <th>期数</th>
            <th>抵扣月份</th>
            <th>冲抵状态</th>
          </tr>
          <tbody id="listBox">
          <c:forEach var="row" items="${detailList}">
	          	<tr>
	          	<td>${row.timeframe}</td>
	          	<td>${row.offsetdate}</td>
	          	<td><c:if  test="${row.infostatus ==0}">未冲抵</c:if><c:if  test="${row.infostatus ==1}">已冲抵</c:if></td>
	          	</tr>
          	</c:forEach>
          </tbody>
        </table>
      </div>
       <%@ include file="../common/tail.jsp" %>
  </body>
  
</html>