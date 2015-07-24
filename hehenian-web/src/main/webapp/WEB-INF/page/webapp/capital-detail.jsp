<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
<title>收支明细</title>
</head>
<body>
<jsp:include page="/include/wap-bottom.jsp"></jsp:include>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <div class="t"><span>投资明细</span></div>
  <dl class="table fund-table">
    <dt><span>类型</span><span>收入</span><span>支出</span><span>时间</span></dt>
    	<s:if test="request.fundRecords==null || request.fundRecords.size==0">
        <dd class="empty">暂无数据</dd>
        </s:if>
        <s:else>
        	<s:iterator value="#request.fundRecords" var="bean" status="s">
        		<dd><span>${bean.fundMode=='汇付天下'?'充值成功':bean.fundMode}</span><span>${bean.income }</span><span>${bean.spending}</span><span><s:date name="#bean.recordTime" format="yyyy-MM-dd" /></dd>
        	</s:iterator>
        </s:else>
  </dl>
</div>
<jsp:include page="/include/wap-js.jsp"></jsp:include>
</body>
</html>