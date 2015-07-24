var AuthorityIndex=function(){
	var grid;
	var that;
	return {
		init:function(){
			that=this;
			grid=$("#tableGrid").datagrid({
				url:"/authority/authoritiesList.html",
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
					{field:"name",title:"名字",width:100,align:"center"},
					{field:"authDesc",title:"备注",width:250,align:"center"},
					{field:"edit",title:"操作",width:80,align:"center",formatter:function(value,row,index){
					  var str="<a href='javascript:void(0);' onclick='authorityIndex.editAuthority("+row.id+")'>修改</a> ";
					  str+="<a href='javascript:void(0);' onclick='authorityIndex.deleteAuthority("+row.id+")'>删除</a>　";
					  str+="<a href='javascript:void(0);' onclick='authorityIndex.setRoles("+row.id+")'>设置角色</a>";
					  return str;
					}}
				]
				],
				onDblClickRow: function (index, row) {
					$('#setAuthorityDiv').dialog({
						title: '设置权限',
						width: 1050,
						height: 450,
						closed: false,
						cache: false,
						href: "/authority/bindResources/"+row.id+".html",
						modal: true,
						toolbar:[{
							iconCls:"icon-cancel",text:"关闭",
							handler:function(){
							$("#setAuthorityDiv").dialog("close");
							}
						}]
					});
				},
				toolbar:[{
					iconCls:"icon-add",
					text:"增加",
					handler:that.editAuthority
				}]
			});
		},
		deleteAuthority:function(id){
			if(!id){
				$.messager.alert("消息","id不能为空");
				return;
			}
			$.messager.confirm("消息","确认删除该权限吗，删除后不可恢复",function(r){
			   if(r){
			     $.ajax({
					url:"/authority/deleteOneAuth/"+id+".html",
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
		editAuthority:function(id){
			$('#addDiv').dialog({
				title: '修改权限',
				width: 370,
				height: 150,
				closed: false,
				cache: false,
				href: '/authority/editAuthority.html'+(id?"?authId="+id:""),
				modal: false,
				toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:that.saveAuthority
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
		saveAuthority:function(){
			var name=$.trim($("#name").val());
			var desc=$.trim($("#desc").val());
			var id=$("#authId").val();
			if(!name){
				$.messager.alert("消息","权限名称不能为空");
				return;
			}
			if(!/ROLE_(.*)/.test(name)){
				$.messager.alert("不符合规则","权限名必须是ROLE_开头");
				return;
			}
			var data={};
			data.name=name;
			data.authDesc=desc;
			if(id){
				data.id=id;
			}
			$.ajax({
				url:"/authority/saveAuthority.html",
				type:"post",
				data:data,
				success:function(ret){
					if(ret.ret==1){
						$("#addDiv").dialog("close");
						$.messager.alert("成功","修改成功");
						$('#tableGrid').datagrid('reload');
					}else{
						$.messager.alert("失败","修改失败，请稍后再试");
					}
				}
			});
		},
		setRoles:function(id){
			$('#setRolesDiv').dialog({
				title: '设置访问角色',
				width: 1050,
				height: 450,
				closed: false,
				cache: false,
				href: "/authority/setRolesIntoAuthorityDialog/"+id+".html",
				modal: true,
				toolbar:[{
					iconCls:"icon-cancel",text:"关闭",
					handler:function(){
					  $("#setRolesDiv").dialog("close");
					}
				}]
			});
			
		}
	}
}
$(function(){
	var authorityIndex=new AuthorityIndex();
	authorityIndex.init();
	window.authorityIndex=authorityIndex;
});