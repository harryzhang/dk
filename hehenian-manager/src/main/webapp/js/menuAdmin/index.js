var AdminMenu=function(){
	var grid;
	var that;
	return {
		init:function(){
			that=this;
			grid=$("#tableGrid").datagrid({
					url:"/menuAdmin/listDatas.html",
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
					columns:[[
					          {field:"name",title:"名字",width:150,align:"center"},
					          {field:"edit",title:"操作",width:80,align:"center",formatter:function(value,row,index){
					          	return "<a href='javascript:void(0);' onclick='adminMenu.editMenu("+row.id+")'>修改</a>  <a href='javascript:void(0);' onclick='adminMenu.deleteMenu("+row.id+")'>删除</a>";
					          }}
						]],
					toolbar:[{
						iconCls:"icon-add",
						text:"增加",
						handler:function(){
							$('#addDiv').dialog({
								title: '新增菜单',
								width: 370,
								height: 210,
								closed: false,
								cache: false,
								href: '/menuAdmin/addMenuDialog.html',
								modal: false,
								toolbar:[
								{
									iconCls:"icon-save",text:"保存",
									handler:that.saveResource
								},
								{
									iconCls:"icon-cancel",text:"关闭",
									handler:function(){
										$("#addDiv").dialog("close");
									}
								}
								]
							});
						}
					}]
				});
		},
		deleteMenu:function(id){
			$.ajax({
				url:"/menuAdmin/deleteOneResource.html",
				type:"POST",
				data:{id:id},
				success:function(data){
					var ret=data.ret;
					if(ret==1){
						$('#tableGrid').datagrid('reload');
					}else{
						$.messager.alert("失败","删除菜单失败");
					}
				}
			});
		},
		saveResource:function(){
			var name=$.trim($("#name").val());
			var module=$("input[name=modules]").val();
			var desc=$.trim($("#desc").val());
			var url=$.trim($("#url").val());
			//var resouceType=$("#resouceType").val();
			var resouceType=$("input[name=resouceType]").val();
			if(!name){
			$.messager.alert("菜单名空","菜单名不能为空");
				return;
			}
			if(!module){
				$.messager.alert("所属模块不能为空");
				return;
			}
			if(!url || url.length>100){
				$.messager.alert("链接不合法","链接不能为空或者已经超过100个字");
				return;
			}
			var id=$("#rid").val();
			var data={name:name,resourceDesc:desc,module:module,resourceStr:url,resouceType:resouceType};
			if(id){
				data.id=id;
			}
			$.ajax({
				url:"/menuAdmin/addMenu.html",
				type:"POST",
				data:data,
				success:function(ret){
					if(ret.ret==1){
						$('#addDiv').dialog("close");
						$('#tableGrid').datagrid('reload');
					}else{
						$.messager.alert("失败","新增菜单失败");
					}
				}
		   });
		},
		editMenu:function(id){
			var title=id?"修改菜单":"新增菜单"
			$('#addDiv').dialog({
				title: title,
				width: 370,
				height: 210,
				closed: false,
				cache: false,
				href: '/menuAdmin/addMenuDialog.html'+(id?"?id="+id:""),
				modal: false,
				toolbar:[
					{
						iconCls:"icon-save",text:"保存",
						handler:that.saveResource
					},
					{
						iconCls:"icon-cancel",text:"关闭",
						handler:function(){
							$("#addDiv").dialog("close");
						}
					}
				]
			});
		}
	}
}
$(function(){
	var adminMenu=new AdminMenu();
	adminMenu.init();
	window.adminMenu=adminMenu;
});