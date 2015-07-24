<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>失败的借款</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				
				$("#excel").click(function(){
					var stIdArray = [];
		 			$("input[name='check']:checked").each(function(){
		 				stIdArray.push($(this).val());
		 			});
		 			
		 			if(stIdArray.length == 0){
		 				alert("请选择需要导出的信息");
		 				return ;
		 			}
		 		
		 			var ids = stIdArray.join(",");
                    window.location.href=encodeURI(encodeURI("exportAllFailedBorrow.do?ids="+ids));
                });
			});
			
			function initListInfo(param){
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.borrowWay"]= $("#borrowWay").val();
		 		$.shovePost("failedBorrowList.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
			
			//弹出窗口关闭
	   		function close(){
	   			 ClosePop();  			  
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
								<a href="failedBorrowIndex.do">失败的借款</a>
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
										用户名：&nbsp;&nbsp;
										<input id="userName" name="paramMap.username" type="text"/>
										&nbsp;&nbsp;
									
								      	标的类型：
										&nbsp;&nbsp;
										<select id="borrowWay" name="paramMap.borrowWay" >
											<option value="0">请选择</option>
											<option value="1">薪金贷</option>
											<option value="2">生意贷</option>
											<option value="3">业主贷</option>
										<input id="bt_search" type="button" value="查 找"  />
										&nbsp;&nbsp;
										&nbsp;&nbsp;
										<input id="excel" type="button" value="导出excel"  />
								  </td>
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
