<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
.mutilClass{ width:350px;}
.mutilClass label{ display:inline-block; width:90px; text-align:right;}
</style>
</head>
<body>
<div id="toolbar">
</div>
<div class="tabelMod">
	<label>投放端口:</label>
	<select id="classifyBox">
		 <option value="-2" selected="selected">全部</option>
		 <option value="-1">pc端+手机端</option>
		 <option value="0">PC端</option>
		 <option value="1">手机端</option>
	</select>
	<label>启用状态:</label>
	<select id="classifyBox">
		 <option value="-1">全部</option>
		 <option value="1"  selected="selected">启用</option>
		 <option value="0">停用</option>
	</select>
	
	<label>问题类型:</label>
	<select id="classifyBox">
		 <option value="-1" selected="selected">请选择</option>
		 <option value="1">选择题</option>
		 <option value="2">文字输入题</option>
		 <option value="3">程度题[符合-不符合]</option>
		 <option value="4">程度题[重要-不重要]</option>
		 <option value="5">是否题[是-否]</option>
		 <option value="6">区间题</option>
	</select>
	
	<label>问题属性:</label>
	<select id="classifyBox">
		 <option value="-1" selected="selected">请选择</option>
		 <option value="-1">男女通用</option>
		 <option value="0">男用户</option>
		 <option value="1">女用户</option>
	</select>
	
	<label>是否保密:</label>
	<select id="classifyBox">
		 <option value="-1" selected="selected">请选择</option>
		 <option value="-1">是</option>
		 <option value="0">否</option>
	</select>
</div>

<div class="tabelMod">
	<div class="mutilClass" style="display:inline-block">
	<label>问题模块:</label>
		<select id="classifyBox" class="modulesParent">
			<c:choose>
				<c:when test="${empty modulesView || empty modulesView.modules }">
					<option value="-1">请选择</option>
				</c:when>
				<c:otherwise>
					<option value="-1">请选择</option>
					<c:forEach items="${modulesView.modules}" var="modules">
						<option value="${modules.questionnaireId }">${modules.content}</option>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</select>
	</div>
	<div style="display:inline-block">
		<label>创建时间：从</label>
		<input id="createTimeBeg" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
		到
		<input id="createTimeEnd" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
		
		<label>修改时间：从</label>
		<input id="modifyTimeBeg" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
		到
		<input id="modifyTimeEnd" type="text" style="width:100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
	</div>
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
<script type="text/javascript" src="${basePath}/js/question/questionnew.js"></script>

</body>
</html>