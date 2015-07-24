var RolesIndex=function(){
	var grid;
	var that;
	return {
		init:function(){
			that=this;
			grid=$("#tableGrid").datagrid({
				url:"/menuAdmin/roleList.html",
				height:550,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				singleSelect:true,
				autoRowHeight:true,
				striped:true,
				pageSize:20,
				pageList:[20],
				idField:"id",
				columns:[
				[
					{field:"name",title:"名字",width:150,align:"center"},
					{field:"edit",title:"操作",width:80,align:"center",formatter:function(value,row,index){
						var str="<a href='javascript:void(0);' onclick='roleIndex.editRole("+row.id+")'>修改</a>  ";
						str+="<a href='javascript:void(0);' onclick='roleIndex.deleteRole("+row.id+")'>删除</a>  ";
						str+="<a href='javascript:void(0);' onclick='roleIndex.showAuthoritiesDialog("+row.id+")'>查看权限</a>";
					  return str;
					}}
				]
				],
				toolbar:[{
					iconCls:"icon-add",
					text:"增加",
					handler:that.editRole
				}],
				onDblClickRow: function (index, row) {
					$('#setUserRolesDiv').dialog({
						title: '设置人员',
						width: 1050,
						height: 450,
						closed: false,
						cache: false,
						href: "/menuAdmin/rolesBindUsers/"+row.id+".html",
						modal: true,
						toolbar:[{
							iconCls:"icon-cancel",text:"关闭",
							handler:function(){
							$("#setUserRolesDiv").dialog("close");
							}
						}]
					});
				}
			});
		},
		editRole:function(id){
			$('#addDiv').dialog({
				title: '修改角色',
				width: 370,
				height: 170,
				closed: false,
				cache: false,
				href: '/menuAdmin/addRoleDialog.html'+(id?"?roleId="+id:""),
				modal: false,
				toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:that.saveRole
				},
				{
					iconCls:"icon-cancel",text:"关闭",
					handler:function(){
						$("#addDiv").dialog("close");
					}
				}
				]
			});
		},
		deleteRole:function(id){
			if(!id){
				$.messager.alert("消息","id不能为空");
				return;
			}
			$.messager.confirm("消息","确认删除该角色吗，删除后不可回复",function(r){
			  if(r){
			    $.ajax({
					url:"/menuAdmin/deleteOneRole/"+id+".html",
					type:"post",
					data:{},
					success:function(data){
						if(data.ret==1){
							$('#tableGrid').datagrid('reload');
						}else{
							$.messager.alert("消息","删除失败，请稍后再试");
						}
					}
				});
			  }
			});
		},
		saveRole:function(){
			var name=$.trim($("#name").val());
			var desc=$.trim($("#desc").val());
			var id=$.trim($("#roleId").val());
			if(!name){
				$.messager.alert("消息","角色名不能为空");
				return;
			}
			var data={};
			id ?data.id=id:null;
			data.name=name;
			data.roleDesc=desc;
			$.ajax({
				url:"/menuAdmin/addOneRole.html",
				type:"post",
				data:data,
				success:function(ret){
					if(ret.ret==1){
						$('#addDiv').dialog("close");
						$.messager.alert("成功","修改成功");
						$('#tableGrid').datagrid('reload');
					}else{
						$.messager.alert("失败","修改失败，请稍后再试");
					}
				}
			});
		},
		showAuthoritiesDialog:function(roleId){
			$("#setAuthorityDiv").dialog({
				title: '权限列表',
				width: 1050,
				height: 450,
				closed: false,
				cache: false,
				href: '/menuAdmin/showRolesAuthorityDialog/'+roleId+'.html',
				modal: false,
				toolbar:[
				{
					iconCls:"icon-cancel",text:"关闭",
					handler:function(){
						$("#setAuthorityDiv").dialog("close");
					}
				}
				]
			});
		}
	}
}
$(function(){
	var roleIndex=new RolesIndex();
	roleIndex.init();
	window.roleIndex=roleIndex;
});