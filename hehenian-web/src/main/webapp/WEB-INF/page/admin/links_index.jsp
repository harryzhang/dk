<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<title>友情连接管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript">
			$(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
			
			function initListInfo(param){
		 		$.shovePost("queryLinksListPage.do",param,initCallBack);
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
	 			window.location.href= "deleteLinks.do?commonId=" + ids;
		 	}
		 	
		 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
	 			window.location.href= "deleteLinks.do?commonId="+id;
		 	}
		 	
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".helpId").attr("checked","checked");
		   		}else{
		   			$(".helpId").removeAttr("checked");
		   		}
   			}
					
    	</script>

		<style type="text/css">
			.collapsed {
				display: none;
			}
			
			.tablemain {
				background-color: #278296;
				border-collapse: collapse;
				border: solid 1px #447;
				padding: 0px;
				text-align: left;
			}
			
			.tablemain td {
				margin-left: 3px;
			}
			
			.treet {
				background-color: #F4FAFB
			}
			
			.treet td {
				font: normal 10pt Arial;
				padding: 0px 2px 0px 0px;
				cursor: pointer;
			}
			
			.adeimg,.ttimage,.parimg,.preimg {
				border: none;
				margin: 0px;
				padding: 0px;
				vertical-align: bottom;
				width: 16px;
				height: 16px;
			}
			
			.adeimg,.parimg {
				cursor: pointer;
			}
			
			.even {
				background-color: #BBDDE5;
			}
			
			.over {
				background-color: #ffc;
			}
		</style>

	</head>
	<body>
		<form action="queryHelpCategoryListPage.do" method="post" >
			<div id="right">
				<div style="padding: 15px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28"  class="main_alll_h2">
									<a href="queryLinksListPageInit.do">友情链接管理</a>
								</td>
								<td width="2">&nbsp;
									
								</td>
								<td width="100" class="xxk_all_a">
									<a href="addLinksInit.do">添加友情链接</a>
								</td>
								<td>&nbsp;
									
								</td>
							</tr>
						</table>
					</div>
					<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<span id="dataInfo"> </span>
				</div>
			</div>
		</form>

	</body>
</html>
