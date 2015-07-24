<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>问卷调查统计首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today" height="28" class="xxk_all_a">
								<a href="questionnaireStatisInit.do">问卷调查统计</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户名:
									<input id="username" value="" />
									&nbsp;&nbsp;

									<select id="level" name="level">
										<option value="0">
											选择风险级别
										</option>
										<option value="1"}>
											级别1
										</option>
										<option value="2"}>
											级别2
										</option>
										<option value="3"}>
											级别3
										</option>
										<option value="4"}>
											级别4
										</option>
										<option value="5"}>
											级别5
										</option>
										<option value="6"}>
											级别6
										</option>
									</select>

									<select id="trend" name="trend">
										<option value="0">
											选择风险偏好
										</option>
										<option value="1"}>
											偏好1
										</option>
										<option value="2"}>
											偏好2
										</option>
										<option value="3"}>
											偏好3
										</option>
										<option value="4"}>
											偏好4
										</option>
										<option value="5"}>
											偏好5
										</option>
										<option value="6"}>
											偏好6
										</option>
									</select>

									&nbsp;&nbsp;
									<input id="search" type="button" value="查找" name="search" />
									&nbsp;&nbsp;
									<input id="excel" type="button" value="导出Excel" name="excel" />
								</td>
							</tr>
						</tbody>
					</table>
					<span id="divList"><img src="../images/admin/load.gif"
							class="load" alt="加载中..." /> </span>
					<div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	$(function() {
		initListInfo(param);
		$('#search').click(function() {
			param["pageBean.pageNum"] = 1;
			initListInfo(param);
		});

		$("#excel")
				.click(
						function() {
							window.location.href = encodeURI(encodeURI("exportQuestionnaireStatis.do?username="
									+ $("#username").val()
									+ "&&trend="
									+ $("#trend").val()
									+ "&&level="
									+ $("#level").val()));

						});
	});
	function initListInfo(praData) {
		praData["paramMap.username"] = $("#username").val();
		praData["paramMap.trend"] = $("#trend").val();
		praData["paramMap.level"] = $("#level").val();
		$.shovePost("questionnaireStatisList.do", praData, initCallBack);
	}
	function initCallBack(data) {
		$("#divList").html(data);
	}
</script>
</html>
