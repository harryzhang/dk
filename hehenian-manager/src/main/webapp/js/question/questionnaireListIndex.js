var QuestionnaireList=function(){
	var _this;
	var grid;
	return {
		init:function(){
			_this=this;
			$("#toolbar").ligerToolBar({ items: [
                {
                    text: '增加', click: function (item)
                    {
                        alert(item.text);
                    }, 
                    icon:'add'
                }
            ]
            });

            $("#searchbtn").ligerButton({ width:150,click: function () {
                _this.reload();
            }
            }).setValue('查询');
			grid=$("#tableGrid").ligerGrid({
	            columns: [ 
	            {display: '主键', name: 'id', align: 'center', width: '5%' },
	            {display:'基本信息',name:'descript',align:'center',width:'30%',render:_this.renderContent,isSort:false},
	            {display:'类型',name:'type',align:'center',width:'8%',render:_this.renderType},
	            {display:'是否启用',name:'inUse',align:'center',width:'10%',render:function(rowdata, index, value){return value==1?"是":"否"}},
	            {display:'创建时间',name:'createTime',align:'center',width:'15%'},
	            {display:'修改时间',name:'modifyTime',align:'center',width:'15%'},
	            {display:'操作',align:'center',width:'17%',render:_this.renderOperation}
	            ], 
	            url:'/questionnaire/questionnaireData.html', 
                pageSize: 20, pageSize: 20, width: '99%',
                height: 0.9*$(document).height(),
	            fixedCellHeight:false,
	            onDblClickRow:_this.showDetail
	        });
		},
		renderOperation:function(rowdata, index, value){
			var str="<a href='/questionnaire/editPage.html?id=" + rowdata.id + "'>问题</a> <a href='javascript:void(0);'>修改</a> <a href='javascript:void(0);'>删除</a>";
			return str;
		},
		renderType:function(rowdata,index,value){
			return (value==1?"大众版":(value==2?"企业版":"新版本"));
		},
		renderContent:function(rowdata,index,value){
			return "<a href='javascript:void(0);' onclick='questionnaireList.showDetail("+rowdata.id+")'>"+rowdata.descript+"</a>";
		},
		showDetail:function(data,rowid,rowdata){
			var id=data.id?data.id:data;
			/**$.ligerDialog.open(
			{ height: 530, 
			width:900,
			url: '/questionnaire/showQuestionnaireDetail.html?id='+id, 
			isResize: true 
			});**/
			art.dialog.open('/questionnaire/showQuestionnaireDetail.html?id='+id,{
				id:'showDetail',
				title:'查看问卷详情',
				lock:true,
				width:900,
				height:530,
				cancel:true
			});
		},
		reload:function(){
			var param=_this.getParam();
			grid.options.parms=param;
			grid.loadData();
		},
		getParam:function(){
			var params={};
			params.status=$("#statusBox option:selected").val();
			params.createTimeBeg=$("#createTimeBeg").val();
			params.createTimeEnd=$("#createTimeEnd").val();
			params.modifyTimeBeg=$("#modifyTimeBeg").val();
			params.modifyTimeEnd=$("#modifyTimeEnd").val();
			params.content=$("#content").val();
			return params;
		}
	};
}
$(function(){
	var questionnaireList=new QuestionnaireList();
	window.questionnaireList=questionnaireList;
	questionnaireList.init();
});