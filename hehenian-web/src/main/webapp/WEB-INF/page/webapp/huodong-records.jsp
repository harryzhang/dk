<%@ page language="java"  pageEncoding="UTF-8" %>
<%@include file="/include/taglib.jsp"%>
 <tr><th>抽奖日期</th><th>奖项</th></tr>
 <s:if test="#request.myRecords.size()>0">
 <s:iterator value="#request.myRecords" var="var">
 <tr><td><s:date name="createTime" format="yyyy-MM-dd"/></td><td>${var.title }</td></tr>
 </s:iterator>
 </s:if>
 <s:else>
 <tr><td colspan="2">暂时没有抽奖记录</td></tr>
 
 </s:else>
                    
