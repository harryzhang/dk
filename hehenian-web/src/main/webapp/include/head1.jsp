<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sp2p.constants.IConstants"%>
<title><%=IConstants.SEO_TITLE%></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="<%=IConstants.SEO_KEYWORDS%>" />
<meta http-equiv="description" content="<%=IConstants.SEO_DESCRIPTION%>" />
<%=IConstants.SEO_OTHERTAGS%>
<link href="css/hhncss.css" rel="stylesheet" type="text/css" />
<link href="css/Site2.css" rel="stylesheet" type="text/css" />
<link href="css/lytebox.css" rel="stylesheet" type="text/css" />
<!--IE6透明判断-->
<!--[if IE 6]>
<script src="../css/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
DD_belatedPNG.fix('.flash_bar,#tit_fc1,#tit_fc2,#tit_fc3,#tit_fc4,#flashLine,#right_tel,#right_qq,#right_tip,.login_all,.wytz_center_onez,.wytz_center_one,img');
</script>
<![endif]-->

<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/lytebox.js"></script>


<style type="text/css">
ul,li {
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
@font-face{font-family:"Merriweather";src:url("font/Merriweather.ttf");}


#rightButton{ position:fixed; _position:absolute; top:208px; right:0; z-index:999999; display:block;}
#right_ul{ position:relative;}
#right_qq{  background:url(images/7_03.png) no-repeat; width:38px; height:41px; }
#right_tel{ background:url(images/7_05.png) no-repeat; width:38px; height:41px; }
#right_tip{  background:url(images/flag_right.png) no-repeat; width:252px; height:91px; position:absolute; right:40px; top:-10px; display:none; z-index:999999; }
.flagShow_p1{ float:left; margin-left:15px; _margin-left:5px; font-size:18px; line-height:91px;}
.flagShow_p2{ float:left; margin-left:10px; _margin-left:5px; font-size:18px;  color:#FA7C00;}
.flagShow_p2 a{ display:block; margin: 20px 0 5px 12px; line-height:0;}
.flagShow_p2 span{ margin: 0 0 0 14px; }
.flag_qq{ display:none;}
#backToTop{z-index:999999; display:none;}
a.backToTop_a{  background:url(images/7_08.png) no-repeat; width:38px; height:44px; display:block; }
a.backToTop_a:active{  background:url(images/7_11.png) no-repeat; }
.line91{ line-height:91px; }


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