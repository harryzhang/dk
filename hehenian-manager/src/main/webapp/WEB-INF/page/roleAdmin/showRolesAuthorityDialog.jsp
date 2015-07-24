<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>人员设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
</head>
<body class="easyui-layout" style="margin-left:10px;">
	<input type="hidden" id="roleId" value="${role.id }" />
	<div style="margin:10px 0;"></div>
	<div class="easyui-layout" style="width:1000px;height:350px;">
		<div data-options="region:'north'" style="height:20px"></div>
		<div data-options="region:'south',split:true" style="height:20px;"></div>
		<div data-options="region:'east',split:true,iconCls:'icon-ok'"
			title="已加入权限" style="width:450px;">
			<table class="easyui-datagrid" id="hasAdd" pagination="true"
				striped="true" rownumbers="true" fitColumns="true"
				data-options="url:'/menuAdmin/authoritiesInRolesOrNot.html?roleId=${role.id }&inRoleOrNot=1&pageSize=10',method:'get',border:false,singleSelect:true,fit:true,fitColumns:true
                    ,onDblClickRow:removeAuthorities">
				<thead>
					<tr>
						<th field="name" width="40">权限</th>
						<th field="authDesc" width="50">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'west',split:true" title="角色"
			style="width:100px;">
			${role.name }
			<div>${role.roleDesc }</div>
		</div>
		<div data-options="region:'center',title:'未加入权限'">
			<table id="unAdd" class="easyui-datagrid" pagination="true"
				striped="true" rownumbers="true" fitColumns="true"
				data-options="url:'/menuAdmin/authoritiesInRolesOrNot.html?roleId=${role.id }&inRoleOrNot=2&pageSize=10',method:'get',border:false,singleSelect:true,fit:true,fitColumns:true
                    ,onDblClickRow:addAuthorities">
				<thead>
					<tr>
						<th field="name" width="40">权限</th>
						<th field="authDesc" width="50">备注</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function removeAuthorities(index,row){
		
	}
	function addAuthorities(index,row){
		
	}
	</script>
</body>
</html>