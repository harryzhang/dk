<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-还款管理-查看投资人</title>
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
		<script type="text/javascript" src="../css/popom.js"></script>
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
<%--				--%>
<%--				$("#excel").click(function(){--%>
<%--                       window.location.href= encodeURI(encodeURI("queryRepaymentInvest.do?userName="+$("#userName").val()));--%>
<%--                });--%>
			});
			
			function initListInfo(param){
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.borrowId"] = $("#borrowId").val();
		 		$.shovePost("queryRepaymentInvest.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
			
						
		</script>
	</head>
	<body>
	
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="main_alll_h2">
								<a href="javascript:void(0)">还款管理</a>
							</td>
						
							<td width="2">
								&nbsp;
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										用户名：
										<input id="userName" name="paramMap.username" type="text"/>
										<input id="borrowId" name="borrowId" type="hidden" value="${request.borrowId}"/>
										&nbsp;&nbsp;
									   
										<input id="bt_search" type="button" value="查找"  />
										
										&nbsp;&nbsp;
<%--										<input id="excel" type="button" value="导出excel"  />--%>
									</td>
								</tr>
								
							</tbody>
						</table>
						<span id="dataInfo"><img src="../images/admin/load.gif"></img></span>
					</div>
				</div>
			</div>
			</div>
	</body>
</html>
