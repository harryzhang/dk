$(function(){
/*#############################################search form begin#################################*/	
	$("#searchForm #search_product").combobox({
		url:"/loan/combox/prod/type.html?&rand=" + Math.random(), 
		panelHeight:'83', 
		valueField: 'id',
		textField: 'name'
	});
	
	$("#searchForm #searchButton").on("click",function(){
		$("#tt").datagrid('load',{
			'prodId':$("#searchForm #search_product").combobox('getValue'),
			'status':$("#searchForm #search_status").combobox('getValue')
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
			text:"修改",
			handler:edit_sett
		} ,'-',{
			iconCls: 'icon-remove',
			text:'删除',
			handler:delete_sett
		}
	];
	/*######################grid toolbar end##############################*/
	/*######################grid columns begin##############################*/
	var columns_tt = [
      			[
	 			 	{field:'id',title:'id',width:100,halign:"center", align:"left",hidden:true},
	 			 	{field:'prodId',title:'prodId',width:100,halign:"center", align:"left",hidden:true},
	 				{field:"name",title:"方案名称",width:250,align:"center"},
	 				{field:"edit",title:"操作",width:250,align:"center",
	 					formatter:function(value,row,index){
	 					  var str="<a href='javascript:void(0);' onclick='add_fee("+row.id+")'>添加规则</a> ";
	 					  return str;
	 					}
	 				},
	 				{field:"code",title:"方案编码",width:200,align:"center"},
	 				{field:"repayWay",title:"还款方式",width:200,align:"center"},
	 				{field:"defaultAnnualRate",title:"默认借款年利率",width:120,align:"center"},
	 				{field:"receiptWay",title:"回款方式",width:150,align:"center"},
	 				{field:"aheadSettlePeriod",title:"提前结清顺延期限",width:190,align:"center"},
	 				{field:"status",title:"status状态",width:100,align:"center",
	 					formatter:function(value,row,index){
		 					  return value == 'E' ? "启用" : value == 'D' ? "禁用" : value == 'P' ? "发布":"<font style='color:red;'>错误信息</font>"; 
		 					}
	 				}
	 			]
	 	];
	/*######################grid columns end##############################*/
	
	$("#tt").datagrid({
		url:"/loan/product/sett/list.html?&rand=" + Math.random(),
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
			'prodId':$("#searchForm #search_product").combobox('getValue'),
			'status':$("#searchForm #search_status").combobox('getValue')
		},
		view:detailview,
		detailFormatter: function(rowIndex, rowData){
			var returnStr='';
			var value = rowData.loanFeeRuleDoList;
			if(value.length != 0){
				for(var i = 0;i < value.length;i++){
					var isInclude = value[i].isInclude == 'T' ? '包含' :value[i].isInclude == 'F' ? '不包含' :'<font style="color:red;">错误信息</font>';
					var baseAmountType = value[i].baseAmountType == '1' ? '借款金额' : value[i].baseAmountType == '2' ? '剩余本金' :'';
					var isInitRepayPlanUse = value[i].isInitRepayPlanUse == 'F' ? '否': value[i].isInitRepayPlanUse == 'F' ? '是':'<font style="color:red;">错误信息</font>';
					returnStr +='<table style="width:95%;margin:10px;border:1px dashed #ccc;">' +
					'<thead ><span style="color:red;font-size:10px;"><b>规则'+(i+1)+'信息</b>&nbsp;'+
					'|<a href="javascript:void(0);" onclick="edit_fee('+value[i].id+')">修改</a>|'+
					'<a href="javascript:void(0);" onclick="delete_fee('+value[i].id+')">删除</a>|</span></thead>'+
					'<tr style="width:100%;height:20px;">' +
						'<td style="border:0;width:25%;font-size:10px;"><b>规则名称: </b>' + value[i].name + '</td>' +
						'<td style="border:0;width:25%;font-size:10px;"><b>规则类型: </b>' + value[i].type + '</td>' +
						'<td style="border:0;width:25%;font-size:10px;"><b>收取方式: </b>' + value[i].gatherWay + '</td>' +
						'<td style="border:0;width:25%;font-size:10px;"><b>是否在借款利率里: </b>' + isInclude + '</td>' +
					'</tr>'+
					'<tr style="width:100%;height:20px;">' +
						'<td style="border:0;width:25%;font-size:10px;"><b>乘数: </b>' + baseAmountType + '</td>' +
						'<td style="border:0;width:25%;font-size:10px;"><b>收取比率: </b>' + value[i].gatherRate + '</td>' +
						'<td style="border:0;width:25%;font-size:10px;"><b>费用金额: </b>' + value[i].feeAmount + '</td>' +
						'<td style="border:0;width:25%;font-size:10px;"><b>是否在生成还款计划时用: </b>' + isInitRepayPlanUse + '</td>' +
					'</tr>'+
					'</table>';
				}
			}else{
				returnStr +='<table style="width:95%;margin:10px;">' +
				'<thead ><span style="color:red;font-size:10px;"><b>暂无规则信息</b></span></thead>'+
				'</table>';	
			}
			return returnStr;
		},
		
	});
	
	
/*##########################grid init end###################################################*/
});

/**
 * 编辑方案信息
 */
/**
 * 修改
 */
function edit_sett(){
	var id,url = "/loan/product/sett/toedit.html?&rand=" + Math.random();
	var id = get_id();
	if(id){
		showDialog(id,url);
	}
}

function showDialog(id,url){
	url += "&id="+id;
	$('#editDiv').dialog({
		title: "修改方案",
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

function save(){
	var  url ="/loan/product/sett/save.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editFormSett").serialize(),
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
/**
 * 删除方案信息
 */
function delete_sett(){
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
	  url: "/loan/product/sett/delete.html?&rand=" + Math.random(),
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

/**
 * 添加方案规则
 * @param id
 */
function add_fee(id){
	var url = "/loan/product/fee/toadd.html?&rand=" + Math.random()+"&settId="+id;
	$('#editFee').dialog({
		title: "新增规则",
		width: 400,
		height: 350,
		closed: false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[{
					iconCls:"icon-save",text:"保存",
					handler:save_fee
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editFee").dialog("close");
					}
				}]
	});
}

/**
 * 修改规则
 */
function edit_fee(id){
	var url = "/loan/product/fee/toedit.html?&rand=" + Math.random()+"&id="+id;
	$('#editFee').dialog({
		title: "修改规则",
		width: 400,
		height: 350,
		closed: false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[{
					iconCls:"icon-save",text:"保存",
					handler:save_fee
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editFee").dialog("close");
					}
				}]
	});
}

/**
 * 保存方案规则信息
 */
function save_fee(){
	var  url ="/loan/product/fee/save.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editFormFee").serialize(),
		 success: function(data){ 
			 if(data.executeStatus == 1){
				 $("#editFee").dialog("close");
				 $('#tt').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}
/**
 * 删除规则
 */
function delete_fee(id){
	$.messager.confirm("提示", "确定要删除吗?", function(b){
		if(b){
			deleFee(id);
		}
	});
}

function deleFee(ids){
	data = {"ids":ids};
	$.ajax({
		  type: 'POST',
		  dataType: 'json',
		  url: "/loan/product/fee/delete.html?&rand=" + Math.random(),
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