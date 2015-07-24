<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>手机更新</title>
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
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				
				$("#excel").click(function(){
				  window.location.href=encodeURI(encodeURI("exportupdatephonexfChange.do?starttime="+$("#starttime").val()+"&&endtime="+$("#endtime").val()+"&&username="+$("#username").val()+"&&statss="+$("#statss").val()));
				});
				
					
			});
			
			function initListInfo(praData){
		        var param = {};
		        param["paramMap.starttime"] = $("#starttime").val();
			    param["paramMap.endtime"] = $("#endtime").val();
			     param["paramMap.username"] = $("#username").val();
			      param["paramMap.statss"] = $("#statss").val();
		 		$.post("updatephoneinfoChange.do",param,initCallBack);
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
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="xxk_all_a">
								<a href="updatephoneIndex.do">手机绑定</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" class="main_alll_h2">
								<a href="updatephoneIndexChange.do">手机变更申请</a>
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
												时间：
										<input id="starttime" class="Wdate"  name="paramMap.starttime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										--
										<input id="endtime" class="Wdate"  name="paramMap.endtime"" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									   &nbsp;&nbsp;&nbsp;&nbsp;
									   用户名
									    <input type="text" id="username" name="paramMap.username">
									     &nbsp;&nbsp;&nbsp;&nbsp;
									    <s:select list="#{-1:'请选择',1:'成功',2:'审核中',4:'失败'}" id="statss" value="#request.types"></s:select>
										<input id="bt_search" type="button" value="搜索"  />&nbsp;&nbsp;
										<input id="excel" type="button" value="导出Excel" name="excel" />
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
