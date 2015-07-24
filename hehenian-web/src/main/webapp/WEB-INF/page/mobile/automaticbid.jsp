<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>自动投标设置</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><span>自动投标设置</span></h1>
<div class="wrap animate-waves" id="wrap" style="display:none;">
   <div class="pd hr account-balance"> <span>您可提取金额为（元）</span> <strong>${automaticBidMap.usableSum}</strong> </div>
   <div class="tender pd clearfix">
      <s:if test="%{automaticBidMap.bidStatus ==2}">
      <div class="tender-close"><a href="#" class="btn-a" id="doClose">已开启，点击关闭</a> </div>
      </s:if>
      <s:else>
      <form action="/automaticBidModify.do" id="form">
        <input type="hidden" id="bidAmt" name="paramMap.bidAmt" maxlength="20" value="100000" />
        <label class="text">帐户保留金额</label>
        <div class="input-item">
          <input type="tel" name="paramMap.remandAmount" value="${automaticBidMap.remandAmount==''?0:automaticBidMap.remandAmount}" id="remandAmount" validate="true" rule="{type:'number2',empty:false,size:'0-999999999'}" tips="{number2:'请输入正确的保留金额',size:'输入的保留金额超出范围',empty:'请输入保留金额'}" />
        </div>
        <label class="text">利率范围(0-24%)</label>
        <div class="tender-income clearfix">
          <div class="half fl">
            <div class="input-item">
              <input type="tel" value="${automaticBidMap.rateStart == '' ? 0:automaticBidMap.rateStart}" name="paramMap.rateStart" id="rateStart" validate="true" rule="{type:'number2',empty:false,size:'0.00-24.00'}" tips="{number2:'请输入正确的利率范围',size:'年利率范围为0-24%',empty:'请输入利率范围'}" />
            </div>
          </div>
          <div class="mid fl">-</div>
          <div class="half fl">
            <div class="input-item">
              <input type="tel" value="${automaticBidMap.rateEnd== '' ? 24:automaticBidMap.rateEnd}" name="paramMap.rateEnd" id="rateEnd" validate="true" rule="{type:'number2',empty:false,size:'0.00-24.00'}" tips="{number2:'请输入正确的利率范围',size:'年利率范围为0-24%',empty:'请输入利率范围'}" />
            </div>
          </div>
        </div>
        <label class="text">贷款期限</label>
        <div class="tender-income clearfix">
          <div class="half fl">
          	 <input type="hidden" name="paramMap.deadlineStart" id="monthStart" value="${automaticBidMap.deadlineStart==''?'1':automaticBidMap.deadlineStart}" />
          	 <div class="input-item select-month" for="monthStart" id="selectMonthStart" content='<ul class="popup-list" id="bankList">
            	<li><a href="#" data="1">1个月</a></li>
                <li><a href="#" data="6">6个月</a></li>
                <li><a href="#" data="12">12个月</a></li>
                <li><a href="#" data="18">18个月</a></li>
                <li><a href="#" data="24">24个月</a></li>
                <li><a href="#" data="36">36个月</a></li>
        	</ul>'><span class="input select-arrow">${automaticBidMap.deadlineStart==''?'1':automaticBidMap.deadlineStart}个月</span></div>
          </div>
          <div class="mid fl">-</div>
          <div class="half fl">
          	<input type="hidden" name="paramMap.deadlineEnd" id="monthEnd" value="${automaticBidMap.deadlineEnd==''?'36':automaticBidMap.deadlineEnd}" />
          	<div class="input-item select-month" for="monthEnd" id="selectMonthEnd" content='<ul class="popup-list" id="bankList">
                <li><a href="#" data="6">6个月</a></li>
                <li><a href="#" data="12">12个月</a></li>
                <li><a href="#" data="18">18个月</a></li>
                <li><a href="#" data="24">24个月</a></li>
                <li><a href="#" data="36">36个月</a></li>
        	</ul>'><span class="input select-arrow">${automaticBidMap.deadlineEnd==''?'36':automaticBidMap.deadlineEnd}个月</span></div>
          </div>
        </div>
        <div class="tender-btn">
            <a href="#" class="btn-a" id="doSet">已关闭，点击开启</a>
         </div>
      </form>
      </s:else>
    </div>
    <div id="redirect" style="display: none;"></div>
   <div class="tender-tips">
      <div class="t t-b"><span>自动投资工具说明</span></div>
      <div class="tips-content p">
        <p>一、自动投资设置完成后即生效；</p>
        <p>二、自动投资工具投资规则如下：</p>
        <p>1、投资序列按照开启自动投资的时间先后进行排序;</p>
        <p>2、帐户可用余额&lt;100时，不执行自动投资；</p>
        <p>3、每次（最大）投资金额是默认值，不需要设置；</p>
      </div>
    </div>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/tender.js?t=2" ></script>
</body>
</html>
