<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
<div class="ifbox1">
  <h2>确定投标 <img src="images/neiye2_06.jpg" width="4" height="7" /></h2>
  <div class="boxmain" style="padding-top:20px; padding-bottom:20px;">
    <div class="qrtb">
      <div class="tbtop"></div>
      <div class="tbmain">
        <div class="tbinfo">
          <div class="lbox">
            <div class="pic">
              <a href="userMeg.do?id=${investDetailMap.userId}" target="_blank">
              <shove:img src="${investDetailMap.personalHead}" defaulImg="images/default-img.jpg" width="80" height="79"></shove:img>
              </a>
              <br/>
              合和年在线信用等级：<img src="images/ico_13.jpg" title="${investDetailMap.creditrating}分" width="33" height="22" /></div>
              <p class="name">
              用户名：${investDetailMap.username}<br/>
籍  贯：${investDetailMap.nativePro}&nbsp;${investDetailMap.nativeCity}<br/>
居住地：${investDetailMap.address}

              </p>
          </div>
          <div class="rbox">
          <ul><li>借款标题：<strong>${investDetailMap.borrowTitle}</strong></li><li>借款金额：<strong>￥${investDetailMap.borrowAmount}</strong></li>
          <li>借款年利率：<strong>${investDetailMap.annualRate}%</strong></li>
<li>已经完成： <strong>${investDetailMap.schedules}%</strong></li>
<li>还需借款: <strong>￥${investDetailMap.investNum}</strong></li>
<li>借款期限: <strong>${investDetailMap.deadline}
<s:if test="%{investDetailMap.isDayThe ==1}">个月</s:if><s:else>天</s:else>
</strong></li>
<li>还款方式: <strong>
<s:if test="%{investDetailMap.paymentMode == 1}">
按月分期还款
</s:if>
<s:elseif test="%{investDetailMap.paymentMode ==2}">
按月付息,到期还本
</s:elseif>
<s:elseif test="%{investDetailMap.paymentMode ==3}">
秒还
</s:elseif>
</strong></li>
<li>交易类型: <strong>
<s:if test="%{investDetailMap.tradeType == 1}">
线上交易
</s:if>
<s:elseif test="%{investDetailMap.tradeType ==2}">
线下交易
</s:elseif>
</strong></li>
</ul>
</div>
</div>
 <div class="tbcz">
  <p class="money">
          您的帐户总额：<strong>${userMap.totalSum} 元 </strong> <a href="rechargeInit.do" class="czbtn"><img src="images/tubiao3.png" /></a><br/>
          您的可用余额：<strong>${userMap.usableSum} 元</strong></p>
          <p class="tips">请填写并确认下面投标金额<br/>
          <s:if test="#request.subscribes!=1">
	<span class="f999">最低投标金额：<s:property value="#request.minTenderedSum" default="0"/>元&nbsp;最多投标金额：<s:if test="%{investDetailMap.maxTenderedSum != -1}">${investDetailMap.maxTenderedSum}</s:if><s:else>无限制</s:else>
 	 <br/>当前年利率: ${investDetailMap.annualRate}%</span>
 	 </s:if>
 	 <s:else>
 	 	<span class="f999">最小认购金额：<s:property value="#request.investMap.smallestFlowUnit" default="0"/>元&nbsp;
 	 	当前年利率: ${investDetailMap.annualRate}%
 	 <br/>认购总份数：${investMap.circulationNumber }份,还有：${investMap.circulationNumber - investMap.hasCirculationNumber }份</span>
 	 </s:else>
 	 </p>
		  <div id="content" class="tbtab">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <s:if test="#request.subscribes!=1">
  <tr>
    <td>投标金额：</td>
    <td><input type="text" id="amount" name="paramMap.amount" class="inp140" maxlength="20" /> 元</td>
  </tr>
  </s:if>
  <s:if test="#session.hasPWD != -1">
    <tr>
    <td>投标密码：</td>
    <td><input type="password" id="investPWD" name="paramMap.investPWD" class="inp140" maxlength="20" /></td>
  </tr>
  </s:if>
  <s:if test="#request.subscribes!=1">
  <tr>
    <td colspan="2" class="tishi">注意：点击按钮表示您将确认投标金额并同意支付贷款</td>
    </tr>
  <tr>
    <td colspan="2" align="center" style="padding-top:15px; padding-bottom:15px;"><input id="btnsave" type="button" class="qdtbbtn" value="" /></td>
    </tr>
    </s:if>
</table>
<s:if test="#request.subscribes==1">
       <div style="background-color: #FDFAE9; height: 75px; width: 320px; margin-top:15px;border: solid 1px #F9DDD1;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tbody><tr>
    <td width="51%" height="48" align="right"><div>
    <input type="text" class="result_btn_detail_3" value="1" id="result" style="width: 100px;"/>
份&nbsp;&nbsp; </div></td>
    <td width="49%"><div style="width:98px;">	
<input type="image" src="images/gogo_rg.gif" value="" id="invest" class="bttn_2"/>	
	</div></td>
  </tr>
  <tr>  
    <td colspan="2" align="center">您的可投标金额为：<font id="account_use" style="color:#FF0000">￥${investDetailMap.investNum}</font>元，最多可认购：<font style="color:#FF0000" id="roam_use">${investMap.circulationNumber - investMap.hasCirculationNumber}份</font>
    <input name="subscribes" type="hidden"  id="subscribes"  value="${subscribes}"/>	
     <input name="smallestFlowUnit" type="hidden"  id="smallestFlowUnit"  value="${investMap.smallestFlowUnit}"/>
    </td>
    </tr>
</tbody></table>          
</div>
</s:if>
          </div>
        </div>
      </div>
      <div class="tbbot"></div>
    </div>
  </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<s:hidden name="id" value="%{investDetailMap.id}"></s:hidden>
<s:hidden name="annualRate" value="%{investDetailMap.annualRate}"></s:hidden>
<s:hidden name="deadline" value="%{investDetailMap.deadline}"></s:hidden>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-lc.js"></script>
<script type="text/javascript">
var flag = true;
$(function(){
     //样式选中
     $("#licai_hover").attr('class','nav_first');
	 $("#licai_hover div").removeClass('none');
	 $('.tabmain').find('li').click(function(){
	    $('.tabmain').find('li').removeClass('on');
	    $(this).addClass('on');
	    $('.lcmain_l').children('div').hide();
        $('.lcmain_l').children('div').eq($(this).index()).show();
	 });
	  $('#invest').click(function(){
	     var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.annualRate'] = $("#annualRate").val();
	     param["paramMap.deadline"] = $("#deadline").val();
	     param['paramMap.amount'] = 1;
	     param['paramMap.investPWD'] = $('#investPWD').val();
	     param['paramMap.result'] = $('#result').val();//认购份数
	     param['paramMap.smallestFlowUnit'] = $('#smallestFlowUnit').val();//每份认购金额
	     param['paramMap.subscribes'] = $('#subscribes').val();//认购模式是否开启
	     if(flag){
           flag = false;
	       $.shovePost('financeInvest.do',param,function(data){
		      var callBack = data.msg;
		      if(callBack == undefined || callBack == ''){
		         $('#content').html(data);
		         flag = true;
		      }else{
		         if(callBack == 1){
		          alert("操作成功!");
		          window.location.href= 'financeDetail.do?id='+id;
		          flag = false;
		          return false;
		         }
		         alert(callBack);
		         flag = true;
		      }
		    });
		 }
	 });
	 $('#btnsave').click(function(){
	     var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.annualRate'] = $("#annualRate").val();
	     param["paramMap.deadline"] = $("#deadline").val();
	     param['paramMap.amount'] = $('#amount').val();
	     param['paramMap.investPWD'] = $('#investPWD').val();
	     if(flag){
           flag = false;
	       $.shovePost('financeInvest.do',param,function(data){
		      var callBack = data.msg;
		      if(callBack == undefined || callBack == ''){
		         $('#content').html(data);
		         flag = true;
		      }else{
		         if(callBack == 1){
		          alert("操作成功!");
		          window.location.href= 'financeDetail.do?id='+id;
		          flag = false;
		          return false;
		         }
		         alert(callBack);
		         flag = true;
		      }
		    });
		 }
	 });
	
});		     
</script>
</body>
</html>