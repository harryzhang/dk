<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>借款管理</title>
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
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				param["id"] =$("#notic_id").val();
				param["notice_style"]= $("#notice_style").val();
				initListInfo(param);
			});
			
			function initListInfo(param){
		 		$.shovePost("queryNoticeTemplateAllList.do",param,initCallBack);
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
					<table border="0" cellspacing="0" cellpadding="0">
					<tr>
							<s:if test="#request.sid==1 || #request.sid == 0">
							<td width="100" height="28"  class="main_alll_h2">
							</s:if>
							<s:else>
							<td width="100" height="28"  class="xxk_all_a">
							</s:else>
								<a href="queryNoticeStyleAllInit.do?sid=1">站内信提醒设置</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<s:if test="#request.sid==2">
							<td width="100" height="28"  class="main_alll_h2">
							</s:if>
							<s:else>
							<td width="100" height="28"  class="xxk_all_a">
							</s:else>
								<a href="queryNoticeStyleAllInit.do?sid=2">短信提醒设置</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							
							<s:if test="#request.sid==3">
							<td width="100" height="28"  class="main_alll_h2">
							</s:if>
							<s:else>
							<td width="100" height="28"  class="xxk_all_a">
							</s:else>
								<a href="queryNoticeStyleAllInit.do?sid=3">邮件提醒设置</a>
							</td>
							<td width="2">
									&nbsp;
								</td>
							
							<s:if test="#request.notice_style==1 || #request.notice_style == 0">
								<td width="100" height="28"  class="main_alll_h2">
									<a href="queryNoticeTemplateAllInit.do?notice_style=${notice_style }&&id=${id}">站内信管理设置</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								
							</s:if>
							<s:if test="#request.notice_style==2">
								<td width="100" height="28"  class="main_alll_h2"
									class="white12">
									<a href="queryNoticeTemplateAllInit.do?notice_style=${notice_style }&&id=${id}">短信管理设置</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
							</s:if>
							<s:if test="#request.notice_style==3">
								<td width="100" height="28"  class="main_alll_h2" class="main_alll_h2">
								<a href="queryNoticeTemplateAllInit.do?notice_style=${notice_style }&&id=${id}">邮件管理设置</a>
							</td>
							</s:if>
							<td>
								&nbsp;<input type="hidden" value="${sid}" name="styleId" id="styleId">
								&nbsp;<input type="hidden" value="${id}" name="notic_id" id="notic_id">
								<input type="hidden" value="${notice_style}" name="notice_style" id="notice_style">
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
						<span id="dataInfo"> <img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					</div>
				</div>
			</div>
	</body>
</html>
