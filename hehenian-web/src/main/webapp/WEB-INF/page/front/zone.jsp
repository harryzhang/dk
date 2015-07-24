<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
<jsp:include page="/include/top.jsp"></jsp:include>

<div class="center" style=" overflow:hidden; margin-bottom:20px;" >
  <div class="mjd_tzlc_all">
    <ul>
      <li class="tzlc" onclick="javascript:window.location.href='#'">福泰年专区</li>
    </ul>
  </div>
  <!--导航图-->
  <div class="promoWD">
    <div id="main_promo">
      <div id="slides">
        <div class="slide"><a><img src="images/zone${param.id}-1.jpg" /></a></div>
        <div class="slide"><a><img src="images/zone${param.id}-2.jpg" ></a></div>
      </div>
    </div>
    <div id="dots">
      <ul>
        <li class="menuItem"><a href="javascript:;"></a></li>
        <li class="menuItem"><a href="javascript:;"></a></li>
      </ul>
    </div>
  </div>
    <script>
$(document).ready(function(){
	var totWidth=0;
	var positions = new Array();
	$('#slides .slide').each(function(i){
		positions[i]= totWidth;
		totWidth += $(this).width();
		if(!$(this).width())
		{
			alert("Please, fill in width & height for all your images!");
			return false;
		}
	});
	$('#slides').width(totWidth);
	$('#dots ul li a').mouseover(function(e,keepScroll){
			$('li.menuItem').removeClass('act').addClass('inact');
			$(this).parent().addClass('act');
			
			var pos = $(this).parent().prevAll('.menuItem').length;
			
			$('#slides').stop().animate({marginLeft:-positions[pos]+'px'},450);
			e.preventDefault();
			if(!keepScroll) clearInterval(itvl);
	});
	$('#dots ul li.menuItem:first').addClass('act').siblings().addClass('inact');
	var current=1;
	function autoAdvance()
	{
		if(current==-1) return false;
		$('#dots ul li a').eq(current%$('#dots ul li a').length).trigger('mouseover',[true]);
		current++;
	}
	var changeEvery = 7;
	var itvl = setInterval(function(){autoAdvance()},changeEvery*8000);//设置自动播放时间，越大越慢
});
</script> 

<!--广告-->
<!-- <div class=" bdlb_text">花样会会员尊享优惠利率。</div> -->
  
  <!--标列表-->
<div style=" overflow:hidden">
<s:if test="pageBean.page!=null || pageBean.page.size>0">
<s:iterator value="pageBean.page" var="finance" status="status">
  <div class="bdlb_all">
    <div class="bdlb_bb"><%-- <img src="${finance.borrowStatus == 2?'images/v1/hyhbbian.png':'images/v1/over.png' }" width="88" height="89"  alt=""/> --%></div>
    <div class="bdlb" style="${status.count%3==0?' margin-right:0px;':''}">
      <ul>
        <li class="bt">${finance.borrowTitle}</li>
        <li class="bh">编号：${finance.number }</li>
        <li class="nr"><img src="images/new/1.gif"  style=" float:left" />借款金额：<s:property value="#finance.borrowAmount" default="0" />元</li>
        <li class="nr"><img src="images/new/2.gif" style=" float:left" />借款期限：<s:property value="#finance.deadline" default="0" />个月</li>
        <li class="nr"><img src="images/new/3.gif"  style=" float:left" />年利率：<s:property value="#finance.annualRate" default="0" />%</li>
        <li class="nr"><img src="images/new/tt.gif"  style=" float:left" />还款方式：  <s:if test="%{#finance.isDayThe ==2}">到期还本付息</s:if>
            <s:elseif test="%{#finance.paymentMode == 1}">按月分期还款</s:elseif>
            <s:elseif test="%{#finance.paymentMode == 2}">按月付息,到期还本</s:elseif>
            <s:elseif test="%{#finance.paymentMode == 3}">秒还</s:elseif>
            <s:elseif test="%{#finance.paymentMode == 4}">一次性还款</s:elseif></li>
        <li>
        	<div class="chart"> <strong>投标进度</strong> <em><s:property value="#finance.schedules" default="0"/>%</em>
            <div class="chart-background">
              <div class="green-chart" style="width: <s:property value="#finance.schedules" default="0"/>%;"></div>
            </div>
          </div>
        </li>
        <li class="al">
          <div class="chart_buttom">
          <!-- <a href="tp-intro.html" class="button-big button-green">立即投标</a> -->
          <s:if test="%{#finance.borrowStatus == 1}">
            <input type="button" value="初审中" style="background: #ccc;cursor:default;" />
          </s:if>
          <s:elseif test="%{#finance.borrowStatus == 2}">
			<a href="financeDetail.do?id=${finance.id}" class="button-big button-green">立即投标</a>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 3}">
            <a href="financeDetail.do?id=${finance.id}" class="button-big button-blue">满标</a>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 4}">
            <a href="financeDetail.do?id=${finance.id}" class="button-big button-dark">还款中</a>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 5}">
            <a href="financeDetail.do?id=${finance.id}" class="button-big button-green">已还完</a>
          </s:elseif>
          </div>
        </li>
      </ul>
    </div>
  </div>
 </s:iterator>
 </s:if>
<s:else>
	<li style="text-align: center;padding-top: 20px;padding-bottom: 20px;">没有数据</li>
</s:else> 

</div>
  
  <!--查看更多-->
 <!--  <div style=" text-align:center; margin-top:30px;"><input type="button" onclick="window.location.href='finance.do'" value="查看更多..." style=" width:238px; height:40px; border:0px; background:#f90; font-size:16px;color:#FFF; margin-left:0px;"/></div>
   -->
  <s:if test="pageBean.page!=null || pageBean.page.size>0">
  <style>
  .mjd_fy_all{
  margin-top: 20px;
  width: 780px;
  }
  </style>
    <div class="mjd_fy_all" >
      <shove:page url="hyn-index.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
      </shove:page>
      <div class="cle"></div>
    </div>
  </s:if>
</div>
<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script>
		$(function() {
//			hhn(5)
            hhnNew("topIndex-zone");
			var param = {};
			param["paramMap.flag"] = 1;
			$.post("queryMessages.do", param, function(data) {
				$("#message").html(data);
			});
		});
		function message(flag) {
			$("li").removeClass();
			$("#li"+flag).addClass("gywm_menu_nr_ong");
			$("#message").html("")

			var param = {};
			param["paramMap.flag"] = flag;
			$.post("queryMessages.do", param, function(data) {
				$("#message").html(data);
			});
		}
		function reggg(){
			if(${session.user!=null}){
				alert("你已是登录用户");
			}else{
				window.location.href="/account/reg.do";
			}
		}
	</script>
</body>
</html>
