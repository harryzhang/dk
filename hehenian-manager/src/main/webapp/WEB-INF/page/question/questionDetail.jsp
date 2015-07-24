<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<%System.out.println("hellp"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改问卷题目</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html";charset="UTF-8">
</head>
<body>

  
<div class="center">
<form id="frm" action="/question/saveEdit.html" method="post" ENCTYPE="multipart/form-data" >

<input name="o_id" 			type="hidden" id="rid" value="${data.id }" 			/>
<input name="o_content" 	type="hidden" id="rid" value="${data.content }" 	/>
<input name="o_orders" 		type="hidden" id="rid" value="${data.orders }" 		/>
<input name="o_status" 		type="hidden" id="rid" value="${data.status }" 		/>
<input name="o_platform" 	type="hidden" id="rid" value="${data.platform }" 	/>

问题类型:<input name="id" type="text" id="rid" value="${data.id }" /><br/>
题目名称:<input name="content" type="text" id="rid" value="${data.content }" /><br/>
题目顺序:<input name="orders" type="text" id="rid" value="${data.orders }"/><br/>
启用状态:
<input name="status" type="radio" value="启用" 	<c:if test="${data.status == 1 }"><c:out value="checked='checked'"/></c:if>		name="status"/>
<input name="status" type="radio" value="未启用" <c:if test="${data.status == 0 }"><c:out value="checked='checked'"/></c:if>		name="status"/>
<br/>
使用平台:<select class="easyui-combobox" id="platform" name="status" style="width:150px;" editable="false">
	<option value="0" <c:if test="${data.platform == 1 }"><c:out value="selected='selected'"/></c:if>>PC</option>
    <option value="1" <c:if test="${data.platform == 2 }"><c:out value="selected='selected'"/></c:if>>Android</option>
    <option value="2" <c:if test="${data.platform == 3 }"><c:out value="selected='selected'"/></c:if>>iOS</option>
    <option value="3" <c:if test="${data.platform == 4 }"><c:out value="selected='selected'"/></c:if>>不限</option>
    </select><br/>
    <input type="submit"/>
</form>
</div>
</body>
</html>
  
  