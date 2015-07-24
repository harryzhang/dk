<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<s:iterator value="#request.investRecords" var="bean">
	<a href="webapp-financeDetail.do?id=${bean.borrowId}"><span>${bean.invest_number }</span><span>${bean.investTime }</span><span>${bean.investAmount }</span><span>${bean.hasPI }</span></a>
</s:iterator>