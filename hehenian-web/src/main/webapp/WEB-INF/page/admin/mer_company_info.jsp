<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<style type="text/css">
* {
	font-family: tahoma;
	font-size: 14px;
}

td {
	padding: 3px;
}
</style>
</head>
<body>
	<div id="right">
		<div style="padding: 15px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" class="main_alll_h2"><a href="companyInfo.do">企业信息</a></td>
						<td width="2">&nbsp;</td>
					</tr>
				</table>
			</div>
			<div style="padding-top: 30px;padding-left:50px;  width: 1120px;  background-color: #fff;">
				<table width="50%" border="0" style="margin: 5px;border-collapse: collapse;">
					<tr>
						<td align="right" width="25%">可用余额：</td>
						<td width="25%">&nbsp;</td>
						<td align="right" width="25%">资产总额：</td>
						<td width="25%">${map.acctBal }</td>
					</tr>
					<tr>
						<td align="right">${map.usable}</td>
						<td>&nbsp;</td>
						<td align="right">冻结总额：</td>
						<td>${map.free}</td>
					</tr>
					<tr>
						<td colspan="2" rowspan="2" align="center"><input type="button" id="recharge" value="充值" /> <input id="withdraw" type="button" value="提现" />
						</td>
						<td align="right">充值总额：</td>
						<td>${map.rechargeTotal }</td>
					</tr>
					<tr>
						<td align="right">提现总额：</td>
						<td>${map.withdrawTotal }</td>
					</tr>
					<tr height="20"></tr>
					<tr>
						<%--						<td></td>--%>
						<td align="right">企业名称：</td>
						<td colspan="3">${map.name }</td>
					</tr>
					<tr height="5"></tr>
					<tr>
						<%--						<td></td>--%>
						<td align="right">营业执照编号：</td>
						<td colspan="3">${map.busiCode }</td>
					</tr>
					<tr height="20"></tr>
					<tr>
						<td colspan="2" align="left"><h2>提现银行：</h2></td>
						<td align="right"><input type="button" value="添加" id="bindCard"></td>
						<td></td>
					</tr>
				</table>

				<table width="38%" border="1" style="margin: 5px;border-collapse: collapse;">
					<tr>
						<th scope="col">开户行</th>
						<th scope="col">银行卡号</th>
						<th scope="col">添加时间</th>
					</tr>
					<s:if test="pageBean.page==null || pageBean.page.size==0">
						<tr align="center" class="gvItem">
							<td colspan="15">暂无数据</td>
						</tr>
					</s:if>
					<s:else>
						<s:set name="counts" value="#request.pageNum" />
						<s:iterator value="pageBean.page" var="bean" status="s">
							<tr>
								<td align="center">${bean.bankName}</td>
								<td align="center">${bean.cardNo}</td>
								<td align="center"><s:date name="#bean.commitTime" format="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
						</s:iterator>
					</s:else>
				</table>
			</div>
		</div>
	</div>
	<div id="infos" style="display: none;"></div>

	<%--充值div--%>
	<div id="reDiv" style="z-index: 88;position: absolute;top: 0px;left: 0px;width:100%;height: 100%;display: none;">
		<div style="z-index: 99;position: absolute;top: 100px;left: 280px;width: 500px;height: 300px;background-color: #F1FEDD;">
			<table style="width: 50%;margin: 50px auto 0px auto;" border="0">
				<tr>
					<td align="right" width="40%">充值金额：</td>
					<td align="left"><input id="money" type="text" style="width: 100px;" /> 元</td>
				</tr>
				<tr>
					<td align="center" colspan="2"><input type="radio" style="vertical-align: middle;vertical-align: middle;margin-right: 10px;" name="types"
							checked="checked" value="D" /> 借记卡 <input style="vertical-align: middle;margin-left: 10px;" type="radio" name="types" value="C" />
							信用卡</td>
				</tr>
				<tr height="40"></tr>
				<tr>
					<td align="center" colspan="2"><input type="button" value="确定" id="cz" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		$("#recharge").click(function() {
			$("#reDiv").show();
		});
		
		$("#withdraw").click(function() {
			withdraw();
		});

		$("#cz").click(function() {
			recharge();
		});

		$("#bindCard").click(function() {
			param = {};
			param["from"] = "company";
			$.post("bindCard.do", $.param(param), function(data) {
				if (data.length < 30) {
					alert(data);
					return;
				}
				$("#infos").html(data);
			});
		});

	});
	
	function withdraw(){
		var money = prompt("请输入提现金额:", "");
		if(isNaN(money)){
			alert("金额非法");
			return false;
		}
		money = Number(money);
		if (money < 0) {
			alert("金额非法");
			return false;
		}
		param = {};
		param["money"] = money;
		$.post("comWithdraw.do", $.param(param), function(data) {
			if (data.length < 30) {
				alert(data);
				return;
			}
			$("#infos").html(data);
		});
	}
	
	function recharge(){
		var money = $("#money").val();
		if (isNaN(money)) {
			alert("金额非法");
			return false;
		}
		money = Number(money);
		if (money < 0) {
			alert("金额非法");
			return false;
		}
		var types = $("input[name=types]:checked").val();
		param = {};
		param["types"] = types;
		param["money"] = money;
		$.post("comRecharge.do", $.param(param), function(data) {
			if (data.length < 30) {
				alert(data);
				return;
			}
			$("#reDiv").hide();
			$("#infos").html(data);
		});
	}
</script>
</html>
