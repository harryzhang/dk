<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>财务管理-用户资金管理</title>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript">
	$(function() {
		param["pageBean.pageNum"] = 1;
		//initListInfo(param);

		$("#bt_search").click(function() {
			initListInfo(param);
		});
	});

	function initListInfo(param) {
		param["paramMap.realName"] = $("#realName").val();
		param["paramMap.number"] = $("#number").val();
		param["paramMap.numberId"] = $("#numberId").val();
		param["paramMap.repayDate"] = $("#repayDate").val();
		$.shovePost("queryUserFundRepayList.do", param, initCallBack);
	}

	function initCallBack(data) {
		$("#dataInfo").html(data);
	}
</script>
</head>
<body>
	<s:hidden id="userId" name="paramMap.userId"></s:hidden>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="120" height="28" class="main_alll_h2"><a
							href="javascript:void(0)">还款管理</a>
							<input id="numberId"	value="${numberId}" type="hidden" /></td>
						<td width="2">&nbsp;</td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="80%" height="36px">
									借款人：&nbsp;&nbsp; 
									<input style="width: 100px" id="realName"
									name="paramMap.realName" type="text" /> &nbsp;&nbsp;
									借款编号：&nbsp;&nbsp; 
									<input style="width: 100px" id="number"
									name="paramMap.number" type="text" />
									
									还款日期：<input style="width: 100px" id="repayDate" name="paramMap.repayDate" type="text"   />
									
									 <input id="bt_search"	type="button" value="搜 索" /> &nbsp;&nbsp;
							</tr>
						</tbody>
					</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
