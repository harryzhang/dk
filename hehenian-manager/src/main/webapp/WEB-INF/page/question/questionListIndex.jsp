<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>问题管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />

<style style="text/css">
.l-labeltext{
display:inline;
}
</style>
</head>
<body>
<div id="toolbar">
</div>
<div class="tabelMod">
	<label>分类:</label>
	<select id="classifyBox">
		 <option value="-1">全部</option>
		 <option value="1">注册</option>
		 <option value="2">QA</option>
		 <option value="3">初步沟通</option>
		 <option value="4">表明立场</option>
		 <option value="5">深入沟通</option>
		 <option value="6">自由沟通</option>
	</select>
	<select id="multiChoiceBox">
		<option value="-1">不限</option>
		<option value="1">单选</option>
		<option value="2">多选</option>
		<option value="3">开放性问题</option>
		<option value="4">程度题</option>
	</select>
	<label>状态:</label>
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
	<label>问题内容：</label>
	<input id="content" type="text" style="width:400px;"/>
</div>
<div class="btnBox">
	<div id="searchbtn"></div>
</div>
<div id="tableGrid"></div>
<jsp:include page="../common_liger.jsp"></jsp:include>
<script type="text/javascript" src="${basePath}/js/ligerUI/js/plugins/ligerToolBar.js"></script>
<script type="text/javascript" src="${basePath}/js/ligerUI/js/plugins/ligerButton.js"></script>
<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/js/question/questionListIndex.js"></script>
</body>
</html>