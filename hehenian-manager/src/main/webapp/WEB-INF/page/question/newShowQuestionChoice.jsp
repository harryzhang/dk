<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>选择内容</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
</head>
<body>
	<table id="tab">
	<thead>
		<tr>
			<td width="10%">ID</td>
			<td width="25%">选项内容</td>
			<td width="10%">顺序</td>
			<td width="20%">选项属性</td>
			<td width="10%">状态</td>
			<td width="25%">关联问题</td>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="var" items="${data}">
		<tr class="row">
			<td>${var.answerId}</td>
			<td>${var.content }</td>
			<td>${var.orders }</td>
			<td>${var.sexStatus == -1 ? '男女通用' : (var.sexStatus == 0 ? '男性' : (var.sexStatus == 1 ? '女性' : '位置')) }</td>
			<td>${var.isUse == 0 ? '停用' : (var.isUse == 1) ? '启用' : '未知' }</td>
			<td>
				<c:choose>
				   <c:when test="${var.relationIds != null}">
                       <c:forEach var = "relate" items="${var.relationIds}">
						${relate},
					   </c:forEach>
                   </c:when>
                   <c:otherwise>
                       无关联
                    </c:otherwise>
				</c:choose>
			</td>
	    </tr>
    </c:forEach>
    </tbody>
    </table>
</body>
</html>
  