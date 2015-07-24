<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<s:iterator value="pageBean.page" var="finance">
   <li><div onclick="window.parent.location.href='webapp-financeDetail.do?id=${finance.id}'" class="list-item"> <strong><s:property value="#finance.borrowTitle" default="---" /><i> （${finance.number}）</i></strong>
      <div class="list-progress">
        <div class="progress-round" data="<s:property value="#finance.schedules" default="0"/>"><b></b><i><s:property value="#finance.schedules" default="0"/>%</i><em>进度</em></div>
      </div>
      <div class="list-invest-num">已投<i><s:property value="#finance.investNum1" default="0" /></i>人</div>
      <dl>
        <dd><i><s:property value="#finance.annualRate" default="0" />%</i>年收益</dd>
        <dd><i><s:property value="#finance.borrowAmount" default="0" /></i>贷款总额</dd>
        <dd><i><s:property value="#finance.deadline" default="0" />个月</i>借款期限</dd>
        <dd class="list-invest">
          <s:if test="%{#finance.borrowStatus == 2}">
          <input type="tel" value="" validate="true" rule="{type:'number2'}" tips="{number2:'请输入有效的投资数额'}" class="input-text" id="invest_<s:property value="#finance.id" default=""/>" >
          </s:if>
          <s:if test="%{#finance.borrowStatus == 2}">     <span for='invest_<s:property value="#finance.id" default=""/>' class="btn fr doTender" data='{"paramMap.id":"<s:property value="#finance.id" default=""/>"}' financeid="${finance.userId}" investamount="${finance.investNum}">投标</span></s:if>
          <s:elseif test="%{#finance.borrowStatus == 3}"> <span class="btn fr doTender btn-red">满标</span></s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 4}"> <span class="btn fr doTender btn-dark">还款中</span></s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 5}"> <span class="btn fr doTender btn-dark">已还完</span></s:elseif>
		</dd>
      </dl>
      </div> </li>
    </s:iterator>
