<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>问卷管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />


</head>
<body>
<div id="toolbar">
</div>
<div class="tabelMod">
	<label>状态：</label>
	<select id="statusBox">
		<option value="-1">不限</option>
		<option value="1">启用</option>
		<option value="0">未启用</option>
	</select>
	<label>创建时间：从</label>
	<input id="createTimeBeg" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
	到
	<input id="createTimeEnd" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
	<label>修改时间：从</label>
	<input id="modifyTimeBeg" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
	到
	<input id="modifyTimeEnd" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
</div>
<div class="tabelMod">
	<label>问卷标题：</label>
	<input id="content" type="text" style="width:400px;"/> 
</div>
<div class="btnBox">
	<div id="searchbtn"></div>
</div>
<div id="tableGrid"></div>
<jsp:include page="../common_liger.jsp"></jsp:include>
<script type="text/javascript" src="${basePath}/js/ligerUI/js/plugins/ligerToolBar.js"></script>
<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/js/question/questionnaireListIndex.js"></script>
</body>
</html>