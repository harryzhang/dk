<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    <jsp:include page="/include/cf-head.jsp"></jsp:include>
    <style>
* { margin:0; padding:0; }
.el_container { font-size:16px; width:974px; }
/*投资减免物业费*/
.cf-header { background:url(/color/images/cf_invest_line.gif) left bottom repeat-x; height:122px; overflow:hidden; margin-bottom:20px; }
.cf-header-content { width:974px; margin:0 auto; }
.cf-logo { position:relative; top:20px; }
.slider {width: 100%;overflow: hidden;margin: 0 auto;-webkit-transform: translateZ(0); position:relative; }
.slider-content {width: 100%;display:none;}
.slider-content:after {content: '';display: block;clear: both;height: 0;}
.slider-item {float: left;width: 100%;}
.slider-item a { display:block; overflow:hidden; }
.slider-item img { width:100%; }
.slider-pointer { position:absolute; left:0; bottom:5px; width:100%; text-align:center; }
.slider-pointer span { display:block; background:#fff; width:7px; height:7px; border-radius:100%; border-radius:#000 solid 1px; margin-left:5px; display:-moz-inline-stack; display:inline-block;zoom:1;*display:inline; }
.slider-pointer span.current { background:#f68147; }
.slider-btns { display:none; }
.slider-btn { display:block; position:absolute; top:50%; margin-top:-20px; height:40px; width:25px; z-index:99999; }
.slider-btn span { width:11px; height:19px; display:block;position:absolute; top:50%; left:50%; margin-left:-6.5px; margin-top:-9.5px; background:url(/color/images/icons_cf.png) no-repeat; background-size:18.75em 5.5em; }
.slider-prev { left:10px; }
.slider-prev span { background-position:-0.6875em 0; }
.slider-next { right:10px; }
.slider-next span {  background-position:-3.875em 0; }


.cf-invest-banner { height:96px; }
.cf-invest-hr { font-size:0.75em; color:#999; border:#eee solid 1px; margin:10px 0; padding:10px }
.cf-invest-hr p { padding:3px 0; color:#777 }
.cf-invest-hr p span { padding-left:10px; }
.cf-invest-info { height:40px; padding:15px 15px 10px 15px; border:#eee solid 1px; }
.cf-invest-info ul { width:100%; }
.cf-invest-info ul li { width:25%; height:40px; float:left; text-align:center; }
.cf-invest-info ul li span { font-size:0.75em; display:block; color:#9fa0a0; padding-bottom:5px; }
.cf-invest-info ul li strong { font-size:0.875em; display:block; color:#fd6b00; font-weight:normal; }

.cf-invest-nav { padding-top:10px; height:280px; }
.cf-invest-nav ul { border-top:#eee solid 1px; }
.cf-invest-nav ul li { width:486px; border-bottom:#eee solid 1px; height:117px; float:left; background:url(/color/images/cf_step.png) left top no-repeat; position:relative; border-right:#eee solid 1px;  }
.cf-invest-nav ul li a { display:block; height:102px; width:100%; }
.cf-invest-nav ul li .icon { display:block; height:42px; width:42px; margin:0 auto; overflow:hidden; background:url(/color/images/icons_cf.png) left top no-repeat; background-size:18.75em 6.25em; margin-top:14px; }
.cf-invest-nav ul li strong { color:#898989; display:block; height:30px; line-height:30px; text-align:center; padding-top:10px;  }
.cf-invest-nav ul li i { height:18px; width:18px; overflow:hidden;background:url(/color/images/icons_cf.png) -10.4375em 0 no-repeat; background-size:18.75em 6.25em; display:block; position:absolute; right:1px; bottom:1px; }
.cf-invest-nav ul li i.checked { background-position:-7.15625em 0; }
.cf-invest-nav ul li.one { background-position:0 0; }
.cf-invest-nav ul li.one .icon { background-position:-0.6875em -3.28125em; }
.cf-invest-nav ul li.two { background-position:0 -250px; }
.cf-invest-nav ul li.two .icon { background-position:-3.90625em -3.28125em; }
.cf-invest-nav ul li.three { background-position:0 -119px; }
.cf-invest-nav ul li.three .icon { background-position:-7.15625em -3.28125em; }
.cf-invest-nav ul li.four { background-position:0 -369px; }
.cf-invest-nav ul li.four .icon { background-position:-10.4375em -3.28125em; }
</style>
    </head>
    <body>
    <div class="cf-header">
    	<div class="cf-header-content">
        	<a href="/" class="cf-logo"><img src="/color/images/cf-logo.gif" /></a>
        </div>
    </div>
    <div class="s_sur_ix main el_container" style=" overflow:hidden;margin:0 auto;padding:0; "> 
  <div class="slider" id="cfSlider"> <a href="#" class="slider-btn slider-prev"><span></span></a> <a href="#" class="slider-btn slider-next"><span></span></a>
    <div class="slider-content">
      <div class="slider-item"><img src="/color/images/cf-01.jpg" /></div>
      <div class="slider-item"><img src="/color/images/cf-02.jpg" /></div>
      <div class="slider-item"><img src="/color/images/cf-03.jpg" /></div>
    </div>
  </div>
  <div class="cf-invest-hr">
    <p>我的物业:<span>碧水龙庭</span></p>
    <p>缴费地址:<span>深圳市福田区保税区内市花路与紫荆路的交汇处花样年福年广场B5栋3楼</span></p>
  </div>
  <div class="cf-invest-info">
    <ul>
      <li><span>每月减免物业费</span><strong>10.00 元</strong></li>
      <li><span>减免时长</span><strong>2014-11 至 2015-10</strong></li>
      <li><span>保本保息收益</span><strong>12000.00 元</strong></li>
      <li><span>您需要投资金额</span><strong>12000.00 元</strong></li>
    </ul>
  </div>
  <div class="cf-invest-nav">
    <ul>
      <li class="one"> <a href="/registerChinaPnr.do"> <span class="icon"></span> <strong>注册汇付天下</strong> </a> <i class="checked"></i> </li>
      <li class="two"> <a href="/webapp/webapp-recharge.do"> <span class="icon"></span> <strong>充值</strong> </a> <i></i> </li>
      <li class="three"> <a href="/webapp/automaticbid.do"> <span class="icon"></span> <strong>设定自动投标</strong> </a> <i></i> </li>
      <li class="four"> <a href="#"> <span class="icon"></span> <strong>设定委托划款协议</strong> </a> <i></i> </li>
    </ul>
  </div>
      
    </div>
    <script type="text/javascript" src="/wap/mobile/scripts/common/slider.js"></script>
    <script>
	//slider
	var slider = $("#cfSlider");
	if(slider.length>0){
		var slider = $('.slider-content'); 
		var sliderItem = slider.find(".slider-item");
		var sliderNum = sliderItem.length;
		sliderItem.css({width:(100/sliderNum)+"%"});
		slider.css({width:sliderNum*100+"%"}).show();
		var flipsnap = Flipsnap('.slider-content',{
			auto:true,
			pointBar:false
		});
		var sliderPrev = $(".slider-prev"),sliderNext = $(".slider-next");
		sliderPrev.on("click",function(){
			flipsnap.toPrev();
			return false;
		});
		sliderNext.on("click",function(){
			flipsnap.toNext();
			return false;
		});
	}
	</script>
</body>
</html>