<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>问卷调查资料</title>
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
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});				
			});
			function initListInfo(param){	
			 	param["paramMap.attitude"] = $("#attitude").val();	
  	             param["paramMap.username"] = $("#keyword").val();
  	             param["paramMap.levels"] = $("#levels").val();
		 		$.shovePost("queryScoreList.do",param,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
			function exportExcel(){
  	            window.location.href=encodeURI(encodeURI("exporScoreList.do?attitude="+$("#attitude").val()
  	          		+"&&keyword="+$("#keyword").val()+"&&levels=" +$("#levels").val(),"UTF-8"),"UTF-8");
			}
			
			function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
   			function excel(){
		 		if(!window.confirm("确定要导出吗?")){
		 			return;
		 		} 
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			if(stIdArray.length == 0){
	 				alert("请选择需要导出的内容");
	 				return ;
	 			}
	 			var ids = stIdArray.join(",");
	 			window.location.href= "exporScoreList.do?ids="+ids;
		 	}
	</script>
	</head>
	<body>
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr><!--
						xxk_all_a
							--><td width="100" height="28" class="main_alll_h2">
							问卷调查资料
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
									<td class="f66" align="left" width="80%" height="36px">
										用户名： <input id="keyword"  type="text"   value="" name ="paramMap.username" />
										风险等级：
										<s:select list="#{'1':'一级','2':'二级','3':'三级','4':'四级'}" 
										headerKey="0" headerValue="--请选择--" name="paramMap.levels" id="levels"></s:select>
										风险偏好：
										<s:select list="#{'保守型':'保守型','中性型':'中性型','激进型':'激进型'}"
										 headerKey="" headerValue="--请选择--" name="paramMap.attitude" id="attitude"></s:select>
										<input id="bt_search" type="button" value="搜索"  />
										<input id="ex_excel" type="button" value="导出全部数据" onclick="exportExcel();" />
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
