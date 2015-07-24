<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>投资详情</title>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<div class="wrap" id="wrap" style="display:none;">
  <h2 class="t-a invest-title"><em><s:property value="#request.borrowDetailMap.moneyPurposes" default="---" /></em><span>编号:${borrowDetailMap.number}</span></h2>
  <div class="tabs">
    <div class="tabs-top">
      <ul class="tabs-nav">
        <li class="on">项目信息</li>
        <li>借款人信息</li>
      </ul>
      <span class="tabs-line"></span> </div>
    <div class="tabs-content">
      <div class="tabs-box">
        <ul>
          <li><span>借款金额</span><strong><s:property value="#request.borrowDetailMap.borrowAmount" default="---" /></strong></li>
          <li><span>已投金额</span><em>${borrowDetailMap.hasInvestAmount}</em></li>
          <li><span>剩余可投金额</span><em>${borrowDetailMap.investAmount}</em></li>
          <li><span>年利率</span><strong><s:property value="#request.borrowDetailMap.annualRate" default="---" />%</strong></li>
          <li><span>借款期限</span><em><s:property value="#request.borrowDetailMap.deadline" default="---" />个月</em></li>
          <li><span>还款方式</span><em>
          	<s:if test="%{#request.borrowDetailMap.isDayThe ==2}">到期还本付息</s:if>
            <s:elseif test="%{#request.borrowDetailMap.paymentMode == 1}">按月分期还款</s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif></em></li>
          <li><span>已投资人数</span><strong><s:property value="#request.borrowDetailMap.investNum" default="0" />人</strong></li>
        </ul>
        <div class="detail-progress"> <span>项目进度</span>
          <div class="detail-progress-bar">
            <div class="progress-meter progress-nostripes"><i style="width: <s:if test="%{#request.borrowDetailMap.schedules > 0 && #request.borrowDetailMap.schedules < 3}">3</s:if><s:else><s:property value="#request.borrowDetailMap.schedules" default="0" /></s:else>%"></i></div>
          </div>
          <em><s:property value="#request.borrowDetailMap.schedules" default="0" />%</em> </div>
      </div>
      <div class="tabs-box">
        <ul>
          <li><span>用户</span><em><s:property value="#request.borrowUserMap.username.substring(0,2)+'***'" /></em></li>
          <li><span>性别</span><em>${borrowUserMap.sex}</em></li>
          <li><span>年龄</span><em>${borrowUserMap.age}</em></li>
          <li><span>学历</span><em>${borrowUserMap.highestEdu}</em></li>
          <li><span>收入</span><em>${borrowUserMap.monthlyIncome}</em></li>
          <li><span>职位</span><em>${borrowUserMap.job}</em></li>
          <li><span>公司名称</span><em>${request.orgName}</em></li>
          <li><span>公司头行业</span><em>${borrowUserMap.companyLine}</em></li>
          <li><span>现工作单位时间</span><em>${borrowUserMap.workYear}</em></li>
        </ul>
         <dl>
        	<dt class="t-a">审核信息</dt>
            <dd><i></i>身份认证</dd>
            <dd><i></i>工作认证</dd>
            <dd><i></i>居住地认证</dd>
            <dd><i></i>信用报告</dd>
            <dd><i></i>收入认证</dd>
            <dd><i></i>房产证</dd>
            <dd><i></i>房屋租赁合同</dd>
            <dd><i></i>水电单据</dd>
            <dd><i></i>工作证明</dd>
            <dd><i></i>银行流水</dd>
            <dd><i></i>信用卡账单</dd>
            <dd><i></i>车产证</dd>
            <dd><i></i>社保</dd>
            <dd><i></i>营业执照</dd>
            <dd><i></i>租赁合同</dd>
            <dd><i></i>其它资产证明</dd>
            <dd><i></i>征信报告</dd>
          </dl>
      </div>
    </div>
  </div>
</div>
<div class="invest-fixed" id="investFixed">
<div id="redirect" style="display: none;"></div>
<s:if test="#session.user.usrCustId==null||#session.user.usrCustId<=0">
	<span>您还没有注册汇付天下，暂时不能投标</span>
    <a href="/webapp/zhuce-huifu.do" class="btn-c">注册汇付天下</a>
</s:if>
<s:else>
<s:if test="%{#request.borrowDetailMap.borrowStatus == 2}">
<span>输入投资金额(您的可投金额为${userMap.usableSum}元)</span>
<input type="hidden" value="${userMap.usableSum}" id="myAccount" />
<input type="hidden" value="${borrowUserMap.username}" id="userid" />
<form id="form" action="/investBorrow.do" method="post">
  <input type="hidden" id="int_id" name="paramMap.id" value="${borrowDetailMap.id }" />
  <ul id="list">
    <li><div class="input-item input-nb"><input type="tel" value="" class="fl" name="paramMap.amount" id="int_amount" validate="true" rule="{type:'number2'}" tips="{number2:'请输入有效的投资数额'}" /></div></li>
    <li><a href="#" for="int_amount" class="btn-b fl doTender" financeid="${user.username}" investamount="${borrowDetailMap.investAmount}" id="BtnInvest">我要投资</a></li>
  </ul>
</form>
</s:if>
<s:elseif test="%{#request.borrowDetailMap.borrowStatus == 3}"><div class="invest-status">满    标</div></s:elseif>
<s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}"><div class="invest-status">还 款 中</div></s:elseif>
<s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}"><div class="invest-status">已 还 完</div></s:elseif>
<s:elseif test="%{#request.borrowDetailMap.borrowStatus == 6}"><div class="invest-status">流    标</div></s:elseif>
<s:elseif test="%{#request.borrowDetailMap.borrowStatus == 8}"><div class="invest-status">待 发 布</div></s:elseif>
</s:else>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/detail.js" ></script>
</body>
</html>