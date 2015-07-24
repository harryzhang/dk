<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>借款管理首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
<%@ include file="/include/includeJs.jsp"%>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" class="main_alll_h2"><a href="addBackBuyInit.do">发起回购</a>
						</td>
						<td width="100" id="tomorrow" class="white12"></td>
					</tr>
				</table>
			</div>
			<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
				<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
					<tbody>
						<tr>
							<td class="f66" align="left" height="36px">用户名: <input id="userName" value="" maxlength="20" />&nbsp;&nbsp; 借款类型： <s:select id="borrowWay"
									list="#{-1:'--请选择--',1:'薪金贷',2:'生意贷',3:'业主贷'}"></s:select>&nbsp;&nbsp; <input id="search" type="button" value="搜索" name="search" /></td>
						</tr>
					</tbody>
				</table>
				<span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /> </span>
				<div></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		initListInfo(param);
		$('#search').click(function() {
			param["pageBean.pageNum"] = 1;
			initListInfo(param);
		});

	});
	function initListInfo(praData) {
		praData["paramMap.userName"] = $("#userName").val();
		praData["paramMap.borrowWay"] = $("#borrowWay").val();
		$.shovePost("addBackBuyList.do", praData, initCallBack);
	}

	function initCallBack(data) {
		$("#divList").html(data);
	}

	function addBackBuy(borrowId) {
		$.post("addBackBuy.do?borrowId=" + borrowId, function(data) {
			alert(data);
			window.location.reload();
		});
	}
</script>
</html>