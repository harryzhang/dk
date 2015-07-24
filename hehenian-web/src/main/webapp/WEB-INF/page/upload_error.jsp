<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>上传失败</title>
<link rel="shortcut icon" href="images/dotoyo.ico" type="image/x-icon" />
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
			<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
				<meta http-equiv="description" content="This is my page">
					<script type="text/javascript">
						function closes() {
							window.close();
						}
					</script>
</head>
<body>
	<div style="color: red;top: 30%;position: absolute;left: 30%" align="center">
		<s:if test="#reqest.errorMng==null">
					你所上传的文件不符合要求！
				</s:if>
		<s:else>
				${errorMng}
				</s:else>
	</div>
	<div align="center" style="top: 40%;position: absolute;left: 40%">
		<input type="button" value="关闭" onclick="closes()" />
	</div>
</body>
</html>
