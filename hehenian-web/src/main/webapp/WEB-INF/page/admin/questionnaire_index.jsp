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
			});
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
	 			window.location.href= "deleteQuestionnaire.do?id="+ids;
		 	}
			function initListInfo(param){
		 		$.post("queryQuestionnaire.do",param,initCallBack);
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
							<a href="javascript:editSize('addQuestionnaireInit.do',630,500)">添加问卷题目</a>
							<s:hidden value="%{#request.fail}" id = "fail"></s:hidden>
					   		</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						
						<span id="dataInfo"> <img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					</div>
				</div>
			</div>
	</body>
</html>
