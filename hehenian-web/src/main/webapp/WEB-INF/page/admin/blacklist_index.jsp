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
  	                initListInfo(param);
				});
				
			   
				
			});
			
			function initListInfo(param){	
			     param["paramMap.username"] = $("#sender").val();	
  	             					    
		 		$.shovePost("queryBlacklistPage.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
	 			window.location.href= "deleteBlackList.do?id="+id;
		 	}
		 	
		 	function addblacklist(){
		 		
		 	  var url="addBlackListInit.do";
   			   ShowIframe("添加黑名单",url,600,400);
	 			
		 	}
		 	
		 	function close(){
		 	  window.location.href="queryBlacklistInit.do";
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
							<td id="adusers" width="100" class="xxk_all_a">
								<a href="queryMailBoxListUserInit.do">用户与用户</a>
							</td>
							<td width="1">
								&nbsp;
							</td>
							<td id="blacklist" width="100" class="main_alll_h2">
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
										用户名：
										<input id="sender" name="paramMap.username" type="text"/>&nbsp;&nbsp;
										<input id="bt_search" type="button" value="搜索"  />
									
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
	</body>
</html>
