<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
* {margin:0; padding:0;}
body {font-size:12px; color:#222; font-family:Verdana,Arial,Helvetica,sans-serif; background:#f0f0f0;}
.clearfix:after {content: "."; display: block; height: 0; clear: both; visibility: hidden;}
.clearfix {zoom:1;}
ul,li {list-style:none;}
img {border:0;}
.wrapper {width:600px; margin:0 auto; padding-bottom:50px;}
.ad {width:468px; margin:10px auto 0;}
.ad li {padding-top:5px;}

h1 {height:50px; line-height:50px; font-size:22px; font-weight:normal; font-family:"Microsoft YaHei",SimHei;}

.download {display:block; width:120px; height:34px; background:#333; line-height:34px; font-size:14px; font-weight:bold; text-align:center; color:#fff; border:1px solid #999; margin-top:10px;}
.fenxiang {height:16px; padding:20px 0 10px; margin-bottom:10px;}

.shuoming {margin-top:20px; border:1px solid #ccc; padding-bottom:10px;}
.shuoming dt {height:30px; line-height:30px; font-weight:bold; text-indent:10px;}
.shuoming dd {line-height:20px; padding:5px 20px;}

.tongji {display:none;}

/* jquery imgFocus */
.wrap {width:500px; height:320px; position:relative; margin:0 auto;}

.imgb {}
.imgb li {width:500px; height:320px; overflow:hidden; position:absolute; left:0; top:0;}
.imgb li.selected {z-index:1;}
.imgb li a {display:block; width:500px; height:320px;}

.imgs {width:500px; padding:5px 0; text-align:right; position:absolute; bottom:0; z-index:3;}
.imgs li {display:inline-block; *display:inline; cursor:pointer; padding-right:10px;}
.imgs li img {width:40px; height:30px; border:3px solid #000;}
.imgs li.selected img {border-color:#999;}
</style>

<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
$(function() {
	var len = $(".imgs li").length;
	var index = 0;
	var picTimer;

	$(".imgs li").click(function() {
		index = $(".imgs li").index(this);
		showPic(index);
	});

	$(".imgb").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPic(index);
			index++;
			if(index==len) {index=0;}
		},3000);
	}).trigger("mouseleave");

	function showPic(index) {
		$(".imgb li").removeClass("selected").eq(index).addClass("selected");
		$(".imgs li").removeClass("selected").eq(index).addClass("selected");
	}
});

</script>
</head>

<body>
<div class="wrapper">
<br />
<br />
<br />
	<div class="wrap">
		<ul class="imgb">
			<s:if test="%{#request.imgList !=null && #request.imgList.size()>0}">
            <s:iterator value="#request.imgList" id="bean" status="s">
              <li class="<s:if test='#s.count == 1'>selected</s:if>"><a href="${bean.imagePath}" target="_blank">
              <shove:img src="${bean.imagePath}" title="${bean.name}" defaulImg="images/default-img.jpg"></shove:img>
              </a></li>
            </s:iterator>        
            </s:if>
			<li><img src="images/authimg.jpg" style="width:500px;height:320px;"/></li>
		</ul>
		<ul class="imgs">
			<s:if test="%{#request.imgList !=null && #request.imgList.size()>0}">
            <s:iterator value="#request.imgList" id="bean" status="s">
              <li class="<s:if test='#s.count == 1'>selected</s:if>">
              <shove:img  src="${bean.imagePath}" title="${bean.name}" defaulImg="images/default-img.jpg"></shove:img>
              </li>
            </s:iterator>        
            </s:if>
		</ul>
	</div><!--wrap end-->
</div><!-- wrapper end -->
</body>
</html>