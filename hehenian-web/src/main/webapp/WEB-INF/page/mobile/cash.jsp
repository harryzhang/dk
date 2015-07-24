<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<s:if test="#session.user.usrCustId!=null&&#session.user.usrCustId>0">
<h1 class="t title"><strong>提现</strong></h1>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <div class="account-balance pd hr"> <span>您可提取金额为（元）</span> <strong class="animate-drop"><s:property value="#request.usableSum" default="---"></s:property></strong> </div>
  <s:if test="#request.banks!=null && #request.banks.size>0">
      <form action="/addWithdraw.do" class="form hr" id="form">
      <input type="hidden" id="int_bankId" name="bankId" />
      <input type="hidden" id="int_openAcctId" name="openAcctId" />
      <input type="hidden" id="int_dealpwd" name="dealpwd" />
      <input type="hidden" id="int_cellPhone" value="${bindingPhone}" name="cellPhone" />
      <input type="hidden" id="int_code" name="code" />
      <input type="hidden" id="int_type" name="type" />
      <ul class="bank-list" id="bankList">
        <s:iterator value="#request.banks" var="bean" status="sta">
            <!-- 并且银行卡的状态为绑定状态 -->
            <s:if test="#bean.cardStatus==1">
              <%--<li><a href="#">
                <input type="radio" value="${bean.id}-${bean.cardNo}" name="bankSelect" />
                <label for="bank">${bean.bankName}<span> 银行账号：<s:property value="#request.bean.cardNo.substring(0,4)+' '+'****' +' ' + #request.bean.cardNo.substring(#request.bean.cardNo.length() - 4)" /></span></label>
              </a></li>--%>
              </s:if>
        </s:iterator>
      </ul>
      <div class="bank-add"><a href="#" onClick="javascript:location.href='/bindCardInit.do';return false;" title="新增提现银行卡"><i></i><span>新增提现银行卡</span></a></div>
      <div class="pd draw-form">
        <p><label>请输入提现金额</label></p>
        <div class="input-item"><input type="tel" value="" name="money" id="int_money" validate="true" rule="{type:'number2',empty:false,size:'0.01-<s:property value="#request.usableSum" default="---"></s:property>'}" tips="{number2:'请输入正确的提现金额<br/>必须为大于0的数字',empty:'提现金额不能为空',size:'提现金额不得低于0.01元<br/>且提现金额不能大于可用余额(0.88)'}" /></div>
        <p><a href="#" class="btn-a" id="doDraw">提现</a></p>
      </div>
      </form>
  </s:if>
  <s:else>
  <div class="bank-tip">提示：您需要绑定银行卡才能提现</div>
  <div class="bank-add hr"><a href="#" onClick="javascript:location.href='/bindCardInit.do';return false;" title="新增提现银行卡"><i></i><span>新增提现银行卡</span></a></div>
  </s:else>
  <div class="t t-b"><span>温馨提示</span></div>
  <div class="tips-content p">
    <p>1、请先将本人的银行卡与“汇付天下”托管账户进行绑定，否则无法提现；</p>
    <p>2、确保本人的银行卡开户人真实姓名与实名认证的真实姓名一致；</p>
    <p>3、提交提现请求后，1至2个工作日(国家节假日除外)之内将钱转入本人绑定的银行卡账户；</p>
    <p>4、为防止套现，所充资金必须经投标回款后才能取现；</p>
    <p>5、若提现过程中遇到任何问题，请及时联系我们 4008-303-737</p>
  </div>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/draw.js?t=1" ></script>
</s:if>
</body>
</html>