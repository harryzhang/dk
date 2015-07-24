<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
	$(function() {
		param["pageBean.pageNum"] = 1;
		initListInfo(param);
		$("#bt_search").click(function() {
			param["pageBean.pageNum"] = 1;
			param["paramMap.id"] = $("#id").val();
			param["paramMap.number"] = $("#number").val();
			param["paramMap.startTime"] = $("#startTime").val();
			param["paramMap.endTime"] = $("#endTime").val();
			param["paramMap.title"] = $("#title").val();
			param["paramMap.borrowType"] = $("#borrowType").val();
			param["paramMap.userGroup"] = $("#userGroup").val();
			initListInfo(param);
		});

		$("#excel").click(function() {
			window.location.href = encodeURI(encodeURI("exportReturnDebtList.do?id="+ $("#id").val()
				+ "&&startTime="+ $("#startTime").val()+ "&&endTime="+ $("#endTime").val()+ "&&title="
				+ $("#title").val()+ "&&borrowtype="+ $("#borrowType").val()+ "&&usergroup="+ $("#userGroup").val()));
		});

	});
	function initListInfo(param) {
		$.post("queryReturnDebtList.do", param, initCallBack);
	}

	function initCallBack(data) {
		$("#dataInfo").html(data);
	}

</script>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="main_alll_h2">
								<a href="queryReturnDebtList.do">还款中的债权</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="90%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="center"  height="36px">
										债权编号：
										<input id="id" name="paramMap.id" type="text" />
									</td>


									<td class="f66" align="center"  height="36px">
										应收时间：
										<input id="startTime" name="paramMap.startTime" type="text"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" />
									</td>

									<td class="f66" align="center"  height="36px">
										&nbsp; 到
										<input id="endTime" name="paramMap.endTime" type="text"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" />
									</td>
									<td class="f66" align="center"  height="36px">
										<input id="excel" type="button" value="导出EXCEL"
											name="btnExportExcel" />
									</td>
									<td class="f66" align="center"  height="36px">
									</td>
								</tr>

								<tr>
									<td class="f66" align="center" height="36px">
										借款标题：
										<input id="title" name="paramMap.title" type="text" />
									</td>
									<td class="f66" align="center"  height="36px">
										借款编号：
										<input id="number" name="paramMap.number" type="text" />
									</td>
									<td class="f66" align="center" height="36px">
										借款类型：
										<s:select id="borrowType"
											list="#{1: '薪金贷', 2: '生意贷',3 :'业主贷' }"
											name="paramMap.borrowType" headerKey="" headerValue="--全部--" />
									</td>
									<td class="f66" align="center" height="36px">
										用户组：
										<s:select id="userGroup" list="userGroup"
											name="paramMap.userGroup" listKey="userGroupId"
											listValue="userGroupValue" />
									</td>
									<td class="f66" align="center"  height="36px">
										<input id="bt_search" type="button" value="搜索" />
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
