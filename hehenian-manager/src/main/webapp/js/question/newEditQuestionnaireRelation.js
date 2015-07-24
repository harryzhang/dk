
 var QuesModule=function(){
		var grid;
		var _this;
		var classfyMap={};
		return {
			init:function(){
				_this=this;
				grid = $("#maingrid1").ligerGrid({
		           columns: getColumns(),
		           url:'/question/queryQuesModuleDetail.html', 
		           parms:_this.setParam(),
		           root :'rows', 
		           //如果没有这句，则拖动的时候 放入表格中，表格数量不会自增
		           tree: { columnName: 'questionId' },
		           pageSize: 20, 
		           checkbox: true,
	                allowHideColumn: false, 
	                rownumbers: true, 
	                colDraggable: true, 
	                rowDraggable: false,
	                width: '500', 
	                height: '600'
			       });
				},
				setParam:function(){
					var params={};
					params.modulesId = modulesId;
					params.inUse = 1;
					return params;
				},
				getGrid:function(){
					return grid;
				},
				reload:function(){
					var param=_this.setParam();
					grid.options.parms=param;
					grid.loadData();
				}
		}
	}
 
 var QuestionRelation = function(){
		var grid;
		var _this;
		return {
			init:function(){
				_this=this;
				$("#savebtn").ligerButton({ width:150,click: function () {
					
			    	var grid = g2.rows;
			       	var list = '';
			       	$(grid).each(function() {
			       		list += this.questionnaireId + '_';
			       	});
			       	list = list.substr(0, list.length - 1);
			       	$.post("/question/editQuestionnaireRelation.html", {"modulesId": modulesId, "list": list}, function(ret) {
			       		if(ret > 0) {
			       			alert("ok");
			       			g1.loadData();
			       			g2.loadData();
			       		} else {
			       			alert("出错了");
			       		}
			       	});
			    }
			    }).setValue('保存+');
			    $("#deletebtn").ligerButton({ width:150,click: function () {
			    	var rows = g2.getSelecteds();
			    	if(rows.length <= 0){
			    		alert("请在右方表格中，选择勾选一个需要在此模块下删除的问题。")
			    	}else{
			    		g2.deleteSelectedRow();
			    	}
			    	
			    }
			    }).setValue('删除+');
	    
				grid = $("#maingrid2").ligerGrid({
			        columns: getColumns(), 
			        pageSize: 100,
			        root :'rows',
			        //如果没有这句，则拖动的时候 放入表格中，表格数量不会自增
			        tree: { columnName: 'questionId' },
			        checkbox: true,
			        allowHideColumn: false, 
			        rownumbers: true, 
			        colDraggable: true, 
			        rowDraggable: true,
			        width: '500',
			        height: '600', 
			        url:'/question/queryQuestionnaireRelation.html',
			        parms:{"modulesId": modulesId, "pageSize": 100}
				});
				},
				getGrid:function(){
					return grid;
				},
				reload:function(){
					var param=_this.setParam();
					grid.options.parms=param;
					grid.loadData();
				}
		}
	}
 
 function getColumns()
 {
     return [{ display: '子模块名', name: 'content', width: 350, align: 'left' },
             { display: 'ID', name: 'questionnaireId', width: 50 }];
 }
 

$(function()
{    
	var obj=new QuesModule();
	obj.init();
	var grid1 = obj.getGrid();
   
    var obj2 = new QuestionRelation();
    obj2.init();
    var grid2 = obj2.getGrid();
    
    window['g1'] =grid1;
    window['g2'] =grid2;
    
    gridDraggable(g1, g2);
    $("#pageloading").hide();
    
});