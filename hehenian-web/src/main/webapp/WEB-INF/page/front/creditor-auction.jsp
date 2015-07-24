<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="script/nav-zq.js"></script>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain" >
  <div class="ifbox1">
  <h2>确定竞拍 <img src="images/neiye2_06.jpg" width="4" height="7" /></h2>
  <div class="boxmain" style="padding-top:20px; padding-bottom:20px;">
    <div class="qrtbs">
      <div class="tbtops"></div>
      <div class="tbmains">
       
        <div class="tbczz">
          <p class="money">
         您的帐户总额：<strong>${paramMap.totalSum} 元 </strong> <a href="rechargeInit.do" class="czbtn"><img src="images/tubiao3.png" /></a><br/>
         您的可用余额：<strong>${paramMap.usableSum}元</strong><br/><br/>
         债权总额：<strong>${paramMap.debtSum}元</strong><br/>
         拍卖底价：<strong>${paramMap.auctionBasePrice} 元 </strong>
          <br/>
         已出最高价：<s:if test="#request.paramMap.auctionMode==1"><strong>
         <s:if test="#request.paramMap.auctionHighPrice !=''">${paramMap.auctionHighPrice}</s:if><s:else>0</s:else>
          元
          </strong>
          </s:if>
          <s:else>暗拍价格不显示</s:else>
          </p>
          <p class="tips">请填写并确认下面竞拍金额</p>
		  <div id="div_editform" class="tbtab">
				<s:include value="creditor-auction-add.jsp"></s:include>
				
          </div>
        </div>
       <div class="tbbots"></div>
      </div >
    </div>
  </div>
  </div>
  
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>


</body>
</html>
