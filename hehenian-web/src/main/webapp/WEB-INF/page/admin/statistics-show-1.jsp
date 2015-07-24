<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>查询图片</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
				<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
	</head>
	<body>
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
							
						         <tr>
									<td class="f66" align="left" width="25%" height="36px">
									 审核状态:
									 <s:if test="#request.queryonemsgmap.auditStatus==1">待审核</s:if>
									 <s:if test="#request.queryonemsgmap.auditStatus==2">审核失败</s:if>
									 <s:if test="#request.queryonemsgmap.auditStatus==3">审核成功</s:if>
									</td>
								 </tr>
								
								<tr>
									<td class="f66" align="left" width="25%" height="36px">
									是否可见：
									 <s:if test="#request.queryonemsgmap.visiable==1"><a style="color: red;">可见</a></s:if>
									  <s:if test="#request.queryonemsgmap.visiable==2"><a style="color: red;">不可见</a></s:if>
									</td>
								</tr>
								
								<tr>
									<td class="f66" align="left" width="25%" height="36px">
									审核意见：${queryonemsgmap.tmdoption }
									</td>
								</tr>
								
								
								<tr>
									<td class="f66" align="left" width="25%" height="36px">
									图片：
									</td>
								</tr>
									<tr>
									<td class="f66" align="left" width="25%" height="36px">
									<img src="../${queryonemsgmap.imagePath }" width="600px" height="600px">
									</td>
								</tr>
								
							</tbody>
						</table>
					</div>
				</div>
			</div>
			</div>
	</body>
</html>
