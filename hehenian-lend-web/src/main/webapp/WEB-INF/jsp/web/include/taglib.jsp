<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
	String path = request.getContextPath();
	String temp=request.getServerPort()==80?"":":"+request.getServerPort();
	String basePath = request.getScheme() + "://" + request.getServerName() + temp + path + "/";
	if(application.getAttribute(basePath)==null){
		application.setAttribute("basePath",basePath);
	}
	String url = request.getServletPath();
  	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(url);
	String pageId = new Date().getTime() + "_" + Math.random();
%>
