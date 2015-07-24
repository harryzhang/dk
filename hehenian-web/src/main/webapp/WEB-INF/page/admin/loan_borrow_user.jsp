<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>投资人详情</title>
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<style>
.hhna{margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%;}
.hhna td{padding: 3px ;}
</style>
<script>
	//弹出窗口关闭
	function closeMthodes() {
		window.location.closeMthod();
	}
</script>
</head>
<body>
	<table id="loan" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="1">
			<tbody>
				<tr class=gvHeader>
				<th style="width: 100px;" scope="col">投资人</th>
				<th style="width: 100px;" scope="col">投资金额</th>
				<th style="width: 60px;" scope="col">利率</th>
			</tr>
			<c:choose>
				<c:when test="${results == null}">
					<tr align="center" class="gvItem">
						<td colspan="5">暂无数据</td>
					</tr>
				</c:when>
				<c:otherwise>
						<c:forEach items="${results}" var="bean">
						<tr class="gvItem">
							<td>${bean.realName}</td>
							<td>${bean.investAmount}</td>
							<td>${bean.annualRate}%</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</body>
</html>
