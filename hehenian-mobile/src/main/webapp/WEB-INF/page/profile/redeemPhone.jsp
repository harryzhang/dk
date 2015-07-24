<%@ page pageEncoding="UTF-8" language="java" %>
<%@page import="com.hhn.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>爱定宝-赎回</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=640,target-densitydpi=320,user-scalable=no">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/base.css">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/redeem.css">
        <%@ include file="../common/head.jsp" %>
    </head>
    <body>
    <form id="form1" name="form1" action="<c:url value="http://m.hehenian.com/profile/redeemVerifyCode.do"/>" method="post">
        <p class="title">爱定宝·<c:out value="${tradePeroid}"/>-M 年化收益<c:out value="${tradeRate*100}"/>%</p>
        <div class="banner">
        	<p class="bannerTitle">赎回金额(元)</p>
        	<p class="jine"><c:out value="${redeeAmount}"/></p>
        	<p class="shouxu">手续费<c:out value="${redeemFee}"/></p>
        	<img src="<c:url value="http://static.hehenian.com/m/img/redeemIcon-mobile.png"/>" class="redeemIcon" onclick="redeemAlert('待收收益: 投资金额 x 实际投资时间 （月）x 年化收益率/12月<br><br>赎回手续费: 投资金额×0.2%×剩余投资期限（月）<br><br>赎回总额: 投资金额 + 待收收益 - 手续费')">
        </div>
        <p class="title"><span style="color:#999999">实际投资</span>　<c:out value="${investDays}"/>月</p>
        <p class="title"><span style="color:#999999">待收收益</span>　<c:out value="${preIncome}"/>元</p>
        <p class="title" style="background:#fffbcc"><span style="color:#999999">到账时间</span>　<span style="color:#ff850e;font-size:21px;">预计<%=DateUtil.getThirtDate(3)%>到账</span></p>
        <input type="hidden" name="tradeId" value="<c:out value="${tradeId}"/>" />
        <a class="subBtn" id="subBtn" onclick="submit(this)">确认</a>
    </form>
      <%@ include file="../common/tail.jsp" %>
    </body>
<script src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
<script src="http://static.hehenian.com/m/js/common.js"></script>
<script type="text/javascript" src="http://static.hehenian.com/m/js/module.js"></script>
    <script type="text/javascript">
        function submit(ob){
            if($(ob).attr("class") == "subBtn disable"){
                return;
            }
            $(ob).addClass('disable');
            $("#form1").submit();
        }
    </script>
</html>