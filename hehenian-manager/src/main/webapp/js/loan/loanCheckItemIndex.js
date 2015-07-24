$(function(){
	
/*#############################################search form begin#################################*/		
	$("#searchForm #searchButton").on("click",function(){
		$("#tt").datagrid('load',{
			'checkItemName':$("#searchForm #search_checkItemName").val(),
			'status':$("#searchForm #search_checkItemStatus").combobox('getValue')
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
			iconCls:"icon-add",
			text:"新增",
			handler:add_item
			
		} ,'-',{
			iconCls: 'icon-edit',
			text:'修改',
			handler:update_item
		} ,'-',{
			iconCls: 'icon-remove',
			text:'删除',
			handler:delete_item
		}
	];
	/*######################grid toolbar end##############################*/
	/*######################grid columns begin##############################*/
	var columns_tt = [
      			[
	 			 	{field:'checkId',title:'checkId',width:100,halign:"center", align:"left",hidden:true},
	 				{field:"checkItemName",title:"评分项名称",width:150,align:"center"},
	 				{field:"edit",title:"操作",width:160,align:"center",
	 					formatter:function(value,row,index){
	 					  var str="<a href='javascript:void(0);' onclick='add_detail("+row.checkId+")'>增加条目</a>";
	 					  return str;
	 					}
	 				},
	 				{field:"checkItemCode",title:"评分项编码",width:150,align:"center"},
	 				{field:"checkType",title:"评分类别",width:150,align:"center"},
	 				{field:"status",title:"状态",width:120,align:"center",
	 					formatter: function(value,row,index){
	 	    				if (value){
	 	    					return value == 'T'? '有效' : value == 'F'? '失效':'<font style="color:blue;">异常数据</font>';
	 	    				}
	 					}
	 				},
	 				{field:"version",title:"版本号",width:120,align:"center"},
	 				{field:"remark",title:"备注",width:150,align:"center"}
	 			]
	 	];
	/*######################grid columns end##############################*/
	
	$("#tt").datagrid({
		url:"/loancheck/item/list.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_area').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:true,
		singleSelect:false,//配合根据状态限制checkbox
		autoRowHeight:true,
		striped:true,
		//checkOnSelect:false,
		pageSize:20,
		showPageList:true, 
		pagination:true,
		loadFilter : function(data){
			return {
				'rows' : data.datas,
				'total' : data.total,
				'pageSize' : data.pageSize,
				'pageNumber' : data.page
			};
		},
		pageList:[10,20,30],
		idField:"checkId",
		frozenColumns : [[{field:'ck',checkbox:true}]],
		columns:columns_tt,
		toolbar:toolbar_tt,
		queryParams:{
			'checkItemName':$("#searchForm #search_checkItemName").val(),
			'status':$("#searchForm #search_checkItemStatus").combobox('getValue')
		}
	});
	
/*##########################grid init end###################################################*/
});

/**
 * 新增
 */
function add_item(){
	var url = "/loancheck/item/toedit.html?&rand=" + Math.random();
	showDialog(null,url);
}

/**
 * 修改
 */
function update_item(){
	var id,url = "/loancheck/item/toedit.html?&rand=" + Math.random();
	var id = get_id();
	if(id){
		showDialog(id,url);
	}
}

function showDialog(data,url){
	var title = "新增";
	if(data != null ){
		title = "修改";
		url += "&checkId="+data;
	}
	$('#editDiv').dialog({
		title: title,
		width: 400,
		height: 320,
		closed: false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[{
					iconCls:"icon-save",text:"保存",
					handler:save
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editDiv").dialog("close");
					}
				}]
	});
}
/**
 * 保存
 */
function save(){
	var b = $('#editFormItem').form('validate');
	if(b){
		var  url ="/loancheck/item/save.html?&rand=" + Math.random();
		 $.ajax({   
			 type: 'POST',
			 dataType: 'json',
			 url: url,  
			 data:$("#editFormItem").serialize(),
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
	}else{
		return;
	}
}

/**
 * 增加条目
 * @param checkId
 */
function add_detail(checkId){
	var url = "/loancheck/detail/toadd.html?&rand=" + Math.random()+"&checkId="+checkId;
	$('#addCheckDetail').dialog({
		title: "新增条目",
		width: 400,
		height: 320,
		closed: false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[{
					iconCls:"icon-save",text:"保存",
					handler:save_detail
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#addCheckDetail").dialog("close");
					}
				}]
	});
}

function save_detail(){
	var b = $('#editFormDetail').form('validate');
	if(b){
		var  url ="/loancheck/detail/save.html?&rand=" + Math.random();
		 $.ajax({   
			 type: 'POST',
			 dataType: 'json',
			 url: url,  
			 data:$("#editFormDetail").serialize(),
			 success: function(data){ 
				 if(data.executeStatus == 1){
					 $("#addCheckDetail").dialog("close");
					 $('#tt').datagrid('reload');
					 $.messager.alert("提示","操作成功","info");
				 }else{
					 $.messager.alert("提示","操作失败","error");
				 }   
			 } 
		});
	}else{
		return;
	}
	
}

/**
 * 删除
 */
function delete_item(){
	var id = get_id();
	if(id){
		$.messager.confirm("提示", "确定要删除吗?", function(b){
			if(b){
				delData(id);
			}
		});
	}
}

/**
 * 删除选中的数据
 */
function delData(id) {
   data = {"id":id};
   $.ajax({
	  type: 'POST',
	  dataType: 'json',
	  url: "/loancheck/item/delete.html?&rand=" + Math.random(),
	  data: data,
	  success: function(data) {
	   		if(data.executeStatus == 1){
	   			$('#tt').datagrid('reload');
	   			$('#tt').datagrid('unselectAll');
	   			$.messager.alert("提示","操作成功","info");
	   		}else if(data.executeStatus == 2){
	   			$.messager.alert("提示",data.msg,"warning");
	   		}else{
	   			$.messager.alert("提示","操作失败", "error");
	   		}
	  }
	});
}

/*##########################公用方法##begin############################*/
/**
 * 得到选中的行
 * @returns {String}
 */
function get_ids(){
	var ids = '';
	var rows = $('#tt').datagrid('getSelections');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据", "info");
		return false;
	}else{
		for(var i=0; i<rows.length; i++){
			ids += rows[i].checkId + ',';
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
	var rows = $('#tt').datagrid('getSelections');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据","info");
		return false;
	}else if(rows.length > 1){
		$.messager.alert("提示","每次只能操作一条数据","info");
		return;
	}else{
		return rows[0].checkId;
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
}
/*##########################公用方法##end############################*/