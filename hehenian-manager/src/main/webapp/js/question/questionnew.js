var QuestionListIndex=function(){
	var grid;
	var _this;
	var classfyMap={};
	var changePage = false;
	return {
		init:function(){
			_this=this;
			_this.initClassifyMap();
            $("#searchbtn").ligerButton({ width:150,click: function () {
            	changePage = true;
                _this.reload();
                changePage = false;
            }
            }).setValue('查询');
            $("#addQuestionBtn").ligerButton({ width:150,click: function () {
                _this.addQuestion();
            }
            }).setValue('新增问题+');
            $("#initQuestionBtn").ligerButton({ width:150,click: function () {
                _this.initQuestion();
            }
            }).setValue('初始化缓存+');
            
            $("#resetQuestionBtn").ligerButton({ width:150,click: function () {
                _this.resetQuestion();
            }
            }).setValue('重新同步问题数据[慎重]+');
            
			grid=$("#tableGrid").ligerGrid({
	            columns: [ 
	            {display:'操作',align:'center',render:_this.operation},
	            {display: "题目ID", name: "questionId",type: "text", align: "left", width: '5%' },
	            {display: '题目内容', name: 'content',align:'left', width: '18%',isSort:false },
	            {display: '是否保密', name: 'isHide',align:'center', width: '6%',render:_this.showIsHide },
	            {display:'题目类型',name:'questionType',align:'center',width:'12%',render:_this.questionType},
	            {display:'问题属性',name:'sexStatus',align:'center',width:'8%', render:_this.showSexStatus},
	            {display:'选项数量',name:'choiceCount',align:'center',width:'6%', render:_this.showChoiceInfo},
	            {display:'答题下/上限',align:'center',width:'8%', render:_this.showMinMax},
	            {display:'问题模块',name:'modules',align:'center',width:'8%',render:_this.moduleInfo},
	            {display:'投放端口',name:'platform',align:'center',width:'6%',render:_this.showPlatform},
	            {display:'状态',name:'inUse',align:'center',width:'8%', render:_this.showInUse},
	            {display:'创建时间',name:'createTime',align:'center',width:'10%'},
	            {display:'修改时间',name:'modifyTime',align:'center',width:'10%'}
	           
	            ], 
	            url:'/question/emgQueryQuesitonsDetail.html', 
	            parms:_this.setParam(),
	            page:1,
	            pageParmName:'page',
                pageSize: 20,width: '99%',
                height: 0.9*$(document).height(),
               // rownumbers : true,
	            fixedCellHeight:false,
	            onDblClickRow:_this.showCallback
	        });
		},
		moduleInfo:function(rowdata, index, value){
			//问题模块
			var str= "<a href='javascript:void(0);' onclick='questionList.showModules("+rowdata.questionId+")'>"+"查看</a>";
			return str;
		},
		showMinMax:function(rowdata, index, value){
			var str = "[" + rowdata.minChoice + "," + rowdata.maxChoice + "]";
			return str;
		},
		removeCallback:function(quesId){
			$.ajax({
				url:'/questionnaire/removeCallback.html',
				type:'post',
				data:{questionId:quesId},
				success:function(res){
					if(res.ret==1){
						questionList.reload();
					}else{
						alert("删除失败");
					}
					
				}
			});
		},
		questionType:function(rowdata, index, value){
			if(value <= 0){
				return "未分类";
			}else if(value == 1){
				return "选择题";
			}else if(value == 2){
				return "文字输入题";
			}else if(value == 3){
				return "程度题-符合-不符合";
			}else if(value == 4){
				return "程度题-重要-不重要";
			}else if(value == 5){
				return "是-否题";
			}else if(value == 6){
				return "区间题";
			}else{
				return "未知";
			}
			//return classfyMap[value];
		},
		showIsHide:function(rowdata, index, value){
			if(value == 0){
				return "公开";
			}else if(value == 1){
				return "保密";
			}else{
				return "未知";
			}
		},
		showPlatform:function(rowdata, index, value){
			if(value == -1){
				return "通用";
			}else if(value == 0){
				return "PC端";
			}else if(value == 1){
				return "手机端";
			}else{
				return "未知";
			}
		},
		showInUse:function(rowdata, index, value){
			if(value == 0){
				return "停用";
			}else if(value == 1){
				return "启用";
			}else{
				return "未知";
			}
		},
		showSexStatus:function(rowdata, index, value){
			if(value < 0){
				return "男女通用";
			}else if(value == 0){
				return "男用户";
			}else if(value == 1){
				return "女用户";
			}else{
				return "未知";
			}
		},
		showChoiceInfo:function(rowdata, index, value){
			var str= "<a href='javascript:void(0);' onclick='questionList.showChoices("+rowdata.questionId+")'>"+value+"</a>";
			return str;
		},
		validRender:function(rowdata,index,value){
			return value==1?"是":"否"
		},
		initClassifyMap:function(){
			classfyMap[1]="注册";
			classfyMap[2]="QA";
			classfyMap[3]="初步沟通";
			classfyMap[4]="表明立场";
			classfyMap[5]="深入了解";
			classfyMap[6]="自由沟通";
			classfyMap[7]="邮件唤回";
		},
		operation:function(rowdata,index,value){
			//return "<a href='javascript:manageQuestion("+rowdata.questionId+");'>编辑</a>&nbsp;&nbsp;<a href='javascript:manageLog("+rowdata.questionId+");'>操作记录</a>";
			return "<a href='javascript:manageQuestion("+rowdata.questionId+");'>编辑</a>";
			
		},
		setParam:function(){
			var params={};
			params.platform=$("#platform option:selected").val();
			params.questionType=$("#questionType option:selected").val();
			params.sexStatus=$("#sexStatus option:selected").val();
			params.inUse=$("#inUse option:selected").val();
			params.modulesId=$("#modulesId  option:selected").val();
			params.questionnaireId=$("#questionnaireId  option:selected").val();
			//alert(params.questionnaireId);
			params.minCreateTime=$("#minCreateTime").val();
			params.maxCreateTime=$("#maxCreateTime").val();
			params.minModifyTime=$("#minModifyTime").val();
			params.maxModifyTime=$("#maxModifyTime").val();
			params.content=$("#content").val();
			params.isHide = $("#isHide").val();
			return params;
		},
		reload:function(){
			var param=_this.setParam();
			grid.options.parms=param;
			grid.options.page = 1;
			grid.loadData();
		},
		addQuestion:function(){
			 art.dialog.open('/question/gotoAddQuestion.html',{
                 id:'addQuestion',
                 title:'新增问题',
                 lock:true,
                 width:1000,
                 height:700
                 });
		},
		initQuestion:function(){
			 art.dialog.open('/question/initQuestionInfo.html',{
                id:'initQuestion',
                title:'初始化缓存',
                lock:true,
                width:300,
                height:200,
                content:'刷新成功，请关闭页面',
                ok:function(contentWindow){
                	questionList.reload();
				}
                });
		},
		resetQuestion : function(){
			 art.dialog.open('/question/resetQuestionInfo.html',{
	                id:'initQuestion',
	                title:'重设问题',
	                lock:true,
	                width:300,
	                height:200,
	                content:'刷新成功，请关闭页面',
	                ok:function(contentWindow){
	                	questionList.reload();
					}
	                });
		},
		showChoices : function(data, rowid, rowdata){
			//var id=data.id?data.id:data;
			art.dialog.open('/question/emgQueryQuestionChoice.html?id='+data,{
				id:'choiceCount',
				title:'答案数据',
				lock:true,
				width:500,
				height:400
			});
		},
		showModules : function(data, rowid, rowdata){
			art.dialog.open('/question/emgQueryQuestionModules.html?id='+data,{
				id:'modules',
				title:'答案数据',
				lock:true,
				width:500,
				height:400
			});
		},
		getGrid:function(){
			return grid;
		}
	}
}
function manageQuestion(id){
	//$.ligerDialog.open({ height: 400,width:600, url: '/question/editAnswer.html?id='+id, isResize: true });
	art.dialog.open('/question/emgQueryEditAnswer.html?id='+id,{
		id:'editQuestion',
		title:'编辑问题',
		lock:true,
		width:1000,
		height:600
	});
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
							options = '<select id="questionnaireId" class="childModules"><option value="-1">请选择</option>';
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
$(function(){
	//加载问题模块的大模块列表
	//ajaxQueryModules($(".modulesParent"),-1, false, null);
	
	$(".modulesParent").live('change', function(){ 
		//alert($(this).children('option:selected').val());
		var modulesId = $(this).children('option:selected').val();
		ajaxQueryModules($(".modulesParent"), modulesId, true, $(".childModules"))
	});
	var obj=new QuestionListIndex();
	obj.init();
	window.questionList=obj;
});