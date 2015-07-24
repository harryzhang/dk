<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>调查问卷</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<%@ include file="/include/includeJs.jsp"%>
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				param["type"] = '${type}';
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				$("#ask_status").click(function(){
					if($("#ask_status").val()=="提问状态：关"){
						updateStatu(0,-1);
						$("#ask_status").val("提问状态：开");
						
					}else{
						updateStatu(1,-1);
						$("#ask_status").val("提问状态：关");
					}
					
				});
				$("#forward_status").click(function(){
					if($("#forward_status").val()=="自动转发：关"){
						updateStatu(-1,0);
						$("#forward_status").val("自动转发：开");
					}else{
						updateStatu(-1,1);
						$("#forward_status").val("自动转发：关");
					}
					
				});
				
			});
			function updateStatu(ask_status,forward_status){
					param["paramMap.askStatus"]= ask_status;
					param["paramMap.forwardStatus"]= forward_status;
					$.post("updateQuestionStatus.do",param,function(data){
						if(data==1){
							alert("状态修改成功");
						}
						if(data==2){
							alert("状态修改失败");
						}
					});
				
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
	 			window.location.href= "deleteQuestion.do?id="+ids;
		 	}
			function initListInfo(param){
			param["paramMap.username"] = $("#username").val();	
			param["paramMap.keyword"] = $("#keyword").val();	
		 		$.post("queryQuestionList.do",param,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#dataInfo").html(data);
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
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr><!--
						xxk_all_a
							--><td width="100" height="28" class="main_alll_h2">
							<a href="#">用户提问</a>
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
										提问者： <input id="username"  type="text"   />&nbsp;&nbsp;
										关键词：<input id="keyword"  type="text"   />
									<input id="bt_search" type="button" value="搜索"  />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input id="ask_status" type="button" value="提问状态：开"  />
									<input id="forward_status" type="button" value="自动转发：关"  />
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> <img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					</div>
				</div>
			</div>
	</body>
</html>
