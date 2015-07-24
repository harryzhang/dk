<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.sp2p.constants.IConstants"%>
<title><%=IConstants.SEO_TITLE%></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="<%=IConstants.SEO_KEYWORDS%>" />
<meta http-equiv="description" content="<%=IConstants.SEO_DESCRIPTION%>" />
<%=IConstants.SEO_OTHERTAGS%>
<link href="<c:url value='/css/hhncss.css?t=15'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/Site2.css?t=2'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/lytebox.css?t=2'/>" rel="stylesheet" type="text/css" />
<!--IE6透明判断-->
<!--[if IE 6]>
<script src="../css/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
DD_belatedPNG.fix('.flash_bar,#tit_fc1,#tit_fc2,#tit_fc3,#tit_fc4,#flashLine,#right_tel,#right_qq,#right_tip,.login_all,.wytz_center_onez,.wytz_center_one,img');
</script>
<![endif]-->

<script src="<c:url value="/script/add_all.js?t=1"/>" type="text/javascript"></script>
<script src="<c:url value="/script/MSClass.js?t=1"/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/script/jquery-1.7.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/jquery.shove-1.0.js?t=1"/>"></script>
<script type="text/javascript" src="<c:url value="/script/lytebox.js?t=1"/>"></script>