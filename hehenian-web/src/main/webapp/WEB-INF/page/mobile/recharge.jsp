﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>充值</title>
<s:if test="#session.user.usrCustId==null||#session.user.usrCustId<=0">
<meta http-equiv="Refresh" content="0; url=/webapp/zhuce-huifu.do" /> 
</s:if>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><strong>充值</strong></h1>
<s:if test="#session.user.usrCustId!=null&&#session.user.usrCustId>0">
<div class="wrap" id="wrap" style="display:none;">
  <div class="tips recharge-info animate-drop" id="tipsInfo">
    <div class="tips-content p">
      <p>您正在使用手机银行进行充值，请确认您的的银行卡是否开通了网银;</p>
      <p>另，由于目前手机端支持的银行只有招行、建行、交行、浦发、兴业5家银行，若你的银行卡不在此列，请到http://www.colourlife.com彩生活网站充值。因各家银行限制单笔充值数额，如需大额充值，请登录您对应的网银进行修改。</p>
    </div>
    <div class="pd"> <a href="#" class="btn-a" id="tipsBtn">我知道了</a> </div>
  </div>
  <div class="recharge animate-waves" style="visibility:hidden" id="recharge">
  	<div class="tips recharge-info2 hr">
    <div class="tips-content p">
      <p>您正在使用手机银行进行充值，请确认您的的银行卡是否开通了网银;</p>
      <p>另，由于目前手机端支持的银行只有招行、建行、交行、浦发、兴业5家银行，若你的银行卡不在此列，请到http://www.colourlife.com彩生活网站充值。因各家银行限制单笔充值数额，如需大额充值，请登录您对应的网银进行修改。</p>
    </div>
  </div>
    <form action="/chinapnrPayment.do" class="form hr" id="form">
        <input type="hidden" id="int_usrCustId" name="usrCustId" value="${map.usrCustId}" />
        <input type="hidden" id="int_cardDcFlag" name="cardDcFlag" value="D" />
        <input type="hidden" id="int_gateBusiId" name="gateBusiId" value="B2C" />
    	<input type="hidden" value="" name="openBankId" id="int_openBankId" validate="true" rule="{empty:false}" tips="{empty:'请选择充值银行'}"/>
      <div class="recharge-content">
        <div class="input-radio recharge-type" id="GateBusiId">
        	<!--<a href="#" data='0' class="select"><input type="radio" checked name="GateBusiId" value="0" />快捷充值</a>--><a href="#" data="0" class="select" style="display:none;"><input type="radio" checked name="GateBusiId" value="1" />网上银行</a>
        </div>
        <!--预加载图片-->
        <img src="/wap/mobile/images/banks-list.png" style="display:none" />
        <div class="input-item" id="selectBank" for="int_openBankId" content0='<ul class="popup-list" id="bankList">
        					<li><a href="#" data="ICBC" class="bank-icon bank-icbc" title="工商银行"></a></li>
                            <li><a href="#" data="ABC" class="bank-icon bank-abc" title="农业银行"></a></li>
                            <li><a href="#" data="CMB" class="bank-icon bank-cmb" title="招商银行"></a></li>
                            <li><a href="#" data="CCB" class="bank-icon bank-ccb" title="建设银行"></a></li>
                            <li><a href="#" data="BOC" class="bank-icon bank-boc" title="中国银行"></a></li>
                            <li><a href="#" data="BOCOM" class="bank-icon bank-bocom" title="交通银行"></a></li>
                            <li><a href="#" data="CEB" class="bank-icon bank-ceb" title="光大银行"></a></li>
                            <li><a href="#" data="CIB" class="bank-icon bank-cib" title="兴业银行"></a></li>
                            <li><a href="#" data="CITIC" class="bank-icon bank-citic" title="中信银行"></a></li>
                            <li><a href="#" data="GDB" class="bank-icon bank-gdb" title="广发银行"></a></li>
                            <li><a href="#" data="HXB" class="bank-icon bank-hxb" title="华夏银行"></a></li>
                            <li><a href="#" data="PINGAN" class="bank-icon bank-pingan" title="平安银行"></a></li>
                            <li><a href="#" data="SPDB" class="bank-icon bank-spdb" title="浦发银行"></a></li></ul>' content1='<ul class="popup-list" id="bankList">
            	<li><a href="#" data="CMB" class="bank-icon bank-cmb" title="招商银行"></a></li>
                <li><a href="#" data="CCB" class="bank-icon bank-ccb" title="建设银行"></a></li>
                <li><a href="#" data="BOCOM" class="bank-icon bank-bocom" title="交通银行"></a></li>
                <li><a href="#" data="SPDB" class="bank-icon bank-spdb" title="浦发银行"></a></li>
                <li><a href="#" data="CIB" class="bank-icon bank-cib" title="兴业银行"></a></li>
        	</ul>'><input type="text" value="请选择充值银行" class="select-arrow" readonly /></div>
        <p>
          <label>请输入充值金额</label>
          <div class="input-item"><input type="tel" value="" name="money" id="money" validate="true" rule="{type:'number2',empty:false,size:'0.01'}" tips="{number2:'请输入正确的充值金额<br/>必须为大于0.01的数值',empty:'请输入充值金额',size:'最小金额不能低于0.01'}" /></div>
        </p>
        <div class="recharge-btn"><a href="#" class="btn-a" id="doRecharge">充值</a> </div>
      </div>
    </form>
    <div class="t t-b"><span>温馨提示</span></div>
    <div class="tips-content p">
      <p>1、请根据借款项目合理安排充值金额，因资金账户由用户自己管理，合和年在线未收取用户任何费用，为防止套现，所充资金必须经投标回款后才能提现；</p>
      <p>2、充值期间，请勿关闭浏览器，待充值成功并返回首页后，所充资金才能入账，如有疑问请联系客服；</p>
      <p>3、充值需开通银行卡网上支付功能，如有疑问请咨询开户行客服；</p>
      <p>4、支付限额由充值银行卡开户行决定。</p>
    </div>
  </div>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/recharge.js" ></script>
</s:if>
</body>
</html>