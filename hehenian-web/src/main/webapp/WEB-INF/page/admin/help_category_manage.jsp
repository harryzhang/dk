<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>帮助中心-帮助类型管理</title>
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
			
			function initListInfo(param){
			   //alert("typeId="+$("input:hidden").val());
			   param["paramMap.typeId"] = $("input:hidden").val();//从对应帮助类型的管理列表进去，那么加载的就是对应类型下的所有帮助问题
		 		$.shovePost("queryHelpListManagePage.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
		
			function del(id){
				if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
				window.location.href='deleteHelpManager.do?typeId='+id;
			}
			
			//信息管理预览  houli
		 	function preview(id){
		 	  var url = "previewHelpInit.do?commonId="+id;
              ShowIframe("帮助问题详情显示",url,600,450);
		 	  
		 	}
   			
   			//弹出窗口关闭 houli
	   		function close(){
	   			 ClosePop();  			  
	   		}
    	</script>

	</head>
	<body>
	<s:hidden name="typeId" id="typeId" />
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table  border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryHelpCategoryListPage.do">帮助中心列表</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100"  class="main_alll_h2">
									<a href="queryHelpListPageInit.do">管理帮助中心</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100"  class="xxk_all_a">
									<a href="addHelpManagerInit.do">添加内容</a>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
						<span id="dataInfo"> </span>
						<div>
						
			</div></div>
	</div>
	</div>
	</body>
</html>
