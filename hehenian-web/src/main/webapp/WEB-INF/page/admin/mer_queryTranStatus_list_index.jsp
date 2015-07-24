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
		$("#queryOrdId").click(function() {
			var userName = $("#userName").val();
			var queryTrade = $("#queryTrade").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			if (userName == null || userName == "") {
				alert("用户名不能为空!");
				return;
			}
			if (queryTrade == '') {
				alert("请选择交易类型!");
				return;
			}
			if (startTime == '' || startTime == null) {
				alert("请选择开始时间!");
				return;
			}
			if (endTime == '' || endTime == null) {
				alert("请选择结束时间!");
				return;
			}
			param = {};
			param["userName"] = userName;
			param["endTime"] = endTime;
			param["startTime"] = startTime;
			param["queryTrade"] = queryTrade;
			//加载列表页,传值
			initListInfo(param);
		});
		
		$("#export").click(function(){
			exportExcel();
		})

	});

	//加载列表页
	function initListInfo(praData) {
		$.post("queryOrdidList.do", praData, initCallBack);
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
	function exportExcel(){
		var arr = new Array();
		$(".export").each(function(i,item){
			arr.push($(item).html());
		});
		var param = {};	
		var userName = $("#userName").val();
		var queryTrade = $("#queryTrade").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (userName == null || userName == "") {
			alert("用户名不能为空!");
			return;
		}
		if (queryTrade == '') {
			alert("请选择交易类型!");
			return;
		}
		if (startTime == '' || startTime == null) {
			alert("请选择开始时间!");
			return;
		}
		if (endTime == '' || endTime == null) {
			alert("请选择结束时间!");
			return;
		}
		param["paramMap.userName"] = userName;
		param["paramMap.endTime"] = endTime;
		param["paramMap.startTime"] = startTime;
		param["paramMap.queryTrade"] = queryTrade;
		param["paramMap.status"] = arr.join(",");
		$.post("exportTransStatus.do",param,function(data){
			window.location.href()
		});
		window.location.href="exportTransStatus.do?userName="+userName+"&&endTime="+endTime+"&&startTime="+startTime+"&&queryTrade="+queryTrade+"&&status="+arr.join(",");
		/*  encodeURI(encodeURI(*/
	}
</script>
</head style="min-width: 1000px">
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="120" height="28" class="main_alll_h2"><a href="queryTransStatusIndex.do">交易状态查询</a></td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" height="36px" width="20%" style="padding-left: 10px;">用户名： <input id="userName" name="paramMap.userName" type="text" /></td>
								<td class="f66" align="left" height="36px" width="20%">开始时间：<input id="startTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
									name="paramMap.startTime" readonly="readonly"></td>
								<td class="f66" align="left" height="36px" width="20%">结束时间： <input id="endTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
									name="paramMap.endTime" readonly="readonly"></td>
								<td class="f66" align="left" height="36px" width="16%">交易类型： <select id="queryTrade" name="queryTrade">
										<option value="" selected="selected">请选择</option>
										<option value="REPAYMENT">还款</option>
										<option value="CASH">取现</option>

								</select>
								</td>
								<td><input id="queryOrdId" type="button" value="搜索" /></td>
								<td><input type="button" value="导出Excel" id="export"/></td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
