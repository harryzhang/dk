<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" type="text/css" href="${basePath }/js/common/css/default.css?v=${cssversion}" />
<link rel="stylesheet" type="text/css" href="${basePath }/js/common/js/jquery-easyui-1.4.1/themes/default/custom-easyui.css?v=${cssversion}" />
<link rel="stylesheet" type="text/css" href="${basePath }/js/common/js/jquery-easyui-1.4.1/themes/icon.css?v=${cssversion}" />
<!-- 自定义的样式和图标 -->
<link rel="stylesheet" type="text/css" href="${basePath }/js/common/js/jquery-easyui-1.4.1/custom-themes/custom-icon.css?v=${cssversion}" />
<script type="text/javascript" src="${basePath }/js/common/js/jquery-easyui-1.4.1/jquery.min.js?v=${jsversion}"></script>
<script type="text/javascript" src="${basePath }/js/common/js/jquery-easyui-1.4.1/jquery.easyui.min.js?v=${jsversion}"></script>

<script type="text/javascript" src="${basePath }/js/common/js/custom/extend-validate.js?v=${jsversion}"></script>
<script type="text/javascript" src="${basePath }/js/common/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js?v=${jsversion}"></script> 

<script type="text/javascript" src="${basePath }/js/common/js/custom/Arrays.js?v=${jsversion}"></script>
<script type="text/javascript" src="${basePath }/js/common/js/custom/common.js?v=${jsversion}"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/js/custom/extends.js?v=${jsversion}"></script>
<script type="text/javascript">
 var httpUrl = "<%=request.getContextPath() %>";
</script>
