<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>待发布</title>
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script>
	//发布
	function updateReleased() {
		var id = $("#id").val();
		param["paramMap.id"] = id;
		$.shovePost("updateReleased.do", param, function(data) {
			if (data == "1") {
				alert("发布成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("发布失败");
				return;
			}
		});
	}

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
	<input type="hidden" name="id" id="id" value="${map.id}"> <input type="hidden" name="borrowTitle" id="borrowTitle" value="${map.borrowTitle}"></input> <input type="hidden"
		name="reciver" id="reciver" value="${map.publisher}"></input>
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
					<td><s:if test="#request.map.paymentMode==1">按月等额本息还款</s:if></td>
			</tr>
			<tr>
				<td align="right">标的类型：</td>
				<td><s:if test="#request.map.borrowWay=='1'">薪金贷</s:if> <s:if test="#request.map.borrowWay==2">生意贷 </s:if> <s:if test="#request.map.borrowWay==3">业主贷</s:if></td>
				<td align="right">投标奖励：</td>
				<td><s:if test="#request.map.excitationSum==-1">不设置奖励</s:if> <s:else>${map.excitationSum}</s:else></td>
			</tr>
			<tr>
				<td align="right">最低投标金额：</td>
				<td>${map.minTenderedSum}</td>
				<td align="right">最高投标金额：</td>
				<td><s:if test="#request.map.maxTenderedSum == -1">没有限制</s:if> <s:else>
						${map.maxTenderedSum}
					</s:else></td>
			</tr>
			<tr>
				<td align="right">添加时间/IP：</td>
				<td>${map.publishTime} <br/>${map.publishIP} 
				</td>
				<td align="right" nowrap="nowrap">借款人认证审核查看：</td>
				<td><a id="queryMsg" onclick="queryMsg();"><span style="cursor: pointer;color:#3366CC;">查看</span>
				</a></td>
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
							<td align="right">初审状态：</td>
							<td align="left"><s:if test="#request.map.source==1">
									<input type="radio" id="statusYes" name="borrowStatus" value="9" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" checked="checked" />
									通过&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" id="statusNo" name="borrowStatus" value="-1" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />
									未通过&nbsp;&nbsp;&nbsp;&nbsp;初审人：${map.firstTrial}
									</s:if> <s:if test="#request.map.source==3">
									<input type="radio" id="statusYes" name="borrowStatus" value="9" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" checked="checked" />
									通过&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" id="statusNo" name="borrowStatus" value="-1" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />
									未通过&nbsp;&nbsp;&nbsp;&nbsp;初审人：${map.firstTrial}
									</s:if></td>
							<td align="left"><s:if test="#request.map.source==0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" id="statusAgain" name="borrowStatus" value="10" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />小贷公司导入用户，跳过初审</s:if> <s:if
									test="#request.map.source==2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" id="statusAgain" name="borrowStatus" value="10" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />彩生活用户，跳过初审</s:if></td>
						</tr>
						<tr>
							<td align="right">初审说明：</td>
							<td align="left" colspan="2"><s:if test="#request.map.source==1">
									<textarea name="firsttrialMailContent" id="firsttrialMailContent" cols="50" readonly="readonly" rows="3">用户：${map.username}的借款[${map.borrowTitle}]通过了初审.初审状态：初审通过
										</textarea>
								</s:if> <s:if test="#request.map.source==3">
									<textarea name="firsttrialMailContent" id="firsttrialMailContent" cols="50" readonly="readonly" rows="3">用户：${map.username}的借款[${map.borrowTitle}]通过了初审.初审状态：初审通过
										</textarea>
								</s:if> <s:if test="#request.map.source==0">
									<textarea name="firsttrialMailContent" id="firsttrialMailContent" cols="50" readonly="readonly" rows="3">无
										</textarea>
								</s:if> <s:if test="#request.map.source==2">
									<textarea name="firsttrialMailContent" id="firsttrialMailContent" cols="50" readonly="readonly" rows="3">无
										</textarea>
								</s:if></td>
						</tr>
						<tr>
							<td align="right">复审状态：</td>
							<td align="left"><input type="radio" id="statusYes" name="borrowStatusr" value="7" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" checked="checked" />
								通过&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" id="statusNo" name="borrowStatusr" value="6" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />
								未通过&nbsp;&nbsp;&nbsp;&nbsp;复审人：${map.recheck}</td>
						</tr>
						<tr>
							<td align="right">复审说明：</td>
							<td align="left" colspan="2"><textarea name="echeckMailContent" id="echeckMailContent" cols="50" readonly="readonly" rows="3">用户：${map.username}的借款[${map.borrowTitle}]通过了复审.可以发布</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="3" align="center"><input type="button" style="background: #666666;" value="发布" onclick="updateReleased();" /> <input type="button" style="background: #666666;"
								value="取消" onclick="closeMthodes();" /></td>
						</tr>
					</table></td>
			</tr>
		</table>
</body>
</html>
