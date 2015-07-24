<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-用户银行卡管理</title>
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
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				
				
				$("#excel").click(function(){
                   window.location.href= encodeURI(encodeURI("exportAllWithdraw.do?userName="+$("#userName").val()+"&&startTime="+$("#startTime").val()+"&&endTime="+$("#endTime").val()+"&&statss=4"));
                });
			});
			
			function initListInfo(param){
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.startTime"]= $("#startTime").val();
				param["paramMap.endTime"]= $("#endTime").val();
				param["paramMap.status"]= "4";
		 		$.shovePost("queryTransList.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
			
			function checkAll(e){
		   		if(e.checked){
		   			$(".helpId").attr("checked","checked");
		   		}else{
		   			$(".helpId").removeAttr("checked");
		   		}
   			}	
   			
   			function updates(){
		 		if(!window.confirm("确定要转账吗?")){
		 			return;
		 		}
		 		var stIdArray = [];
		 		
	 			$("input[name='cb_ids']:checked").each(function(){
	 			  
	 				stIdArray.push($(this).val());
	 			});
	 			if(stIdArray.length == 0){
	 				alert("请选择需要转账的内容");
	 				return ;
	 			}
	 			var adminId = '${sessionScope.admin.id }';
	 			var ids = stIdArray.join(",");
	 			window.location.href= "updateWithdraws.do?wids=" + ids+"&adminId="+adminId+"&oldStatus=4";
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
							<td width="120" height="28" class="xxk_all_a">
								<a href="queryAllWithdrawInit.do">全部提现</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" class="xxk_all_a">
								<a href="queryCheckInit.do">待审核的提现</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="main_alll_h2">
								<a href="queryTransInit.do">转账中的提现</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
								<a href="querySuccessInit.do">成功的提现</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
								<a href="queryFailInit.do">失败的提现</a>
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
									
								        提交时间：
										<input id="startTime" name="startTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										&nbsp;
										到
										<input id="endTime" name="paramMap.endTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									   &nbsp;&nbsp;
									  
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
