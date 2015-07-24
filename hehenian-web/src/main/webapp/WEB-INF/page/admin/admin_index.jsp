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
		    param["paramMap.userName"] = $("#userName").val();
			param["paramMap.roleId"] = $("#roleId").val();
			param["paramMap.enable"] = $("#enable").val();
	   		$.shovePost("queryAdminInfo.do",praData,initCallBack);
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
   	//启用
   		function  isNotenable(id){
	 		if(window.confirm("确定要禁用该用户吗?")){
	 			window.location.href = "isenableAdmin.do?id="+id+"&&enable=2"
	 		}
   		}
   	//禁用
   		function isenable(id){
	 		if(window.confirm("确定要启用该用户吗?")){
	 			window.location.href = "isenableAdmin.do?id="+id+"&&enable=1"
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
	</script>

	</head>
	<body>
		<div id="right">
			<div style="padding: 15px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28"  class="xxk_all_a">
									<a href="queryRoleList.do">管理组列表</a>
								</td>
								<td width="2">&nbsp;
									
								</td>
								<td width="100"  class="xxk_all_a">
									<a href="addRoleInit.do">添加管理组</a>
								</td>
								<td width="2">&nbsp;
									
								</td>
							<td width="100" height="28"  class="main_alll_h2">
								<a href="queryAdminInit.do">管理员列表</a>
							</td>
							<td width="2">&nbsp;
								
							</td>
								<td width="100"  class="xxk_all_a">
									<a href="addAdminInit.do">添加管理员</a>
								</td>
							<td>&nbsp;
								
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
									管理员账号：
									<input id="userName" name="paramMap.userName" />
									管理组:
									<s:select name="roleId" id="roleId" theme="simple"
											list="roleList.{?#this.id!=1&&#this.id!=2}" value="-2" listKey="id" listValue="name" headerKey="-2" headerValue="-请选择-">
									</s:select>
									是否禁用:
									<s:select name="enable" id="enable" theme="simple"
										list="#{-1:'-请选择-',1:'否',2:'是'}" value="-1">
									</s:select>
									<input id="search" type="button" value="确定" name="search" />
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
