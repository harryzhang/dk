$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchForm #searchButton").on("click",function(){
		$("#tt").datagrid('load',{
			'searchStr': $("#searchForm #searchStr").val(),
			'searchCuserName': $("#searchForm #searchCuserName").val()
		});
	});
	
	$("#searchForm #resetButton").on("click",function(){
		$("#searchForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin###################################################*/
	/*######################grid toolbar begin##############################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:add_community
					}
	          	];
	
	/*######################grid toolbar end##############################*/
	/*######################grid columns begin##############################*/
	var columns_tt = [
      			[
	 			 	{field:'id',title:'id',width:100,halign:"center", align:"left",hidden:true},
	 				{field:"cname",title:"事业部名称",width:180,align:"center"},
	 				{field:"cuserId",title:"彩之云员工ID",width:100,align:"center"},
	 				{field:"cusername",title:"真实姓名",width:100,align:"center"},
	 				{field:"cmobile",title:"手机号码",width:100,align:"center"},
	 				{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_edit(\''+row.id+'\');">修改</a>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	 					      str += '<a href="javascript:void(0);" onclick="delete_community(\''+row.id+'\');">删除</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
	/*######################grid columns end##############################*/
	$("#tt").datagrid({
		url:"/community/listCommunity.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_area').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:true,
		singleSelect:false,//配合根据状态限制checkbox
		autoRowHeight:true,
		striped:true,
		checkOnSelect:false,//配合根据状态限制checkbox
		selectOnCheck:false,//配合根据状态限制checkbox
		loadFilter : function(data){
			return {
				'rows' : data.datas,
				'total' : data.total,
				'pageSize' : data.pageSize,
				'pageNumber' : data.page
			};
		},
		
		pagination:true,
		showPageList:true,
		pageSize:20,
		pageList:[10,20,30],
		idField:"id",
		columns:columns_tt,
		toolbar:toolbar_tt,
		queryParams:{
			'searchStr': $("#searchForm #searchStr").val(),
			'searchCuserName': $("#searchForm #searchCuserName").val()
		},
		onLoadSuccess:function(data){//根据状态限制checkbox
			
		}
	});
	
	/*$(window).resize(function (){
		domresize();
	 }); */
/*##########################grid init end###################################################*/
});


function delete_community(id){
	var opt = $.messager.confirm("删除", '你确定要删除这个记录吗？', function(r){
		if(r == true){
			$.ajax({
				  type: 'POST',
				  dataType: 'json',
				  url: "/community/deleteCommunity.html?&rand=" + Math.random(),
				  data: {id:id},
				  success: function(data) {
				   		if(data.executeStatus == 1){
				   			$('#tt').datagrid('reload');
				   			$.messager.alert("提示","操作成功", "info");
				   		}else{
				   			$.messager.alert("提示","操作失败", "error");
				   		}
				  }
				});
		}
	});
}

/**
 * 新增事业部人员
 */
function add_community(){
	to_edit();
}

/**
 * 编辑事业部人员
 * @param id
 */
function to_edit(id){
	if(!id){
		id='';
	}
	var url = "/community/editCommunity.html?&rand=" + Math.random()+"&id="+id;
	$('#editDiv').dialog({
		title: "编辑事业部人员",
		width: 400,
		height: 300,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:save_community
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editDiv").dialog("close");
				}
		}]
	});
}

function save_community(){
	var  url ="/community/saveCommunity.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editFormCommunity").serialize(),
		 success: function(data){ 
			 if(data.executeStatus == 1){
				 $("#editDiv").dialog("close");
				 $('#tt').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("#tt").datagrid("reload");
}




/*##########################公用方法##begin############################*/
/**
 * 得到选中的行
 * @returns {String}
 */
function get_ids(){
	var ids = '';
	var rows = $('#tt').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据", "info");
		return false;
	}else{
		for(var i=0; i<rows.length; i++){
			ids += rows[i].loanId + ',';
		}
		ids = ids.substring(0, ids.length - 1);
		return ids;
	}
}
/**
 * 得到一条数据
 * @returns {String}
 */
function get_id(){
	var rows = $('#tt').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据","info");
		return false;
	}else if(rows.length > 1){
		$.messager.alert("提示","每次只能操作一条数据","info");
		return;
	}else{
		return rows[0].id;
	}
}

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('#tt').datagrid('resize',{  
		height:$("#body").height()-$('#search_area').height()-5,
		width:$("#body").width()
	});
	$('#search_area').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
/*##########################公用方法##end############################*/