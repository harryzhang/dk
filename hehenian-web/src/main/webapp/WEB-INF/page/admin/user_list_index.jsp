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
<link rel="stylesheet" type="text/css" href="../common/date/calendar.css" />
<script type="text/javascript" src="../common/date/calendar.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<style type="text/css">
.xixihaha input{
width: 130px;
}
</style>
<script type="text/javascript" language="javascript">
	$(function() {
		param["pageBean.pageNum"] = 1;
		initListInfo(param);

		$("#search").click(function() {
			param["pageBean.pageNum"] = 1;
			initListInfo(param);
		});
	});

	function initListInfo(praData) {
		param["paramMap.userName"] = $("#userName").val();
		param["paramMap.phone"] = $("#phone").val();
		param["paramMap.idNo"] = $("#idNo").val();
		param["paramMap.realName"] = $("#realName").val();
		 param["paramMap.source"] = $("select option:selected").val();
		$.shovePost("queryPersonInfolist.do", praData, initCallBack);
	}

	function initCallBack(data) {
		$("#dataInfo").html(data);
	}

	function selectStartDate() {
		return showCalendar('startDate', '%Y-%m-%d', '24', true, 'startDate');
	}

	function selectEndDate() {
		return showCalendar('endDate', '%Y-%m-%d', '24', true, 'endDate');
	}
</script>

</head>
<body style="min-width: 1000px">
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" class="main_alll_h2"><a href="javascript:void(0);">基本信息审核</a>
						</td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
			<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;" align="center">
				<table style="margin-bottom: 8px;margin-left: -110px;" cellspacing="0" cellpadding="0" width="90%" border="0">

					<tr height="36" class="xixihaha">
						<td class="f66" align="center"  >用户名: <input id="userName" name="paramMap.userName" /></td>
						<td class="f66" align="center"  >手机号码： <input id="phone" name="paramMap.phone" /></td>
						<td class="f66" align="center"  >真实姓名： <input id="realName" name="paramMap.realName" /></td>
						<td class="f66" align="center"  >身份证号： <input id="idNo" name="paramMap.idNo" /></td>
						<td class="f66" align="center"  >
								用户来源: 
									<select id="source">
										<option selected="selected" value="-1">--请选择--</option>
										<option  value="0">小贷公司</option>
										<option  value="1">网站注册</option>
										<option  value="2">彩生活用户</option>
										<option  value="3">净值用户</option>
									</select>
						 </td>
						<td class="f66" align="center"  ><input id="search" type="button" value="查找" name="search" style="width: 60px;" />
					</tr>

				</table>
				<span id="dataInfo"><img src="../images/admin/load.gif" class="load" alt="加载中..." /> </span>
			</div>
		</div>
	</div>
</body>
</html>
