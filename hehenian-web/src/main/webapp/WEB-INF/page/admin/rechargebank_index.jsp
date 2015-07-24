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
		<link href="../common/date/calendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/date/calendar.js"></script>
		<script type="text/javascript" language="javascript">
	    $(function(){
	    	param["pageBean.pageNum"]=1;
		    initListInfo(param);
		    
		    $("#search").click(function(){
		    param["pageBean.pageNum"] = 1;
			initListInfo(param);
		    	});
		    }
	    )
	    //加载留言信息
	   function initListInfo(praData) {
		    param["userName"] = $("#userName").val();
		    param["realName"] = $("#realName").val();
			param["startDate"] = $("#startDate").val();
			param["endDate"] = $("#endDate").val();
	   		$.shovePost("querybankeditInfo.do",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
   		}
   		
   		//分页
   		function pageInfo(pageId){
   			param["pageBean.pageNum"] = pageId;
   			initListInfo(param);
   		}
   		
   		//删除单个
   		function deleteAdminById(adminIds){
	 		if(window.confirm("确定要删除吗?")){
	 			window.location.href = "deleteAdmin.do?id="+adminIds
	 		}
   		}
   		
   		//判断是否有选中项
   		function checked(str){
   			var c = 0;
   			$(".adminId").each( function(i, n){
				if(n.checked){
					c = 1;
				}
			});
			if(c==0){
				alert("请先选中您要"+str+"的项！");
				return false;
			}
			return true;
   		}
   		
   		//删除多个选中项
   		function deleteAdmins(){
   			if(!checked("删除")){
   				return;
   			}
	 		if(!window.confirm("确定要删除所有选中记录?")){
	 			return;
	 		}
	 		var adminIds = "";
			$(".adminId").each( function(i, n){
				if(n.checked){
					adminIds += n.value+",";
				}
			});
			adminIds = adminIds.substring(0,adminIds.lastIndexOf(","));
		 	window.location.href = "deleteAdmin.do?id="+adminIds;
   		}

   		//全选
   		function checkAll(e){
	   		if(e.checked){
	   			$(".adminId").attr("checked","checked");
	   		}else{
	   			$(".adminId").removeAttr("checked");
	   		}
   		}
   		function startDate(){
				return showCalendar('startDate', '%Y-%m-%d', '24', true, 'startDate');
			}
			
			function endDate(){
				return showCalendar('endDate', '%Y-%m-%d', '24', true, 'endDate');
			}
	</script>

	</head>
	<body>
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="200px" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2">
								<a href="querybankeditInit.do">充值银行卡管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
								<td width="100" height="28" class="xxk_all_a">
								<a href="addRechargeBankInit.do">添加充值银行卡</a>
							</td>
								<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
	</body>
</html>
