<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>借款管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<div id="contentDiv">
	<table class="gvItem" style="width:100%;">
							<tr>
							    <td colspan="2" class="blue12 left">
									沟通内容：<span class="require-field">*&nbsp;</span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.content" cols="30" rows="10"></s:textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="left" style="padding-left: 30px;">
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
								</td>
							</tr>
						</table>
   </div>
<div id="divList" style="padding: 15px 10px 0px 10px;">
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
<s:hidden name="id" value="%{#request.id}"></s:hidden>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
			$(function(){
				$('#btn_save').click(function(){
				   var id = $('#id').val();
				   param['paramMap.id'] = id;
				   param['paramMap.content'] = $('#paramMap_content').val();
				   $.shovePost('addRepayMentNotice.do',param,function(data){
		              var callBack = data.msg;
		              if(callBack == undefined || callBack == ''){
		                 $('#contentDiv').html(data);
		              }else{
		                if(callBack == 1){
		                    alert("操作成功!");
		                    window.location.href="repaymentNoticeInit.do?id="+id;
		                    return false;
		                }
		                alert(callBack);
		              }
		            });
				});
				initListInfo(param);
			});
			function initListInfo(praData){
				praData["paramMap.id"] = '${id}';
		 		$.shovePost("repaymentNoticeList.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}				
</script>
</html>
