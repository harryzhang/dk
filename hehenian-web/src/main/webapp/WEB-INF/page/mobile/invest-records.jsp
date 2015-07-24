<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>投资明细</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><span>投资明细</span></h1>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <dl class="table fund-table" id="table">
      <dt><span>债权编号</span><span>交易日期</span><span>投资金额<br/>(元)</span><span>已收金额<br/>(元)</span></dt>
      <s:iterator value="#request.investRecords" var="bean">
        <a href="webapp-financeDetail.do?id=${bean.borrowId}"><span>${bean.invest_number }</span><span>${bean.investTime }</span><span>${bean.investAmount }</span><span>${bean.hasPI }</span></a>
      </s:iterator>
  </dl>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<script>var noIScroller = true;</script>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
</body>
</html>