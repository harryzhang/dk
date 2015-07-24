<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户举报</title>
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
  	                initListInfo(param);
				});
				
			   
				
			});
			
			function initListInfo(param){	
			     param["paramMap.reporter"] = $("#reporter").val();	
  	             param["paramMap.user"] = $("#user").val();
  	             param["paramMap.status"] = $("#status").val();						    
		 		$.shovePost("queryReportListPage.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			
			
		 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
	 			window.location.href= "deleteReport.do?id="+id;
		 	}
		 	
	
   			
   			function updates(e){
   			   var url="updateReportInit.do?id="+e;
   			   ShowIframe("修改站内信",url,600,400);
   			}
   			
   			function close(){
   			   window.location.href="queryReportListInit.do";
   			  
   			  ClosePop();
   			  
   			}
		 	
		</script>
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td  width="10%" height="28"  class="main_alll_h2">
								<a href="queryReportListInit.do">用户举报</a>
							</td>
							<td width="1">
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
										举报人：
										<input id="user" name="paramMap.user" type="text"/>&nbsp;&nbsp;
										被举报人：
										<input id="reporter" name="paramMap.reporter" type="text"/>&nbsp;&nbsp;
										状态：
										<select id="status" name="paramMap.status">
										  <option value="">请选择</option>
										  <option value="1">等待处理</option>
										  <option value="2">举报成功</option>
										  <option value="3">举报失败</option>
										</select>&nbsp;&nbsp;
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
