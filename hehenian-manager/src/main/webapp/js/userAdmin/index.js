var UserMenu=function(){
	var grid;
	var that;
	return {
		init:function(){
			that=this;
			$("#tableGrid").datagrid({
					url:"/menuAdmin/userDatas.html",
					height:550,
					pagination:true,
					fitColumns:true,
					rownumbers:true,
					singleSelect:true,
					autoRowHeight:true,
					striped:true,
					pageSize:20,
					pageList:[20],
					idField:"id",
					columns:[[
					          {field:"username",title:"用户名",width:150,align:"center"},
					          {field:"nickname",title:"昵称",width:100,align:"center"},
					          {field:"edit",title:"操作",width:80,align:"center",formatter: function(value,row,index){
					          	  return "<a href='javascript:void(0);' onclick='userMenu.editUser("+row.id+")'>编辑</a> <a href='javascript:void(0);' onclick='userMenu.resetPwd("+row.id+")'>重置密码</a> <a href='javascript:void(0);' onclick='userMenu.deleteUser("+row.id+")'>删除</a>";
					          }
					      }
						]],
					toolbar:[{
						iconCls:"icon-add",
						text:"增加",
						handler:that.editUser
					}]
				});
		},
		editUser:function(id){
			$('#addDiv').dialog({
				title: '新增人员',
				width: 370,
				height: 170,
				closed: false,
				cache: false,
				href: '/menuAdmin/addUserDialog.html'+(id?"?userId="+id:""),
				modal: false,
				toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:function(){
						var userId=$("#userId").val();
						var name=$.trim($("#name").val());
						var nickName=$.trim($("#nickName").val());
						var password=$.trim($("#password").val());
						if(!name){
							$.messager.alert("账号空","账号不能为空");
							return;
						}
						if(!nickName){
							$.messager.alert("昵称空","昵称不能为空");
							return;
						}
						if(!password){
							$.messager.alert("密码空","密码不能为空");
							return;
						}
						var data={username:name,nickname:nickName,password:password};
						if(userId){
							data.id=userId;
						}
						$.ajax({
							type:'post',
							url:'/menuAdmin/addUser.html',
							data:data,
							success:function(ret){
								if(ret.ret==1){
								$('#addDiv').dialog("close");
								$('#tableGrid').datagrid('reload');
								}else{
									$.messager.alert("新增用户失败");
								}
						    }
						});
					}
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
		resetPwd:function(id){
			$.messager.confirm("消息","确认重置密码为111111",function(r){
			   if(r){

			   }
			});
		},
		deleteUser:function(id){
			if(!id){
				$.messager.alert("消息","id不能为空");
				return;
			}
			$.messager.confirm("消息","确认删除该模块吗，删除后不可恢复",function(r){
			  if(r){
			    $.ajax({
					url:"/menuAdmin/deleteUser/"+id+".html",
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
		}
	}
}
$(function(){
	var userMenu=new UserMenu();
	userMenu.init();
	window.userMenu=userMenu;
});