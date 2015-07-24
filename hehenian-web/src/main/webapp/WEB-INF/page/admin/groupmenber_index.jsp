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
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" language="javascript">
	    $(function(){
	    	param["pageBean.pageNum"]=1;
		    initListInfo(param);
		  	
		    $("#search").click(function(){
		    param["pageBean.pageNum"] = 1;
				initListInfo(param);
		    });
		   
	    });
	    //加载留言信息
	   function initListInfo(praData) {
	  		 param["paramMap.userName"] = $("#userName").val();
	   		param["paramMap.startUseableSum"] = $("#startUseableSum").val();
	   		param["paramMap.endUseableSum"] = $("#endUseableSum").val();
	   		param["paramMap.realName"] = $("#realName").val();
	   		param["paramMap.startAllSum"] = $("#startAllSum").val();
	   		param["paramMap.endAllSum"] = $("#endAllSum").val();
	   		param["paramMap.groupId"] = $("#groupId").val();
	   		var pageSize = $("#pageSize").val();
	   		if(pageSize == -1){
	   			pageSize = $("#totalNums").val();
	   		}
	   		param["pageSize"] = pageSize;
	   		$.shovePost("queryGroupMemberInfo.do",param,initCallBack);
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
   		function deleteById(ids){
	 		if(window.confirm("确定要删除吗?")){
	 			window.location.href = "deleteGroupMemberUser.do?id="+ids;
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
   		function deleteAll(){
   			if(!checked("删除")){
   				return;
   			}
	 		
	 		var ids = "";
			$(".adminId").each( function(i, n){
				if(n.checked){
					ids += n.value+",";
				}
			});
			ids = ids.substring(0,ids.lastIndexOf(","));
		 	window.location.href = "exportUserMember.do?id="+ids;
   		}

   		//全选
   		function checkAll(e){
	   		if(e.checked){
	   			$(".adminId").attr("checked","checked");
	   		}else{
	   			$(".adminId").removeAttr("checked");
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
							<td width="100" height="28"  class="main_alll_h2">
									<a href="queryGroupInit.do">用户组列表</a>
								</td>
								<td width="2px">
									&nbsp;
								</td>
								<!--  modify by houli 暂时不用该功能
								<td width="100" height="28" align="center" bgcolor="#8594A9"
									class="white12">
									<a href="queryDrawCashManagerInit.do">提现管理</a>
								</td>
								<td width="2px">
									&nbsp;
								</td>
								-->
								<td width="100" height="28"  class="xxk_all_a">
									<a href="#">用户组成员</a>
								</td>
								<td>
									&nbsp;
								</td>
						</tr>
						</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									
										<input type="hidden" id="groupId" value="${paramMap.groupId }" />
									
									用户名：<input id="userName"  type="text"> &nbsp;&nbsp;
									可用金额：<input id="startUseableSum"  type="text"> -- <input id="endUseableSum"  type="text">&nbsp;&nbsp;<br /><br />
									真实名字：<input id="realName"  type="text"> &nbsp;&nbsp;
									总金额：<input id="startAllSum"  type="text"> -- <input id="endAllSum" type="text"> &nbsp;&nbsp;
									&nbsp;&nbsp;每页显示条数<select id="pageSize">
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="30">30</option>
										<option value="50">50</option>
										<option value="-1">全部</option>
									</select>&nbsp;&nbsp;
									<input id="search" type="button" value="确定"  />	
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
