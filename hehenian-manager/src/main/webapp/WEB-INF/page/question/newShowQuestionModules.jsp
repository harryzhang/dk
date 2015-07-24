<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>所处模块</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
</head>
<body>
	<table id="tab">
	<thead>
		<tr>
			<td width="10%">ID</td>
			<td width="25%">父级模块</td>
			<td width="10%">子模块</td>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="var" items="${data}">
		<tr class="row">
			<td>${var.questionnaireId}</td>
			<td>${var.content }</td>
			<td>
				<c:choose>
				   <c:when test="${var.children != null}">
                       <c:forEach var = "child" items="${var.children}">
						${child.content},
					   </c:forEach>
                   </c:when>
                   <c:otherwise>
                       无子模块
                    </c:otherwise>
				</c:choose>
			</td>
	    </tr>
    </c:forEach>
    </tbody>
    </table>
</body>
</html>
  