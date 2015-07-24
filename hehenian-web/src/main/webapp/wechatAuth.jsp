<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.hehenian.web.common.util.HttpClientUtils"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hehenian.biz.common.base.result.IResult"%>
<html>
<head>
<title>网页授权获取用户基本信息</title>
</head>
<body>
	<%
	    String code = request.getParameter("code");
	    if (StringUtils.isBlank(code)) {
	        out.print("授权失败!");
	        return ;
	    }

	    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx6950a51c0294f729&secret=7e07e2a769136381b89d6a1a55a9f70e&code="
	            + code + "&grant_type=authorization_code";
	    IResult<?> result = HttpClientUtils.post(url, new HashMap<String, String>());
	    out.print("Code:" + code);
	    out.print("授权成功，微信返回的数据:" + result.getModel());
	    response.sendRedirect("http://www.hehenian.com/webapp/webapp-index.do");
	%>
</body>

</html>