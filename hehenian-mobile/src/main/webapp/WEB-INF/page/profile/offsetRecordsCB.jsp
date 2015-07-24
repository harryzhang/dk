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
		<title>${channel_name}-冲抵期限</title>
	</head>
<body class="bg-2">
<header class="top-bar">
		<a href="javascript:history.go(-1);">冲抵期限
		<span class="icon-back">
			</span>
		</a>
	</header>
      <div class="container">
        <table>
          <tr>
            <th>订单Id</th>
            <th><c:if test="${product.sub_channel eq 2}">物业费</c:if><c:if test="${product.sub_channel eq 3}">停车费</c:if></th>
            <th>冲抵时长</th>
            <th>操作</th>
          </tr>
          <tbody id="listBox">
          <c:if test="${empty recordList }">
			<tr><td colspan="4" align="center">查无记录</td></tr>          
          </c:if>
          <c:if test="${not empty recordList }">
	          <c:forEach var="row" items="${recordList}">
		          	<tr>
		          	<td>${row.trade_id}</td>
		          	<td>${row.fee}</td>
		          	<td>${row.begindate}~${row.enddate}</td>
		          	<td><a href="http://m.hehenian.com/profile/queryOffsetDetails.do?tradeId=${row.trade_id }" style="color:#08b2e6;">查看明细</a></td>
		          	</tr>
	          	</c:forEach>
          	 </c:if>
          </tbody>
        </table>
      </div>
       <%@ include file="../common/tail.jsp" %>
  </body>
  
</html>