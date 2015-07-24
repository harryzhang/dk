<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<style>
.main .error01 {
	height: 125px;
	width: 448px;
	margin: auto;
	padding: 150px 10px;
	clear: both;
	overflow: hidden;
}
</style>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
	
	<div class="center">
		<div class="wytz_center">
			<div class="main">
				<div class="error01">
					<img src="images/index11_352.jpg" width="448" height="125" />
				</div>
			</div>
		</div>
	</div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			hhn(4);
		});
	</script>
</body>
</html>