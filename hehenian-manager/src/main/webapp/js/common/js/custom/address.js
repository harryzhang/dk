$(function (){
	$("#province").combobox({
		panelHeight:'200', 
		valueField: 'code',
		textField: 'name',
		url:httpUrl + "/city/province/search.do?pageSize=1000&rand=" + Math.random(),
		onSelect:function (record){
			cb_city.combobox({
                 disabled: false,
                 url: httpUrl + '/city/city/search.do?provinceCode='+record.code+'&pageSize=1000&rand=' + Math.random(),
                 valueField: 'code',
                 textField: 'name'
             }).combobox('clear');
		}
	});
	
	var cb_city = $('#city').combobox({
		 panelHeight:'200', 
         valueField: 'code',
         textField: 'name',
         onSelect:function (record){
			cb_area.combobox({
              url:  httpUrl + '/city/area/search.do?cityCode='+record.code+'&pageSize=1000&rand=' + Math.random(),
              valueField: 'code',
              textField: 'name'
          }).combobox('clear');
		}
     });
	 
	 var cb_area = $('#area').combobox({
		 panelHeight:'200',
         valueField: 'code',
         textField: 'name'
     });
});