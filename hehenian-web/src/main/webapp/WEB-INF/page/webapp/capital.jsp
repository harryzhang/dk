<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
</head>
<style>
.bg {
	display: none;
	position: fixed;
	width: 100%;
	height: 100%;
	background: #000;
	z-index: 80;
	top: 0;
	left: 0;
	opacity: 0.9;
}
.tc_wenxts {
	display: none;
	width: 100%;
	position: fixed;
	top: 50%;
	margin-top: -150px;
	z-index: 90;
}
</style>
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
  <div id="container">
    <div class="content">
      <div class="container" >
        <div >
          <div class="container no-bottom" style=" float:right">
            <div class="one-half" style=" margin-right:10px; margin-top:45px;">
              <ul class="icon-list" >
                <li class="gear-list" style=" padding-left:20px;"><a href="automaticbid.do" style=" color:#FFF">设置自动投标</a></li>
                <li  style=" padding-left:20px; color:#FFF">您的状态：${automaticBidMap.bidStatus ==2?'开':'<font style=" color:#CCC">关</font>' }</li>
              </ul>
            </div>
          </div>
          <ul style=" padding:20px; background:#7C108F; color:#FFF ">
            <li style="list-style-type:none;">
              <%Calendar cal = Calendar.getInstance();  int hour = cal.get(Calendar.HOUR_OF_DAY);  if (hour >= 4 && hour < 9) {   out.print("早上好");  } else if (hour >= 9 && hour < 12) {   out.print("上午好");  } else if (hour >= 12 && hour < 14) {   out.print("中午好");  } else if (hour >= 14 && hour < 18) {   out.print("下午好");  } else {   out.print("晚上好");  }%>
              ${realName}，您的累计收益为：</li>
            <li style="list-style-type:none; margin-top:10px; font-size:36px; height:40px; line-height:40px;">${accmountStatisMap.earnSum}</li>
          </ul>
        </div>
      </div>
      <div class="container" style=" text-align:center;"> <a href="javascript:;" class="button-big button-green" onclick="cz();">充值</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="webapp-cash.do" class="button-big button-orange">提现</a>
        
        <input type="hidden" id="usrCustId" value="${usrCustId}"/>
        <input type="hidden" id="idNo" value="${idNo}"/>
        <input type="hidden" id="email" value="${session.user.email}"/>
      </div>
      <div class="decoration"></div>
      <div class="container no-bottom">
        <div class="facile-menu " style=" background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF"><strong>资产统计</strong></div>
        <div >
          <div class="facile">
            <div style=" border-right:1px dashed #d9d8d6; border-bottom:1px dashed #d9d8d6;padding:15px 0px;">
              <div style=" margin:15px 0px;"> <span class="text-highlight highlight-blue">昨日增值</span>
                <p style="margin-bottom:0px; font-size:16px;"><fmt:formatNumber pattern="#0.00" value="${dailyIncome}"></fmt:formatNumber>元</p>
              </div>
            </div>
          </div>
          <div class="facile">
            <div style=" border-bottom:1px dashed #d9d8d6;padding:15px 0px;">
              <div   style=" margin:15px 0px;" > <span class="text-highlight highlight-turqoise">累计收益</span>
                <p style="margin-bottom:0px;font-size:16px;"><fmt:formatNumber pattern="#0.00" value="${accmountStatisMap.earnSum}"></fmt:formatNumber> 元</p>
              </div>
            </div>
          </div>
          <div class="facile"  >
            <div style=" border-right:1px dashed #d9d8d6; border-bottom:1px dashed #d9d8d6;padding:15px 0px;">
              <div > <span class="text-highlight highlight-green">资产估值</span>
                <p style="margin-bottom:0px;font-size:16px;"><fmt:formatNumber pattern="#0.00" value="${accmountStatisMap.accountSum+totalIncome}"></fmt:formatNumber> 元 </p>
              </div>
            </div>
          </div>
          <div class="facile" >
            <div>
              <div style=" border-bottom:1px dashed #d9d8d6;padding:15px 0px;"> <span class="text-highlight highlight-magenta">待收本金</span>
                <p style="margin-bottom:0px;font-size:16px;"><fmt:formatNumber pattern="#0.00" value="${accmountStatisMap.forPayPrincipal}"></fmt:formatNumber> 元</p>
              </div>
            </div>
          </div>
          <div class="facile"  >
            <div style=" border-right:1px dashed #d9d8d6;padding:15px 0px;">
              <div > <span class="text-highlight highlight-dark">冻结金额</span>
                <p style="margin-bottom:0px;font-size:16px;"><fmt:formatNumber pattern="#0.00" value="${accmountStatisMap.freezeAmount}"></fmt:formatNumber> 元</p>
              </div>
            </div>
          </div>
          <div class="facile" >
            <div>
              <div style="padding:15px 0px;" > <span class="text-highlight highlight-red">可用余额</span>
                <p style="margin-bottom:0px;font-size:16px;"><fmt:formatNumber pattern="#0.00" value="${accmountStatisMap.usableAmount}"></fmt:formatNumber> 元</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      
      <s:if test="#request.investRecords!=null&&#request.investRecords.size()>0">
      <div class="container no-bottom" id="xxxx">
        <div class="facile-menu" style=" background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF"><strong>最近投资记录</strong></div>
        <table style=" margin-bottom:0px;">
          <tr class='even'>
            <td class="table-sub-title">借款标题</td>
            <td class="table-sub-title">交易日期</td>
            <td class="table-sub-title">投资金额</td>
            <td class="table-sub-title">年利率</td>
            <td class="table-sub-title">债权期限</td>
            <td class="table-sub-title">借款状态</td>
          </tr>
          <s:iterator value="#request.investRecords" var="bean" status="status">
            <tr  class="${status.count%2==0?'even':''}">
              <td>${bean.borrowTitle }</td>
              <td>${bean.investTime }</td>
              <td>${bean.investAmount }元</td>
              <td>${bean.annualRate }%</td>
              <td>${bean.deadline }个月</td>
              
               <s:if test="%{#bean.borrowStatus == 2}">
               <td>撮合中</td>
              </s:if>
              <s:if test="%{#bean.borrowStatus == 3}">
               <td>满标</td>
              </s:if>
              <s:if test="%{#bean.borrowStatus == 4}">
               <td>还款中</td>
              </s:if>
              <s:if test="%{#bean.borrowStatus == 5}">
              <td>已还完</td>
              </s:if>
              <s:if test="%{#bean.borrowStatus == 6}">
              <td>流标</td>
              </s:if>
              
            </tr>
          </s:iterator>
        </table>
      </div>
      
      <div style=" margin:15px auto; text-align:center;"><a href="webapp-invest-records.do" class="button-big button-magenta">查看更多投资记录</a></div>
   </s:if>
       <div class="container no-bottom">
        <div class="facile-menu"  style=" background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF"><strong>最近收支明细</strong></div>
        <table cellspacing='0' class="table">
          <tr class='even'>
            <td class="table-sub-title">类型</td>
            <td class="table-sub-title">收入</td>
            <td class="table-sub-title">支出</td>
            <td class="table-sub-title">时间</td>
          </tr>
          <s:if test="request.fundRecords==null || request.fundRecords.size==0">
            <tr align="center" class="gvItem" height="30">
              <td colspan="4">暂无数据</td>
            </tr>
          </s:if>
          <s:else>
            <s:iterator value="#request.fundRecords" var="bean" status="s">
              <tr >
                <td>${bean.fundMode=='汇付天下'?'充值成功':bean.fundMode}</td>
                <td>${bean.income }</td>
                <td>${bean.spending}</td>
                <td><s:date name="#bean.recordTime" format="yyyy-MM-dd" /></td>
              </tr>
            </s:iterator>
          </s:else>
        </table>
      </div> 
    </div>
  </div>
</div>
<jsp:include page="hftx.jsp"></jsp:include>
</body>
<script type="text/javascript">
//var usrCustId="${map.usrCustId}";
//if(usrCustId==''||(usrCustId-0)<=0){
	// $("#wenxts").css("display", "block");
	//$('.bg').fadeIn(200);
	//$('.tc_wenxts').fadeIn(400); 
//}
</script>
<script>
function cz(){
	var idNo=$("#idNo").val();
	var usrCustId=$("#usrCustId").val();
	if(usrCustId==""||(usrCustId-0)<=0){
		alert("请先注册汇付天下");
		window.location.href="/registerChinaPnr.do";
	}else{
		window.location.href="webapp-recharge.do";
	}
}
</script>

</html>
