<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<table width="940" border="0" cellpadding="0" cellspacing="1"
			bgcolor="#e5e5e5" style="margin:10px;">
  <tr height="30">
    <th bgcolor="#faf9f9" scope="col">期数</th>
    <th bgcolor="#faf9f9" scope="col">应还金额</th>
    <th bgcolor="#faf9f9" scope="col">催收结果</th>
    <th bgcolor="#faf9f9" scope="col">催收时间</th>
  </tr>
  <s:if test="%{#request.collectionList !=null && #request.collectionList.size()>0}">
      <s:iterator value="#request.collectionList" id="bean">
      <tr height="30">
          <td bgcolor="#ffffff" align="center">${bean.repayPeriod}</td>
          <td bgcolor="#ffffff" align="center">${bean.forPI}</td>
          <td bgcolor="#ffffff" align="center">${bean.colResult} </td>
          <td bgcolor="#ffffff" align="center">${bean.collectionDate} </td>
      </tr>
  </s:iterator>        
  </s:if>
  <s:else>
      <td colspan="4" align="center" height="30"  bgcolor="#ffffff">没有数据</td>
  </s:else>
</table>