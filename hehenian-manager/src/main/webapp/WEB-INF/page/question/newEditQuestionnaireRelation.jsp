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
	<p style="color:red">注意：往子模块中添加一条记录，需要调整前端jsp的文案，请不要随便添加；子模块的顺序可以顺便调整</p>
	<div id="toolbar">
	</div>

	<div class="btnBox">
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
   	 var modulesId = ${modulesId};
    </script>
    <script src="${basePath}/js/question/newEditQuestionnaireRelation.js" type="text/javascript" ></script>
</html>

