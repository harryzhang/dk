<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link href="${basePath }/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
<link href="${basePath }/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="${basePath}/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${basePath}/js/ligerUI/js/ligerui.all.js"></script>
<script type="text/javascript"
	src="${basePath }/js/artDialog/artDialog.js?skin=blue"></script>
<script type="text/javascript"
	src="${basePath }/js/artDialog/plugins/iframeTools.js"></script>