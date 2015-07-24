<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="center">审核项目</th>
    <th align="center">状态</th>
    <th align="center">通过时间</th>
    <th align="center">操作</th>
  </tr>
  <s:if test="%{#request.auditList !=null && #request.auditList.size()>0}">
      <s:iterator value="#request.auditList" id="bean">
      <tr>
          <td align="center">${bean.name}</td>
          <td align="center">
          <s:if test="#bean.auditStatus == 1">
                                      等待审核
          </s:if>
          <s:elseif test="#bean.auditStatus == 2">
                                       审核失败
          </s:elseif>
          <s:elseif test="#bean.auditStatus == 3">
              <img src="images/neiye2_44.jpg" width="14" height="15" />
          </s:elseif>
          </td>
          <td align="center">${bean.passTime} </td>
          <td align="center" class="ck">
          <s:if test="#bean.visiable== 1 && #bean.auditStatus==3">
			  <a href="javascript:showImg('${bean.materAuthTypeId}','${bean.userId}','${user.id }');">查看</a>
          </s:if>
          <s:else>---
          </s:else>
          </td>
      </tr>
  </s:iterator>        
  </s:if>
  <s:else>
      <td colspan="4" align="center">没有数据</td>
  </s:else>
</table>