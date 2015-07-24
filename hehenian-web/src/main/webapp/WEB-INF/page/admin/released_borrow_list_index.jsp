<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>待发布管理列表初始化</title>
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
			param["paramMap.source"] = $("#source").val();
			
			//加载列表页,传值
				initListInfo(param);
			});
			
		$("#bt_search1").click(function(){
			var stIdArray = [];
		 	$("input[name='cb_ids']:checked").each(function(){
		 		stIdArray.push($(this).val());
		 	});
		 			
		 	if(stIdArray.length == 0){
		 		alert("请选择需要导出的信息");
		 		return ;
		 	}
		 		
		 	var ids = stIdArray.join(",");
                  window.location.href=encodeURI(encodeURI("exportReleasedBorrow.do?ids="+ids));
             });

	});

	//加载列表页
	function initListInfo(praData) {
		$.post("queryReleasedList.do", praData, initCallBack);
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
								<a href="queryReleasedListIndex.do">待发布管理列表</a>
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
									<td class="f66" align="right" width="60%" height="36px">
										用户名：
										<input id="userName" name="paramMap.userName" type="text" />
									</td>
									<td class="f66" align="left" height="36px">
										&nbsp;&nbsp;用户来源：
										<select id="source" name="source">
											<option value="" ${paramMap.borrowWay eq "" ? "selected=selected" : "" }>
												请选择
											</option>
											<option value="0" ${paramMap.source eq 0 ? "selected=selected" : ""}>
												小贷公司导入
											</option>
											<option value="1" ${paramMap.source eq 1 ? "selected=selected" : ""}>
												网站注册
											</option>
											<option value="2" ${paramMap.source eq 2 ? "selected=selected" : ""}>
												彩生活用户
											</option>
											<option value="3" ${paramMap.source eq 3 ? "selected=selected" : ""}>
												净值用户
											</option>
											</select>&nbsp;&nbsp;
										<input id="bt_search" type="button" value="搜索" />
										<input id="bt_search1" type="button" value="导出Excel" />
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
