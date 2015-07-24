<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link href="${basePath}/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding: 6px; overflow: hidden; width: 100%; height: 100%;"> 

	<div id="toolbar">
	</div>
	<div class="tabelMod">

	<label>问题类型:</label>
	<select id="questionType">
		 <option value="-1" selected="selected">请选择</option>
		 <option value="1">选择题</option>
		 <option value="2">文字输入题</option>
		 <option value="3">程度题[符合-不符合]</option>
		 <option value="4">程度题[重要-不重要]</option>
		 <option value="5">是否题[是-否]</option>
		 <option value="6">区间题</option>
	</select>
	
	<label>问题属性:</label>
	<select id="sexStatus">
		 <option value="-1" selected="selected">请选择</option>
		 <option value="-1">男女通用</option>
		 <option value="0">男用户</option>
		 <option value="1">女用户</option>
	</select>
	
	<label>是否保密:</label>
	<select id="isHide">
		 <option value="-1" selected="selected">请选择</option>
		 <option value="-1">是</option>
		 <option value="0">否</option>
	</select>
	
	<div class="mutilClass" style="display:inline-block">
	<label>问题模块:</label>
		<select id="modulesId" class="modulesParent">
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
</div>
<div class="tabelMod">
	<label>问题内容：</label>
	<input id="content" type="text" style="width:400px;"/>
</div>
	<div class="btnBox">
		<div id="searchbtn"  style="cursor: hand"></div>
		<div id="savebtn"  style="cursor: hand"></div>
		<div id="deletebtn"  style="cursor: hand"></div>
	</div>
	<div id="maingrid1" style="margin: 4px; padding: 0; float: left;"></div>
    <div id="maingrid2" style="margin: 4px; padding: 0; margin-left: 10px; float: left;"></div>
</body>

 	<jsp:include page="../common_liger.jsp"></jsp:include>
	<script type="text/javascript" src="${basePath}/js/ligerUI/js/plugins/ligerToolBar.js"></script>
	<script type="text/javascript" src="${basePath}/js/ligerUI/js/plugins/ligerButton.js"></script>
	<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
	<script src="http://www.ligerui.com/demos/grid/base/draggable.js" type="text/javascript"></script>
	 <script src="${basePath}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${basePath}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>


    <script type="text/javascript">
   	 var qnId = ${questionnaireId};
    </script>
    <script src="${basePath}/js/question/newEditQuestionRelation.js" type="text/javascript" ></script>
</html>

