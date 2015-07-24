﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>个人中心</title>
<s:if test="#session.user.usrCustId==null||#session.user.usrCustId<=0">
<meta http-equiv="Refresh" content="0; url=/webapp/zhuce-huifu.do" /> 
</s:if>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<s:if test="#session.user.usrCustId!=null&&#session.user.usrCustId>0">
<h1 class="t title"><span>个人中心</span></h1>
<div class="wrap" id="wrap" style="display:none;">
  <ul class="items hr">
    <li class="nb account-user"><a href="/webapp/webapp-userinfo.do"><i class="account-icon"></i><span><%Calendar cal = Calendar.getInstance();  int hour = cal.get(Calendar.HOUR_OF_DAY);  if (hour >= 4 && hour < 9) {   out.print("早上好");  } else if (hour >= 9 && hour < 12) {   out.print("上午好");  } else if (hour >= 12 && hour < 14) {   out.print("中午好");  } else if (hour >= 14 && hour < 18) {   out.print("下午好");  } else {   out.print("晚上好");  }%>，${realName}</span></a></li>
  </ul>
  <div id="accountList">
  <ul class="account-list account-list2 hr">
  	 <li title="资产估值" content='<p>投资人当前的资产总价值</p>' class="account-zcgz"><span><i></i>资产估值(元)</span><strong class="animateNum" data-animateTarget="<fmt:formatNumber pattern="#0.00" value="${userIncomeDo.assetValue}"></fmt:formatNumber>"></strong></li>
    <li title="昨日增值" content='<p>昨日的新增资产</p>' class="account-zrzz"><span class="account-icon1"><i></i>昨日增值(元)</span><strong class=""><fmt:formatNumber pattern="#0.00" value="${userIncomeDo.dailyIncome}"></fmt:formatNumber></strong></li>   
  </ul>
  <ul class="account-list">
    <li title="待收本金" content='<p>投资人已投资尚未收回的本金</p>'><span>待收本金(元)</span><strong class="animate-drop"><fmt:formatNumber pattern="#0.00" value="${userIncomeDo.recivedPrincipal - userIncomeDo.hasPrincipal}"></fmt:formatNumber></strong></li>
    <li title="冻结金额" content='<p>投资中或提现中冻结的资金</p>'><span>冻结金额(元)</span><strong class="animate-drop"><fmt:formatNumber pattern="#0.00" value="${userIncomeDo.freezeAmount}"></fmt:formatNumber></strong></li>
    <li title="累计收益" content='<p>投资人获得的投资收益及其他收益</p>'><span>累计收益(元)</span><strong class="animate-drop"><fmt:formatNumber pattern="#0.00" value="${userIncomeDo.earnSum}"></fmt:formatNumber></strong></li>
    <li title="可用余额" content='当前帐户可用余额'><span>可用余额(元)</span><strong class="animate-drop"><fmt:formatNumber pattern="#0.00" value="${userIncomeDo.withdrawalAmount}"></fmt:formatNumber></strong></li>
  </ul>
  </div>
  <input type="hidden" id="usrCustId" value="${usrCustId}"/>
  <input type="hidden" id="idNo" value="${idNo}"/>
  <input type="hidden" id="email" value="${session.user.email}"/>
  <ul class="account-btn hr pd">
    <li><a href="webapp-cash.do" class="btn-c" id="draw">提现</a></li>
    <li><a href="webapp-recharge.do" class="btn-c" id="recharge">充值</a></li>
  </ul>
  <ul class="items">
    <li><a href="/webapp/webapp-userinfo.do"><i class="account0-icon"></i><span>个人信息</span></a></li>
    <%--<li><a href="/hhn_web/view/mobile/personCenter.jsp"><i class="account0-icon"></i><span>爱定宝账户</span></a></li>--%>
    <li><a href="/webapp/automaticbid.do"><i class="account2-icon"></i><span>自动投标设置<%--<span>状态:${automaticBidMap.bidStatus ==2?'<b>开</b>':'关'}</span></span>--%></a></li>
    <li><a href="/webapp/webapp-invest-records.do"><i class="account3-icon"></i><span>投资明细</span></a></li>
    <li><a href="/webapp/webapp-invest-zr.do"><i class="account3-icon"></i><span>债权转让</span></a></li>
    <li><a href="/webapp/capital-records.do"><i class="account4-icon"></i><span>资金流水</span></a></li>
    <s:if test="#session.appstyle!='cf'">
    <li><a href="/webapp/webapp-logout.do"><i class="account5-icon"></i><span>登出</span></a></li>
    </s:if>
  </ul>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/account.js" ></script>
<script> $.get("/updateUserUsableSum.do?_="+new Date().getTime());</script>
</s:if>
</body>
</html>