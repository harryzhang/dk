<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<style type="text/css">
*{font-size: 13px;}
td{padding-top: 2px;padding-bottom: 2px;padding-left: 10px;}
</style>
</head>
<body>
	<div style="width: 880px;margin: 0 auto;text-align: center;padding: 10px;" align="center">
		<p>
<%--			<a href="upload/pdf/No.${borrowId}_pact.pdf">下载PDF</a>--%>
<%--			<a href="javascript:downloadPDF()">下载PDF</a>--%>
		</p>
		${content}
	</div>
</body>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	function downloadPDF() {
		param = {};
		param["url"] = window.location + "";
		param["borrowId"] = '${request.borrowId}';
		$.post("exportPDF.do", $.param(param), function(data) {
			if (data == '') {
				alert("出错了");
			} else {
				window.open(data);
				//window.location.href = data;
			}
		});
	}
</script>
</html>
