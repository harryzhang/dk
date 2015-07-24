<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function trans() {//转账  
		param['paramMap.wid'] = $("#wid").val();
		param['paramMap.status'] = $("input[name='status']:checked").val();
		param['paramMap.adminId'] = '${sessionScope.admin.id }';
		param['paramMap.userId'] = $("#userId").val();
		param['paramMap.sum'] = '${sum}';
		param['paramMap.poundage'] = '${poundage}';
		param['paramMap.check'] = 'false';
		param['paramMap.oldStatus'] = '4';//正在转账状态
		param['paramMap.cardNo'] = '${cardNo }';
		param['paramMap.usrCustId'] = '${usrCustId}';
		param['paramMap.trxId'] = '${trxId}';
		window.parent.$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
		$("#btnSave").attr("disabled", 'disabled');
		$.shovePost("updateWithdrawTransfer.do", param, function(data) {
			alert(data);
			window.parent.location.reload();
		});
	}
</script>
<style>
td {
	font-size: 13
}
</style>
<div style="padding: 15px 10px 0px 10px; font-size: 8px;" id="huifu">
	<div>
		<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 97%; padding-top: 10px; background-color: #fff;">

			<table style="margin: 8px;border-collapse: collapse;border: none;" cellspacing="0" cellpadding="0" width="100%" border="1">
				<tbody>
					<tr>
						<td align="left" width="20%" height="28px">用户名： <s:hidden id="wid" name="#request.wid" /> <s:hidden id="userId" name="#request.userId" /></td>
						<td width="30%">${username }&nbsp;</td>
						<td align="left" width="20%" height="28px">充值成功总额：</td>
						<td width="30%">${r_total }&nbsp;</td>
					</tr>

					<tr>
						<td align="left" width="20%" height="28px">2个月内提现总额：</td>
						<td width="30%">${w_total }&nbsp;</td>
						<td align="left" width="20%" height="28px">2个月内回款总额：</td>
						<td width="30%">${retSum }&nbsp;</td>
					</tr>

					<tr>
						<td align="left" width="20%" height="28px">投标成功总额：</td>
						<td width="30%">${ real_Amount}&nbsp;</td>

						<td align="left" width="20%" height="28px">可用余额：</td>
						<td width="30%">${usableSum }&nbsp;</td>
					</tr>
					<tr>
						<td align="left" width="20%" height="28px">开户名：</td>
						<td align="left" width="30%" height="28px">${realName }&nbsp;</td>
						<td align="left" width="20%" height="28px">提现支行：</td>
						<td align="left" width="30%" height="28px">${branchBankName }&nbsp;</td>
					</tr>
					<tr>
						<td align="left" width="20%" height="28px">提现账号：</td>
						<td width="30%">${cardNo }&nbsp;</td>
						<td align="left" width="20%" height="28px">提现总额：</td>
						<td width="30%">${sum }&nbsp;</td>
					</tr>
					<tr>
						<td align="left" width="20%" height="28px">到账金额：</td>
						<td width="30%">${sum }&nbsp;</td>
						<td align="left" width="20%" height="28px">手续费：</td>
						<td width="30%">${poundage }&nbsp;</td>

					</tr>
					<tr>
						<td align="left" width="20%" height="28px">状态：</td>
						<td width="30%">${status }&nbsp;</td>
						<td align="left" width="20%" height="28px">添加时间/IP：</td>
						<td width="30%">${applyTime }/${ipAddress}&nbsp;</td>

					</tr>
					<tr>
						<td align="left" width="20%" height="28px">转账状态：</td>
						<td align="left" width="80%" height="28px" colspan="3"><s:radio id="radio1" name="status" list="#{2:'提交转账',5:'不予转账'}" value="2"></s:radio>
						</td>
					</tr>
					<tr>
						<td align="left" width="20%" height="28px">审核信息：</td>
						<td colspan="3" width="80%"><span style="color: gray;"> ${rk }&nbsp; </span>
						</td>
					</tr>
				</tbody>
			</table>

			<br /> <br />
			<div align="center">
				<button id="btnSave" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px" onclick="trans();"></button>
			</div>
		</div>
	</div>
</div>
