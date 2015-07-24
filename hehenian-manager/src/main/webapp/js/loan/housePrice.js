$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchForm #searchButton").on("click",function(){
		$("#tt").datagrid('load',{
			'searchStr': $("#searchForm #searchStr").val()		
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
						handler:to_add
					}
	          	];
	
	/*######################grid toolbar end##############################*/
	/*######################grid columns begin##############################*/
	var columns_tt = [
      			[
	 			 	{field:'id',title:'id',width:100,halign:"center", align:"left",hidden:true},
	 				{field:"cid",title:"小区id",width:180,align:"center"},
	 				{field:"cname",title:"小区名称",width:100,align:"center"},
	 				{field:"housePrice",title:"当前房价",width:100,align:"center"},
	 				{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 						//"+row.loanId+","+"\'"+row.loanStatus+"\'"+"
	 					  var str= '<a href="javascript:void(0);" onclick="to_edit(\''+row.id+'\');">编辑</a>';
	 					  //var status = row.loanStatus;
	 					  //if(status == 'PENDING' || status == 'PROCESSING'){
	 					  //	 str += "| <a href='javascript:void(0);' onclick='to_edit("+row.loanId+")'>编辑</a> ";
	 					  //}
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
	/*######################grid columns end##############################*/
	
	$("#tt").datagrid({
		url:"/housePrice/listHousePrice.html?&rand=" + Math.random(),
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
		
		pagination:false,
		showPageList:false,
		//pageSize:20,
		//pageList:[10,20,30],
		idField:"id",
		columns:columns_tt,
		toolbar:toolbar_tt,
		queryParams:{
			'searchStr': $("#searchForm #searchStr").val()
		},
		onLoadSuccess:function(data){//根据状态限制checkbox
			
		}
	});
	
	/*$(window).resize(function (){
		domresize();
	 }); */
/*##########################grid init end###################################################*/
});


/**
 * 编辑房价
 * @param id
 */
function to_add(){
	to_edit('save');
}
/**
 * 编辑房价
 * @param id
 */
function to_edit(id){
	var url = "/housePrice/editHousePrice.html?&rand=" + Math.random()+"&id="+id;
	$('#editDiv').dialog({
		title: "编辑房价",
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
	var  url ="/housePrice/saveHousePrice.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editHousePriceForm").serialize(),
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
function IsNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
    } else {
        if (window.event) {
            window.event.returnValue = false;
        }
        else {
            e.preventDefault(); //for firefox 
        }
    }
} 
/*##########################公用方法##end############################*/