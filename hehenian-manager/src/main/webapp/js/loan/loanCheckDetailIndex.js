$(function(){
	/*#############################################search form begin#################################*/		
	$("#searchForm #searchButton").on("click",function(){
		$("#tt").datagrid('load',{
			'checkItemName':$("#searchForm #search_checkItemName").val(),
			'status':$("#searchForm #search_checkItemStatus").combobox('getValue'),
			'valType':$("#searchForm #search_valType").combobox('getValue')
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
			text:"删除",
			handler:delete_detail
		}
	];
	/*######################grid toolbar end##############################*/
	/*######################grid columns begin##############################*/
	var columns_tt = [
      			[
	 			 	{field:'itemCheckId',title:'条目分值表ID',width:100,halign:"center", align:"left",hidden:true},
	 			 	{field:"checkItemName",title:"评分项名称",width:150,align:"center"},
	 			 	{field:"status",title:"评分项状态",width:120,align:"center",
	 					formatter: function(value,row,index){
	 	    				if (value){
	 	    					return value == 'T'? '有效' : value == 'F'? '失效':'<font style="color:blue;">异常数据</font>';
	 	    				}
	 					}
	 				},
	 				{field:"valType",title:"取值方式",width:120,align:"center",
	 					formatter: function(value,row,index){
	 	    				if (value){
	 	    					return value == '1'? '固定值' : value == '2'? '范围值':'<font style="color:blue;">异常数据</font>';
	 	    				}
	 					}
	 				},
	 				{field:"edit",title:"操作",width:130,align:"center",
	 					formatter:function(value,row,index){
	 					  var str="<a href='javascript:void(0);' onclick='update_detail("+row.itemCheckId+")'>编辑</a>";
	 					  return str;
	 					}
	 				},
	 				{field:"checkVal",title:"分值",width:150,align:"center"},
	 				{field:"startItemVal",title:"条目起始值",width:150,align:"center"},
	 				{field:"endItemVal",title:"条目截止值",width:120,align:"center"},
	 				{field:"fixItemVal",title:"固定值",width:120,align:"center"},
	 				{field:"coefficient",title:"系数",width:120,align:"center"},
	 				{field:"remark",title:"备注",width:150,align:"center"}
	 				
	 			]
	 	];
	/*######################grid columns end##############################*/
	
	$("#tt").datagrid({
		url:"/loancheck/detail/list.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_area').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:true,
		singleSelect:false,//配合根据状态限制checkbox
		autoRowHeight:true,
		striped:true,
		checkOnSelect:false,//配合根据状态限制checkbox
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
		idField:"itemCheckId",
		frozenColumns : [[{field:'ck',checkbox:true}]],
		columns:columns_tt,
		toolbar:toolbar_tt,
		queryParams:{
			'checkItemName':$("#searchForm #search_checkItemName").val(),
			'status':$("#searchForm #search_checkItemStatus").combobox('getValue'),
			'valType':$("#searchForm #search_valType").combobox('getValue')
		}
	});
	
/*##########################grid init end###################################################*/
});

/**
 * 
 * @param itemCheckId
 */
function update_detail(itemCheckId){
	var url = "/loancheck/detail/toupdate.html?&rand=" + Math.random()+"&id="+itemCheckId;
	$('#updateCheckDetail').dialog({
		title: "编辑条目",
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
						$("#updateCheckDetail").dialog("close");
					}
				}]
	});
}

/**
 * 保存
 */
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
					 $("#updateCheckDetail").dialog("close");
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
 * 删除条目
 */
function delete_detail(){
	var ids = get_ids();
	if(ids){
		$.messager.confirm("提示", "确定要删除吗?", function(b){
			if(b){
				delData(ids);
			}
		});
	}
}

/**
 * 删除选中的数据
 */
function delData(ids) {
   data = {"ids":ids};
   $.ajax({
	  type: 'POST',
	  dataType: 'json',
	  url: "/loancheck/detail/delete.html?&rand=" + Math.random(),
	  data: data,
	  success: function(data) {
	   		if(data.executeStatus == 1){
	   			$('#tt').datagrid('reload');
	   			$('#tt').datagrid('unselectAll');
	   			$.messager.alert("提示","操作成功","info");
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
			ids += rows[i].itemCheckId + ',';
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
		return rows[0].itemCheckId;
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