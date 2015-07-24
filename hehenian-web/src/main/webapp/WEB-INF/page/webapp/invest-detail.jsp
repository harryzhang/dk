<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
<title>投资明细</title>
</head>
<body>
<jsp:include page="/include/wap-bottom.jsp"></jsp:include>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <div class="t"><span>投资明细</span></div>
  <dl class="table">
    <dt><span>借款标题</span><span>交易日期</span><span>投资金额(元)</span><span>借款状态</span></dt>
    <s:if test="#request.investRecords!=null&&#request.investRecords.size()>0">
        <s:iterator value="#request.investRecords" var="bean" status="status">
        	<dd><a href="webapp-financeDetail.do?id=${bean.id}"><span>${bean.borrowTitle }</span><span>${bean.investTime }</span><span>${bean.investAmount }</span><span><s:if test="%{#bean.borrowStatus == 2}">撮合中</s:if><s:if test="%{#bean.borrowStatus == 3}">满标</s:if><s:if test="%{#bean.borrowStatus == 4}">还款中</s:if><s:if test="%{#bean.borrowStatus == 5}">已还完</s:if><s:if test="%{#bean.borrowStatus == 6}">流标</s:if></span></a></dd>
        </s:iterator>
  	</s:if>
  </dl>
  <div><a href="webapp-invest-records.do" class="button-big button-magenta">查看更多投资记录</a></div>
</div>
<jsp:include page="/include/wap-js.jsp"></jsp:include>
</body>
</html>