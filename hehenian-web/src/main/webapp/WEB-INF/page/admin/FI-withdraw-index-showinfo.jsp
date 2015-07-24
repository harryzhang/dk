<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>财务管理-用户银行卡管理</title>
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
</head>
<body>
	<div id="right" style="background-image: url; background-position: top; background-repeat: repeat-x;">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<div style="padding:0px; width: 550px;  background-color: #fff;">
					<table style="margin: 8px;border-collapse: collapse;border: none;" cellspacing="0" cellpadding="0" width="100%" border="1">
						<tbody>
							<tr>
								<td class="f66" align="center" width="20%" height="36px">用户名： <s:hidden id="wid" name="#request.wid" />
								</td>
								<td class="f66" align="center" width="30%" height="36px">${username }</td>
								<td class="f66" align="center" width="20%" height="36px">状态</td>
								<td class="f66" align="center" width="30%" height="36px"><s:if test="#request.status==1">初审中</s:if> <s:elseif
								test="#request.status==2">成功</s:elseif> <s:elseif
								test="#request.status==3">已取消</s:elseif> <s:elseif
								test="#request.status==4">待转账</s:elseif> <s:elseif
								test="#request.status==5">失败</s:elseif>
								<s:else>初始</s:else></td>
							</tr>
							<tr>
								<td class="f66" align="center" width="20%" height="36px">充值成功总额：</td>
								<td class="f66" align="center" width="30%" height="36px">${r_total }</td>
								<td class="f66" align="center" width="20%" height="36px">提现成功总额：</td>
								<td class="f66" align="center" width="30%" height="36px">${w_total }</td>
							</tr>
							<tr>
								<td class="f66" align="center" width="20%" height="36px">投标成功总额：</td>
								<td class="f66" align="center" width="30%" height="36px">${ real_Amount}</td>
								<td class="f66" align="center" width="20%" height="36px">提现上额限制：</td>
								<td class="f66" align="center" width="30%" height="36px">${withdraw_max }</td>

							</tr>
							<tr>
								<td class="f66" align="center" width="20%" height="36px">可用余额：</td>
								<td class="f66" align="center" width="30%" height="36px">${usableSum }</td>
								<td class="f66" align="center" width="20%" height="36px">开户名：</td>
								<td class="f66" align="center" width="30%" height="36px">${realName }</td>
							</tr>
							<tr>
								<td class="f66" align="center" width="20%" height="36px">提现支行：</td>
								<td class="f66" align="center" width="30%" height="36px">${branchBankName } &nbsp;</td>
								<td class="f66" align="center" width="20%" height="36px">提现账号：</td>
								<td class="f66" align="center" width="30%" height="36px">${cardNo }</td>
							</tr>
							<tr>
								<td class="f66" align="center" width="20%" height="36px">提现总额：</td>
								<td class="f66" align="center" width="30%" height="36px">${sum }</td>
								<td class="f66" align="center" width="20%" height="36px">到账金额：</td>
								<td class="f66" align="center" width="30%" height="36px">${realMoney }</td>
							</tr>
							<tr>
								<td class="f66" align="center" width="20%" height="36px">费率：</td>
								<td class="f66" align="center" width="30%" height="36px">${poundage }</td>
								<td class="f66" align="center" width="20%" height="36px">添加时间/IP：</td>
								<td class="f66" align="center" width="30%" height="36px">${applyTime }/${ipAddress}</td>
								<!-- <s:date name="paramMap.applyTime" format="yyyy-MM-dd HH:mm:ss" />-->
							</tr>
							<tr>
								<td class="f66" align="center" width="100%" height="36px" colspan="4">审核信息： ${rk }</td>
							</tr>

						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>
</body>
</html>
