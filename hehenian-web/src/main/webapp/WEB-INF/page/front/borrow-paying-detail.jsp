<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
	<div class="hk_tc_ah">
		<h2>
			<span>还款</span><i>X</i>
		</h2>
		<table width="550" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5">
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>用户名：</span></td>
				<td bgcolor="#ffffff"><strong>moxia</strong></td>
				<td bgcolor="#ffffff"><span>真实姓名：</span></td>
				<td bgcolor="#ffffff"><strong>莫下</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>借款标题：</span></td>
				<td bgcolor="#ffffff"><strong>资金周转</strong></td>
				<td bgcolor="#ffffff"><span>借款金额：</span></td>
				<td bgcolor="#ffffff"><strong>200,000.00</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>借款期限：</span></td>
				<td bgcolor="#ffffff"><strong>6个月</strong></td>
				<td bgcolor="#ffffff"><span>年利率：</span></td>
				<td bgcolor="#ffffff"><strong>24%</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>第几期：</span></td>
				<td bgcolor="#ffffff"><strong>5/6</strong></td>
				<td bgcolor="#ffffff"><span>应还款日期：</span></td>
				<td bgcolor="#ffffff"><strong>2013年3月3日</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>本期应还本息：</span></td>
				<td bgcolor="#ffffff"><strong>1120</strong></td>
				<td bgcolor="#ffffff"><span>其中利息为：</span></td>
				<td bgcolor="#ffffff"><strong>120</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>逾期罚款：</span></td>
				<td bgcolor="#ffffff"><strong>0</strong></td>
				<td bgcolor="#ffffff"><span>逾期天数：</span></td>
				<td bgcolor="#ffffff"><strong>0</strong></td>
			</tr>
			<tr height="30" align="center">
				<td bgcolor="#ffffff"><span>实际还款日期：</span></td>
				<td bgcolor="#ffffff"><strong>2013年8月29日</strong></td>
				<td bgcolor="#ffffff"><span>还款状态：</span></td>
				<td bgcolor="#ffffff"><strong>未还款</strong></td>
			</tr>
		</table>
		<table width="550" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5">
			<tr height="30">
				<td colspan="8" bgcolor="#faf9f9"><strong style="margin-left:15px;">已还记录</strong></td>
			</tr>
			<tr height="27" align="center">
				<td bgcolor="#ffffff" width="50">序号</td>
				<td bgcolor="#ffffff" width="50">期数</td>
				<td bgcolor="#ffffff" width="90">应还款日期</td>
				<td bgcolor="#ffffff" width="90">本期应还本息</td>
				<td bgcolor="#ffffff" width="60">利息</td>
				<td bgcolor="#ffffff" width="60">逾期罚款</td>
				<td bgcolor="#ffffff" width="60">逾期天数</td>
				<td bgcolor="#ffffff" width="90">实际还款日期</td>
			</tr>
			<tr height="27" align="center">
				<td bgcolor="#ffffff" width="50">4</td>
				<td bgcolor="#ffffff" width="50">4/6</td>
				<td bgcolor="#ffffff" width="90">2013.1.2</td>
				<td bgcolor="#ffffff" width="90">1120</td>
				<td bgcolor="#ffffff" width="60">120</td>
				<td bgcolor="#ffffff" width="60">0</td>
				<td bgcolor="#ffffff" width="60">0</td>
				<td bgcolor="#ffffff" width="90">2013.1.2</td>
			</tr>
			<tr height="27" align="center">
				<td bgcolor="#ffffff" width="50">3</td>
				<td bgcolor="#ffffff" width="50">3/6</td>
				<td bgcolor="#ffffff" width="90">2013.1.2</td>
				<td bgcolor="#ffffff" width="90">1120</td>
				<td bgcolor="#ffffff" width="60">120</td>
				<td bgcolor="#ffffff" width="60">0</td>
				<td bgcolor="#ffffff" width="60">0</td>
				<td bgcolor="#ffffff" width="90">2013.1.2</td>
			</tr>
			<tr height="27" align="center">
				<td bgcolor="#ffffff" width="50">2</td>
				<td bgcolor="#ffffff" width="50">2/6</td>
				<td bgcolor="#ffffff" width="90">2013.1.2</td>
				<td bgcolor="#ffffff" width="90">1120</td>
				<td bgcolor="#ffffff" width="60">120</td>
				<td bgcolor="#ffffff" width="60">0</td>
				<td bgcolor="#ffffff" width="60">0</td>
				<td bgcolor="#ffffff" width="90">2013.1.2</td>
			</tr>
			<tr height="27" align="center">
				<td bgcolor="#ffffff" width="50">1</td>
				<td bgcolor="#ffffff" width="50">1/6</td>
				<td bgcolor="#ffffff" width="90">2013.1.2</td>
				<td bgcolor="#ffffff" width="90">1120</td>
				<td bgcolor="#ffffff" width="60">120</td>
				<td bgcolor="#ffffff" width="60">0</td>
				<td bgcolor="#ffffff" width="60">0</td>
				<td bgcolor="#ffffff" width="90">2013.1.2</td>
			</tr>
		</table>
		<p>
			<input type="button" value="取消" /><input type="button" value="还款" />
		</p>
	</div>
</body>
</html>
