<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.hehenian.web.common.util.HttpClientUtils"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hehenian.biz.common.base.result.IResult"%>
<%@ page import="net.sf.json.JSONObject" %>
<html>
<head>
<title>网页授权获取用户基本信息</title>
</head>
<body>
	<%
      	out.println(request.getQueryString());
      	String openid = request.getParameter("openid");
          
        response.sendRedirect("http://www.hehenian.com/webapp/toRobflow.do?openid="+openid);
    %>
</body>

</html>