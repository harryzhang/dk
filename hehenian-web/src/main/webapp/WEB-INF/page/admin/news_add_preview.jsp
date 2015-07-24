<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>试卷展示</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../script/setWidthHeight.js"></script>
		<script type="text/javascript">
			window.onload = function(){
				questionClass(720, 365);
			}
		</script>
	</head>
	<body>
	    <div class="lcmain">
    <div class="lcmain_l">
    <div class="lctab" style="padding:0 10px;">
    <div class="gginfo">
    <h2 align="center">${request.newsPreview.title}</h2>
    <p align="center" class="time">发布者：${request.newsPreview.userName}-   发布时间：${request.newsPreview.pubiishTime }   点击次数：${request.newsPreview.visits} </p>
    <p align="center"  class="zw">${request.newsPreview.content}</p>
    </div>
    </div>
    </div>
    </div>
	</body>
</html>
