<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>问题管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />


</head>
<body>
<input type="hidden" id="questionnaireId" value="${questionnaireId }"/>
<div style="padding-left:10px;margin-top:5px;margin-bottom:5px;">
	使用状态:
	<select id="statusBox">
		<option value="-1">不限</option>
		<option value="1">启用</option>
		<option value="0">未启用</option>
	</select>
	创建时间：从
	<input id="createTimeBeg" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
	到
	<input id="createTimeEnd" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
	修改时间：从
	<input id="modifyTimeBeg" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
	到
	<input id="modifyTimeEnd" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
	<div id="searchbtn" style="display:inline;padding-top:3px;padding-bottom:5px;">
	</div>
</div>
<div id="tableGrid"></div>
<jsp:include page="../common_liger.jsp"></jsp:include>
<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/js/question/showQuestionnaireDetail.js"></script>
</body>
</html>