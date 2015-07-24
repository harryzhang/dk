<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
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
		<script type="text/javascript">
			$(function(){				
				param["pageBean.pageNum"] = 1;
				initListInfo(param);				
			});
			
			function initListInfo(praData){							    
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.title"] = $("#realName").val();
				praData["paramMap.parentID"] = $("#parentID").val();
		 		$.shovePost("queryNewsListPage.do",praData,initCallBack);
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
	 			window.location.href= "deleteNews.do?id="+ids;
		 	}
			
		 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
	 			window.location.href= "deleteNews.do?id="+id;
		 	}
		 	
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
   			
   			//网站公告预览  houli
		 	function preview(id){
		 	  var url = "previewNewsInit.do?id="+id;
              ShowIframe("网站公告详情显示",url,600,450);
		 	  
		 	}
   			
   			//弹出窗口关闭 houli
	   		function close(){
	   			 ClosePop();  			  
	   		}
		 	
		</script>
	</head>
	<body>
		<div id="right"
			style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" align="center" bgcolor="#CC0000"
								class="white12">
								<a href="queryNewsListInit.do">内容管理</a>
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
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户名:
									<input id="userName" value="" />&nbsp;&nbsp; 标题：<input id="title" value="" />
									所属类型：
									<select name="parentID"   id="parentID"  style="width: 300px;">
										  <option value="-1">--请选择--</option>
												<s:iterator value="rightsList.{?#this.id<0}" var="bean">
														<option value="${bean.id }">${bean.itemize }</option>
													<s:iterator value="rightsList.{?#this.parentID==#bean.id}" var="twoBean">
															<option value="${twoBean.id }">&nbsp;&nbsp;------${twoBean.itemize }</option>
														<s:iterator value="rightsList.{?#this.parentID==#twoBean.id}" var="threeBean">&nbsp;&nbsp;&nbsp;
																	<option value="${threeBean.id }">&nbsp;&nbsp;&nbsp;&nbsp;--------${threeBean.itemize }</option>
														</s:iterator>
													</s:iterator>
					           				  </s:iterator>
									</select>
									<input id="search" type="button" value="查找" name="search" />
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
