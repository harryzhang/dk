<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<link href="css/hhncss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
	$(function() {
		$("#b_cancel").click(function() {
			window.parent.jBox.close();
		})

		$("#b_repay").click(
				function() {
					if ($("#payId").val() != '')
						var repayDate = $("#repayDate").val();
					var consultFee = $("#consultFee").val();
					var stillInterest = $("#stillInterest").val();
					window.location.href = "queryMyPayData.do?payId=" + $("#payId").val() + "&repayDate=" + repayDate + "&consultFee=" + consultFee
							+ "&stillInterest=" + stillInterest;
				})
	});
</script>
</head>
<body>
	<div class="hk_tc_ah" style="display: block;margin-left: -46px;margin-top: 90px;">
		<h2 style="margin-top: 0px;">
			<span>还款</span>
		</h2>
		<input type="hidden" value="${curr.payId }" id="payId" />
		<table width="550" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" style="margin-top: 5px;font-size: 12px;">
			<tr height="30" align="center">
				<td bgcolor="#ffffff" width="25%"><span>用户名：</span></td>
				<td bgcolor="#ffffff" width="25%"><strong>${curr.username }</strong></td>
				<td bgcolor="#ffffff" width="25%"><span>真实姓名：</span></td>
				<td bgcolor="#ffffff" width="25%"><strong>${curr.realName }</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>借款标题：</span></td>
				<td bgcolor="#ffffff"><strong>${curr.borrowTitle }</strong></td>
				<td bgcolor="#ffffff"><span>借款金额：</span></td>
				<td bgcolor="#ffffff"><strong>${curr.borrowAmount }</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>借款期限：</span></td>
				<td bgcolor="#ffffff"><strong>${curr.deadline }</strong></td>
				<td bgcolor="#ffffff"><span>年利率：</span></td>
				<td bgcolor="#ffffff"><strong>${curr.annualRate }%</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>第几期：</span>
				</td>
				<td bgcolor="#ffffff"><strong>${curr.repayPeriod }</strong> <input id="repayPeriod" type="hidden" value="${curr.repayPeriod }" /></td>
				<td bgcolor="#ffffff"><span>应还款日期：</span>
				</td>
				<td bgcolor="#ffffff"><strong>${curr.repayDate }</strong> <input id="repayDate" type="hidden" value="${curr.repayDate }" /></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>本期应还本息：</span></td>
				<td bgcolor="#ffffff"><strong>${curr.stillPI }</strong></td>
				<td bgcolor="#ffffff"><span>其中利息为：</span></td>
				<td bgcolor="#ffffff"><strong>${curr.stillInterest }</strong> <input id="stillInterest" type="hidden" value="${curr.stillInterest }" /></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>逾期罚款：</span></td>
				<td bgcolor="#ffffff"><strong>${curr.lateFI }</strong></td>
				<td bgcolor="#ffffff"><span>逾期天数：</span></td>
				<td bgcolor="#ffffff"><strong>${curr.lateDay }</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>咨询费：</span></td>
				<td bgcolor="#ffffff"><strong>${curr.consultFee }</strong> <input id="consultFee" type="hidden" value="${curr.consultFee }" /></td>
				<td bgcolor="#ffffff"><span>还款状态：</span></td>
				<td bgcolor="#ffffff"><strong> <c:if test="${curr.repayStatus==1 }">未还款</c:if> <c:if test="${curr.repayStatus==2 }">已还款</c:if> <c:if test="${curr.repayStatus==3 }">--</c:if>
				</strong></td>
			</tr>
		</table>
		<table width="550" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" style="font-size: 12px;">
			<tr height="30">
				<td colspan="8" bgcolor="#faf9f9"><strong style="margin-left:15px;">还款计划表</strong></td>
			</tr>
			<tr height="27" align="center">
				<td bgcolor="#ffffff" width="50">期数</td>
				<td bgcolor="#ffffff" width="90">还款日期</td>
				<td bgcolor="#ffffff" width="90">总还款额</td>
				<td bgcolor="#ffffff" width="60">应还罚息</td>
				<td bgcolor="#ffffff" width="60">应还总额</td>
				<td bgcolor="#ffffff" width="60">还款状态</td>
				<td bgcolor="#ffffff" width="90">实际还款日</td>
			</tr>
			<s:if test="#request.record.size==0">
				<tr height="30">
					<td colspan="8" bgcolor="#faf9f9" align="center"><strong style="margin-left:15px;">尚未成功放款,暂无记录</strong></td>
				</tr>
			</s:if>
			<s:else>
				<s:iterator value="#request.record" var="bean">
					<tr height="27" align="center">
						<td bgcolor="#ffffff" width="50">${bean.repayPeriod }</td>
						<td bgcolor="#ffffff" width="90"><s:date name="#bean.repayDate" format="yyyy-MM-dd" /></td>
						<td bgcolor="#ffffff" width="90">${bean.stillPI }</td>
						<td bgcolor="#ffffff" width="60">${bean.lateFI }</td>
						<td bgcolor="#ffffff" width="60">${bean.stillPI+bean.lateFI }</td>
						<td bgcolor="#ffffff" width="90">
							<s:if test="#bean.isLate == 1 && #bean.repayStatus == 1">未还</s:if> 
											<s:elseif test="#bean.isLate == 1 && #bean.repayStatus == 2">已还</s:elseif>
											<s:elseif test="#bean.isLate == 2 && #bean.isWebRepay == 1">逾期</s:elseif>
											<s:elseif test="#bean.isLate == 2 && #bean.isWebRepay == 2">代偿</s:elseif>
											<s:else>已还</s:else></td>
						
						<td bgcolor="#ffffff" width="60">${bean.realRepayDate }</td>
					</tr>
				</s:iterator>
			</s:else>
		</table>
		<p>
			<input type="button" value="取消" id="b_cancel" /> <input type="button" value="还款" id="b_repay"
				<s:if test="#request.curr.repayStatus!=1">disabled="disabled"  style="background: #cccccc"</s:if> />
		</p>
	</div>
</body>
</html>
