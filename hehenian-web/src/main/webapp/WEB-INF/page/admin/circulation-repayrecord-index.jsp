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
	</head>
	<body>
		<div id="right"
			style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100px;" height="28" align="center" 
								class="xxk_all_a">
								<a href="publishCirculation.do">代发流转标</a>
							</td>
							<td width="2px;">
								&nbsp;
							</td>
							<td width="100px;" height="28" align="center" 
								class="main_alll_h2">
								<a href="circulationRepayRecordInit.do">流转标还款记录</a>
							</td>
							<td width="2px;">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户名:
									<input id="userName" value="" />&nbsp;&nbsp;
									借款标题:
									<input id="borrowTitle" value="" />&nbsp;&nbsp;
									借款状态：
									<s:select id="borrowStatus" list="#{-1:'--请选择--',2:'认购中',4:'回购中',5:'已还完'}"></s:select>
									<input id="search" type="button" value="查找" name="search" />
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					<div>
	</div>
</div>
			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
				   initListInfo(param);
				});				
			});			
			function initListInfo(praData){
			    param["pageBean.pageNum"] = 1;
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.borrowTitle"] = $("#borrowTitle").val();
				praData["paramMap.borrowStatus"] = $("#borrowStatus").val();
		 		$.shovePost("circulationRepayRecordList.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
