var QuesModule=function(){
	var grid;
	var _this;
	var classfyMap={};
	return {
		init:function(){
			_this=this;
//			_this.initClassifyMap();

            $("#searchbtn").ligerButton({ width:150,click: function () {
                _this.reload();
            }
            }).setValue('查询');
            $("#addQuesModuleBtn").ligerButton({ width:150,click: function () {
                _this.addModules();
            }
            }).setValue('新增问题模块+');
            $("#initQuestionBtn").ligerButton({ width:150,click: function () {
                _this.initQuestion();
            }
            }).setValue('初始化问题信息+');

			grid=$("#tableGrid").ligerGrid({
	           columns: [ 
					{display: '问题模块', name: 'parentContent',align:'center', width: '15%',render:_this.editModulesRelation},
					{display: '子模块',name:'content',align:'center',width:'15%'},
					{display: '题目数量',name:'quesNum',align:'center',width:'8%',render:_this.quesNumRender},
					{display: '平台',name:'platform',align:'center',width:'8%', render:_this.platformRender},
					{display: '启用状态',name:'inUse',align:'center',width:'8%',render:_this.statusRender},
					{display: '最近编辑时间',name:'createTime',align:'center',width:'16%'},
					{display: '最近编辑时间',name:'modifyTime',align:'center',width:'16%'},
					{display: '操作',name:'operation',align:'center',width:'16%',render:_this.operationRender}
	           ], 
	           url:'/question/queryQuesModuleDetail.html', 
	           parms:_this.setParam(),
	           pageSize: 20,width: '99%',
	           height: 0.9*$(document).height(),
	           fixedCellHeight:false,
	           onDblClickRow:_this.showCallback
		       });
			},
			setParam:function(){
				var params={};
				params.platform=$("#platform option:selected").val();
				params.modulesId=$("#modulesId option:selected").val();
				params.questionnaireId=$("#childModules option:selected").val();
				params.inUse=$("#inUse option:selected").val();
				params.minCreateTime=$("#minCreateTime").val();
				params.maxCreateTime=$("#maxCreateTime").val();
				params.minModifyTime=$("#minModifyTime").val();
				params.maxModifyTime=$("#maxModifyTime").val();
				return params;
			},
			quesNumRender:function(rowdata, index, value){
				//跳转到对应的问题列表页
				var str="<a href='javascript:void(0);' onclick='gotoEditQuestionRelation("+rowdata.questionnaireId+",\""+rowdata.content+"\")'>"+value+"</a>";
				return str;
			},
			editModulesRelation:function(rowdata, index, value){
				return value + "[<a href='avascript:void(0);' onclick='gotoEditQuestionnaireRelation("+rowdata.parentId+",\""+rowdata.parentContent+"\")'>子模块顺序</a>]"
			},
			platformRender:function(rowdata, index, value){
				if(value == -1){
					return "全部";
				}else if(value == 0){
					return "PC端";
				}else if(value == 1){
					return "手机端";
				}else{
					return "未知";
				}
			},
			statusRender:function(rowdata, index, value){
				return (value==1?"启用":(value==0?"停用":"未知"));
			},
			operationRender:function(rowdata, index, value){
				//alert(rowdata.parentContent);
				//var str="<a href='javascript:void(0);' onclick='quesModuleAnswerEdit("+rowdata.questionnaireId+","+rowdata.parentId+",\""+rowdata.parentContent+"\")'>编辑模块</a>"+"  <a href='javascript:void(0);' onclick='quesModule.quesModuleRecord("+rowdata.id+")'>操作记录</a>";
				var str="<a href='javascript:void(0);' onclick='quesModuleAnswerEdit("+rowdata.questionnaireId+","+rowdata.parentId+",\""+rowdata.parentContent+"\")'>编辑模块</a>";
				
				return str;
			},
			initQuestion:function(){
				 art.dialog.open('/question/initQuestionInfo.html',{
		                id:'initQuestion',
		                title:'初始化问题数据',
		                lock:true,
		                width:600,
		                height:400,
		                content:'刷新成功，请关闭页面',
		                ok:function(contentWindow){
		                	quesModule.reload();
						}
		                });
			},
			getGrid:function(){
				return grid;
			},
			reload:function(){
				var param=_this.setParam();
				grid.options.parms=param;
				grid.loadData();
			},
			addModules:function(){
				art.dialog.open('/question/quesModuleAdd.html',{
                    id:'editQuestion',
                    title:'新增问题',
                    lock:true,
                    width:600,
                    height:400
                    });
			}
	}
}
function gotoEditQuestionRelation(quesetionnaireId, questionnaireContent){
	art.dialog.open('/question/gotoEditQuestionRelation.html?id='+quesetionnaireId,{
		id:'gotoEditQuestionRelation',
		title:'<p><en style="color:red">'+questionnaireContent + '&nbsp;&nbsp;</en>问题顺序</p>',
		width:1200,
		height:900
	});
}

function gotoEditQuestionnaireRelation(parentId, parentContent){
	art.dialog.open('/question/gotoEditQuestionnaireRelation.html?id='+parentId,{
		id:'gotoEditQuestionRelation',
		title:'<p><en style="color:red">'+parentContent + '&nbsp;&nbsp;</en>所有子模块内容</p>',
		width:1200,
		height:900
	});
}

function quesModuleAnswerEdit(questionnaireId,parentId,parentContent){
	if(questionnaireId != null && questionnaireId > 0 && parentId !=null && parentId > 0){
		art.dialog.open('/question/quesModuleEdit.html?questionnaireId='+questionnaireId+'&parentId=' + parentId + '&parentContent=' + parentContent,{
			id:'quesModuleEdit',
			title:'编辑模块',
			lock:true,
			width:600,
			height:430
		});
	}
}
function quesModuleEdit(id){
	art.dialog.open('/quesModule/quesModuleRecord.html?id='+id,{
		id:'quesModuleRecord',
		title:'操作记录',
		lock:true,
		width:600,
		height:400
	});
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
$(function(){
	$(".modulesParent").live('change', function(){ 
		//alert($(this).children('option:selected').val());
		var modulesId = $(this).children('option:selected').val();
		ajaxQueryModules($(".modulesParent"), modulesId, true, $(".childModules"))
	});
	var obj=new QuesModule();
	obj.init();
	window.quesModule=obj;
});
