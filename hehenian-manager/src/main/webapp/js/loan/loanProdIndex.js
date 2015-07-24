$(function(){
/*#############################################search form begin#################################*/		
	 $("#searchForm #searchButton").on("click",function(){
			$("#tt").datagrid('load',{
				'name': $("#searchForm #search_code").val(),
				'code': $("#searchForm #search_name").val(),
				'guarantee':$("#searchForm #search_guarantee").combobox('getValue'),
				'mortgage':$("#searchForm #search_mortgage").combobox('getValue'),
				'retaLock':$("#searchForm #search_retaLock").combobox('getValue'),
				'status':$("#searchForm #search_status").combobox('getValue')
			});
		});
		
		$("#searchForm #resetButton").on("click",function(){
			$("#searchForm").form('reset');
		});
		
/*#############################################search form begin#################################*/		
/*##########################grid init begin###################################################*/
	/*######################grid toolbar begin##############################*/
	var toolbar_tt = [
	    {
			iconCls:"icon-add",
			text:"增加",
			handler:add_prod
		} ,'-',{
			iconCls: 'icon-edit',
			text:'修改',
			handler:update_prod
		},'-',{
			iconCls: 'icon-remove',
			text:'删除',
			handler:delete_prod
		}
	];
	/*######################grid toolbar end##############################*/
	
	/*######################grid columns begin##############################*/
	var columns_tt = [
      			[
	 			 	{field:'id',title:'ID',width:100,halign:"center", align:"left",hidden:true},
	 				{field:"name",title:"名称",width:150,align:"center"},
	 				{field:"edit",title:"操作",width:300,align:"center",
	 					formatter:function(value,row,index){
	 					  var str="<a href='javascript:void(0);' onclick='select_detail("+row.id+")'>产品明细</a>";	 					  			
	 					  return str;
	 					}
	 				},
	 				{field:"code",title:"产品编码",width:100,align:"center"},
	 				{field:"minLines",title:"最低额度",width:120,align:"center"},
	 				{field:"maxLines",title:"最高额度",width:120,align:"center"},
	 				{field:"loanIssue",title:"贷款期限类型",width:140,align:"center"},
	 				{field:"guarantee",title:"是否需担保",width:120,align:"center",
	 					formatter: function(value,row,index){
	 	    				if (value){
	 	    					return value == '0'? '否' : value == '1'? '是':'<font style="color:blue;">异常数据</font>';
	 	    				}
	 					}
	 				},
	 				{field:"mortgage",title:"是否需抵押",width:120,align:"center",
	 					formatter: function(value,row,index){
	 	    				if (value){
	 	    					return value == '0'? '否' : value == '1'? '是':'<font style="color:blue;">异常数据</font>';
	 	    				}
	 					}
	 				},
	 				{field:"retaLock",title:"利率锁定",width:120,align:"center",
	 					formatter: function(value,row,index){
	 	    				if (value){
	 	    					return value == '0'? '否' : value == '1'? '是':'<font style="color:blue;">异常数据</font>';
	 	    				}
	 					}
	 				},
	 				{field:"status",title:"状态",width:120,align:"center",
	 					formatter: function(value,row,index){
	 	    				if (value){
	 	    					return value == 'T'? '有效' : value == 'F'? '失效':'<font style="color:blue;">异常数据</font>';
	 	    				}
	 					}
	 				},
	 				{field:"publishCode",title:"渠道编码",width:140,align:"center"},
	 				{field:"refeCode",title:"推荐奖励",width:140,align:"center"},
	 				{field:"remark",title:"备注",width:250,align:"center"}
	 				
	 			]
	 	];
	/*######################grid columns end##############################*/
	
	$("#tt").datagrid({
		url:"/loan/product/list.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_area').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		singleSelect:false,
		autoRowHeight:true,
		striped:true,
		pageSize:20,
		loadFilter : function(data){
			return {
				'rows' : data.datas,
				'total' : data.total,
				'pageSize' : data.pageSize,
				'pageNumber' : data.page
			};
		},
		pageList:[10,20,30],
		idField:"id",
		frozenColumns : [[{field:'ck',checkbox:true}]],
		columns:columns_tt,
		toolbar:toolbar_tt,
		queryParams:{
			'name': $("#searchForm #search_code").val(),
			'code': $("#searchForm #search_name").val(),
			'guarantee':$("#searchForm #search_guarantee").combobox('getValue'),
			'mortgage':$("#searchForm #search_mortgage").combobox('getValue'),
			'retaLock':$("#searchForm #search_retaLock").combobox('getValue'),
			'status':$("#searchForm #search_status").combobox('getValue')
		}
		
	});
	
	
/*##########################grid init end###################################################*/
});

/**
 * 新增
 */
function add_prod(){
	var url = "/loan/product/toedit.html?&rand=" + Math.random();
	showDialog(null,url);
}

/**
 * 修改
 */
function update_prod(){
	var id,url = "/loan/product/toedit.html?&rand=" + Math.random();
	var id = get_id();
	if(id){
		showDialog(id,url);
	}
}

function showDialog(data,url){
	var title = "新增产品";
	if(data != null ){
		title = "修改产品";
		url += "&id="+data;
	}
	$('#editDiv').dialog({
		title: title,
		width: 400,
		height: 420,
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
	var  url ="/loan/product/save.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 onSubmit: function(param){    
			 /*param.p1 = 'value1';    
	        param.p2 = 'value2';*/    
		 },
		 data:$("#editForm").serialize(),
		 success: function(data){ 
			 if(data.executeStatus == 1){
				 $('#tt').datagrid('reload');
				 $("#editDiv").dialog("close");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}
/**
 * 删除产品
 */
function delete_prod(){
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
	  url: "/loan/product/delete.html?&rand=" + Math.random(),
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

function select_detail(id){
	var url = "/loan/product/detail.html?&rand=" + Math.random()+"&id="+id;
	$('#prodDetail').dialog({
		title: "产品明细",
		width: 800,
		height: 550,
		closed: false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#prodDetail").dialog("close");
					}
				}]
	});
}

/****************产品前提*********************/
/**
 * 增加产品前提
 */
function add_prov(id){
	var url = "/loan/product/prov/toedit.html?&rand=" + Math.random()+"&prodId="+id;
	$('#editProv').dialog({
		title: "编辑产品前提",
		width: 400,
		height: 420,
		closed: false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[{
					iconCls:"icon-save",text:"保存",
					handler:save_prov
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editProv").dialog("close");
					}
				}]
	});
}

function save_prov(){
	var  url ="/loan/product/prov/save.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editFormProv").serialize(),
		 success: function(data){ 
			 if(data.executeStatus == 1){
				 $("#editProv").dialog("close");
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}

/***************产品方案*************************/
/**
 * 添加产品方案
 */
function add_sett(id){
	var url = "/loan/product/sett/toadd.html?&rand=" + Math.random()+"&prodId="+id;
	$('#editSett').dialog({
		title: "新增产品方案",
		width: 400,
		height: 320,
		closed: false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[{
					iconCls:"icon-save",text:"保存",
					handler:save_sett
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editSett").dialog("close");
					}
				}]
	});
}

function save_sett(){
	var  url ="/loan/product/sett/save.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editFormSett").serialize(),
		 success: function(data){ 
			 if(data.executeStatus == 1){
				 $("#editSett").dialog("close");
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
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
			ids += rows[i].id + ',';
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
}
/*##########################公用方法##end############################*/