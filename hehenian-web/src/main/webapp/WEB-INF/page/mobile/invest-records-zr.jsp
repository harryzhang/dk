<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>债权转让</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><span>债权转让</span></h1>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <dl class="table fund-table" id="table">
      <dt><span>债权编号</span><span>交易日期</span><span>待收本金<br/>(元)</span><span>债权转让</span></dt>
      <s:if test="pageBean.page!=null && pageBean.page.size>0">
	        <dd>
			</dd>
      </s:if>
  </dl>
 	   <s:if test="pageBean.page==null || pageBean.page.size==0">
			<div class="empty">暂无数据</div>
	   </s:if>
</div>
<c:if test="${param.noBottom ne true }">
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
</c:if>
<script>var noIScroller = true;</script>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/record-dz.js" ></script>
</body>
</html>