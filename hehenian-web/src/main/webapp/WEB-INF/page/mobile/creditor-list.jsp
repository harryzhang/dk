<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<c:url value='/wap/mobile/styles/hhn.common.css?t=1'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/wap/mobile/styles/hhn.layout.css?t=1'/>" />
    <%if(session.getAttribute("appstyle")!=null){%>
    <link rel="stylesheet" type="text/css" href='/wap/mobile/styles/hhn.<%=session.getAttribute("appstyle") %>.css?t=2' />
    <%}%>
</head>
<body class="index">
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<div class="wrap" id="wrap" style="display:none;">
  <dl class="table fund-table" id="table">
     <dt><span>标题</span><span>债权详情</span><span>其他信息<br/>(元)</span><span>操作</span></dt>
	    	<dd></dd> 
   </dl>
    <s:if test="pageBean.page==null || pageBean.page.size==0">
     <div class="empty">暂无数据</div>
  </s:if>
</div>
<script>var noIScroller = true;</script>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/creditor.js" ></script>
<script>$.get("/updateUserUsableSum.do?_="+new Date().getTime());</script>
</body>
</html>