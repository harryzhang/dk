<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
	<!--顶部状态栏 开始-->
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="nav_bottom">
		<div class="nav_b_left"></div>
		<div class="nav_b_right"></div>
	</div>
	<!--顶部主导航 结束-->

	<div class="main">
		<div class="error01">
			<img src="/images/index11_352.jpg" width="448" height="125" />
		</div>
	</div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/nav.js"></script>
	<script type="text/javascript">
		$(function() {
			
		});
	</script>
</body>
</html>