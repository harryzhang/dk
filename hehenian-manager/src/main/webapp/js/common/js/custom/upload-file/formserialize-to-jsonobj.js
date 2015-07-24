function formserializeTojsonobj(formSerialize){
	//var formSerialize = $("#userForm").serialize();
	   //userId=111111&fullName=dasda&loginName=dassssssss
	var data = "";
	   var mapValue = formSerialize.split("&");
	   for(var i = 0;i<mapValue.length;i++){
		   var map = mapValue[i].split("=");
		   var key = map[0];
		   //转义
		  // console.log("map1:"+map[1]);
		  // console.log("map2:"+decodeURI(map[1]));//中文解码
		  // console.log("map3:"+unescape(map[1]));//特殊字符解码
		  // console.log("map4:"+unescape(decodeURI(map[1])));
		   
		   var value = unescape(decodeURI(map[1]));//unescape(map[1]);
		   //console.log("value:"+value);
		   if(i == mapValue.length-1){
			   data = data+"\""+key+"\":\""+value+"\"";
		   }else{
			   data = data+"\""+key+"\":\""+value+"\",";
		   }
	   }
	   data = "{"+data+"}";
	   data = JSON.parse(data);
	   //console.log("data:"+data);
	   //console.log("data:"+JSON.stringify(data));
	   //console.log($("#userForm").serialize());
	   return data;
	   
	   /*上面的方法取代这种麻烦的从form表单取值转成jsonojb方式*/
	   /*postData = { "userId":$('#userId').val(),"fullName":$('#fullName').val(),
			   "loginName":$('#loginName').val(),"loginPwd":$('#loginPwd').val(),
			   "fromSystem":$('#fromSystem').val(),"userType":$('#userType').val(),
			   "mobile": $('#mobile').val(),"phone":$('#phone').val(),
			   "dptName":  $('#dptName').val(), "jobName": $('#jobName').val(),
			   "email":$('#email').val(),"jobFlag":$('#jobFlag').val(),
			   "updateCount":$('#updateCount').val(),
			   "id":eId};*/
	   //console.log("postData:"+JSON.stringify(postData));
}