<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/docs/commons/jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>合和年接口服务文档首页</title>
<%@ include file="/docs/commons/html-head.jsp"%>
<style>
body{ background-color:#c4d6d2;}
.spanCursor{cursor: pointer;padding-bottom: 3px;border-bottom: 1px solid;}
</style>
<script type="text/javascript" src="/docs/static/js/iframetabs.js"></script>
<script type="text/javascript" src="/docs/static/js/jquery/jquery.bgiframe.js"></script>
<script type="text/javascript">
var resetFrameSize=function(){
	var windowWidth=$(window).width();
	windowWidth=windowWidth<960?960:windowWidth;
	$('#mainFrameTable').width(windowWidth);
	var windowHeight=$(window).height();
	windowHeight=windowHeight<555?555:windowHeight;
	if(window.hideMenu){
		$('#menuDiv').hide();
		$('#showMenuDiv').show().height((windowHeight-51)/2+85).css('padding-top',(windowHeight-51)/2-85);
		$('#menuTd').width(15);
		$('#tabsTd').width(windowWidth-45);
		$('#mainFrameTable').height(windowHeight);
		$('#iframeHolder').height(windowHeight-86-($.browser.msie?1:2));
	}else{
		$('#showMenuDiv').hide();
		$('#menuDiv').show();
		$('#menuTd').width(200);
		$('#tabsTd').width(windowWidth-230);
		$('#mainFrameTable').height(windowHeight);
		$('#menuTd iframe').height(windowHeight-76);
		$('#iframeHolder').height(windowHeight-86-($.browser.msie?1:2));
	}
};
var formatTime=function(time){
	var hours=parseInt(time/3600,10);
	var minutes=parseInt(time%3600/60,10);
	var seconds=time%60;
	var str='';
	if(hours>0){
		str+=hours+':';
	}
	if(minutes>0&&minutes<10){
		str+='0'+minutes+':';
	}else if(minutes>=10){
		str+=minutes+':';
	}
	str+=seconds<10?'0'+seconds:seconds;
	return str;
};
window.onresize=function(){
	if(resetFrameSize.timeout){
		clearTimeout(resetFrameSize.timeout);
	}
	resetFrameSize.timeout=setTimeout('resetFrameSize()',300);
};
$(function(){
	iframeTabs.newInstance('mainTabs','iframeHolder');
	resetFrameSize();
	$('#ctiButtonUl').bind('selectstart',function(){
		return false;
	});
	$('#logoutLink').click(function(){
		cti.logout();
		return true;
	});
	$('#hideMemuDiv').hover(function(){
		$(this).addClass('mouseOn');
	},function(){
		$(this).removeClass('mouseOn');
	}).click(function(){
		window.hideMenu=true;
		resetFrameSize();
	});
	$('#showMenuDiv').hover(function(){
		$(this).addClass('mouseOn');
	},function(){
		$(this).removeClass('mouseOn');
	}).click(function(){
		window.hideMenu=false;
		resetFrameSize();
	});
});
</script>
</head>
<body>
	<div id="mainFrameTable">
		<ul id="layout-header" class="clearfix">
			<li class="toptit"><h1>elove服务文档首页</h1></li>
		</ul>
		<div id="menuTd">
			<div id="menuDiv">
				<iframe src="menu.jsp" width="100%" height="95%" frameborder="0"></iframe>
				<div id="hideMemuDiv" title="快捷键Alt+M">隐藏菜单(Alt+M)</div>
			</div>
			<div id="showMenuDiv" style="display:none" title="快捷键Alt+M">显示菜单</div>
		</div>
		<div id="tabsTd">
			<div id="mainTabs" class="iframetabs-holder clearfix">
				<ul></ul>
				<a href="javascript:;" class="iframetabs-close" title="双击标签也能关闭">关闭</a> 
				<a href="javascript:;" class="iframetabs-reload">刷新</a>
			</div>
			<div id="iframeHolder"></div>
		</div>
	</div>
</body>
</html>