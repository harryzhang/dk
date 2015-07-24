<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="include/top.jsp"%>
		<title>成功</title>
	</head>
<body>
	<article class="xin xin3">
		<div class="paiming">
			<p>目测我的 人品值<em>${credit.creditAmt/10000}</em>万</p>
			<p>在${loanChannelDo.loanUserDo.cname}排名第<em>${creditSeq}</em>位</p>
		</div>
		<a class="xin-btn xinbtn3" href="<c:url value='/app/elend/personalInfo'/>"></a>
	</article>
</body>
</html>