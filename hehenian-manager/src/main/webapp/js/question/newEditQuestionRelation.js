var QuesModule=function(){
		var grid;
		var _this;
		var classfyMap={};
		return {
			init:function(){
				_this=this;
	            $("#searchbtn").ligerButton({ width:150,click: function () {
	                _this.reload();
	            }
	            }).setValue('查询');
				grid = $("#maingrid1").ligerGrid({
		          
		           columns: getColumns(),
		           url:'/question/emgQueryQuesitonsDetail.html', 
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
					params.platform=$("#platform option:selected").val();
					params.questionType=$("#questionType option:selected").val();
					params.sexStatus=$("#sexStatus option:selected").val();
					params.modulesId=$("#modulesId").val();
					params.questionnaireId=$("#questionnaireId").val();
					params.content=$("#content").val();
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
			       		list += this.questionId + '_';
			       	});
			       	list = list.substr(0, list.length - 1);
			       	$.post("/question/editQuestionRelation.html", {"qnId": qnId, "list": list}, function(ret) {
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
			        url:'/question/queryQuestionRelation.html',
			        parms:{"questionnaireId": qnId, "pageSize": 100}
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
     return [{ display: '题目', name: 'content', width: 350, align: 'left' },
             { display: '平台', name: 'platform', width: 50, align: 'center', render:getPlatform},
             { display: 'ID', name: 'questionId', width: 50 }];
 }
 
 function getPlatform(rowdata, index, value){
		if(value == -1){
			return "通用";
		}else if(value == 0){
			return "PC端";
		}else if(value == 1){
			return "手机端";
		}else{
			return "未知";
		}
 }
 
 function ajaxQueryModules(obj,moduleId, isAdd, childModules){
		if(moduleId <= 0){
			var options;
			if(isAdd){
				if(childModules.length > 0){
					options = '<option value="-1">请选择</option>';
				}else{
					options = '<select id="questionnaireId" class="childModules"><option value="-1">请选择</option>';
					options += '</select>';
				}
				showContent(obj, options, isAdd, childModules);
			}else{
				options = '<option value="-1">请选择</option>';
				for(var i = 0; i < data.modules.length; i++){
					options += '<option value="'+data.modules[i].questionnaireId+'">'+data.modules[i].content+'</option>'
				}
				showContent(obj, options, isAdd, null);
			}
		}else{
			$.ajax({
				url : "/question/queryModulesinfo.html",
				type : 'post',
				dataType : 'json',
				data : {
					"moduleId" : moduleId
				},
				success : function(data){
					if(typeof(data) != "undefined" && typeof(data.modules) != "undefined"){
						var options;
						if(isAdd){
							if(childModules.length > 0){
								options = '<option value="-1">请选择</option>';
								for(var i = 0; i < data.modules.length; i++){
									options += '<option value="'+data.modules[i].questionnaireId+'">'+data.modules[i].content+'</option>'
								}
							}else{
								options = '<select id="classifyBox" class="childModules"><option value="-1">请选择</option>';
								for(var i = 0; i < data.modules.length; i++){
									options += '<option value="'+data.modules[i].questionnaireId+'">'+data.modules[i].content+'</option>'
								}
								options += '</select>';
							}
							showContent(obj, options, isAdd, childModules);
						}else{
							options = '<option value="-1">请选择</option>';
							for(var i = 0; i < data.modules.length; i++){
								options += '<option value="'+data.modules[i].questionnaireId+'">'+data.modules[i].content+'</option>'
							}
							showContent(obj, options, isAdd, null);
						}
						//alert(options);
					}else{
						alert("服务器忙，请重新加载。");
					}
				}
			});
		}
	}
	function showContent(obj, options, isAdd, chileModules){
		if(isAdd){
			//是否存在
			if(chileModules.length > 0){
				chileModules.html(options);
			}else{
				obj.after(options);
			}
		}else{
			obj.html(options);
		}
	}
	function getPlatform(rowdata, index, value){
		if(value == -1){
			return "通用";
		}else if(value == 0){
			return "PC端";
		}else if(value == 1){
			return "手机端";
		}else{
			return "未知";
		}
	}
$(function()
{    	
	$(".modulesParent").live('change', function(){ 
		//alert($(this).children('option:selected').val());
		var modulesId = $(this).children('option:selected').val();
		ajaxQueryModules($(".modulesParent"), modulesId, true, $(".childModules"))
	});
	
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
