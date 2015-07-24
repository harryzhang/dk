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
		<h2 class="suc-tip">尊敬的E贷款用户：</h2>
		<p class="suc-dic">您的申请已成功提交，E贷款将在24小时之内与您取得联系</p>
	</article>		
  <div class="p1 db bs" style="padding-top:4f0px; width:100%;">
    <a class="apply" href="<c:url value='/app/elend/personalCenter?loanId=${loanId}'/>">确定</a>
  </div>
</body>
</html>
<script type="text/javascript">
    var url = "<c:url value='/app/elend/personalCenter?loanId=${loanId}'/>";
	setTimeout("window.location.href='"+url+"'", 3000); 
</script>
