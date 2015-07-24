<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<style>
.main { width: 1000px; margin: 0px auto; clear:both; overflow:hidden;border-left: 1px #ccc solid;border-right: 1px #ccc solid;}
.main .error01 { height: 100px; width: 448px; margin-right: auto; margin-left: auto; padding-top: 150px; padding-bottom: 150px; }
</style>
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
			<img src="images/index11_35.jpg" width="448" height="125" />
		</div>
	</div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
		
	</script>
</body>
</html>