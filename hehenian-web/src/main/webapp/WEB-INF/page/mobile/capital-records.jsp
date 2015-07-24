<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>资金流水</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><span>资金流水</span></h1>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <dl class="table fund-table" id="table">
    <dt><span>类型</span><span>收入</span><span>支出</span><span>时间</span></dt>
    <s:if test="pageBean.page!=null || pageBean.page.size!=0">
    <s:iterator value="pageBean.page" var="bean" status="s">
                <dd><span>${bean.fundMode=='汇付天下'?'充值成功':bean.fundMode}</span><span>${bean.income }</span><span>${bean.spending}</span><span><s:date name="#bean.recordTime" format="yyyy-MM-dd" /></dd>
     </s:iterator>
    </s:if>
  </dl>
  <s:if test="pageBean.page==null || pageBean.page.size==0">
     <div class="empty">暂无数据</div>
  </s:if>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<script>var noIScroller = true;</script>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/account.js" ></script>
</body>
</html>