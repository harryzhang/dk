<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="include/top.jsp"%>
		<title>成功</title>
	</head>
<body>
	<article class="suc">
		<h2 class="suc-tip">尊敬的${loanPersonDo.realName}用户：</h2>
		<p class="suc-dic">您申请的贷款信息已经提交，集团贷将尽快与您取得联系，谢谢您的配合。如有需要，请联系客服：15919843250
</p>
	</article>		
  <div class="p1 db bs" style="padding-top:4f0px; width:100%;">
    <a class="apply" href="<c:url value='/app/group/welcome.do'/>">确定</a>
  </div>
</body>
</html>
<script type="text/javascript">
    var url = "<c:url value='/app/group/welcome.do'/>";
	setTimeout("window.location.href='"+url+"'", 3000);
</script>
