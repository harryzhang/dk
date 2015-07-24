<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>手动发送</title>
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
			     param["paramMap.userName"] = $("#userName").val();	
  	             param["paramMap.realName"] = $("#realName").val();
  	           
		 		$.shovePost("queryUserListPage.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			
			function checkSend(){
		 		if(!window.confirm("确定选中用户要发送短信吗?")){
		 			return;
		 		} 
		 		//获取id集合
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			
	 			if(stIdArray.length == 0){
	 				alert("请选择需要发送短信的用户");
	 				return ;
	 			}
	 		
	 			var ids = stIdArray.join(",");
	 			
	 			var param={};
	 			param["paramMap.id"]=ids;	 			
	 			$.post("SendSMSs.do",param,function(data){
	 			      if(data==0){
	 			        alert("短信内容不能为空，请编辑短信内容");
	 			      }else if(data==2){
	 			         alert("发送失败");
	 			      }else if(data==1){
	 			         alert("发送成功");
	 			      }else if(data==3){
	 			        alert("用户不存在");
	 			      }
	 			});
	 			
		 	}
		 	
		 	function allSend(){
		 	   if(!window.confirm("确定全部用户要发送短信吗?")){
		 			return;
		 		} 
		 		//获取id集合
		 		var stIdArray = [];
	 			$("input[name='allid']").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			var ids = stIdArray.join(",");
	 			var param={};
	 			param["paramMap.id"]=ids;	 			
	 			$.post("SendSMSs.do",param,function(data){
	 			      if(data==0){
	 			        alert("短信内容不能为空，请编辑短信内容");
	 			      }else if(data==2){
	 			         alert("发送失败");
	 			      }else if(data==1){
	 			         alert("发送成功");
	 			      }else if(data==3){
	 			        alert("用户不存在");
	 			      }
	 			});
		 	}
			
		 
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
   			
   			//编辑发送内容
   			function editSend(){
   			   var url="getSendSMSContent.do";
   			   ShowIframe("编辑短信内容",url,600,400);
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
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td id="admins" width="100" height="28" class="main_alll_h2">
								<a href="queryUserListInit.do">手动发送</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td id="adusers" width="100"  class="xxk_all_a">
								<a href="querySendSMSListInit.do">发送记录</a>
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
									<td class="f66" align="left" width="68%" height="36px">
										用户名：
										<input id="userName" name="paramMap.userName" type="text"/>
										姓名：
										<input id="realName" name="paramMap.realName" type="text"/>
										
										<input id="bt_search" type="button" value="查找"  />
									</td>
									<td>
									  <input id="bt_allSend" type="button" onclick="allSend()" value="全部发送"  />&nbsp;
									  <input id="bt_checkSend" type="button" onclick="checkSend()" value="选定发送"  />&nbsp;
									  <input id="bt_editSend" type="button" onclick="editSend()" value="编辑发送内容"  />
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
