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
		$("#recharge").click(function() {
			var amount = $("#amount").val();
			var outSubAcct = $("#outSubAcct").val();
			var inSubAcct = $("#inSubAcct").val();
			if (isNaN(amount) || amount < 0) {
				alert("转出金额错误");
				return;
			}
			if (outSubAcct == '') {
				alert("请选择转出子账户");
				return;
			}
			if (inSubAcct == '') {
				alert("请选择转入子账户");
				return;
			}
			if(outSubAcct == inSubAcct)
			{
				alert("转入与转出子账户一致");
				return;
			}
			param = {};
			param["amount"] = amount;
			param["outSubAcct"] = outSubAcct;
			param["inSubAcct"] = inSubAcct;
			$.post("merRechargeTransfer.do", $.param(param), function(data) {
<%--				if (data.length < 10) {--%>
<%--					alert("转账失败,请重试");--%>
<%--				} else {--%>
<%--					var a =$("#right").html(data);--%>
					alert(data);
					if(data=="成功"){
						window.location.reload();
					}
<%--				}--%>
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
						<td width="100" height="28" class="main_alll_h2"><a href="merRechargeIndex.do">子账户转账</a>
						</td>
						<td width="2">&nbsp;</td>
					</tr>
				</table>
			</div>
			<div style="padding: 10px;  width: 1120px;  background-color: #fff;">
				<table style="margin: auto;border-collapse: collapse;" cellspacing="0" cellpadding="0" width="50%" border="1">
					<tbody>
					<tr>
							<td class="f66" align="center"  height="28px">子账户号</td>
							<td class="f66" align="center"  height="28px">账户余额</td>
							<td class="f66" align="center"  height="28px">可用余额</td>
							<td class="f66" align="center"  height="28px">冻结金额</td>
						</tr>
					<s:iterator value="#request.bean" var="b">
						<tr>
							<td class="f66" align="center"  height="28px">${SubAcctId }</td>
							<td class="f66" align="center"  height="28px">${AcctBal }</td>
							<td class="f66" align="center"  height="28px">${AvlBal }</td>
							<td class="f66" align="center"  height="28px">${FrzBal }</td>
						</tr>
					</s:iterator>
					</tbody>
				</table>
			</div>
			<div style="padding:100px;margin-top: -50px;">
				<table width="50%" border="0" cellspacing="0" cellpadding="0" style=" font-size:12px;margin: auto;">
					<tr>
						<td align="right">出账子账户：</td>
						<td><select id="outSubAcct">
								<option value="" selected="selected">请选择</option>
								<option value="MDT000001" selected="selected">彩付宝专属户</option>
								<option value="SDT000001" selected="selected">代偿专用子账户</option>
								<option value="SDT000002" selected="selected">咨询费专用子账户</option>
						</select> <b>*</b><span id="utip" style="color: red;"></span></td>
					</tr>
					<tr>
						<td align="right">入账子账户：</td>
						<td><select id="inSubAcct">
								<option value="" selected="selected">请选择</option>
								<option value="MDT000001" selected="selected">彩付宝专属户</option>
								<option value="SDT000001" selected="selected">代偿专用子账户</option>
								<option value="SDT000002" selected="selected">咨询费专用子账户</option>
						</select> <b>*</b><span id="utip" style="color: red;"></span></td>
					</tr>
					<tr>
						<td align="right">转账金额：</td>
						<td><input type="text" id="amount" style="width: 132px;" /> <b>*</b> 元
						</td>
					</tr>
					<tr height="10"></tr>
					<tr>
						<td align="right"></td>
						<td><input value="确定" id="recharge" style="background: #666666;color: white;width: auto;" type="button" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
