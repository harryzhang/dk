<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<jsp:include page="/include/head.jsp"></jsp:include>
	</head>
	<body >
		<!--顶部状态栏 开始-->
		<jsp:include page="/include/top.jsp"></jsp:include>
		<table cellpadding="0" cellspacing="0"
			style="margin-top: 50px;margin-bottom:50px; width: 100%;">
			<tr valign="middle" align="center">
				<td style="text-align: right;width: 50%;padding-right: 10px;">
					<img src="images/no_permision.png" width="106" height="118"
						style="padding-top: 15px;" />
				</td>
				<td valign="middle" style="text-align: left;width: 50%;padding-left: 10px;">
					<h3>
						抱歉，您没有操作权限
					</h3>
				</td>
			</tr>
		</table>
		<!--底部快捷导航 开始-->
		<jsp:include page="/include/footer.jsp"></jsp:include>
	</body>
</html>