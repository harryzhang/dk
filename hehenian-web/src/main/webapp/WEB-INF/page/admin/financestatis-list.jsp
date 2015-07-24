<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 0px;">
		<table id="gvNews" style="width: 1120px; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 30%;" scope="col">
						统计项
					</th>
					<th style="width: 70%;" scope="col">
						金额
					</th>
				</tr>
					<tr class="gvItem">
						<td>
							投资成功待收金额：
						</td>
						<td align="center">
							${financeEarnMap.investForAmount}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							投资奖励金额：
						</td>
						<td align="center">
							${financeEarnMap.investRewardAmount}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							借款人逾期罚金金额：
						</td>
						<td align="center">
							${financeEarnMap.borrowLateFAmount}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							用户邀请好友金额：
						</td>
						<td align="center">
							${financeEarnMap.inviteReward}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							借款成功总额：
						</td>
						<td align="center">
							${financeEarnMap.borrowAmount}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							借款管理费总额：
						</td>
						<td align="center">
							${financeEarnMap.borrowManageFee}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							借款利息总额：
						</td>
						<td align="center">
							${financeEarnMap.borrowInterestAmount}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							借款奖励总额：
						</td>
						<td align="center">
							${financeEarnMap.borrowRewardAmount}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							借款逾期罚息总额：
						</td>
						<td align="center">
							${financeEarnMap.borrowLateFI}
						</td>
					</tr>
			</tbody>
		</table>
</div>