<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="right">
	<div style="padding: 15px 10px 0px 10px;">
		<div>
			<div style="width: 1000px;">
				<div style="float: left; width: 500px;">
					<table id="gvNews" cellspacing="1" cellpadding="3"
						bgcolor="#dee7ef" border="0">
						<tbody>
							<tr class=gvHeader>
								<th style="width: 250px;" scope="col">名称</th>
								<th style="width: 150px;" scope="col">金额</th>
								<th style="width: 100px;" scope="col">次数</th>
							</tr>
							<tr class="gvItem">
								<td>借款奖励扣除</td>
								<td align="center">${webMap.borrowRewardCut}</td>
								<td align="center">${webMap.borrowRewardCutCount}</td>
							</tr>
							<tr class="gvItem">
								<td>借款手续费扣除</td>
								<td align="center">${webMap.borrowCost}</td>
								<td align="center">${webMap.borrowCostCount}</td>
							</tr>
							<tr class="gvItem">
								<td>还款</td>
								<td align="center">${webMap.repayAmount}</td>
								<td align="center">${webMap.repayCount}</td>
							</tr>
							<tr class="gvItem">
								<td>逾期还款</td>
								<td align="center">${webMap.lateRepayAmount}</td>
								<td align="center">${webMap.lateRepayCount}</td>
							</tr>
							<tr class="gvItem">
								<td>借款入账</td>
								<td align="center">${webMap.borrowOkAmount}</td>
								<td align="center">${webMap.borrowOkCount}</td>
							</tr>
							<tr class="gvItem">
								<td>提现取消</td>
								<td align="center">${webMap.cancelWithdrawAmount}</td>
								<td align="center">${webMap.cancelWithdrawCount}</td>
							</tr>
							<tr class="gvItem">
								<td>提现成功</td>
								<td align="center">${webMap.withdrawSuccessAmount}</td>
								<td align="center">${webMap.withdrawSuccessCount}</td>
							</tr>
							<tr class="gvItem">
								<td>充值成功</td>
								<td align="center">${webMap.rechargeSuccessAmount}</td>
								<td align="center">${webMap.rechargeSuccessCount}</td>
							</tr>

						</tbody>
					</table>
				</div>
				<div style="float: left; width: 500px;">
					<table id="gvNews" cellspacing="1" cellpadding="3"
						bgcolor="#dee7ef" border="0">
						<tbody>
							<tr class=gvHeader>
								<th style="width: 250px;" scope="col">名称</th>
								<th style="width: 150px;" scope="col">金额</th>
								<th style="width: 100px;" scope="col">次数</th>
							</tr>
							<tr class="gvItem">
								<td>借款逾期还款罚金</td>
								<td align="center">${webMap.borrowHasFIAmount}</td>
								<td align="center">${webMap.borrowHasFICount}</td>
							</tr>
							<tr class="gvItem">
								<td>投标金额冻结</td>
								<td align="center">${webMap.investFreezeAmount}</td>
								<td align="center">${webMap.investFreezeCount}</td>
							</tr>
							<tr class="gvItem">
								<td>投资奖励增加</td>
								<td align="center">${webMap.investRewardAmount}</td>
								<td align="center">${webMap.investRewardCount}</td>
							</tr>
							<tr class="gvItem">
								<td>投资利息管理费</td>
								<td align="center">${webMap.investFeeAmount}</td>
								<td align="center">${webMap.investFeeCount}</td>
							</tr>
							<tr class="gvItem">
								<td>用户还款资金收入</td>
								<td align="center">${webMap.userRepayAmount}</td>
								<td align="center">${webMap.userRepayCount}</td>
							</tr>
							<tr class="gvItem">
								<td>投标成功金额扣除</td>
								<td align="center">${webMap.investSuccessCutAmount}</td>
								<td align="center">${webMap.investSuccessCutCount}</td>
							</tr>
							<tr class="gvItem">
								<td>投标成功待收金额增加</td>
								<td align="center">${webMap.investSuccessDueinAmount}</td>
								<td align="center">${webMap.investSuccessDueinCount}</td>
							</tr>
							<tr class="gvItem">
								<td>投资用户取消</td>
								<td align="center">${webMap.investCancelAmount}</td>
								<td align="center">${webMap.investCancelCount}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
