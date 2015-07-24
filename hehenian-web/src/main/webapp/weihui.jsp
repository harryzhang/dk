<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>系统维护中</title>

<style type="text/css">
   body{ margin:0px; padding:0px; background:url(index2_03.jpg); background-repeat:repeat-x; background-position:left top;}
   .main{width:552px; margin:150px auto;}
</style>
  </head>
  
  <body>
   <div class="main"><img src="images/weihu1.jpg" width="552" height="326" border="0" />
   		<div>${network.content }</div>
   </div>
  </body>
</html>
