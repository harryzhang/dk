<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<s:iterator value="pageBean.page" var="bean" status="s">
            <dd><span>${bean.fundMode=='汇付天下'?'充值成功':bean.fundMode}</span><span>${bean.income }</span><span>${bean.spending}</span><span><s:date name="#bean.recordTime" format="yyyy-MM-dd" /></dd>
</s:iterator>