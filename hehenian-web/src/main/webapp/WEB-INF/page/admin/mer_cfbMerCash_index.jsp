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
</head>
<body>
	<div id="right">
		<div style="padding: 15px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" class="main_alll_h2"><a href="merRechargeInit.do">子账户取现</a>
						</td>
						<td width="2">&nbsp;</td>
					</tr>
				</table>
			</div>
			<div style="padding: 10px;  width: 1120px;  background-color: #fff;">
				<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
					<tbody>
						<tr>
							<td class="f66" align="left" width="50%" height="36px">&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div style="padding: 10px 280px 20px 280px;">
				<table width="50%" border="0" cellspacing="0" cellpadding="0" style=" font-size:12px;">
					<tr>
						<td align="right">子账户：</td>
						<td><select id="subAcct">
								<option value="cfb" selected="selected">彩付宝专属户</option>
						</select> <b>*</b><span id="utip" style="color: red;"></span></td>
					</tr>
					<tr height="10"></tr>
					<tr>
						<td align="right">取现金额：</td>
						<td><input type="text" id="amount" /> 元<b>*</b>
						</td>
					</tr>
<%--					<tr height="10"></tr>--%>
<%--					<tr>--%>
<%--						<td align="right">银行卡号：</td>--%>
<%--						<td><input type="radio" value="D" name="cardDcFlag" id="cardDcFlagC" style="vertical-align: middle;"/>借记卡--%>
<%--					</tr>--%>
<%--					<tr height="10"></tr>--%>
<%--					<tr>--%>
<%--						<td align="right">备注：</td>--%>
<%--						<td><<textarea rows="3" cols="30" id="remark" name="remark"></textarea></b>--%>
<%--						</td>--%>
<%--					</tr>--%>
					<tr height="10"></tr>
					<tr>
						<td align="right"></td>
						<td><input value="确定" id="recharge" style="background: #666666;color: white;width: auto;" type="button" /></td>
					</tr>
					<tr height="10"></tr>
					<tr >
					<td></td>
					<td><span style="font-size: 24px;color:#eb6100;font-family: 微软雅黑;">${request.title }</span></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><a href="javascript:history()" style="margin-left: 25px;color: #FF1493">查看记录</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		$("#recharge").click(function() {
			var amount = $("#amount").val();
			var subAcct = $("#subAcct").val();
			var remark = $("#remark").val();
			var cardDcFlag = $("input[name='cardDcFlag']:checked").val();
			if (isNaN(amount) || amount < 0) {
				alert("取现金额错误");
				return;
			}
			if (subAcct == '') {
				alert("请选择子账户");
				return;
			}
<%--			if (cardDcFlag == "" || cardDcFlag == undefined) {--%>
<%--				alert("请填写银行卡号");--%>
<%--				return;--%>
<%--			}--%>
			param = {};
			param["amount"] = amount;
			param["subAcct"] = subAcct;
<%--			param["remark"] = remark;--%>
			$.post("cfbMerCash.do", $.param(param), function(data) {
				if (data.length < 10) {
					alert("取现失败,请重试");
				} else {
<%--					$("#right").html(data);--%>
					alert(data);
				}
			});
		});
	});

	//查看记录
	function history() {
		var url = "iframe:admin/queryCfbMerCashList.do";
		if('${request.title}'==''){
			url = "iframe:queryCfbMerCashList.do";
		}
		$.jBox(url, {
			title : "子账户取现详情",
			width : 700,
			height : 480,
			buttons : {}
		});
	}
</script>
</html>
