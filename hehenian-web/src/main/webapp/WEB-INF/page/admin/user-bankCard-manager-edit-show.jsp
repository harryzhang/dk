<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>财务管理-用户银行卡管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.f66 {
	
}

.hhn {
	padding-left: 20px;
	font-family: "tahoma";
	font-size: 12px;
	line-height: 20px;
	color: #666;
}

 td{
border:solid #333 1px;
}

</style>
</head>
<body>
	<div id="right" style="width: 600px;">
		<div style="padding: 15px 10px 0px 10px;">
			<div style="margin-top: -10px;">
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;" class="hhn">
					<table style="margin: 8px 30px;border-collapse:collapse;border:none;"   width="500px;" border="1"  >
						<tbody>
							<tr>
								<td class="f66" align="right" width="25%" height="36px">用户名：</td>
								<td class="hhn" align="left" height="36px" colspan="3"><s:property value="paramMap.username" />&nbsp;
								</td>
							</tr>

							<tr>
								<td class="f66" align="right" height="36px">真实姓名：</td>
								<td class="hhn" align="left" height="36px"  colspan="3"><s:property value="paramMap.realName" />&nbsp;
								</td>
							</tr>

							<tr>
								<td class="f66" align="right" height="36px">手机号码：</td>
								<td class="hhn" align="left" height="36px"  colspan="3"><s:property value="paramMap.mobilePhone" />&nbsp;
								</td>
							</tr>

							<tr>
								<td class="f66" align="right" height="36px">身份证：</td>
								<td class="hhn" align="left" height="36px"  colspan="3"><s:property value="paramMap.idNo" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="f66" align="right" height="36px">开户银行：</td>
								<td class="hhn" align="left" height="36px"><s:property value="paramMap.bankName" />&nbsp;</td>
								<td class="f66" align="right" height="36px">新开户银行：</td>
								<td class="hhn" align="left" height="36px"><s:property value="paramMap.modifiedBankName" />&nbsp;</td>
							</tr>

							<tr>
								<td class="f66" align="right" height="36px">支行：</td>
								<td class="hhn" align="left" height="36px"><s:property value="paramMap.branchBankName" />&nbsp;</td>
								<td class="f66" align="right" height="36px">新支行：</td>
								<td class="hhn" align="left" height="36px"><s:property value="paramMap.modifiedBranchBankName" />&nbsp;</td>
							</tr>

							<tr>
								<td class="f66" align="right" height="36px">银行卡号：</td>
								<td class="hhn" align="left" height="36px"><s:property value="paramMap.cardNo" />&nbsp;</td>
								<td class="f66" align="right" height="36px">新银行卡号：</td>
								<td class="hhn" align="left" height="36px"><s:property value="paramMap.modifiedCardNo" />&nbsp;</td>
							</tr>

						</tbody>
					</table>
					<br/>
<%--					<input type="button" style="background: #777;margin-left: 190px;" value="确定" />--%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
