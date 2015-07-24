var ModuleIndex=function(){
	var grid;
	var that;
	return {
		init:function(){
			that=this;
			grid=$("#tableGrid").datagrid({
					url:"/module/moduleDatas.html",
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
					          {field:"name",title:"名字",width:100,align:"center"},
					          {field:"module",title:"模块",width:100,align:"center"},
					          {field:"moduleDesc",title:"备注",width:200,align:"center"},
					          {field:"edit",title:"操作",width:80,align:"center",formatter:function(value,row,index){
					          	return "<a href='javascript:void(0);' onclick='module.editModule("+row.id+")'>修改</a>  <a href='javascript:void(0);' onclick='module.deleteModule("+row.id+")'>删除</a>";
					          }}
						]],
					toolbar:[
					{
						iconCls:"icon-add",
						text:"增加",
						handler:that.editModule
					}]
				});
		},
		editModule:function(id){
			$('#addDiv').dialog({
				title: '修改模块',
				width: 370,
				height: 170,
				closed: false,
				cache: false,
				href: '/module/editModuleDialog.html'+(id?"?moduleId="+id:""),
				modal: false,
				toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:that.saveModule
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
		deleteModule:function(id){
			if(!id){
				$.messager.alert("消息","id不能为空");
				return;
			}
			$.messager.confirm("消息","确认删除该模块吗，删除后不可恢复",function(r){
			   if(r){
			     $.ajax({
					url:"/module/deleteModule/"+id+".html",
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
		saveModule:function(){
			var name=$.trim($("#name").val());
			var module=$.trim($("#module").val());
			var desc=$.trim($("#desc").val());
			var id=$("#moduleId").val();
			if(!name){
				$.messager.alert("消息","模块名称不能为空");
				return;
			}
			if(!module){
				$.messager.alert("消息","模块编码不能为空");
				return;
			}
			var data={name:name,module:module,moduleDesc:desc};
			if(id){
				data.id=id;
			}
			$.ajax({
				url:"/module/saveModule.html",
				type:"post",
				data:data,
				success:function(ret){
					if(ret.ret==1){
						$('#addDiv').dialog("close");
						$('#tableGrid').datagrid('reload');
					}else{
						$.messager.alert("失败","新增模块失败");
					}
				}
			});
		}
	}
}
$(function(){
	var module=new ModuleIndex();
	module.init();
	window.module=module;
});