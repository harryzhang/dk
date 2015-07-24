<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>问题回写</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />

</head>
<body>
  <input type="hidden" id="questionId" value="${questionId }"></input>
  <input type="text" id="clazz"/>
  <br/>
  <input type="text" id="fields"/>
  <input type="hidden" id="clazzSelected"></input>
  <input type="hidden" id="fieldSelected"/>
<jsp:include page="../common_liger.jsp"></jsp:include>
<script src="${basePath}/js/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="${basePath}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="${basePath}/js/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}/js/question/showCallbackData.js"></script>
</body>
</html>