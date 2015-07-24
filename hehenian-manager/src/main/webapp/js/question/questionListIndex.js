var QuestionListIndex=function(){
	var grid;
	var _this;
	var classfyMap={};
	return {
		init:function(){
			_this=this;
			_this.initClassifyMap();
			$("#toolbar").ligerToolBar({ items: [
                {
                    text: '增加', click: function (item)
                    {
                        //$.ligerDialog.open({ height: 400,width:600, url: '/question/add.html', isResize: true });
                        art.dialog.open('/question/add.html',{
                        id:'editQuestion',
                        title:'新增问题',
                        lock:true,
                        width:600,
                        height:400
                        });
                        
                    }, icon:'add'},
                { line:true },
//                { text: '修改', click: function(item){
//                	 $.ligerDialog.open({ height: 200, url: '/question/edit.html?id='+item.id, isResize: true 
//                		 });
//                } }
            ]
            });
            //$("#createDateBeg").ligerDateEditor({ label: '创建日期', format: "yyyy-MM-dd", labelWidth: 65, width: 100,labelAlign: 'left' });
            $("#searchbtn").ligerButton({ width:150,click: function () {
                _this.reload();
            }
            }).setValue('查询');
			grid=$("#tableGrid").ligerGrid({
	            columns: [ 
	            {display: '主键', name: 'id', align: 'center', width: '5%' } ,
	            {display: '内容', name: 'content',align:'left', width: '18%',isSort:false },
	            {display:'所属类别',name:'classify',align:'center',width:'6%',render:_this.classifyRender},
	            {display:'类型',name:'multiChoice',align:'center',width:'6%',render:_this.multiChoiceRender},
	            {display:'最少选择几个',name:'minChoice',align:'center',width:'8%'},
	            {display:'最多选择几个',name:'maxChoice',align:'center',width:'8%'},
	            {display:'启用',name:'status',align:'center',width:'6%',render:_this.validRender},
	            {display:'回写',name:'callback',align:'center',width:'8%',render:_this.callbackRender},
	            {display:'平台',name:'platform',align:'center',width:'6%',render:function(){return "未知";}},
	            {display:'创建时间',name:'createTime',align:'center',width:'10%'},
	            {display:'修改时间',name:'modifyTime',align:'center',width:'10%'},
	            {display:'操作',align:'center',render:_this.operation}
	            ], 
	            url:'/questionnaire/questionListData.html', 
	            parms:_this.getParam(),
                pageSize: 20,width: '99%',
                height: 0.9*$(document).height(),
	            fixedCellHeight:false,
	            onDblClickRow:_this.showCallback
	        });
		},
		callbackRender:function(rowdata, index, value){
			var str= "<a href='javascript:void(0);' onclick='questionList.showCallback("+rowdata.id+")'>"+(value?"是":"否")+"</a>";
			str+=value?"   <a href='javascript:void(0);' onclick='questionList.removeCallback("+rowdata.id+")'>取消回写</a>":"";
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
		classifyRender:function(rowdata, index, value){
			return classfyMap[value];
		},
		multiChoiceRender:function(rowdata,index,value){
			var str=null;
			switch(value){
			case 1:
				str="单选";
				break;
			case 2:
				str="多选";
				break;
			case 3:
				str="开放性问题";
				break;
			case 4:
				str="程度题";
				break;
			}
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
			return "<a href='javascript:edit("+rowdata.id+");'>修改</a> <a href='javascript:manageAnswer("+rowdata.id+");'>管理答案</a>";
		},
		getParam:function(){
			var params={};
			params.classify=$("#classifyBox option:selected").val();
			params.multiChoice=$("#multiChoiceBox option:selected").val();
			params.status=$("#statusBox option:selected").val();
			params.createTimeBeg=$("#createTimeBeg").val();
			params.createTimeEnd=$("#createTimeEnd").val();
			params.modifyTimeBeg=$("#modifyTimeBeg").val();
			params.modifyTimeEnd=$("#modifyTimeEnd").val();
			params.content=$("#content").val();
			return params;
		},
		reload:function(){
			var param=_this.getParam();
			grid.options.parms=param;
			grid.loadData();
		},
		showCallback:function(data,rowid,rowdata){
			var id=data.id?data.id:data;
			art.dialog.open('/questionnaire/showCallbackDialog.html?questionId='+id,{
				id:'callbackData',
				title:'回写数据',
				lock:true,
				width:500,
				height:400,
				ok:function(contentWindow){
					var page=$(contentWindow.document);
					var questionId=page.find("#questionId").val();
					if(!questionId){
						alert("questionId为空，请关闭页面");
						return false;
					}
					var selectedClass=page.find("#clazzSelected").val();
					var selectedField=page.find("#fieldSelected").val();
					if(!selectedClass || !selectedField){
						alert("类别跟属性都不能为空...（如果想关掉页面，请点击取消或者X）");
						return false;
					}
					$.ajax({
						url:'/questionnaire/saveQuestionRelation.html',
						type:'post',
						data:{questionId:questionId,selectedClass:selectedClass,selectedField:selectedField},
						success:function(res){
							if(res.ret==1){
								questionList.reload();
							}else{
								alert("设置失败");
							}
						}
					});
				},
				cancel:true
			});
		},
		getGrid:function(){
			return grid;
		}
	}
}

function edit(id){
	//$.ligerDialog.open({ height: 400,width:600, url: '/question/edit.html?id='+id, isResize: true });
	art.dialog.open('/question/edit.html?id='+id,{
		id:'editQuestion',
		title:'编辑问题',
		lock:true,
		width:600,
		height:400
	});
}

function manageAnswer(id){
	//$.ligerDialog.open({ height: 400,width:600, url: '/question/editAnswer.html?id='+id, isResize: true });
	art.dialog.open('/question/editAnswer.html?id='+id,{
		id:'editAnswer',
		title:'编辑问题',
		lock:true,
		width:600,
		height:400
	});
}

$(function(){
	var obj=new QuestionListIndex();
	obj.init();
	window.questionList=obj;
});