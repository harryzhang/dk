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
      <li class="tzlc" onclick="javascript:window.location.href='#'">花样年专区</li>
    </ul>
  </div>
  <!--导航图-->
  <div class="promoWD">
    <div id="main_promo">
      <div id="slides">
        <div class="slide"><a><img src="images/ad/hyn01.jpg" /></a></div>
        <div class="slide"><a><img src="images/ad/hyn02.jpg" ></a></div>
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
<div class=" bdlb_text">花样年会员独享12%年化收益率。</div>
  
  <!--标列表-->
<div style=" overflow:hidden">
  <div class="bdlb_all">
    <div class="bdlb_bb"><img src="images/v1/bbian.png" width="88" height="89"  alt=""/></div>
    <div class="bdlb">
      <ul>
        <li class="bt">我是标题我是标题</li>
        <li class="bh">编号：HHN1406160238</li>
        <li class="nr"><img src="images/new/1.gif"  style=" float:left" />借款金额：50000.00元</li>
        <li class="nr"><img src="images/new/2.gif" style=" float:left" />借款期限：12个月</li>
        <li class="nr"><img src="images/new/3.gif"  style=" float:left" />年利率：12%</li>
        <li class="nr"><img src="images/new/tt.gif"  style=" float:left" />还款方式： 按月分期还款</li>
        <li>
        	<div class="chart"> <strong>投标进度</strong> <em>90%</em>
            <div class="chart-background">
              <div class="green-chart p90"></div>
            </div>
          </div>
        </li>
        <li class="al">
          <div class="chart_buttom"><a href="tp-intro.html" class="button-big button-green">立即投标</a></div>
        </li>
      </ul>
    </div>
  </div>
  
    <div class="bdlb_all">
    <div class="bdlb_bb"><img src="images/v1/bbian.png" width="88" height="89"  alt=""/></div>
    <div class="bdlb">
      <ul>
        <li class="bt">我是标题我是标题</li>
        <li class="bh">编号：HHN1406160238</li>
        <li class="nr"><img src="images/new/1.gif"  style=" float:left" />借款金额：50000.00元</li>
        <li class="nr"><img src="images/new/2.gif" style=" float:left" />借款期限：12个月</li>
        <li class="nr"><img src="images/new/3.gif"  style=" float:left" />年利率：12%</li>
        <li class="nr"><img src="images/new/tt.gif"  style=" float:left" />还款方式： 按月分期还款</li>
         <li>
        	<div class="chart"> <strong>投标进度</strong> <em>90%</em>
            <div class="chart-background">
              <div class="green-chart p90"></div>
            </div>
          </div>
        </li>
        <li class="al">
           <div class="chart_buttom"><a href="tp-intro.html" class="button-big button-green">立即投标</a></div>
        </li>
      </ul>
    </div>
  </div>
  
    <div class="bdlb_all">
    <div class="bdlb_bb"><img src="images/v1/bbian.png" width="88" height="89"  alt=""/></div>
    <div class="bdlb" style=" margin-right:0px;">
      <ul>
        <li class="bt">
 
购买原材料
</li>
        <li class="bh">编号：HHN1406160238</li>
        <li class="nr"><img src="images/new/1.gif"  style=" float:left" />借款金额：50000.00元</li>
        <li class="nr"><img src="images/new/2.gif" style=" float:left" />借款期限：12个月</li>
        <li class="nr"><img src="images/new/3.gif"  style=" float:left" />年利率：12%</li>
        <li class="nr"><img src="images/new/tt.gif"  style=" float:left" />还款方式： 按月分期还款</li>
         <li>
        	<div class="chart"> <strong>投标进度</strong> <em>90%</em>
            <div class="chart-background">
              <div class="green-chart p90"></div>
            </div>
          </div>
        </li>
        <li class="al">
           <div class="chart_buttom"><a href="tp-intro.html" class="button-big button-green">立即投标</a></div>
        </li>
      </ul>
    </div>
  </div>
  <div class="bdlb_all">
    <div class="bdlb_bb"><img src="images/v1/bbian.png" width="88" height="89"  alt=""/></div>
    <div class="bdlb">
      <ul>
        <li class="bt">
 
经营周转
</li>
        <li class="bh">编号：HHN1406160238</li>
        <li class="nr"><img src="images/new/1.gif"  style=" float:left" />借款金额：50000.00元</li>
        <li class="nr"><img src="images/new/2.gif" style=" float:left" />借款期限：12个月</li>
        <li class="nr"><img src="images/new/3.gif"  style=" float:left" />年利率：12%</li>
        <li class="nr"><img src="images/new/tt.gif"  style=" float:left" />还款方式： 按月分期还款</li>
        <li>
        	<div class="chart"> <strong>投标进度</strong> <em>90%</em>
            <div class="chart-background">
              <div class="green-chart p90"></div>
            </div>
          </div>
        </li>
        <li class="al">
          <div class="chart_buttom"><a href="tp-intro.html" class="button-big button-green">立即投标</a></div>
        </li>
      </ul>
    </div>
  </div>
  
    <div class="bdlb_all">
    <div class="bdlb_bb"><img src="images/v1/bbian.png" width="88" height="89"  alt=""/></div>
    <div class="bdlb">
      <ul>
        <li class="bt">经营周转</li>
        <li class="bh">编号：HHN1406160238</li>
        <li class="nr"><img src="images/new/1.gif"  style=" float:left" />借款金额：50000.00元</li>
        <li class="nr"><img src="images/new/2.gif" style=" float:left" />借款期限：12个月</li>
        <li class="nr"><img src="images/new/3.gif"  style=" float:left" />年利率：12%</li>
        <li class="nr"><img src="images/new/tt.gif"  style=" float:left" />还款方式： 按月分期还款</li>
         <li>
        	<div class="chart"> <strong>投标进度</strong> <em>90%</em>
            <div class="chart-background">
              <div class="green-chart p90"></div>
            </div>
          </div>
        </li>
        <li class="al">
           <div class="chart_buttom"><a href="tp-intro.html" class="button-big button-green">立即投标</a></div>
        </li>
      </ul>
    </div>
  </div>
  
    <div class="bdlb_all">
    <div class="bdlb_bb"><img src="images/v1/bbian.png" width="88" height="89"  alt=""/></div>
    <div class="bdlb" style=" margin-right:0px;">
      <ul>
        <li class="bt">
 
进货
</li>
        <li class="bh">编号：HHN1406160238</li>
        <li class="nr"><img src="images/new/1.gif"  style=" float:left" />借款金额：50000.00元</li>
        <li class="nr"><img src="images/new/2.gif" style=" float:left" />借款期限：12个月</li>
        <li class="nr"><img src="images/new/3.gif"  style=" float:left" />年利率：12%</li>
        <li class="nr"><img src="images/new/tt.gif"  style=" float:left" />还款方式： 按月分期还款</li>
         <li>
        	<div class="chart"> <strong>投标进度</strong> <em>90%</em>
            <div class="chart-background">
              <div class="green-chart p90"></div>
            </div>
          </div>
        </li>
        <li class="al">
           <div class="chart_buttom"><a href="tp-intro.html" class="button-big button-green">立即投标</a></div>
        </li>
      </ul>
    </div>
  </div>

</div>
  
  <!--查看更多-->
  <div style=" text-align:center; margin-top:30px;"><input type="button" onclick="window.location.href='finance.do'" value="查看更多..." style=" width:238px; height:40px; border:0px; background:#f90; font-size:16px;color:#FFF; margin-left:0px;"/></div>
  
  
</div>
<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script>
		$(function() {
			hhn(5)
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
