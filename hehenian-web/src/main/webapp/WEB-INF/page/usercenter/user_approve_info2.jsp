 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
		<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="8">${userMap.rating}分&nbsp;<img src="images/ico_r_${homeMap.rating}.gif"/></th>
    </tr>
      <tr>
    <td colspan="6" align="center">积分类型</td>
    <td align="center">积分</td>
    <td align="center">备注</td>
  </tr>	
 <s:if test="#pageBean.page==null || pageBean.page.size==0">
	<tr>
		<td align="center" colspan="6">暂无数据</td>
	</tr>
  </s:if>
  <s:else>
	<s:iterator value="pageBean.page" var="bean" status="sta">

  <tr>
    <td colspan="6" align="center">${intergraltype}</td>
    <td align="center">${changerecore}</td>
    <td align="center">${remark}</td>
  </tr>
  </s:iterator>
  </s:else>
 </table>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>