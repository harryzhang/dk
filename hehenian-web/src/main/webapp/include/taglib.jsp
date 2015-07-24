<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="java.util.Date"%>
<%@page import="com.shove.web.util.ServletUtils"%>
<%@page import="com.sp2p.constants.IConstants" %>
<%@page import="com.shove.vo.ShoppingCart" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="shove" uri="/shove-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.hehenian.com" prefix="hhn" %>
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
    String remoteAddr = ServletUtils.getIpAddress(request);
	String url = request.getServletPath();
  	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(url);
	log.info(request.getServerName()+"---"+remoteAddr +"==>" + url);
	String pageId = new Date().getTime() + "_" + Math.random();
%>
