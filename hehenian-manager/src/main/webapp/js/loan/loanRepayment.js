$(function(){
/*#############################################search form begin#################################*/	
	
	$("#searchForm #loanAll").on("click",function(){
		$("#tt").datagrid('load',{
			'searchType': 'loanAll'
		});
	});
	$("#searchForm #loanToday").on("click",function(){
		$("#tt").datagrid('load',{
			'searchType': 'loanToday'
		});
	});
	$("#searchForm #loanToday3").on("click",function(){
		$("#tt").datagrid('load',{
			'searchType': 'loanToday3'
		});
	});
	$("#searchForm #loanAdd3").on("click",function(){
		$("#tt").datagrid('load',{
			'searchType': 'loanAdd3'
		});
	});
	$("#searchForm #loanAdd").on("click",function(){
		$("#tt").datagrid('load',{
			'searchType': 'loanAdd'
		});
	});
	
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin###################################################*/

	var toolbar_tt = [
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[
	 			 	{field:'loanId',title:'loanId',width:30,halign:"center", align:"left",hidden:true},
	 				{field:"orderCode",title:"订单号",width:180,align:"center"},
	 				{field:"edit",title:"操作",width:80,align:"center",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_edit('+row.loanId+');">明细</a>';
	 					  return str;
	 					}
	 				},
	 				{field:"realName",title:"借款人",width:120,align:"center"},
	 				{field:"loanTime",title:"放款日期",width:120,align:"center"},
	 				{field:"repayDate",title:"还款日",width:120,align:"center"},
	 				{field:"stillRepayAll",title:"还款金额",width:140,align:"center"},
	 				{field:"repayStatus",title:"还款情况",width:140,align:"center",
	 					formatter:function(value,row,index){
		 					   return value == '1' ?" <font style='color:2EB017;'>已还款</font>" : "<font style='color:red;'>未还歀</font>" ;  
		 					}
	 				},
	 				{field:"name",title:"产品类型",width:140,align:"center"},
	 				{field:"loanType",title:"贷款类型",width:110,align:"center",
	 					formatter:function(value,row,index){
		 					   return value == '1' ? '信用贷款' : value == '2' ? '抵押贷款' : value == '3' ? '担保贷款' :"<font style='color:red;'>错误信息</font>";  
		 					}
	 				},
	 				
	 				{field:"mobile",title:"手机号",width:150,align:"center"},
	 				{field:"loanAmount",title:"借款金额",width:180,align:"center"},
	 				{field:"loanPeriod",title:"借款期限",width:100,align:"center"},
	 				{field:"annualRate",title:"年利率",width:130,align:"center",
	 					formatter:function(value,row,index){
		 					   return value+"%";  
		 					}
	 				},
	 				{field:"repayStyle",title:"还款方式",width:140,align:"center",
	 					formatter:function(value,row,index){
		 					   return value == '1' ? '正常还款' : value == '2' ? '代偿' : value == '3' ? '提前结清' :"<font style='color:red;'>错误信息</font>";  
		 					}
	 				}
	 				
	 			]
	 	];
/*######################grid columns end##############################*/
		
	$("#tt").datagrid({
		url:"/loanRepayment/loanRepaymentList.html?&rand=" + Math.random(),
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
			'searchType': 'loanAll'
		},
		onLoadSuccess:function(data){//根据状态限制checkbox
			
		}
	});
/*##########################grid init end###################################################*/
});



/**
 * 订单明细
 * @param id
 */
function to_edit(id){
	var url = "/loan/toupdate.html?&rand=" + Math.random()+"&loanId="+id;
	$('#editDiv').dialog({
		title: "编辑订单",
		width: 400,
		height: 350,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:save_loan
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editDiv").dialog("close");
				}
		}]
	});
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