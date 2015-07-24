<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!--底部快捷导航 开始-->
<!--底部footer 结束-->
<script type="text/javascript" src="script/jqueryV172.js"></script>
<script type="text/javascript" src="script/xl.js"></script>
<script type="text/javascript">
function addCookie()
{
 if (document.all){
       window.external.addFavorite('<%=application.getAttribute("basePath")%>','合和年在线');
    }
    else if (window.sidebar) {
       window.sidebar.addPanel('合和年信贷', '<%=application.getAttribute("basePath")%>', "");
    }else{
       alert('请手动设为首页');
    }
}

function setHomepage(){
    if (document.all){
        document.body.style.behavior='url(#default#homepage)';
        document.body.setHomePage('<%=application.getAttribute("basePath")%>');
    }else if (window.sidebar){
        if(window.netscape){
         try{  
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
         }  
         catch (e)  
         {  
            alert( "该操作被浏览器拒绝，如果想启用该功能，请在地址栏内输入 about:config,然后将项 signed.applets.codebase_principal_support 值该为true" );  
         }
    }else{
        alert('请手动添加收藏');
    }
    var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
    prefs.setCharPref('browser.startup.homepage','<%=application.getAttribute("basePath")%>');
		}
	}
	$(function() {
		$(window).scroll(function() {
			if ($(window).scrollTop() >= 109) {
				$(".nav-zdh").css("position", "fixed")
			} else {
				$(".nav-zdh").css("position", "relative")
			}
		})
	})
</script>
<style type="text/css">
ul, li {
	margin: 0;
	padding: 0
}
#scrollDiv {
	height: 182px;
	overflow: hidden
}
.load {
	width: 18px;
	height: 18px;
	margin: 10px;
}
@font-face {
	font-family: "Merriweather";
	src: url("font/Merriweather.ttf");
}
#rightButton {
	position: fixed;
	_position: absolute;
	top: 208px;
	right: 0;
	z-index: 999999;
	display: block;
}
#right_ul {
	position: relative;
}
#right_qq {
	background: url(images/7_03.png) no-repeat;
	width: 38px;
	height: 41px;
}
#right_tel {
	background: url(images/7_05.png) no-repeat;
	width: 38px;
	height: 41px;
}
#right_tip {
	background: url(images/flag_right.png) no-repeat;
	width: 252px;
	height: 91px;
	position: absolute;
	right: 40px;
	top: -10px;
	display: none;
	z-index: 999999;
}
.flagShow_p1 {
	float: left;
	margin-left: 15px;
	_margin-left: 5px;
	font-size: 18px;
	line-height: 91px;
}
.flagShow_p2 {
	float: left;
	margin-left: 10px;
	_margin-left: 5px;
	font-size: 18px;
	color: #FA7C00;
}
.flagShow_p2 a {
	display: block;
	margin: 20px 0 5px 12px;
	line-height: 0;
}
.flagShow_p2 span {
	margin: 0 0 0 14px;
}
.flag_qq {
	display: none;
}
#backToTop {
	z-index: 999999;
	display: none;
}
a.backToTop_a {
	background: url(images/7_08.png) no-repeat;
	width: 38px;
	height: 44px;
	display: block;
}
a.backToTop_a:active {
	background: url(images/7_11.png) no-repeat;
}
.line91 {
	line-height: 91px;
}
</style>
<script type="text/javascript">
$(document).ready(function(e) {
	$("#rightButton").css("right", "0px");
	
    var button_toggle = true;
	$(".right_ico").live("mouseover", function(){
		var tip_top;
		var show= $(this).attr('show');
		var hide= $(this).attr('hide');
		tip_top = show == 'tel' ?  15 :  -20;
		button_toggle = false;
		$("#right_tip").css("top" , tip_top).show().find(".flag_"+show).show();
		$(".flag_"+hide).hide();
		
	}).live("mouseout", function(){
		button_toggle = true;
		hideRightTip();
	});
	
	
	$("#right_tip").live("mouseover", function(){
		button_toggle = false;
		$(this).show();
	}).live("mouseout", function(){
		button_toggle = true;
		hideRightTip();
	});
	
	function hideRightTip(){
		setTimeout(function(){		
			if( button_toggle ) $("#right_tip").hide();
		}, 500);
	}
	
	$("#backToTop").live("click", function(){
		var _this = $(this);
		$('html,body').animate({ scrollTop: 0 }, 500 ,function(){
			_this.hide();
		});
	});

	$(window).scroll(function(){
		var htmlTop = $(document).scrollTop();
		if( htmlTop > 0){
			$("#backToTop").fadeIn();	
		}else{
			$("#backToTop").fadeOut();
		}
	});
});
</script>
