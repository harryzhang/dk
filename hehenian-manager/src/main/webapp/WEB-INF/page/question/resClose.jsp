<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改问卷题目</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
<script type="text/javascript">
window.onload=function(){
    parent.window.questionList.reload();
	parent.$.ligerDialog.close(); //关闭弹出窗; //关闭弹出窗
	//alert(parent.$.ligerDialog);
	parent.$(".l-dialog,.l-window-mask").css("display","none"); //只隐藏遮罩层

}
 </script>
</head>
<body>
<div class="center">

</div>
</body>
</html>