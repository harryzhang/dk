<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户评论管理列表</title>
		<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
		<script type="text/javascript" src="../css/popom.js"></script>
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
		<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">

	$(function() {
		param["pageBean.pageNum"] = 1;
		initListInfo(param);
		$("#bt_search").click(function() {
			var param = {};
			param["pageBean.pageNum"] = 1;
			param["paramMap.userName"] = $("#userName").val();
			param["paramMap.msgContent"] = $("#msgContent").val();
			param["paramMap.status"] = $("#status").val();
			initListInfo(param);
		});
	});

	function initListInfo(praData) {
		$.post("findUserReview.do", praData, initCallBack);
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
							<td width="120" height="28" class="main_alll_h2">
								<a href="findUserReview.do">用户评论管理列表</a>
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
									<td class="f66" align="left" width="50%" height="36px">
										评论者：
										<input id="userName" name="paramMap.userName" type="text" />
										关键内容：
										<input id="msgContent" name="paramMap.msgContent" type="text" />
										状态：

										<select id="status" name="status">
											<option value="" ${paramMap.status eq "" ? "selected=selected" : "" }>
												请选择
											</option>
											<option value="1" ${paramMap.status eq 1 ? "selected=selected" : ""}>
												通过
											</option>
											<option value="0" ${paramMap.status eq 0 ? "selected=selected" : ""}>
												等待处理
											</option>
										</select>
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
