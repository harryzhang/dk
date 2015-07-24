<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="./commons/jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文档菜单</title>
<%@ include file="/docs/commons/html-head.jsp"%>
<link href="/docs/static/css/jquery.treeview.css" rel="stylesheet" type="text/css" />
<style>
html { overflow-x:hidden; }
body { background-color:#e6eeee; padding-bottom:10px; }
</style>
<script type="text/javascript" src="/docs/static/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/docs/static/js/jquery/jquery.treeview.js"></script>
<script type="text/javascript">
$(function(){
	$("#menu").treeview({
		control:'#menuControl',
		persist:'cookie',
		cookieId:'menuTree'
	});
});
</script>
</head>
<body>
	<div id="menuControl">
		<a><img src="/docs/static/images/jquery.treeview/minus.gif" />全部收起</a>
		<a><img src="/docs/static/images/jquery.treeview/plus.gif" />全部展开</a>
	</div>
	<ul id="menu" class="filetree">
		<li class="closed">
			<span class="folder">应用服务</span>
			<ul>
				<li><span class="file"><a href="/docs/summary/index.jsp" class="iframeTabsLink">服务概览</a></span></li>
				<li><span class="file"><a href="/docs/summary/convention.jsp" class="iframeTabsLink">编码约定</a></span></li>
			</ul>
		</li>
		<li class="closed">
			<span class="folder">接口文档</span>
			<ul>
				<li><span class="file"><a href="/docs/api/login.jsp" class="iframeTabsLink">接口实例</a></span></li>
				<li><span class="file"><a href="/docs/api/rcmdHistory.jsp" class="iframeTabsLink">推荐历史接口</a></span></li>
				<li><span class="file"><a href="/docs/api/userInfo.jsp" class="iframeTabsLink">用户信息接口</a></span></li>
				<li><span class="file"><a href="/docs/api/searchapi.jsp" class="iframeTabsLink">搜索接口</a></span></li>
				<li><span class="file"><a href="/docs/api/sendEloveMessage.jsp" class="iframeTabsLink">发送Elove邮件接口</a></span></li>
				<li><span class="file"><a href="/docs/api/createEloveOrder.jsp" class="iframeTabsLink">创建Elove订单</a></span></li>
				<li><span class="file"><a href="/docs/api/getEloveMemberIdByMobile.jsp" class="iframeTabsLink">根据手机号获取elove老站memberId</a></span></li>
				<li><span class="file"><a href="/docs/api/getEloveVIPType.jsp" class="iframeTabsLink">获取EloveVIP类型的接口</a></span></li>
				<li><span class="file"><a href="/docs/api/crmUserLogin.jsp" class="iframeTabsLink">CRM后台模拟登陆Elove接口</a></span></li>
				<li><span class="file"><a href="/docs/api/importUserCheck.jsp" class="iframeTabsLink">根据邀请码获得用户邀请信息</a></span></li>
				<li><span class="file"><a href="/docs/api/activeImportUser.jsp" class="iframeTabsLink">激活被邀请用户</a></span></li>
			</ul>
		</li>
		<li class="closed">
			<span class="folder">调试环境</span>
			<ul>
			</ul>
		</li>
		<li class="closed">
			<span class="folder">应用范例</span>
			<ul>
			</ul>
		</li>
	</ul>
</body>
</html>