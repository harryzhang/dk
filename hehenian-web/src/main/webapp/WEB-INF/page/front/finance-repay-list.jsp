<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<table width="940" border="0" cellpadding="0" cellspacing="1"
			bgcolor="#e5e5e5" style="margin:10px;">
  <tr height="30">
    <th bgcolor="#faf9f9" scope="col">序号</th>
    <th bgcolor="#faf9f9" scope="col">还款日期</th>
    <th bgcolor="#faf9f9" scope="col">已还本息</th>
    <th bgcolor="#faf9f9" scope="col">待还本息</th>
    <th bgcolor="#faf9f9" scope="col">已付罚息</th>
    <th bgcolor="#faf9f9" scope="col">待还罚息</th>
    <th bgcolor="#faf9f9" scope="col">状态</th>
  </tr>
  <s:if test="%{#request.repayList !=null && #request.repayList.size()>0}">
      <s:iterator value="#request.repayList" id="bean">
      <tr height="30">
          <td bgcolor="#ffffff" align="center">${bean.repayPeriod}</td>
          <td bgcolor="#ffffff" align="center"><s:date name="#bean.repayDate" format="yyyy-MM-hh"/> </td>
          <td bgcolor="#ffffff" align="center">${bean.hasPI} </td>
          <td bgcolor="#ffffff" align="center">${bean.stillPI} </td>
          <td bgcolor="#ffffff" align="center">${bean.hasFI} </td>
          <td bgcolor="#ffffff" align="center">${bean.lateFI} </td>
          <td bgcolor="#ffffff" align="center">
          <s:if test="#bean.repayStatus == 1">
                                      未偿还
          </s:if>
          <s:elseif test="#bean.repayStatus == 2">
                                       已偿还
          </s:elseif>
          <s:elseif test="#bean.repayStatus == 3">
                                       偿还中
          </s:elseif>
          </td>
      </tr>
  </s:iterator>        
  </s:if>
  <s:else>
      <td colspan="7" align="center" bgcolor="#ffffff" height="30">没有数据</td>
  </s:else>
</table>