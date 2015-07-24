<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<div class="biaoge" id="send_biaoge" >
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
<%--     	<tr>--%>
<%--		    <th width="20%">标题</th>--%>
<%--		    <th>内容</th>--%>
<%--  		</tr>--%>
  		<s:if test="#request.map!=null && #request.map.size!=0">
		       <s:iterator value="#request.map" id="querySendMails" var="bean">
				    <tr>
<%--				      <td align="center">${bean.title }</td>--%>
				      <td align="left">${bean.content }</td>
				    </tr>
			 </s:iterator>
		</s:if>
		<s:else>
		    <tr><td colspan="2" align="center">暂无信息</td></tr>
		</s:else>
          </table>

	<div class="page">
	</div> 
	
	</div>
          