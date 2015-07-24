<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>复审</title>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<style>
.hhn {
	border: 1px #ccc solid;
}
</style>
<script>
	//审核
	function updateRecheck() {
		var id = $("#id").val();
		var borrowStatus = $("input[name='borrowStatus']:checked").val();
		var windControl = $("#windControl").val();
		var mailContent = $("#mailContent").val();
		var borrowTitle = $("#borrowTitle").val();
		var reciver = $("#reciver").val();
		var minTenderedSum = $("#minTenderedSum").val();
		var maxTenderedSum = $("#maxTenderedSum").val();
		var borrowAmount = $("#hhnmount").val();
		if (isNaN(borrowAmount) || borrowAmount < 0) {
			alert("借款金额非法");
			return null;
		}
		var deadline = $("#hhnline").val();
		if (isNaN(deadline) || deadline < 0) {
			alert("借款期限非法");
			return null;
		}
		var annualRate = $("#hhnanna").val();
		if (isNaN(annualRate) || annualRate < 0) {
			alert("借款年利率非法");
			return null;
		}
		var userId = $("#userId").val();
		param["paramMap.borrowStatus"] = borrowStatus;
		param["paramMap.id"] = id;
		param["paramMap.windControl"] = windControl;
		param["paramMap.mailContent"] = mailContent;
		param["paramMap.borrowTitle"] = borrowTitle;
		param["paramMap.reciver"] = reciver;
		param["paramMap.userIds"] = userId;
		param["paramMap.minTenderedSum"] = minTenderedSum;
		param["paramMap.maxTenderedSum"] = maxTenderedSum;
		param["paramMap.borrowAmount"] = borrowAmount;
		param["paramMap.deadline"] = deadline;
		param["paramMap.annualRate"] = annualRate;
		param["paramMap.raiseTerm"] = "${map.raiseTerm}";
		param["paramMap.borrowGroup"] = $("#borrowGroup").val();
		if (borrowStatus == null && borrowStatus != "9") {
			alert("请选择审核按钮");
			return null;
		}
		if (mailContent == null && borrowStatus != "9") {
			alert("请填写审核内容");
			return null;
		}
		if (minTenderedSum == null || minTenderedSum == "" && borrowStatus != "9") {
			alert("请选择最小投标金额");
			return null;
		}
		$.shovePost("updateRecheck.do", param, function(data) {
			if (data == "1") {
				alert("审核成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("审核失败");
				return;
			}
		});
	}

	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}

	function station() {
		var borrowStatus = $("input[name='borrowStatus']:checked").val();
		var borrowTitle = $("#borrowTitle").val();
		if (borrowStatus == 6) {
			$("#mailContent").attr("value", "您申请的的借款[" + borrowTitle + "]未通过复审.审核状况:复审未通过");
		}
		if (borrowStatus == 8) {
			$("#mailContent").attr("value", "您申请的的借款[" + borrowTitle + "]通过了复审.审核状况:复审通过");
		}
		if (borrowStatus == 9) {
			$("#mailContent").attr("value", "发回初审");
		}
	}

	//认证查看
	function queryMsg() {
		var id = '${map.publisher}';
		window.location.href = 'queryPerUserCreditAction.do?userId=' + id;
	}
</script>
</head>
<body>
	<input type="hidden" name="id" id="id" value="${map.id}"> <input type="hidden" name="userId" id="userId" value="${map.userId}"> <input type="hidden" name="borrowTitle"
			id="borrowTitle" value="${map.borrowTitle}"></input> <input type="hidden" name="reciver" id="reciver" value="${map.publisher}"></input>
			<table style="margin: 30px;margin-top:15px;margin-bottom:-20px; width: 90%" border="1">
				<tr style="height: 30px">
					<td colspan="4" style="text-align: center;">借款信息</td>
				</tr>
				<tr>
					<td style="width: 150px;" align="right">用户名：</td>
					<td style="width: 200px;">${map.username}</td>
					<td align="right" style="width: 150px;">真实姓名：</td>
					<td style="width: 200px;">${map.realName}</td>
				</tr>
				<tr>
					<td align="right">标的标题：</td>
					<td>${map.borrowTitle}</td>
					<td align="right">借款金额：</td>
					<td><input id="hhnmount" class="hhn" value="${map.borrowAmount}" /></td>
				</tr>
				<tr>
					<td align="right">借款期限：</td>
					<td><input id="hhnline" class="hhn" value="${map.deadline}" /></td>
					<td align="right">年利率：</td>
					<td><input id="hhnanna" class="hhn" value="${map.annualRate}" /></td>
				</tr>
				<tr>
					<td align="right">借款用途：</td>
					<td>${map.borrowTitle}</td>
					<td align="right">还款方式：</td>
					<td><s:if test="#request.map.paymentMode == 1">按月等额本息还款</s:if>
					</td>
				</tr>
				<tr>
					<td align="right">标的类型：</td>
					<td><s:if test="#request.map.borrowWay == 1">薪金贷</s:if> <s:elseif test="#request.map.borrowWay == 2">生意贷 </s:elseif> <s:elseif test="#request.map.borrowWay == 3">业主贷</s:elseif>
						<s:else>其他借款</s:else>
					</td>
					<%-- <td align="right">投标奖励：</td>
					<td>${map.excitationSum}</td> --%>
					<td align="right">所属专区：</td>
					<td>
					<select id="borrowGroup">
					<option value="0">普通区</option>
					<option value="1" ${map.borrowGroup==1?'selected':'' }>花样年专区</option>
					<option value="2" ${map.borrowGroup==2?'selected':'' }>花样会专区</option>
					<option value="2" ${map.borrowGroup==3?'selected':'' }>福泰年专区</option>
					</select>
					</td> 
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
					<td><a id="queryMsg" onclick="queryMsg();"><span style="cursor: pointer;color:#3366CC;">查看</span> </a>
					</td>
				</tr>
			</table>
			<table style="margin: 30px; width: 90%" border="1">
				<tr style="height: 30px">
					<td colspan="4" style="text-align: left;">&nbsp; 审核</td>
				</tr>
				<tr>
					<td colspan="4">
						<table style="width: 100%" border="0">
							<tr>
								<td align="right">复审状态：</td>
								<td align="left"><input type="radio" id="statusYes" name="borrowStatus" value="8" style="vertical-align: text-bottom; margin-bottom: -2px;" onclick="station();" /> 通过</td>
								<td align="left"><input type="radio" id="statusNo" name="borrowStatus" value="6" style="vertical-align: text-bottom; margin-bottom: -2px;" onclick="station();" /> 未通过 <s:if
										test="#request.map.source==0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
										<input type="radio" id="statusAgain" name="borrowStatus" value="9" checked="checked" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />小贷公司导入用户，没有初审</s:if>
									<s:elseif test="#request.map.source==2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
										<input type="radio" id="statusAgain" name="borrowStatus" checked="checked" value="9" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />彩生活用户，没有初审</s:elseif> <s:else>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" id="statusAgain" name="borrowStatus" value="9" style="vertical-align: text-bottom; margin-bottom: -2px;" onclick="station();" />重新审核
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 初审人：${map.firstTrial}
									</s:else>
								</td>
							</tr>
							<tr>
								<td class="f66" align="right" height="36px" colspan="2" nowrap="nowrap">&nbsp;最低投标金额： <select id="minTenderedSum" name="minTenderedSum">
										<option value="" selected="selected">请选择</option>
										<option value="100" <s:if test="#request.map.minTenderedSum==100">selected="selected"</s:if>>100</option>
										<option value="200" <s:if test="#request.map.minTenderedSum==200">selected="selected"</s:if>>200</option>
										<option value="500" <s:if test="#request.map.minTenderedSum==500">selected="selected"</s:if>>500</option>
										<option value="1000" <s:if test="#request.map.minTenderedSum==1000">selected="selected"</s:if>>1000</option>
								</select>
								</td>
								<td class="f66" align="left" height="36px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最高投标金额： <select id="maxTenderedSum" name="maxTenderedSum">
										<option value="-1" selected="selected">没有限制</option>
										<option value="100" <s:if test="#request.map.maxTenderedSum==100">selected="selected"</s:if>>100</option>
										<option value="500" <s:if test="#request.map.maxTenderedSum==500">selected="selected"</s:if>>500</option>
										<option value="1000" <s:if test="#request.map.maxTenderedSum==1000">selected="selected"</s:if>>1000</option>
										<option value="10000" <s:if test="#request.map.maxTenderedSum==10000">selected="selected"</s:if>>10000</option>
								</select>
								</td>
							</tr>
							<tr>
								<td align="right">站内信通知：</td>
								<td align="left" colspan="2"><textarea name="mailContent" id="mailContent" cols="50" readonly="readonly" rows="3">
								</textarea>
								</td>
							</tr>
							<tr>
								<td align="right">风控意见：</td>
								<td align="left" colspan="2"><textarea name="windControl" id="windControl" cols="50" rows="3">${map.windControl}</textarea>
								</td>
							</tr>
							<tr>
								<td colspan="3" align="center"><input type="button" style="background: #666666;" value="确定" onclick="updateRecheck();" /> <input type="button" style="background: #666666;"
									value="取消" onclick="closeMthodes();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
</body>
</html>
