<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<style type="text/css">
b {
	color: red;
}

td {
	padding: 5px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#queryOrdId").click(function() {
			var userName = $("#userName").val();
			var queryTrade = $("#queryTrade").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			if (userName == null || userName == "") {
				alert("用户名不能为空!");
				return;
			}
			if (queryTrade == '') {
				alert("请选择交易类型!");
				return;
			}
<%--			if (inSubAcct == '') {--%>
<%--				alert("请选择转入子账户");--%>
<%--				return;--%>
<%--			}--%>
<%--			if(outSubAcct == inSubAcct)--%>
<%--			{--%>
<%--				alert("转入与转出子账户一致");--%>
<%--				return;--%>
<%--			}--%>
			param = {};
			param["userName"] = userName;
			param["endTime"] = endTime;
			param["startTime"] = startTime;
			param["queryTrade"] = queryTrade;
			$.post("queryOrdid.do", $.param(param), function(data) {
					$("#showOrdid").attr("value",data);
			});
		});

		$("#queryTradeStatus").click(function() {
			var queryTrade = $("#queryTrade").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var ordId = $("#ordId").val();

			if (queryTrade == '') {
				alert("请选择交易类型!");
				return;
			}
			if(startTime == '' || startTime == null)
			{
				alert("请选择开始时间!");
				return;
			}
			if (endTime == '' || endTime == null) {
				alert("请选择结束时间!");
				return;
			}
			
			param = {};
			param["ordId"] = ordId;
			param["queryTrade"] = queryTrade;
			param["startTime"] = startTime;
			param["endTime"] = endTime;
			$.post("queryTradeStatus.do", $.param(param), function(data) {
					$("#show").attr("value",data);
			});
		});
	})
	
</script>
</head>
<body>
	<div id="right">
		<div style="padding: 15px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" class="main_alll_h2"><a href="merRechargeInit.do">子账户充值</a>
						</td>
						<td width="2">&nbsp;</td>
					</tr>
				</table>
			</div>
			<div background-color: #fff;">
				<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
					<tbody>
						<tr>
							<td class="f66" align="left" width="50%" height="36px">&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div>
				<table border="0" cellspacing="0" cellpadding="0" style=" font-size:12px;">
					<tr>
						<td align="right" nowrap="nowrap">用户名：</td>
						<td><input id="userName" value="${userName}" type="text"> <b>*</b><span id="utip" style="color: red;"></span></td>
						<td align="right" nowrap="nowrap">开始时间：</td>
						<td><input id="startTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" name="paramMap.startTime" readonly="readonly"> <b>*</b><span id="utip" style="color: red;"></span></td>
						<td align="right" nowrap="nowrap">结束时间：</td>
						<td><input id="endTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" name="paramMap.endTime" readonly="readonly"> <b>*</b><span id="utip" style="color: red;"></span></td>
						<td align="right">交易类型：</td>
						<td><select id="queryTrade">
								<option value="" selected="selected">请选择</option>
								<option value="netSave" selected="selected">充值</option>
								<option value="loans" selected="selected">放款</option>
								<option value="repayment" selected="selected">还款</option>
								<option value="cash" selected="selected">取现</option>
								<option value="cashAudit" selected="selected">取现复核</option>
								<option value="initiativeTender" selected="selected">投标</option>
								<option value="merCash" selected="selected">代取现</option>
						</select> <b>*</b><span id="utip" style="color: red;"></span></td>
					</tr>
					<tr height="10"></tr>
					<tr>
						<td colspan="4" align="right"><input value="查询订单号" id="queryOrdId" style="background: #666666;color: white;width: auto;" type="button" /></td>
						<td colspan="4"><input value="查询交易状态" id="queryTradeStatus" style="background: #666666;color: white;width: auto;" type="button" /></td>
					</tr>
					<tr>
						<td colspan="8"><div id="showOrdid"></div>&nbsp;&nbsp;&nbsp;&nbsp;<div id="show"></div><input value="${ordId}" type="hidden" id="ordId"/></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
