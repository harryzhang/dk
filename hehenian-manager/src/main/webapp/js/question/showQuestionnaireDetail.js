var Detail=function(){
	var grid;
	var _this;
	return {
		init:function(){
			_this=this;
			$("#searchbtn").ligerButton({ width:150,click: function () {
                _this.reload();
            }
            }).setValue('查询');
			grid=$("#tableGrid").ligerGrid({
	            columns: [ 
	            {display: '顺序', name: 'questionOrder', align: 'center', width: '5%' },
	            {display:'内容',name:'content',align:'left',width:'30%',isSort:false},
	            {display:'下一题预览',name:'nextQuestion',align:'left',width:'31%',isSort:false},
	            {display:'创建时间',name:'createTime',align:'center',width:'16%'},
	            {display:'修改时间',name:'modifyTime',align:'center',width:'16%'}
	            ], 
	            url:'/questionnaire/questionnaireDetailData.html', 
                pageSize: 10, width: 880,
                height: 450,
	            fixedCellHeight:false,
	            parms:{questionnaireId:$("#questionnaireId").val()}
	        });
		},
		reload:function(){
			var param=_this.getParam();
			grid.options.parms=param;
			grid.loadData();
		},
		getParam:function(){
			var params={};
			params.questionnaireId=$("#questionnaireId").val();
			params.status=$("#statusBox option:selected").val();
			params.createTimeBeg=$("#createTimeBeg").val();
			params.createTimeEnd=$("#createTimeEnd").val();
			params.modifyTimeBeg=$("#modifyTimeBeg").val();
			params.modifyTimeEnd=$("#modifyTimeEnd").val();
			return params;
		}
	}
}
$(function(){
	var detail=new Detail();
	detail.init();
	window.detail=detail;
})