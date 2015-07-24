<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<style type="text/css">
.wrap1 {  width:100%;min-width:320px;margin:0 auto;font-size:1em; padding-bottom:0px; }
</style>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<c:url value='/wap/mobile/styles/hhn.common.css?t=1'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/wap/mobile/styles/hhn.layout.css?t=1'/>" />
    <%if(session.getAttribute("appstyle")!=null){%>
    <link rel="stylesheet" type="text/css" href='/wap/mobile/styles/hhn.<%=session.getAttribute("appstyle") %>.css?t=2' />
    <%}%>
<title>投资项目</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t-a title"><strong>投资项目</strong></h1>
<div class="wrap1" id="wrap" style="display:none;">
  <form action="#" id="form">
  <input type="hidden" value="${user.usableSum}" id="myAccount" />
  <input type="hidden" value="${user.id}" id="userid" />
  <ul class="list bg" id="list">
   <s:iterator value="pageBean.page" var="finance">
   <li><div class="list-item" onclick="window.parent.location.href = 'webapp-financeDetail.do?id=${finance.id}'"> <strong><s:property value="#finance.borrowTitle" default="---" /><i>（${finance.number}）</i></strong>
      <div class="list-progress">
        <div class="progress-round animate-route" data="<s:property value="#finance.schedules" default="0"/>"><b></b><i><s:property value="#finance.schedules" default="0"/>%</i><em>进度</em></div>
      </div>
      <div class="list-invest-num">已投<i><s:property value="#finance.investNum1" default="0" /></i>人</div>
      <dl>
        <dd><i><s:property value="#finance.annualRate" default="0" />%</i>年收益</dd>
        <dd><i><s:property value="#finance.borrowAmount" default="0" /></i>贷款总额</dd>
        <dd><i><s:property value="#finance.deadline" default="0" />个月</i>借款期限</dd>
        <dd class="list-invest">
          <s:if test="%{#finance.borrowStatus == 2}">
          <input type="tel" value="" placeholder="请输入投资金额"  validate="true" rule="{type:'number2'}" tips="{number2:'请输入有效的投资数额'}" class="input-text" id="invest_<s:property value="#finance.id" default=""/>" >
          </s:if>
          <s:if test="%{#finance.borrowStatus == 2}"><span for='invest_<s:property value="#finance.id" default=""/>' class="btn fr doTender animate-drop" data='{"paramMap.id":"<s:property value="#finance.id" default=""/>"}' financeid="${finance.userId}" investamount="${finance.investNum}">投标</span></s:if>
          <s:elseif test="%{#finance.borrowStatus == 3}"> <span class="btn fr doTender btn-red animate-drop">满标</span></s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 4}"> <span class="btn fr doTender btn-dark animate-drop">还款中</span></s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 5}"> <span class="btn fr doTender btn-dark animate-drop">已还完</span></s:elseif>
		</dd>
      </dl>
      </div> </li>
    </s:iterator>
  </ul>
  </form>
</div>
<c:if test="${param.noBottom ne true }">
	<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
</c:if>
<script>var noIScroller = true;</script>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/list.js" ></script>
<script>
$(function(){
	$("input[id^='invest_']").bind("click",function(event){
		event.stopPropagation();
	})
});

</script>
</body>
</html>