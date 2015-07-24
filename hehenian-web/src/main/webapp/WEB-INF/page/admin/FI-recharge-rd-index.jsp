<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>财务管理-充值管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript"
	src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript">
	$(function() {
		param["pageBean.pageNum"] = 1;
		initListInfo(param);
		$("#bt_search").click(function() {
			param["pageBean.pageNum"] = 1;
			initListInfo(param);
		});

		$("#excel").click(function() {
			var userId = $("#userId").val();
			window.location.href = "exportBackCashSimple.do?userId=" + userId;
		});
	});

	function initListInfo(param) {
		param["paramMap.userId"] = $("#userId").val();
		param["paramMap.userName"] = $("#userName").val();
		param["paramMap.checkUser"] = $("#checkUser").val();
		param["paramMap.startTime"] = $("#startTime").val();
		param["paramMap.endTime"] = $("#endTime").val();
		$.shovePost("queryBackCashList.do", param, initCallBack);
	}

	function initCallBack(data) {
		$("#dataInfo").html(data);
	}
	

	function addRecharge() {
		var userId = $('#userId').val();
		$.jBox("iframe:addBackRechargeInit.do?userId=" + userId, {
			title : "奖励充值页面",
			width : 500,
			height : 400,
			buttons:{}
		});
	}

	function addUserRecharge(userId) {
		//var userId = $('#userId').val();
		$.jBox("iframe:addBackUserRechargeInit.do?userId=" + userId, {
			title : "商户无卡代扣页面",
			width : 500,
			height : 400,
			buttons:{}
		});
	}
	
	function addWithdraw() {
		var userId = $('#userId').val();
		$.jBox("iframe:addBackWithdrawInit.do?userId=" + userId, {
			title : "扣费页面",
			width : 500,
			height : 400,
			buttons:{}
		});
	}

	function show(id) {
		$.jBox("iframe:queryR_WShow.do?rwId=" + id, {
			title : "用户信息详情",
			width : 500,
			height : 400,
			buttons : {
				'确定' : true
			}
		});
	}
	
	function closeJbox(){
		window.jBox.close();
		window.location.reload();
	}
	
</script>
</head>
<body>
	<input type="hidden" id="userId" value="${userId }" />
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="120" class="main_alll_h2"><a
							href="javascript:void(0)">商户无卡代扣</a>
						</td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="80%" height="36px">
										用户名：&nbsp;&nbsp;
										<input style="width: 100px" id="userName"
											name="paramMap.username" type="text" />
										&nbsp;&nbsp; 操作员：&nbsp;&nbsp;
										<input style="width: 100px" id="checkUser"
											name="paramMap.checkUser" type="text" />
										&nbsp;&nbsp; 操作时间：
										<input id="startTime" name="paramMap.startTime" type="text"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" />
										&nbsp;&nbsp; 到
										<input id="endTime" name="paramMap.endTime" type="text"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" />
<%--										&nbsp;&nbsp; 类型：--%>
<%--										<s:select id="type" list="operateType" name="paramMap.type"--%>
<%--											listKey="typeId" listValue="tvalue" headerKey="-100"--%>
<%--											headerValue="--请选择--" />--%>
										&nbsp;&nbsp;
										<input id="bt_search" type="button" value="搜 索" />
										&nbsp;&nbsp;
										<input id="excel" type="button" value="导出EXCEL"
											name="btnExportExcel" />
									</td>
								</tr>
							</tbody>
						</table>
					
<%--					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"--%>
<%--						width="100%" border="0">--%>
<%--						<tbody>--%>
<%--							<tr>--%>
<%--								<td class="f66" align="left" width="50%" height="36px">--%>
<%--									 <input id="excel" type="button" value="导出EXCEL" name="btnExportExcel" />--%>
<%--								</td>--%>
<%--							</tr>--%>
<%--						</tbody>--%>
<%--					</table>--%>
					<span id="dataInfo"> </span>
				</div>
				<table width="89%">
					<tr>
						<td align="center">
<%--							<input onclick="addUserRecharge();"  id="bt_addRecharge" type="button" value="充值" />--%>
<%--						 	<input onclick="addWithdraw();" id="bt_delCost" type="button" value="扣费" />--%>
						 </td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
