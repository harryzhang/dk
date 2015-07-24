<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>资料下载-内容维护</title>
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
				initListInfo(param);
				
				
				$("#excel").click(function(){
				 
				   window.location.href=encodeURI(encodeURI("exportintegralinfo.do?username="+$("#username").val()+"&&viprecode="+$("#viprecode").val()+"&&creditcode="+$("#creditcode").val()));
				
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
				
			   		 param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				
					
			});
			
			function initListInfo(praData){
				param["paramMap.username"] = $("#username").val();
			    param["paramMap.viprecode"] = $("#viprecode").val();
			    param["paramMap.creditcode"] = $("#creditcode").val();
		 		$.post("queryUserManageintegralinfo.do",praData,initCallBack);
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
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										用户名：
										<input id="username" name="paramMap.username" type="text"/>
										信用积分：
										<s:select list="#{-1:'--请选择--',1:'从小到大',2:'从大到小'}" id="creditcode"></s:select>
										会员积分：
										<s:select list="#{-1:'--请选择--',1:'从小到大',2:'从大到小'}" id="viprecode"></s:select>
										<input id="bt_search" type="button" value="搜索"  />&nbsp;&nbsp;
										<input id="excel" type="button" value="导出Excel" name="excel" />
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
