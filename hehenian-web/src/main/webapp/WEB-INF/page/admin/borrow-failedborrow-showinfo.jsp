<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>初审</title>
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script>
	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}
	//认证查看
	function queryMsg() {
		var id = '${map.publisher}';
		window.location.href = 'queryPerUserCreditAction.do?userId=' + id;
	}
</script>
</head>
<body>
	<input type="hidden" name="id" id="id" value="${map.id}"></input>
	<input type="hidden" name="borrowTitle" id="borrowTitle" value="${map.borrowTitle}"></input>
	<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
		<tr style="height: 30px">
			<td colspan="4" style="text-align: center;">借款信息</td>
		</tr>
		<tr>
			<td style="width: 150px;" align="right">用户名：</td>
			<td style="width: 200px;">${ map.username}</td>
			<td align="right" style="width: 150px;">真实姓名：</td>
			<td style="width: 200px;">${map.realName}</td>
		</tr>
		<tr>
			<td align="right">标的标题：</td>
			<td>${map.borrowTitle}</td>
			<td align="right">借款金额：</td>
			<td>${map.borrowAmount}</td>
		</tr>
		<tr>
			<td align="right">借款期限：</td>
			<td>${map.deadline}</td>
			<td align="right">年利率：</td>
			<td>${map.annualRate}</td>
		</tr>
		<tr>
			<td align="right">借款用途：</td>
			<td>${map.moneyPurposes}</td>
			<td align="right">还款方式：</td>
			<td><s:if test="#request.map.paymentMode==1">按月等额本息还款</s:if> <s:if test="#request.map.paymentMode==2">按先息后本还款</s:if></td>
		</tr>
		<tr>
			<td align="right">标的类型：</td>
			<td><s:if test="#request.map.borrowWay=='1'">薪金贷</s:if> <s:if test="#request.map.borrowWay==2">生意贷</s:if> <s:if test="#request.map.borrowWay==3">普通借款</s:if> <s:if
					test="#request.map.borrowWay==4">实地考察借款 </s:if> <s:if test="#request.map.borrowWay==5">机构担保借款</s:if> <s:if test="#request.map.borrowWay==6">流转标</s:if></td>
			<td align="right">投标奖励：</td>
			<td>
				<!--<s:if test="#request.map.excitationType==1">不设置奖励</s:if>
					<s:if test="#request.map.excitationType==2">按固定比例金额分摊</s:if>
					<s:if test="#request.map.excitationType==3">按投标金额比例</s:if>--> ${map.excitationSum}</td>
		</tr>
		<tr>
			<td align="right">最低投标金额：</td>
			<td>${map.minTenderedSum}</td>
			<td align="right">最高投标金额：</td>
			<td>${map.maxTenderedSum}</td>
		</tr>
		<tr>
			<td align="right">添加时间/IP：</td>
			<td>${map.publishTime} <br /> ${map.publishIP}</td>
			<td align="right" nowrap="nowrap">借款人认证审核查看：</td>
			<td onclick="queryMsg()" style="cursor: pointer;">查看</td>
		</tr>
	</table>
	<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
		<tr style="height: 30px">
			<td colspan="4" style="text-align: left;">&nbsp; 审核</td>
		</tr>
		<tr>
			<td colspan="4">
				<table style="width: 100%" border="0">
					<tr>
						<td align="right">审核状态：</td>
						<td align="left"><input type="radio" id="statusYes" name="borrowStatus" value="8" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;"
							<s:if test="#request.map.borrowStatus==8">checked="checked"</s:if> /> 通过</td>
						<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" id="statusNo" name="borrowStatus" value="-1" disabled="disabled"
							style="vertical-align: text-bottom; margin-bottom: -2px;" <s:if test="#request.map.borrowStatus==6">checked="checked"</s:if> /> 未通过 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
							type="radio" id="statusNo" name="borrowStatus1" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;"
							<s:if test="#request.map.borrowStatus==10">checked="checked"</s:if> /> 复审未通过</td>
					</tr>
					<tr>
						<td align="right">站内信通知：</td>
						<td align="left" colspan="2"><textarea name="mailContent" id="mailContent" cols="50" rows="3" readonly="readonly" style="font-size: 14px;">
									${map.mailContent}
								</textarea></td>
					</tr>
					<tr>
						<td align="right">风控意见：</td>
						<td align="left" colspan="2"><textarea name="windControl" id="windControl" cols="50" rows="3" readonly="readonly" style="font-size: 14px;">
									${map.windControl}
								</textarea></td>
					</tr>
					<tr>
						<td colspan="3" align="center"><input type="button" style="background: #666666;" value="确定" onclick="closeMthodes();" /></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>
