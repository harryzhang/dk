<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>

    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
	<script type="text/javascript">
	$(function(){
	/*
	 	var para = {auth:'{"loginType":"1","app_key":"123456","imei":"444012","os":"Iphone os","os_version":"5.0","app_version":"1.0.0","source_id":"Yek_test","ver":"0.9","uid":"-1","crc":"3e64055bf4056d1dc68b85dd4365d649","time_stamp":"20090310113016"}',info:'{"name":"eims10","pwd":"123456"}'};
		$.shovePost("${path }/appLogin.do",para,function(data){
			$("#bodycontent").html(data.error+"==>"+data.msg);
		});
	*/
		var para = {auth:’{"loginType":"1","app_key":"123456","imei":"444012","os":"Iphone os","os_version":"5.0","app_version":"1.0.0","source_id":"Yek_test","ver":"0.9","uid":"-1","crc":"3e64055bf4056d1dc68b85dd4365d649","time_stamp":"20090310113016"}’,info:’{"name":"stone003","pwd":"md5之后的密码串"}’};
		$.shovePost("${path }/app/login.do",para,function(data){
			$("#bodycontent").html(data.error+"==>"+data.msg);
		});
	});
	</script>

  </head>
  
  <body id="bodycontent">
   	<
  </body>
</html>
