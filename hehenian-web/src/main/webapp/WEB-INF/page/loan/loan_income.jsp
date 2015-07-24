<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>业绩查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<%@ include file="/include/includeJs.jsp"%>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
			.bigBoss  tbody td input{width: 140px;}
		</style>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100" height="28" class="main_alll_h2"><a href="getLoan.do">业务受理</a></td>
					<td width="100" height="28" class="main_alll_h2">&nbsp;<a href="loanTreatyQuery.do">合同签约</a></td>
					<td width="100" height="28" align="left" class="main_alll_h2"><a href="getManagerData.do">贷后管理</a></td>
					<td width="100" height="28" align="left" class="main_alll_h2"><a href="getIncomeManager.do">业绩查询</a></td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="0">
				<tr>
					<td style="width: 100px; height: 30px;" align="center"
						colspan="4">
						<font size="20px">恭喜你！</font>
						<br /><br />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<font size="4px">您已成功放款 ${map.successDs }单，放款金额：${map.borrowAmount }元 .<br />
						截止目前，客户还款金额：${map.hasPI }元，未还款金额为：${map.stillPI }元</font><br /><br /><br /><br />
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
					<font size="4px">实际收益&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber value=" ${map.loanExpectedEarnings }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber> 元</font>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
					<font size="4px">未来收益&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber value="${map.comingExpectedEarnings }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber> 元</font>
					</td>
				</tr>
		</table>
		</div>
	</div>
</body>
</html>
