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
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					var param1 = {};
					param1["paramMap.titleName"] = $("#titleName").val();
					$.post("addDeadline.do",param1,function(data){
					if(data==0){
					alert("你输入的借款期限已经存在");
					}else if(data==1){
					alert("操作成功");
					window.location.reload();
					}
					});
					initListInfo(param);
				});
				
					
			});
			
			function initListInfo(praData){
				praData["paramMap.title"] = $("#titleName").val();
			    
		 		$.post("queryDeadlineinfo.do",praData,initCallBack);
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
							<td width="100" height="28" class="xxk_all_a">
								<a href="linkageinit.do">借款目的</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="main_alll_h2">
								<a href="queryDeadlineIndex.do">借款期限</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="xxk_all_a">
								<a href="queryMomeyIndex.do">金额范围</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="xxk_all_a">
								<a href="queryAcountIndex.do">担保机构</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
								<td width="100"   class="xxk_all_a">
								<a href="queryInversIndex.do">反担保方式</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="xxk_all_a">
								<a href="queryImageIndex.do">系统头像设置</a>
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
										添加借款期限：
										<input id="titleName" name="paramMap.title" type="text"/>
										
										<input id="bt_search" type="button" value="添加"  />
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
