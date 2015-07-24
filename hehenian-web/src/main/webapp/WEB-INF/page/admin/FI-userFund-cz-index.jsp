<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>财务管理-用户资金管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript">
	$(function() {
		param["pageBean.pageNum"] = 1;
		initListInfo(param);
		$("#bt_search").click(function() {
			param["pageBean.pageNum"] = 1;
			initListInfo(param);
		});
		$("#excelout").click(
				function() {
					var userId = $("#userId").val();
					window.location.href = encodeURI(encodeURI("exportUserFundRecharge.do?userId=" + userId + "&&start=" + $("#start").val()
							+ "&&end=" + $("#end").val() + "&&statss=" + $("#status").val() + "&&reType=" + $("#rechargeType").val()));
				});
	});

	function initListInfo(param) {
		param["paramMap.end"] = $("#end").val();
		param["paramMap.start"] = $("#start").val();
		param["paramMap.status"] = $("#status").val();
		param["paramMap.userId"] = $("#userId").val();
		param["paramMap.rechargeType"] = $("#rechargeType").val();
		$.shovePost("queryUserFundRechargeList.do", param, initCallBack);
	}

	function initCallBack(data) {
		$("#dataInfo").html(data);
	}
</script>
</head>
<body>
	<s:hidden id="userId" name="paramMap.userId"></s:hidden>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">充值时间： <input id="start" name="paramMap.start" type="text"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> &nbsp;&nbsp; 到： <input id="end" name="paramMap.end" type="text"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> &nbsp;&nbsp; 状态： <s:select id="status" list="rechargeStatus" name="paramMap.rechargeStatus" listKey="statusId"
										listValue="statusValue" /> &nbsp;&nbsp; <input id="bt_search" type="button" value="查 找" /> &nbsp;&nbsp; <input id="excelout" type="button" value="导出EXCEL" /></td>
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
