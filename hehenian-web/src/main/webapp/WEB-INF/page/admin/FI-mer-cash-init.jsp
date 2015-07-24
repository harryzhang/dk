<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
* {
	padding: 3px;
	font-size: 12px;
	font-family: "tahoma";
}

td {
	height: 33px;
}
</style>
</head>
<body>
	<div align="center" style="text-align: center;padding: 10px;">
		<table width="80%" style="margin: auto;border-collapse: collapse;" border="1">
			<tr>
				<td align="right">用户名:</td>
				<td>${username }</td>
			</tr>
			<tr>
				<td align="right">汇付会员编号:</td>
				<td>${usrCustId}</td>
			</tr>
			<tr>
				<td align="right">取现银行卡:</td>
				<td>${cardNo}</td>
			</tr>
			<tr>
				<td align="right">取现金额:</td>
				<td style="padding-top: 1px;padding-bottom: 1px;"><input id="amt" style="height: 24px;padding: 0px;margin: 0px;" />
				</td>
			</tr>
		</table>
		<table width="80%" style="margin:10px auto;">
			<tr>
				<td colspan="2" align="center"><input type="button" value="确定" id="sub_cash" />
				</td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#sub_cash").click(function() {
			$("#sub_cash").attr("disabled", true);
			var param = {};
			param["userId"] = '${userId}';
			param["usrCustId"] = '${usrCustId}';
			param["transAmt"] = $("#amt").val();
			param["username"] = '${username}';
			window.parent.$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
			$.post("merCashSubmit.do", $.param(param), function(data) {
				alert(data);
				if (data == "取现成功") {
					window.parent.location.reload();
					return;
				}
				window.parent.$.jBox.closeTip();
				$("#sub_cash").attr("disabled", false);
			});
		});
	});
</script>
</html>