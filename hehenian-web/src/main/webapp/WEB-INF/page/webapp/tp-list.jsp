<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>投资列表</title>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<!--顶部底部浮动层-->
<jsp:include page="/include/wap-bottom.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap">
  <div id="container"  style=" padding-left:0px; padding-right:0px;">
    <div class="content">
      <div class="one-half-responsive last-column">
        <div class="container" style=" margin-bottom:0px;">
          <s:iterator value="pageBean.page" var="finance">
            <div class="toggle-2"><a href="#" class="deploy-toggle-2">借款金额：
              <s:property value="#finance.borrowAmount" default="0" />
              <s:if test="%{#finance.borrowStatus == 2}">
              
                <div class="deploy-toggle-2-t">可投</div>
              </s:if>
              <s:if test="%{#finance.borrowStatus == 3}">
                <div class="deploy-toggle-2-m">满标</div>
              </s:if>
              <s:if test="%{#finance.borrowStatus == 4}">
                <div class="deploy-toggle-2-f">还款中</div>
              </s:if>
              <s:if test="%{#finance.borrowStatus == 5}">
                <div class="deploy-toggle-2-f">已还完</div>
              </s:if>
              </a>
              <div class="toggle-content">
                <ul>
                  <li>借款金额：
                    <s:property value="#finance.borrowAmount" default="0" />
                  </li>
                  <li>剩余可投金额：
                    <s:property value="#finance.investNum" default="---" />
                  </li>
                  <li>年利率：
                    <s:property value="#finance.annualRate" default="0" />
                    %</li>
                  <li>借款期限：
                    <s:property value="#finance.deadline" default="0" />
                    个月</li>
                  <li>还款方式：
                    <s:if test="%{#finance.isDayThe ==2}">到期还本付息</s:if>
                    <s:elseif test="%{#finance.paymentMode == 1}">按月分期还款</s:elseif>
                    <s:elseif test="%{#finance.paymentMode == 2}">按月付息,到期还本</s:elseif>
                    <s:elseif test="%{#finance.paymentMode == 3}">秒还</s:elseif>
                    <s:elseif test="%{#finance.paymentMode == 4}">一次性还款</s:elseif>
                  </li>
                  <li>已投人数：
                    <s:property value="#finance.investNum1" default="0" />
                    人</li>
                  <div class="chart"> <strong>投资进度</strong> <em>
                    <s:property value="#finance.schedules" default="0"/>
                    %</em>
                    <div class="chart-background">
                      <div class="green-chart p90" style='width: <s:property value="#finance.schedules" default="0"/>%;'></div>
                    </div>
                  </div>
                  <div class="chart_buttom" style=" margin-top:0px; margin-bottom:8px;">
                    <s:if test="%{#finance.borrowStatus == 2}"> <a href="webapp-financeDetail.do?id=${finance.id}" class="button-big button-green">立即投资</a> </s:if>
                    <s:elseif test="%{#finance.borrowStatus == 3}"> <a href="webapp-financeDetail.do?id=${finance.id}" class="button-big button-red">满标</a> </s:elseif>
                    <s:elseif test="%{#finance.borrowStatus == 4}"> <a href="webapp-financeDetail.do?id=${finance.id}" class="button-big button-dark">还款中</a> </s:elseif>
                    <s:elseif test="%{#finance.borrowStatus == 5}"> <a href="webapp-financeDetail.do?id=${finance.id}" class="button-big button-dark">已还完</a> </s:elseif>
                  </div>
                </ul>
              </div>
            </div>
          </s:iterator>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>