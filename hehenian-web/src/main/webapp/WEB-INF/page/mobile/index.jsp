<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<c:url value='/wap/styles/dqlc/index.css?t=1'/>" />
<title>首页</title>
</head>
<body class="index">
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<div class="wrap" id="wrap" style="display:none;">
  <div class="slider hr" id="indexSlider">
	<div class="slider-content">
        <%--<div class="slider-item"><a href="/webapp/huodong.do"><img src="/wap/mobile/images/ad/song100-app.jpg" /></a></div>--%>
       <%-- <div class="slider-item"><a><img src="/images/llsjapp.jpg" /></a></div>--%>
	  <%--<div class="slider-item"><a href="#"><img src="/images/jfwhapp.jpg?t=0318" /></a></div>--%>
	  	<div class="slider-item"><a href="/webapp/webapp-motherDay.do"><img src="http://static.hehenian.com/m/img/banner/app-motherday-banner.jpg" /></a></div>
        <div class="slider-item"><a href="/webapp/webapp-money.do"><img src="/images/zdtbb-webapp.jpg" /></a></div>
        <div class="slider-item"><a href="/webapp/qa.do"><img src="/wap/images/general-nature/3.jpg" /></a></div>
    </div>
  </div>
 <%-- <div class="quick-nav hrw2">
    <input type="hidden" id="usrCustId" value="${usrCustId}"/>
    <input type="hidden" id="idNo" value="${idNo}"/>
    <input type="hidden" id="email" value="${user.email}"/>
    <ul >


            <% if(session.getAttribute("user")!=null) {%>
            <li class="animate-scale"><a href="/webapp/webapp-money.do"><i class="nav1-icon"></i><span>我的帐户</span></a></li>
            <li class="animate-scale"><a href="/webapp/webapp-recharge.do" id="recharge"><i class="nav2-icon"></i><span>充值</span></a></li>
            <li class="animate-scale"><a href="webapp-cash.do"><i class="nav3-icon"></i><span>提现</span></a></li>
            <li class="animate-scale"><a href="/webapp/webapp-invest-records.do"><i class="nav4-icon"></i><span>已投项目</span></a></li>
            <%}else{%>

            <li class="animate-scale"><a href="/webapp/webapp-login.do"><i class="nav5-icon"></i><span>登录</span></a></li>
            <li class="animate-scale"><a href="/webapp/webapp-register.do"><i class="nav6-icon"></i><span>注册</span></a></li>
            <li class="animate-scale"><a href="/webapp/webapp-login.do"><i class="nav1-icon"></i><span>我的帐户</span></a></li>
            <li class="animate-scale"><a href="/webapp/webapp-login.do"><i class="nav2-icon"></i><span>充值</span></a></li>

        <%}%>
    </ul>
  </div>--%>
  <%--<div class="invest-info hrw2">累计投资<strong>${s1_}</strong>元，总投资人数<span>${countUser}</span>人</div>
  <div class="income-compare hr">
    <ul class="income-item" id="income">
      <li class="income-icon1"><i></i> <span>高收益</span> <em>年利率12%</em></li>
      <li class="income-icon2"><i></i> <span>低门槛</span> <em>起投100元</em></li>
      <li class="income-icon3"><i></i> <span>低风险</span> <em>100%逾期回购</em></li>
    </ul>
  </div>--%>

    <%--<div class="products" onclick="goToBuy(12)">
        <img src="/wap/styles/dqlc/right.png" style="position: absolute;top:30%;right:4.7%">
        <img src="/wap/styles/dqlc/HOT.png" style="position: absolute;top:0px;right:0px;width:12.5%">
        <img src="/wap/styles/dqlc/12.png" style="position: absolute;top:18%;left:3.1%;width:9.4%">
        <p class="productsName">爱定宝·12-M</p>
        <p class="top1 left1 font1">年化收益</p>
        <p class="top1 left2 font1">比同期银行定存高</p>
        <p class="top1 left3 font1">最低投资</p>
        <p class="top2 left1 font2" style="color:#ff9900">10%</p>
        <p class="top2 left2 font2">3.3倍</p>
        <p class="top2 left3 font2">1元起投</p>
    </div>
    <div class="products" onclick="goToBuy(6)">
        <img src="/wap/styles/dqlc/right.png" style="position: absolute;top:30%;right:4.7%">
        <img src="/wap/styles/dqlc/6.png" style="position: absolute;top:18%;left:3.1%;width:9.4%">
        <p class="productsName">爱定宝·6-M</p>
        <p class="top1 left1 font1">年化收益</p>
        <p class="top1 left2 font1">比同期银行定存高</p>
        <p class="top1 left3 font1">最低投资</p>
        <p class="top2 left1 font2" style="color:#ff9900">8%</p>
        <p class="top2 left2 font2">2.8倍</p>
        <p class="top2 left3 font2">1元起投</p>
    </div>
    <div class="products" onclick="goToBuy(3)">
        <img src="/wap/styles/dqlc/right.png" style="position: absolute;top:30%;right:4.7%">
        <img src="/wap/styles/dqlc/3.png" style="position: absolute;top:18%;left:3.1%;width:9.4%">
        <p class="productsName">爱定宝·3-M</p>
        <p class="top1 left1 font1">年化收益</p>
        <p class="top1 left2 font1">比同期银行定存高</p>
        <p class="top1 left3 font1">最低投资</p>
        <p class="top2 left1 font2" style="color:#ff9900">5%</p>
        <p class="top2 left2 font2">2.2倍</p>
        <p class="top2 left3 font2">1元起投</p>
    </div>--%>


    <div id="dqlc">

    </div>


  <h2 class="t">热门项目</h2>
  <form action="#" id="form">
  <input type="hidden" value="${user.usableSum}" id="myAccount" />
  <input type="hidden" value="${user.id}" id="userid" />
  <div id="redirect" style="display: none;"></div>
  <ul class="list bg" id="list">
   <s:iterator value="#request.mapList" var="finance">
   <li><div href="webapp-financeDetail.do?id=${finance.id}" class="list-item"> <strong><s:property value="#finance.borrowTitle" default="---" /><i>（${finance.number}）</i></strong>
      <div class="list-progress">
        <div class="progress-round animate-route" data="<s:property value="#finance.schedules" default="0"/>"><b></b><i><s:property value="#finance.schedules" default="0"/>%</i><em>进度</em></div>
      </div>
      <div class="list-invest-num">已投<i><s:property value="#finance.investNum1" default="0" /></i>人</div>
      <dl>
        <dd><i><s:property value="#finance.annualRate" default="0" />%</i>年收益</dd>
        <dd><i><s:property value="#finance.borrowAmount" default="0" /></i>贷款总额</dd>
        <dd><i><s:property value="#finance.deadline" default="0" />个月</i>借款期限</dd>
        <dd class="list-invest">
          <s:if test="%{#finance.borrowStatus == 2}">
          <input type="tel" placeholder="请输入投资金额" value="" validate="true" rule="{type:'number2'}" tips="{number2:'请输入有效的投资数额'}" class="input-text" id="invest_<s:property value='#finance.id' default=''/>" >
          </s:if>
          <s:if test="%{#finance.borrowStatus == 2}">     <span for='invest_<s:property value="#finance.id" default=""/>' class="btn fr doTender animate-drop" data='{"paramMap.id":"<s:property value="#finance.id" default=""/>"}' financeid="${finance.publisher}"  investamount="${finance.investNum}">投标</span></s:if>
          <s:elseif test="%{#finance.borrowStatus == 3}"> <span class="btn fr doTender btn-red animate-drop">满标</span></s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 4}"> <span class="btn fr doTender btn-dark animate-drop">还款中</span></s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 5}"> <span class="btn fr doTender btn-dark animate-drop">已还完</span></s:elseif>
		</dd>
      </dl>
      </div> </li>
    </s:iterator>
  </ul>
  </form>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script>$.get("/updateUserUsableSum.do?_="+new Date().getTime());</script>
<script type="text/javascript">
    $(function(){
        $("#dqlc").load("/hhn_web/termFinancePhone.do?_="+new Date().getTime());
    });
    /*function goToBuy(rateId,month,rate){
        window.location.href = "<c:url value="/view/mobile/conductFinancial.jsp"/>?rateId="+rateId+"&month="+month+"&rate="+rate;
    }*/
</script>
<%--<article id="nian">
<style>
.dialog{
			position: absolute;
			width: 100%;
			height: 100%;
			top: 0;
			overflow: hidden;
			z-index: 1000;
			background:url(./flow-pop.png) no-repeat;
			background-size: 100%;
			display:none;
		}
		.flow-num {
			position: absolute;
			top:160px;
			text-align: center;
			font-size:30px;
			font-weight: bold;
			font-family: "microsoft yahei";
			width:100%;
			color:#ab0900;
		}
		.flow-tip {
			position: absolute;
			top:185px;
			text-align: center;
			font-size:15px;
			font-family: "microsoft yahei";
			width:100%;
			color:#ff4546;
			font-weight: bold;
		}
		.flow-close {
			display: block;
			position: absolute;
			height: 25px;
			width: 25px;
			top: 95px;
			left: 240px;
		}
</style>
<div class="dialog">
		<span class="flow-close"></span>
		<div class="flow-num">
			<span>100</span>M
		</div>
		<div class="flow-tip">
			<p>
				恭喜你，成为合和年会员<br />
				获得<span>100</span>M流量红包
			</p>
		</div>
</div>
<script>
	$(function(){	
		$(".flow-close").click(function {
			$(".dialog").css({
				display: 'none'
			});
		})
	})
</script>
</article>--%>
</body>
</html>