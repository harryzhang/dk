<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>资料下载-内容维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript"
	src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var pageNumber = 0;
var pageSize = 10;
	$(function() {

		initListInfo(param);
		$("#bt_search").click(function() {
			param["pageBean.pageNum"] = 1;
			param["paramMap.beginDate"] = $("#beginDate").val();
			param["paramMap.endDate"] = $("#endDate").val();
			param["paramMap.productPeriod"] = $("#productPeriod").val();
			initListInfo(param);
		});

	});

	function initListInfo(praData) {
		praData['pageBean.pageSize'] = pageSize;
		param["paramMap.beginDate"] = $("#beginDate").val();
		param["paramMap.endDate"] = $("#endDate").val();
		param["paramMap.productPeriod"] = $("#productPeriod").val();
		$.post("admin/listColorLifeCheckedInfo.do", praData, initCallBack);
	}
	
	function changePage(pageSelect) {
		param["pageBean.pageNum"] = pageNumber;
		param["pageBean.pageSize"] = $(pageSelect).val();
		param["paramMap.beginDate"] = $("#beginDate").val();
		param["paramMap.endDate"] = $("#endDate").val();
		param["paramMap.productPeriod"] = $("#productPeriod").val();
		$.post("admin/listColorLifeCheckedInfo.do", param, initCallBack);
	}

	function initCallBack(data) {
		$("#dataInfo").html(data);
	}

	function del(id) {
		if (!window.confirm("确定要删除吗?")) {
			return;
		}

		window.location.href = "deleteDownload.do?id=" + id;
	}

	function checkAll(e) {
		if (e.checked) {
			$(".downloadId").attr("checked", "checked");
		} else {
			$(".downloadId").removeAttr("checked");
		}
	}

	//弹出窗口关闭
	function close() {
		ClosePop();
	}
</script>
</head>
<body style="min-width: 1000px">
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
					<tbody>
						<tr>
							<td class=main_alll_h2 height=28 width=120><a href="#">用户购买记录列表</a></td>
							<td width=2>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div
				style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
				<form id="form" name="form"
					action="<c:url value="/admin/listColorLifeCheckedInfo.do"/>"
					method="post">
					<table style="margin-bottom: 8px" border=0 cellspacing=0
						cellpadding=0 width="100%">
						<tbody>
							<tr>
								<td class=f66 height=36 width="50%" align=left>
									&nbsp;&nbsp;购买期限：
									<select id="productPeriod">
										<option value="">请选择</option>
										<option value="3">3个月</option>
										<option value="6">6个月</option>
										<option value="12">12个月</option>
									</select>
									&nbsp;&nbsp;起止时间：
									<input name="paramMap.beginDate" id="beginDate"
									value="<c:out value="${beginDate}"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
									type="text" /> -- <input id="endDate" name="paramMap.endDate"
									value="<c:out value="${endDate}"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
									type="text" />&nbsp;<input id="bt_search" value="搜索"
									type="button" /> <!-- <input id="excel" type="button"
									value="导出Excel" name="excel" /> -->
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<span id="dataInfo"></span>
			</div>
		</div>
	</div>
</body>
</html>
