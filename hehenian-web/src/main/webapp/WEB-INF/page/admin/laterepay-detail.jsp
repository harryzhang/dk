<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>借款管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right"
			style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							  <td width="100" id="today" height="28" align="center"
								class="white12 t_on">
								用户详情
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow" class="white12">
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="afterday" class="white12">
							</td>
							<td>
								&nbsp;
							</td>
							</tr>
					</table>
				</div>
				  </div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
						    <tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户名:
									<input id="userName" value="" maxlength="20"/>&nbsp;&nbsp;
									借款类型：
									<s:select id="borrowWay" list="#{-1:'--请选择--',1:'薪金贷',2:'生意贷',3:'业主贷',4:'实地考察借款',5:'机构担保借款'}"></s:select>
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									还款状态：
									<s:select id="status" list="#{-1:'--请选择--',1:'未偿还',2:'已偿还',3:'偿还中'}"></s:select>
									<input id="search" type="button" value="查找" name="search" />
									<input id="excel" type="button" value="导出Excel" name="excel" />
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					<div>
	</div>
</div>
			</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
				   param["pageBean.pageNum"] = 1;
				   initListInfo(param);
				});
			});			
			function initListInfo(praData){
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.borrowWay"] = $("#borrowWay").val();
				praData["paramMap.status"] = $("#status").val();
		 		$.shovePost("lateRepayList.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>