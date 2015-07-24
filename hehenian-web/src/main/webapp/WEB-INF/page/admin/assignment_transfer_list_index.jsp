<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
	$(function() {
		param["pageBean.pageNum"] = 1;
		initListInfo(param);
		$("#bt_search").click(function() {
			var param = {};
			param["pageBean.pageNum"] = 1;
			param["paramMap.userName"] = $("#userName").val();
			param["paramMap.id"] = $("#id").val();
			param["paramMap.borrowWay"] = $("#borrowWay").val();
			//加载列表页,传值
				initListInfo(param);
			});

	});

	//加载列表页
	function initListInfo(praData) {
		$.post("queryTransferById.do", praData, initCallBack);
	}
	function initCallBack(data) {

		$("#dataInfo").html(data);
	}
</script>
	</head style="min-width: 1000px">
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="main_alll_h2">
								<a href="queryTransferByIdIndex.do">转让中的债权列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="right" width="50%" height="36px">
										借款人：
										<input id="publisher" name="paramMap.publisher" type="text" />
										转让人：
										<input id="userName" name="paramMap.userName" type="text" />&nbsp;&nbsp;
										<input id="bt_search" type="button" value="搜索" />
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
	</body>
</html>
