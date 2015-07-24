var ShowCallbackData=function(){
	var _this;
	var classDatas=new Array();
	var fieldDatasMap={};
	var fields;
	//var fieldDatas=new Array();
	return {
		init:function(){
			_this=this;
			this.getClassData();
			
			$("#clazz").ligerComboBox({
				width:360,
                data: classDatas, isMultiSelect: false,
                selectBoxWidth:360,valueFieldID:"clazzSelected",
                onSelected:_this.reloadTableData
            });
            var _options=_this.getOptions();
            fields=$("#fields").ligerComboBox({
	            width: 360, slide: false,
	            selectBoxWidth: 360, selectBoxHeight: 300,
	            grid: _options,
	            valueField: 'fieldName',
           		textField: 'fieldName',
				valueFieldID:"fieldSelected"
            });
			
		},
		getClassData:function(){
			$.ajax({
				url:'/questionnaire/getCallbackDatas.html',
				method:'post',
				data:{questionId:$("#questionId").val()},
				success:function(res){
					var datas=eval(res);
					var data=null;
					var fieldName=null,fieldType=null;
					for(var i=0,n=datas.length;i<n;i++){
						data=datas[i];
						classDatas.push({id:data.clazz,text:data.clazz});
						fieldName=data.fieldName;
						fieldType=data.fieldType;
						var fieldDatas=new Array();
						for(var j=0,m=fieldName.length;j<m;j++){
							if(fieldName[j]=='serialVersionUID'){
								continue;
							}
							fieldDatas.push({fieldName:fieldName[j],fieldType:fieldType[j]});
						}
						fieldDatasMap[data.clazz]=fieldDatas;
					}
				},
				async:false
			});
		},
		reloadTableData:function(value,text){
			var data=fieldDatasMap[value];
			window.liger.managers["fields"].setGrid(_this.getOptions(data));
		},
		getOptions:function(data){
			var options= {
	            columns: [
	            { display: '列名', name: 'fieldName', align: 'left', width: 150, minWidth: 60 },
	            {display:'类型',name:'fieldType',align:'left',width:150}
	            ], switchPageSizeApplyComboBox: false,
	            data: {rows:(data?data:{})},
                pageSize: 50,
                checkbox: true,
                usePager:false
	        };
	        return options;
		}
	}
}
$(function(){
	var obj=new ShowCallbackData();
	obj.init();
	window.showCallback=obj;
});