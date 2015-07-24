<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>团队介绍-内容维护</title>
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
		<script type="text/javascript" src="../css/admin/popom.js"></script>
		<script type="text/javascript">
			$(function(){	
			  
			       param["pageBean.pageNum"] = 1;
				   initListInfo(param);
				   
			   $("#bt_search").click(function(){	
			 		  param["pageBean.pageNum"] = 1;		    					
  	                initListInfo(param);
				});
			   
 	
								
			});
			
			function initListInfo(param){	
			     param["paramMap.sender"] = $("#sender").val();	
  	             param["paramMap.beginTime"] = $("#beginTime").val();
  	             param["paramMap.endTime"] = $("#endTime").val();							    
		 		$.shovePost("queryMailBoxListUserPage.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			function dels(){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		} 
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			if(stIdArray.length == 0){
	 				alert("请选择需要删除的内容");
	 				return ;
	 			}
	 			var ids = stIdArray.join(",");
	 			window.location.href= "delewmailuser.do?id="+ids;
		 	}
			
		 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
	 			window.location.href= "delewmailuser.do?id="+id;
		 	}
		 	//加入黑名单
		 	function black(id){
		 	  if(!window.confirm("您确定把该会员加入站内信黑名单吗？")){
		 			return;
		 		}
		 		
		 		window.location.href= "joinBlackList.do?id="+id;
		 	}
		 	
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
   			
   		    function updates(e){
   			   var url="updateMailInit.do?id="+e;
   			   ShowIframe("查看站内信",url,600,400);
   			}
   			
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
							<td id="admins" width="100"  class="xxk_all_a">
								<a href="queryMailBoxListInit.do">管理员与用户</a>
							</td>
							<td width="1">
								&nbsp;
							</td>
							<td id="adusers" width="100" class="main_alll_h2">
								<a href="queryMailBoxListUserInit.do">用户与用户</a>
							</td>
							<td width="1">
								&nbsp;
							</td>
							<td id="blacklist" width="100" class="xxk_all_a">
								<a href="queryBlacklistInit.do">黑名单</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td >
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
									<td class="f66" align="left" width="80%" height="36px">
										发送人：
										<input id="sender" name="paramMap.sender" type="text"/>
										发送时间
										 <input id="beginTime"  type="text"   name="paramMap.beginTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
										 --
										  <input id="endTime"  type="text"   name="paramMap.endTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
										<input id="bt_search" type="button" value="搜索"  />
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
