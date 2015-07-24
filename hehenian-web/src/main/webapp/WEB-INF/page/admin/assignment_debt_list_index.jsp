<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		param["pageBean.pageNum"] = 1;
		initListInfo(param);
		$("#bt_search").click(function() {
			var param = {};
			param["pageBean.pageNum"] = 1;
			param["paramMap.userName"] = $("#userName").val();
			param["paramMap.id"] = $("#id").val();
			param["paramMap.number"] = $("#number").val();
			param["paramMap.borrowWay"] = $("#borrowWay").val();
			param["paramMap.isDebt"] = $("#isDebt").val();
			//加载列表页,传值
			initListInfo(param);
		});

	});

	//加载列表页
	function initListInfo(praData) {
		$.post("queryAssignmentList.do", praData, initCallBack);
	}

	function initCallBack(data) {
		$("#dataInfo").html(data);
	}

	function checkAll(e) {
		if (e.checked) {
			$(".downloadId").attr("checked", "checked");
		} else {
			$(".downloadId").removeAttr("checked");
		}
	}
</script>
</head style="min-width: 1000px">
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="120" height="28" class="main_alll_h2"><a href="queryAssignmentIndex.do">债权管理列表</a></td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" height="36px" width="20%" style="padding-left: 10px;"> 用户名： <input id="userName" name="paramMap.userName" type="text" /></td>
								<td class="f66" align="left" height="36px"  width="20%"> 债权编号： <input id="id" name="paramMap.id" type="text" /></td>
								<td class="f66" align="left" height="36px"  width="20%"> 借款编号： <input id="number" name="paramMap.number" type="text" /></td>
								<td class="f66" align="left" height="36px" width="16%"> 标的类型： <select id="borrowWay" name="borrowWay">
										<option value="" ${paramMap.borrowWay eq "" ? "selected=selected" : "" }>请选择</option>
										<option value="1" ${paramMap.borrowWay eq 1 ? "selected=selected" : ""}>薪金贷</option>
										<option value="2" ${paramMap.borrowWay eq 2 ? "selected=selected" : ""}>生意贷</option>
										<option value="3" ${paramMap.borrowWay eq 3 ? "selected=selected" : ""}>业主贷</option>
								</select></td>
								<td class="f66" align="left" height="36px"> 债权状态： <select id="isDebt" name="isDebt">
										<option value="" ${paramMap.isDebt eq "" ? "selected=selected" : "" }>请选择</option>
										<option value="1" ${paramMap.isDebt eq 1 ? "selected=selected" : ""}>未转让</option>
										<option value="2" ${paramMap.isDebt eq 2 ? "selected=selected" : ""}>已转让</option>
								</select> <input id="bt_search" type="button" value="搜索" /></td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
</body>
</html>
