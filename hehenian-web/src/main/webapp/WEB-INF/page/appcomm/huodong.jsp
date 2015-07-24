<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<title>活动</title>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
<jsp:include page="/include/huodong.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<!--顶部底部浮动层-->

<jsp:include page="/include/app-comm.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap">
  <div style=" margin-top:40px;"><a><img src="/wap/images/huodong.jpg" class="responsive-image" alt="img"> </a> </div>
  <div id="container" style=" padding-top:0px;">

      <!--内容-->
  <div class="container no-bottom"  style="padding: 0px 10px;">
    <div class="section-title">
      <h3 style=" text-align:left">活动介绍</h3>
    </div>
    <p>为了庆祝e理财进入社区，为了感谢各会员的大力支持，现进行感恩大回馈，7月29日开始预约，7月30日进行投标，只要在30日当天进行投资的用户，都可以获得1%的现金奖！</p>
        <div class="section-title">
      <h3 style=" text-align:left">活动规则</h3>
    </div>
    <p> 1.	7月29日开始预约，在网站端和E理财都可以进行预约；      </p>
    <p>2.	7月30日0点-22点进行投标，所有用户，只要当天有投资，都送本人投资额的1%作为奖励；      </p>
    <p>3. 活动奖励均在活动结束后5个工作日内发送到用户的投资账户，用户可自行提现或继续投资。
      </p>
  </div>
  </div>
</div>
<div id="fixed-footer-wrap" style=" bottom:50px; text-align:center; background:#FFF; padding-top:5px; border:0px;"><!-- footer -->
<div >
<s:if test="#request.hasYuyue">
<s:if test="#request.nowDay!='20140730'">
 <a class="button-big button-orange"  style=" width:90%">已预约</a>
</s:if>
<s:else>
 <a class="button-big button-green" href="finance.do" style=" width:90%">立即投标</a>
</s:else>
</s:if>
<s:else>
 <a class="button-big button-green" href="?cmd=yy" style=" width:90%">立即预约</a>
</s:else>     
</div>
</div>
</body>
</html>
