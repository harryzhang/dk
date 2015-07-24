<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>网站统计管理首页</title>
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
					<table width="90%">
						<tr>
							<th class="main_alll_h2" align="left">
								网站统计
							</th>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td align="right">
								<input id="excel" type="button" value="导出Excel" name="excel" />
							</td>
						</tr>
					</table>
					<table id="gvNews" style="width: 1120px;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 20%;" scope="col">
						统计项
					</th>
					<th style="width: 10%;" scope="col">
						金额
					</th>
					<th style="width: 20%;" scope="col">
						统计项
					</th>
					<th style="width: 10%;" scope="col">
						金额
					</th>
					<th style="width: 25%;" scope="col">
						统计项
					</th>
					<th style="width: 15%;" scope="col">
						金额
					</th>
				</tr>
					<tr class="gvItem">
						<td>
							网站会员总金额：
						</td>
						<td align="center">
							${webMap.webUserAmount}
						</td>
						<td>
							投资人会员盈利统计：
						</td>
						<td align="center">
							${webMap.investorProfit }(统计包括未收完)
						</td>
						<td>
							网站成功线下充值总额：
						</td>
						<td align="center">
							${webMap.downlineSuccessPrepaid }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站会员总可用金额：
						</td>
						<td align="center">
							${webMap.webUserUsableAmount}
						</td>
						<td>
							投资成功待收金额
						</td>
						<td align="center">
							${webMap.investorForPI }
						</td>
						<td>
							网站提现总额
						</td>
						<td align="center">
							${webMap.cashWith }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站会员总冻结金额：
						</td>
						<td align="center">
							${webMap.webUserFreezeAmount}
						</td>
						<td>
							投资奖励所得
						</td>
						<td align="center">
							${webMap.investReward }
						</td>
						<td>
							网站提现实际到账总额
						</td>
						<td align="center">
							${webMap.cashSuccessWith }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站会员总待收本金：
						</td>
						<td align="center">
							${webMap.webUserForPICapital}
						</td>
						<td>
							担保投资奖励所得
						</td>
						<td align="center">
							##########
						</td>
						<td>
							网站提现手续费
						</td>
						<td align="center">
							${webMap.cashWithFee }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站会员总待收利息：
						</td>
						<td align="center">
							${webMap.webUserForPIInterest}
						</td>
						<td>
							借款人逾期罚金所得
						</td>
						<td align="center">
							总金额=${webMap.borrowPI}??已付=${webMap.borrowHasPI}
						</td>
						<td>
							网站总的成功借款金额(所有借款未还总额+所有借款已还总额)
						</td>
						<td align="center">
							${webMap.borrowForPI+webMap.borrowHasAmount }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站会员总待收金额：
						</td>
						<td align="center">
							${webMap.webUserForPI}
						</td>
						<td>
							借款人费用支出
						</td>
						<td align="center">
							##########
						</td>
						<td>
							所有借款未还总金额
						</td>
						<td align="center">
							${webMap.borrowForPI }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站收入总金额：
						</td>
						<td align="center">
							${webMap.webComeInAmount}
						</td>
						<td>
							借款管理费
						</td>
						<td align="center">
							${webMap.borrowManageFee }有区别??
						</td>
						<td>
							所有借款没有逾期未还总金额
						</td>
						<td align="center">
							${webMap.borrowNomalForPay }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站总的借款管理费金额：
						</td>
						<td align="center">
							${webMap.borrowManageFee}
						</td>
						<td>
							借款利息总额
						</td>
						<td align="center">
							${webMap.borrowInterest }
						</td>
						<td>
							借款逾期网站垫付未还金额
						</td>
						<td align="center">
							${webMap.webAdvinceForP }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站总借款逾期罚金金额：
						</td>
						<td align="center">
							${webMap.borrowFI}
							有区别??
						</td>
						<td>
							借款奖励支出总额
						</td>
						<td align="center">
							${webMap.borrowRewrad }
						</td>
						<td>
							借款逾期网站未垫付未还金额
						</td>
						<td align="center">
							${webMap.borrowForAmount }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站总的投资管理费用金额
						</td>
						<td align="center">
							${webMap.creditManageFee}
						</td>
						<td>
							借款担保奖励支出总额
						</td>
						<td align="center">
							##########
						</td>
						<td>
							所有借款已还总金额
						</td>
						<td align="center">
							${webMap.borrowHasAmount }
						</td>
						
					</tr>
					<tr class="gvItem">
						<td>
							网站总的线上充值费用金额
						</td>
						<td align="center">
							充值总额=${webMap.onlinePrepaid}还是手续费总额??
						</td>
						<td>
							借款逾期罚金支出总额
						</td>
						<td align="center">
							替用户还款的罚金支出??
						</td>
						<td>
							所有借款正常还款总金额
						</td>
						<td align="center">
							${webMap.borrowNomalRepayAmount }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							担保垫付扣费
						</td>
						<td align="center">
							##########
						</td>
						<td>
							借款逾期催缴费支出总额
						</td>
						<td align="center">
							##########
						</td>
						<td>
							借款逾期的网站垫付已还金额
						</td>
						<td align="center">
							${webMap.webAdvinceHasP }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							借款人罚金扣回
						</td>
						<td align="center">
							##########
						</td>
						<td>
							网站充值提现金额统计
						</td>
						<td align="center">
							又充值又提现?
						</td>
						<td>
							借款逾期的网站未垫付已还金额
						</td>
						<td align="center">
							${webMap.webNoAdvinceHasP }
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							其他金额
						</td>
						<td align="center">
							##########
						</td>
						<td>
							网站成功线上充值总额
						</td>
						<td align="center">
							${webMap.onlineSuccessPrepaid }
						</td>
						<td>
							借款逾期网站垫付总金额
						</td>
						<td align="center">
							${webMap.webAdvinceForP+webMap.webAdvinceHasP }
						</td>
					</tr>
			</tbody>
		</table>
</div>
</div>
   <div>
	</div>
</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script>
	$(function() {
		$("#excel").click(function() {
			window.location.href = "exportwebStatis.do";
		});

	});
</script>
</html>
