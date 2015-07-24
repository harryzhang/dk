<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<style>
td {
	padding-left: 6px;
}
</style>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script>
	//审核
	function updateAssginment() {
		var id = $("#id").val();
		var status = $("input[name='status']:checked").val();
		var dsc = $("#dsc").val();
		param["paramMap.status"] = status;
		param["paramMap.id"] = id;
		param["paramMap.dsc"] = dsc;
		var size = $("input[name='status']:checked").val();
		if (status == null) {
			alert("请选择审核按钮");
			return null;
		}
		$.shovePost("updateUserReview.do", param, function(data) {
			if (data == "1") {
				alert("提交成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("提交失败");
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
		var id = "${map.publisher}";
		window.location.href = 'queryPerUserCreditAction.do?userId=' + id;
	}
</script>
</head>
<body>

	<!-- 
	<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
		<tr style="height: 25px">
			<td colspan="4" style="text-align: center;font-weight: bold;">借款信息</td>
		</tr>
		<tr style="height: 25px">
			<td style="width: 150px;" align="right">用户名：</td>
			<td style="width: 200px;">${ map.buserName}</td>
			<td align="right" style="width: 150px;">真实姓名：</td>
			<td style="width: 200px;">${map.realName}</td>
		</tr>
		<tr style="height: 25px">
			<td align="right">标的标题：</td>
			<td>${map.borrowTitle}</td>
			<td align="right">借款金额：</td>
			<td>${map.borrowAmount}</td>
		</tr>
		<tr style="height: 25px">
			<td align="right">借款期限：</td>
			<td>${map.deadlineTime}个月</td>
			<td align="right">年利率：</td>
			<td>${map.annualRate}</td>
		</tr>
		<tr style="height: 25px">
			<td align="right">借款用途：</td>
			<td>${map.moneyPurposes}</td>
			<td align="right">还款方式：</td>
			<td><s:if test="#request.map.paymentMode==1">按月等额本息还款</s:if> <s:if test="#request.mappaymentMode==2">按先息后本还款</s:if></td>
		</tr>
		<tr style="height: 25px">
			<td align="right">标的类型：</td>
			<td><s:if test="#request.map.borrowWay==1">薪金贷</s:if> <s:if test="#request.map.borrowWay==2">生意贷</s:if> <s:if test="#request.map.borrowWay==3">业主贷</s:if></td>
			<td align="right">投标奖励：</td>
			<td><s:if test="#request.map.excitationSum==-1">不设置奖励</s:if> <s:else>${map.excitationSum}</s:else></td>
		</tr>
		<tr style="height: 25px">
			<td align="right">最低投标金额：</td>
			<td>${map.minTenderedSum}</td>
			<td align="right">最高投标金额：</td>
			<td><s:if test="#request.map.excitationSum==-1">没有限制</s:if> <s:else>${map.maxTenderedSum}</s:else></td>
		</tr>
		<tr style="height: 25px">
			<td align="right">添加时间/IP：</td>
			<td>${map.publishTime} <br /> ${map.publishIP}</td>
			<td align="right">借款人认证审核查看：</td>
			<td><a id="queryMsg" onclick="queryMsg();"><span style="cursor: pointer;color:#3366CC;">查看</span> </a></td>
		</tr>
	</table>
	
	<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
		<tr class=gvItem>
			<th style="width: 100px;" scope="col">债权编号</th>
			<th style="width: 100px;" scope="col">用户名</th>
			<th style="width: 100px;" scope="col">真实姓名</th>
			<th style="width: 100px;" scope="col">投资金额</th>
			<th style="width: 100px;" scope="col">投资时间</th>
			<th style="width: 100px;" scope="col">投资比例</th>
		</tr>
		<s:if test="pageBean.page==null || pageBean.page.size==0">
			<tr class="gvItem" style="height: 25px;">
				<td colspan="6">暂无数据</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="pageBean.page" var="bean" status="s">
				<tr class="gvItem" style="height: 25px;">
					<td align="center">${bean.number}</td>
					<td align="center">${bean.username}</td>
					<td align="center">${bean.realName}</td>
					<td align="center">${bean.investAmount}</td>
					<td align="center"><s:date name="#bean.investTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="center">${bean.trad}</td>
				</tr>
			</s:iterator>
		</s:else>
	</table>
	 -->
	 
	 <table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
		<tr style="height: 25px">
			<td colspan="4" style="text-align: center;font-weight: bold;">债权信息</td>
		</tr>
		<tr style="height: 25px">
			<td style="width: 150px;" align="right">用户名：</td>
			<td style="width: 200px;">${ map.buserName}</td>
			<td align="right" style="width: 150px;">真实姓名：</td>
			<td style="width: 200px;">${map.realName}</td>
		</tr>
		<tr style="height: 25px">
			<td align="right">标的标题：</td>
			<td>${map.borrowTitle}</td>
			<td align="right">借款金额：</td>
			<td>${map.borrowAmount}</td>
		</tr>
		<tr style="height: 25px">
			<td align="right">借款期限：</td>
			<td>${map.deadlineTime}个月</td>
			<td align="right">年利率：</td>
			<td>${map.annualRate}</td>
		</tr>
		<tr style="height: 25px">
			<td align="right">借款用途：</td>
			<td>${map.moneyPurposes}</td>
			<td align="right">还款方式：</td>
			<td><s:if test="#request.map.paymentMode==1">按月等额本息还款</s:if> <s:if test="#request.mappaymentMode==2">按先息后本还款</s:if></td>
		</tr>
		<tr style="height: 25px">
			<td align="right">标的类型：</td>
			<td colspan="3"><s:if test="#request.map.borrowWay==1">薪金贷</s:if> <s:if test="#request.map.borrowWay==2">生意贷</s:if> <s:if test="#request.map.borrowWay==3">业主贷</s:if></td>
		</tr>	
	
	</table>
	 <table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
		<tr class=gvItem>
			<th style="width: 100px;" scope="col">期数</th>
			<th style="width: 100px;" scope="col">还款日期</th>
			<th style="width: 100px;" scope="col">应还本金</th>
			<th style="width: 100px;" scope="col">应还利息</th>
			<th style="width: 100px;" scope="col">应还本息</th>
			<th style="width: 100px;" scope="col">还款状态</th>
		</tr>
		<s:if test="pageBean.page==null || pageBean.page.size==0">
			<tr class="gvItem" style="height: 25px;">
				<td colspan="6">暂无数据</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="pageBean.page" var="bean" status="s">
				<tr class="gvItem" style="height: 25px;">
					<td align="center">${bean.repayPeriod}</td>
					<td align="center">${bean.repayDate}</td>
					<td align="center">${bean.recivedPrincipal}</td>
					<td align="center">${bean.recivedInterest}</td>
					<td align="center">${bean.recivedPrincipal+bean.recivedInterest}</td>
					<td align="center"><s:if test="#bean.repayStatus == 1">未偿还 </s:if><s:if test="#bean.repayStatus == 2">已偿还</s:if></td>
				</tr>
			</s:iterator>
		</s:else>
	</table>
</body>
</html>
