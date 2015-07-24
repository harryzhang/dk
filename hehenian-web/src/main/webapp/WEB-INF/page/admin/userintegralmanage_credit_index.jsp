<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript">
			$(function(){
				
				param["pageBean.pageNum"] = 1;
				param["paramMap.userId"] = ${i};
				param["paramMap.type"] = ${y};
				initListInfo(param);
				
				$("#excel").click(function(){
				     var userId=$("#userId").val();
				     
				     var type=$("#type").val();
				    
				    window.location.href="exportuserintegralcreditinfo.do?userId="+userId+"&type="+type;
				
				});
				
				$("#bt_search").click(function(){
				/*
					var param1 = {};
					param1["paramMap.viprecode"] = $("#viprecode").val();
					param1["paramMap.viprecode"] = $("#viprecode").val();
					$.post("addTarge.do",param1,function(data){
					if(data==0){
					alert("你输入的借款目的已经存在");
					}else if(data==1){
					alert("操作成功");
					window.location.reload();
					}
					});
					*/
					
					
				var param = {};
			    param["pageBean.pageNum"] = 1;
			    param["paramMap.username"] = $("#username").val();
			    param["paramMap.viprecode"] = $("#viprecode").val();
			    param["paramMap.creditcode"] = $("#creditcode").val();
					initListInfo(param);
				});
				
					
			});
			
			function initListInfo(praData){
		
			 
		 		$.post("userintegralcreditinfo.do",praData,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			
		 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
	 			window.location.href= "deleteDownload.do?id="+id;
		 	}
		 	
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
		 	
		</script>
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
				  
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2">
								<a href="queryUserManageintegralindex.do">用户积分</a>
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
							<input id="excel" type="button" value="导出Excel" name="excel" />
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
	</body>
</html>
