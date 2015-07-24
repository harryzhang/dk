$(function(){
/*#############################################search form begin#################################*/	
	$("#searchForm #search_productCode").combobox({
		url:"/loan/combox/prod/type.html?&rand=" + Math.random(), 
		panelHeight:'90', 
		valueField: 'code',
		textField: 'name'
	});
	
	$("#searchForm #search_loanStatus").combobox({
		url:"/loan/combox/status.html?&rand=" + Math.random(), 
		panelHeight:'90', 
		valueField: 'name',
		textField: 'desc'
	});
	
	$("#searchForm #searchButton").on("click",function(){
		$("#tt").datagrid('load',{
			'searchStr': $("#searchForm #searchStr").val(),
			'productCode':$("#searchForm #search_productCode").combobox('getValue'),
			'loanType':$("#searchForm #search_loanType").combobox('getValue'),
			'loanStatus':$("#searchForm #search_loanStatus").combobox('getValue'),
			'startDate':$("#searchForm #search_startDate").datebox('getValue'),
			'endDate':$("#searchForm #search_endDate").datebox('getValue'),
			'processNextStep':$("#searchForm #search_processNextStep").combobox('getValue')
			
		});
	});
	
	$("#searchForm #resetButton").on("click",function(){
		$("#searchForm").form('reset');
	});
	
	
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin###################################################*/
	/*######################grid toolbar begin##############################*/
	/*
	var toolbar_tt = [
	    {
			iconCls:"icon-ok",
			text:"审核通过",
			handler:audit_one
		} ,'-',{
			iconCls: 'icon-no',
			text:'拒绝',
			handler:audit_two
		},'-',{
			iconCls: 'icon-stop',
			text:'作废',
			handler:audit_three
		},'-',{
			iconCls: 'icon-excel',
			text:'导出',
			handler:export_excel
		},'-',{
			iconCls: 'icon-edit',
			text:'同步放款数据',
			handler:syn_loan_data
		}
	];
	*/
	var toolbar_tt = [
	          	    {
	          			iconCls: 'icon-excel',
	          			text:'导出',
	          			handler:export_excel
	          		},'-',{
	          			iconCls: 'icon-edit',
	          			text:'同步放款数据',
	          			handler:syn_loan_data
	          		}
	          	];
	
	/*######################grid toolbar end##############################*/
	/*######################grid columns begin##############################*/
	var columns_tt = [
      			[
	 			 	{field:'loanId',title:'loanId',width:30,halign:"center", align:"left",hidden:true},
	 				{field:"orderCode",title:"订单号",width:180,align:"center"},
	 				{field:"edit",title:"操作",width:80,align:"center",
	 					formatter:function(value,row,index){
	 						//"+row.loanId+","+"\'"+row.loanStatus+"\'"+"
	 					  var str= '<a href="javascript:void(0);" onclick="to_detail('+row.loanId+',\''+row.loanStatus+'\');">明细</a>';
	 					  //var status = row.loanStatus;
	 					  //if(status == 'PENDING' || status == 'PROCESSING'){
	 					  //	 str += "| <a href='javascript:void(0);' onclick='to_edit("+row.loanId+")'>编辑</a> ";
	 					  //}
	 					  return str;
	 					}
	 				},
	 				{field:"loanStatusDesc",title:"订单状态",width:100,align:"center"},
	 				{field:"processCurrentStep",title:"当前节点",width:100,align:"center"},
	 				{field:"processNextStep",title:"下一节点",width:100,align:"center"},
	 				{field:"productName",title:"产品类型",width:140,align:"center"},
	 				{field:"loanType",title:"贷款类型",width:110,align:"center",
	 					formatter:function(value,row,index){
		 					   return value == '1' ? '信用贷款' : value == '2' ? '抵押贷款' : value == '3' ? '担保贷款' :"<font style='color:red;'>错误信息</font>";  
		 					}
	 				},
	 				{field:"realName",title:"借款人",width:120,align:"center"},
	 				{field:"mobile",title:"手机号",width:150,align:"center"},
	 				{field:"applyAmount",title:"申请金额",width:180,align:"center"},
	 				{field:"loanAmount",title:"放款金额",width:180,align:"center"},
	 				{field:"auditAmount",title:"风控额度",width:180,align:"center"},
	 				{field:"loanPeriod",title:"借款期限",width:100,align:"center"},
	 				{field:"annualRate",title:"借款年利率",width:130,align:"center",
	 					formatter:function(value,row,index){
		 					   return value+"%";  
		 					}
	 				},
	 				{field:"schemeName",title:"还款方式",width:160,align:"center"},
	 				{field:"createTime",title:"申请日期",width:210,align:"center"},
	 				{field:"channelId",title:"渠道",width:140,align:"center"},
	 				{field:"subjectType",title:"上标渠道",width:140,align:"center"}
	 				
	 			]
	 	];
	/*######################grid columns end##############################*/
	
	$("#tt").datagrid({
		url:"/loan/list.html?&rand=" + Math.random(),
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
		idField:"loanId",
		frozenColumns : [[{field:'ck',checkbox:true}]],
		columns:columns_tt,
		toolbar:toolbar_tt,
		queryParams:{
			'searchStr': $("#searchForm #searchStr").val(),
			'productCode':$("#searchForm #search_productCode").combobox('getValue'),
			'loanType':$("#searchForm #search_loanType").combobox('getValue'),
			'loanStatus':$("#searchForm #search_loanStatus").combobox('getValue'),
			'startDate':$("#searchForm #search_startDate").datebox('getValue'),
			'endDate':$("#searchForm #search_endDate").datebox('getValue'),
			'processNextStep':$("#searchForm #search_processNextStep").combobox('getValue')
		},
		onLoadSuccess:function(data){//根据状态限制checkbox
			if(data){
				$.each(data.rows,function(index, item){
					if(item.loanStatus != 'PENDING' && item.loanStatus != 'PROCESSING'){
						$(".datagrid-header-check").find("input:checkbox").attr('disabled', true);
						$(".datagrid-cell-check").eq(index).find("input:checkbox").attr('disabled', true);
					}
				});
			}
			
		},
		view:detailview,
		detailFormatter: function(rowIndex, rowData){
			var returnStr='';
			returnStr +='<table style="width:95%;margin:10px;border:1px dashed #ccc;">' +
							'<thead ><span style="color:red;font-size:10px;"><b>订单号:&nbsp;'+rowData.orderCode+'</b>&nbsp;'+
							'</span></thead>'+
							'<tr style="width:100%;height:20px;">' +
								'<td style="border:0;width:20%;font-size:10px;"><b>是否推荐: </b><font style="color:#3C0;">' + rowData.referenceMobile + '</font></td>' +
								'<td style="border:0;width:20%;font-size:10px;"><b>贷款用途: </b><font style="color:#3C0;">' + rowData.loanUsage + '</font></td>' +
								'<td style="border:0;width:20%;font-size:10px;"><b>贷款标题: </b><font style="color:#3C0;">' + rowData.loanTitle + '</font></td>' +
								'<td style="border:0;width:40%;font-size:10px;" colspan="2"><b>备注: </b><font style="color:#3C0;">' + rowData.remark + '</font></td>' +
							'</tr>'+
							'<tr style="width:100%;height:20px;">' +
								'<td style="border:0;width:20%;font-size:10px;"><b>审核人: </b><font style="color:#3C0;">' + rowData.auditUser + '</font></td>' +
								'<td style="border:0;width:20%;font-size:10px;"><b>审核时间: </b><font style="color:#3C0;">' + rowData.auditTime + '</font></td>' +
								'<td style="border:0;width:20%;font-size:10px;"><b>放款金额: </b><font style="color:#3C0;">' + rowData.loanAmount + '</font></td>' +
								'<td style="border:0;width:20%;font-size:10px;"><b>放款日期: </b><font style="color:#3C0;">' + rowData.loanTime + '</font></td>' +
								'<td style="border:0;width:20%;font-size:10px;"><b>投资人利率: </b><font style="color:#3C0;">' + rowData.investAnnualRate + '</font></td>' +
							'</tr>'+
						'</table>';
			return returnStr;
		}
	});
	
	/*$(window).resize(function (){
		domresize();
	 }); */
/*##########################grid init end###################################################*/
});

function reloadDataGrid()
{
	$("#tt").datagrid("reload");
}

/*#########################同步放款数据#######################################################*/
function syn_loan_data(){
	var url = "/loan/syn/tocall.html?&rand=" + Math.random();
	$('#sysLoanData').dialog({
		title: "同步放款数据",
		width: 400,
		height: 200,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:sys_data
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#sysLoanData").dialog("close");
				}
		}]
	});
}

function sys_data(){
	var  url ="/loan/syn/callback.html?&rand=" + Math.random();
	var b = $("#editFormSyn #id_synDate_edit").datebox("getValue")
	var data = {"selectDate":b};
	if(b){
		 $.ajax({   
			 type: 'POST',
			 dataType: 'json',
			 url: url,  
			 data:data,
			 success: function(data){ 
				 if(data.executeStatus == 1){
					 $("#sysLoanData").dialog("close");
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

function save_loan(){
	var  url ="/loan/update.html?&rand=" + Math.random();
	var applyAmount = $("#editFormLoan #id_applyAmount_edit").numberbox('getValue');
	if(applyAmount.trim() == null || applyAmount.trim() == '0'){
		$.messager.alert("提示","申请金额不能为空", "info");
		return false;
	}else if(applyAmount%100 != 0){
		$.messager.alert("提示","申请金额只能为100的倍数", "info");
		return false;
	}else{
		 $.ajax({   
			 type: 'POST',
			 dataType: 'json',
			 url: url,  
			 data:$("#editFormLoan").serialize(),
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
}

function to_detail(id,status){
	var url = "/loan/detail.html?&rand=" + Math.random()+"&loanId="+id;
	var detailtoolbar; 
	//根据状态设置按钮
	if(status == "PENDING" || status == "PROCESSING"){
		detailtoolbar = [
		 				{
							iconCls:"icon-no",text:"关闭",
							handler:function(){
								$("#detailLoanDiv").dialog("close");
							}
						},'-',
						{
		          			iconCls:"icon-ok",
		          			text:"上标",
		          			handler:function(){
		          				auditexc(id,1);
		          			}
		          		} ,'-',{
		          			iconCls: 'icon-no',
		          			text:'拒绝',
		          			handler:function(){
		          				auditexc(id,2);
		          			}
		          		},'-',{
		          			iconCls: 'icon-stop',
		          			text:'失效',
		          			handler:function(){
		          				auditexc(id,3);
		          			}
		          		},'-',{
		          			iconCls: 'icon-stop',
		          			text:'编辑',
		          			handler:function(){
		          				to_edit(id);
		          			}
		          		},'-',
						{
							iconCls:"icon-epolice",text:"一审",
							handler:function(){
								toChecked(id,1);
							}
						},'-',
						{
							iconCls:"icon-man",text:"二审",
							handler:function(){
								toChecked(id,2);
						}		
				}];
	}else{
		detailtoolbar = [
			 				{
								iconCls:"icon-no",text:"关闭",
								handler:function(){
									$("#detailLoanDiv").dialog("close");
								}
							},'-',							
							{
								iconCls:"icon-epolice",text:"一审",
								handler:function(){
									toChecked(id,1);
								}
							},'-',
							{
								iconCls:"icon-man",text:"二审",
								handler:function(){
									toChecked(id,2);
							}		
					}];
	}
	
	
		
	$('#detailLoanDiv').dialog({
		title: "订单明细",
		height:$("#body").height()-25,
		width:$("#body").width()-30,
		top:'10px',
		left:'10px',
		closed: false,
		cache: false,
		href: url,
		modal: true
//		toolbar:detailtoolbar
	});
}

function toChecked(id,checkType){
	var url = "/loan/checked/toedit.html?&rand=" + Math.random()+"&loanId="+id+"&checkType="+checkType,title,divId,formId,diaHeight,diaWidth;
	if(checkType == 1){
		title="一审";
		divId= '#oneCheckedDiv';
		formId = '#editOneLoanChecked';
		diaHeight = 300;
		diaWidth = 600;
	}else if(checkType == 2){
		title="二审";
		divId= '#twoCheckedDiv';
		formId = '#editTwoLoanChecked';
		diaHeight = 300;
		diaWidth = 600;
	}
	$(divId).dialog({
		title: title,
		height:diaHeight,
		width:diaWidth,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
		         /*{
					iconCls:"icon-save",text:"保存",
					handler:function(){
						save_loan_checked(divId,formId);
					}
				},*/
//		         {
//					iconCls:"icon-no",text:"关闭",
//					handler:function(){
//						$(divId).dialog("close");
//				}
//		}
	]
	});
}

function save_loan_checked(divId,formId){
	var  url ="/loan/checked/save.html?&rand=" + Math.random();
		 $.ajax({   
			 type: 'POST',
			 dataType: 'json',
			 url: url,  
			 data:$(formId).serialize(),
			 success: function(data){ 
				 if(data.executeStatus == 1){
					 $(divId).dialog("close");
					 $.messager.alert("提示","操作成功","info");
					 reloadDataGrid();
				 }else{
					 parent.$.messager.alert("提示","操作失败","error");
				 }   
			 } 
		});
	
}

/*###########**********审核******begin***************###############*/
/**
 * 审核通过
 */
function audit_one(){
	audit(1);
}
/**
 * 拒绝通过
 */
function audit_two(){
	audit(2);
}
/**
 * 作废
 */
function audit_three(){
	audit(3);
}
/**
 * 操作选中的数据
 */
function audit(type) {
	var ids = get_ids();
	if(ids.length){
		auditexc(ids,type);
	}
}
function auditexc(ids,type){
	var msgStr = "上标";
	if(type == 2){
		msgStr="拒绝";
	}
	if(type == 3){
		msgStr="失效";
	}
	var data = {"ids":ids,"audType":type};
	
	var opt = $.messager.confirm(msgStr, '你确定要'+msgStr, function(r){
		if(r == true){
			$.ajax({
				  type: 'POST',
				  dataType: 'json',
				  url: "/loan/audited.html?&rand=" + Math.random(),
				  data: data,
				  success: function(data) {
				   		if(data.executeStatus == 1){
				   			$('#tt').datagrid('uncheckAll');
				   			$('#tt').datagrid('unselectAll');
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
/*###########**********审核******end***************###############*/

/*################***导出**begin*################### */
function export_excel(){
	var searchStr = $("#searchForm #searchStr").val();
	var productCode = $("#searchForm #search_productCode").combobox('getValue');
	var loanStatus =  $("#searchForm #search_loanStatus").combobox('getValue');
	var loanType = $("#searchForm #search_loanType").combobox('getValue');
	var startDate =  $("#searchForm #search_startDate").datebox('getValue');
	var endDate =  $("#searchForm #search_endDate").datebox('getValue');
	var processNextStep = $("#searchForm #search_processNextStep").combobox('getValue');
	//document.getElementById("exportExcel").disabled = true;
	//document.getElementById("exportExcel").value = "正在导出";
	var exportIframe = document.createElement('iframe');
	exportIframe.src ="/loan/export/excel.html"+ "?searchStr="+ searchStr+ "&productCode="+ productCode+
	"&loanType="+loanType+ "&loanStatus="+ loanStatus+ "&startDate="+startDate+ "&endDate="+ endDate+
	"&processNextStep="+processNextStep;

	exportIframe.style.display = 'none';
	document.body.appendChild(exportIframe);
}
/*################***导出**end*################### */


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