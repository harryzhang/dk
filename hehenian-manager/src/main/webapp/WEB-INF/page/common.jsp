<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- <script type="text/javascript" src="${basePath}/js/jquery-1.4.2.min.js"></script>  -->
 <script type="text/javascript" src="${basePath }/js/jquery-easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${basePath }/js/jquery-easyui/jquery.easyui.min.js"></script> 
 <script type="text/javascript"
	src="${basePath }/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<link type="text/css" rel="stylesheet" href="${basePath }/js/jquery-easyui/themes/default/easyui.css" />
<link type="text/css" rel="stylesheet"  href="${basePath }/js/jquery-easyui/themes/icon.css" /> 

<script type="text/javascript">
 var httpUrl = "<%=request.getContextPath() %>";
</script>
