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
					<div style="width: 1000px;">
						<div style="float: left;width: 500px;">
							<table id="gvNews"  cellspacing="1"
								cellpadding="3" bgcolor="#dee7ef" border="0">
								<tbody>
									<tr class=gvHeader>
										<th style="width: 210px;" scope="col">
											统计项
										</th>
										<th style="width: 290px;" scope="col">
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
									</tr>
									<tr class="gvItem">
										<td>
											网站会员总冻结金额：
										</td>
										<td align="center">
											${webMap.webUserFreezeAmount}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站会员总待收金额：
										</td>
										<td align="center">
											${webMap.webUserForPI}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站收入总金额：
										</td>
										<td align="center">
											${webMap.webComeInAmount}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站总VIP金额：
										</td>
										<td align="center">
											${webMap.webVIPAmount}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站总学历认证金额：
										</td>
										<td align="center">
											${webMap.webXLAmount}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站总借款管理费金额：
										</td>
										<td align="center">
											${webMap.borrowManageFee}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站总借款逾期罚息金额：
										</td>
										<td align="center">
											${webMap.borrowFI}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站总债权转让手续费：
										</td>
										<td align="center">
											${webMap.creditManageFee}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											后台手动添加费用：
										</td>
										<td align="center">
											${webMap.backAddAmount}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											后台手动扣除费用：
										</td>
										<td align="center">
											${webMap.backDelAmount}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站成功充值总额：
										</td>
										<td align="center">
											${webMap.webSucPrepaid}
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div style="float: left;width: 500px;">
							<table id="gvNews"  cellspacing="1"
								cellpadding="3" bgcolor="#dee7ef" border="0">
								<tbody>
									<tr class=gvHeader>
										<th style="width: 210px;" scope="col">
											统计项
										</th>
										<th style="width: 290px;" scope="col">
											金额
										</th>
									</tr>
									<tr class="gvItem">
										<td>
											网站线上充值总额：
										</td>
										<td align="center">
											${webMap.onlinePrepaid}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站线下充值总额：
										</td>
										<td align="center">
											${webMap.downlinePrepaid}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站提现总额：
										</td>
										<td align="center">
											${webMap.cashWith}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											网站提现手续费总额：
										</td>
										<td align="center">
											${webMap.cashWithFee}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											所有借款未还总额：
										</td>
										<td align="center">
											${webMap.borrowForPI}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											所有逾期网站垫付未还款金额：
										</td>
										<td align="center">
											${webMap.webAdvinceForP}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											借款逾期网站未垫付未还款金额：
										</td>
										<td align="center">
											${webMap.borrowForAmount}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											所有借款已还款总额：
										</td>
										<td align="center">
											${webMap.borrowHasAmount}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											所有借款正常还款总额：
										</td>
										<td align="center">
											${webMap.borrowNomalRepayAmount}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											借款逾期网站垫付后已还款总额：
										</td>
										<td align="center">
											${webMap.webAdvinceHasP}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											借款逾期的网站未垫付已还款总额：
										</td>
										<td align="center">
											${webMap.webNoAdvinceHasP}
										</td>
									</tr>
									<tr class="gvItem">
										<td>
											借款逾期网站垫付总额：
										</td>
										<td align="center">
											${webMap.webAdviceAmount}
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
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
