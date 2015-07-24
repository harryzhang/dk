<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		$("#bt_search").click(function() {
			param["pageBean.pageNum"] = 1;
			initListInfo(param);
		});

		/*$("#excel").click(function() {
			var url = "exportBackCash.do?checkUser=" + $("#checkUser").val() + "&&startTime=" + $("#startTime").val() + "&&endTime="
					+ $("#endTime").val() + "&&userName=" + $("#userName").val() + "&&type=" + $("#type").val();
			window.location.href = encodeURI(encodeURI(url));
		});*/
	});
	
	function initListInfo(param) {
		if ($("#beginDate").val() == "") {
			alert("请选择开始时间");
			return;
		}
		if ($("#endDate").val() == "") {
			alert("请选择结束时间");
			return;
		}
		param["beginDate"] = $("#beginDate").val();
		param["endDate"] = $("#endDate").val();
		$.shovePost("merBackCheckList.do",$.param(param),initCallBack);
	}
	function initCallBack(data){
	 	$("#dataInfo").html(data);
	}
</script>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<div style="margin-bottom: 10px;">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2"><a href="merBackCheck.do">商户扣款对账</a>
							</td>
							<td width="2">&nbsp;</td>
							<td width="2">&nbsp;</td>
							<td width="2">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="80%" height="36px">
									<%--用户名：&nbsp;&nbsp; <input style="width: 100px" id="userName" name="paramMap.username" type="text" /> &nbsp;&nbsp;--%> <%--									操作员：&nbsp;&nbsp; <input style="width: 100px" id="checkUser" name="paramMap.checkUser" type="text" /> &nbsp;&nbsp; --%>
									操作时间： <input id="beginDate" name="beginDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> &nbsp;&nbsp; 到：  
									<input id="endDate" name="endDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> 
									&nbsp;&nbsp; 
<%--类型： <s:select id="type" cssStyle="width:60px;" list="#{'REPAYMENT':'还款' }" name="type"  headerKey="LOANS" headerValue="放款" /> &nbsp;&nbsp;  --%>
									<input id="bt_search" type="button" value="搜 索" /> &nbsp;&nbsp; <%--<input id="excel" type="button" value="导出EXCEL" name="btnExportExcel" />--%>
								</td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> <a style="color: red;font-size: 12px;">请选择时间</a>  </span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
