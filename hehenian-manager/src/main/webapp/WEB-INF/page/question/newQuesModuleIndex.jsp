<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>问题模块</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />

<style style="text/css">
.l-labeltext {
	display: inline;
}
</style>
</head>
<body>
	<div id="toolbar"></div>
	<div class="tabelMod">
		<div class="mutilClass" style="display: inline-block">
			<label>问题模块:</label> <select id="modulesId" class="modulesParent">
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
			<label>平台:</label> <select id="platform">
				<option value="-1">全部</option>
				<option value="1">企业版</option>
				<option value="2">大众版</option>
			</select> <label>启用状态:</label> <select id="inUse">
				<option value="-1">全部</option>
				<option value="1">启用</option>
				<option value="0">关闭</option>
			</select>
		</div>
		<div style="display: inline-block">
			<label>创建时间：从</label> <input id="minCreateTime" type="text"
				style="width: 100px;"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})" /> 到 <input
				id="maxCreateTime" type="text" style="width: 100px;"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})" /> <label>修改时间：从</label>
			<input id="minModifyTime" type="text" style="width: 100px;"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})" /> 到 <input
				id="maxModifyTime" type="text" style="width: 100px;"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})" />
		</div>
	</div>
	<div class="tabelMod">
	<p style="color:red">注意：目前只有注册问题模块的内容，编辑后才会生效到website中</p>
	</div>
	<div class="btnBox">
		<div id="searchbtn" style="cursor: hand"></div>
		<div id="addQuesModuleBtn" style="cursor: hand"></div>
		<div id="initQuestionBtn" style="cursor: hand"></div>
	</div>
	<div id="tableGrid"></div>
	<jsp:include page="../common_liger.jsp"></jsp:include>
	<script type="text/javascript" src="${basePath}/js/ligerUI/js/plugins/ligerToolBar.js"></script>
	<script type="text/javascript" src="${basePath}/js/ligerUI/js/plugins/ligerButton.js"></script>
	<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${basePath}/js/question/newQuesModule.js"></script>
</body>
</html>
