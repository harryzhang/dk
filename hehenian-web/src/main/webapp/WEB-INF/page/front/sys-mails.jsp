<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
        <div class="biaoge" id="biaoge">
        <input type="hidden" id="curPage" value="${pageBean.pageNum}">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th><input type="checkbox" name="checkbox" id="checkbox" onclick="checkAll_Sys(this)" /></th>
    <th>标记</th>
    <th>发件人</th>
    <th colspan="6">标题</th>
    <th>发送时间</th>
  </tr>
  
  	<s:if test="pageBean.page!=null && pageBean.page.size>0">
		       <s:iterator value="pageBean.page" id="querySysMails" var="bean">
		         <s:if test="#bean.mailStatus=='未读'">
				    <tr>
				      <td align="center"><input type="checkbox" value="${bean.id}" name="sysMail" id="checkbox2" class="sys" /></td>
				      <td align="center"><b>${bean.mailStatus}</b><br /></td>
				      <td align="center"><b>${bean.sender}</b><br /></td>
				      <td colspan="6" align="center"><a href="javascript:void(0);" onclick="showSysMailInfo(${bean.id},1)" ><b>${bean.mailTitle}</b></a><br /></td>
				      <td align="center"><b><s:date name="#bean.sendTime" format="yyyy-MM-dd HH:mm:ss" /></b></td>
				    </tr>
			    </s:if>
			    <s:else>
			        <tr>
				      <td align="center"><input type="checkbox" value="${bean.id}" name="sysMail" id="checkbox2" class="sys" /></td>
				      <td align="center">${bean.mailStatus}<br /></td>
				      <td align="center">${bean.sender}<br /></td>
				      <td colspan="6" align="center"><a href="javascript:void(0);" onclick="showSysMailInfo(${bean.id},0)">${bean.mailTitle}</a><br /></td>
				      <td align="center"><s:date name="#bean.sendTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				    </tr>
			    </s:else>
			 </s:iterator>
		</s:if>
		<s:else>
		    <tr><td colspan="10" align="center">暂无信息</td></tr>
		</s:else>
          </table>
<div class="page">
		<p>
		<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>		
	    </p>
	</div> 
          </div>