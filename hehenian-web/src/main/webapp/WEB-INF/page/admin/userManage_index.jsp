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
				
				initListInfo(param);
				$("#bt_search").click(function(){
				param["pageBean.pageNum"] = 1;
				 param["paramMap.userName"] = $("#userName").val();
				 param["paramMap.idNo"] = $("#idNo").val();
				 param["paramMap.id"] = $("#id").val();
				 param["paramMap.source"] = $("select option:selected").val();
				 param["paramMap.cellPhone"] = $("#cellPhone").val();
			     initListInfo(param);
				});
				
					
			});
			
			function initListInfo(praData){
		 		$.post("queryUserManageBaseInfo.do",praData,initCallBack);
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
		<style type="text/css">
			.bigBoss  tbody td input{width: 140px;}
		</style>
</head>
<body style="min-width: 1000px">
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" class="main_alll_h2"><a href="queryUserManageBaseInfoindex.do">用户基本信息</a></td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" class="bigBoss">
						<tbody>
							<tr>
								<td class="f66" align="left" width="80%" height="36px">用户名 ： <input id="userName" name="paramMap.userName" type="text" />&nbsp;&nbsp; 手机号码 ： <input id="cellPhone"
									name="paramMap.cellPhone" type="text" />&nbsp;&nbsp; 用户编号： <input id="id" name="paramMap.id" type="text" />&nbsp;&nbsp; 身份证号： <input id="idNo" name="paramMap.idNo" type="text" />&nbsp;&nbsp;
									用户来源: 
									<select id="source">
										<option selected="selected" value="-1">--请选择--</option>
										<option  value="0">小贷公司</option>
										<option  value="1">网站注册</option>
										<option  value="2">彩生活用户</option>
										<option  value="3">净值用户</option>
									</select> 
								<input id="bt_search" type="button" value="搜索" style="width: 60px;" /></td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
