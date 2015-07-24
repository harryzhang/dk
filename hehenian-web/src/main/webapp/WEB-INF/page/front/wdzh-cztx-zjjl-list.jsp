<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <th>序号</th>
		    <th>时间</th>
		    <th>操作类型</th>
		    <th>备注</th>
		    <th>收入</th>
		    <th>支出</th>
		    <th>可用余额</th>
		    <th>冻结金额</th>
		    <th>待收金额</th>
		    <th>总金额</th>
		  </tr>
		  
		  <s:if test="pageBean.page!=null && pageBean.page.size>0">
		  <s:set name="counts" value="#request.pageNum"/>
            <s:iterator value="pageBean.page"  var="bean" status="s">
			    <tr>
				    <td align="center"><s:property value="#s.count+#counts"/></td>
				    <td align="center" width="70px;"><s:date name="#bean.recordTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				   	<td align="center">${bean.fundMode }</td>
				    <td align="left" width="95px;" >${bean.remarks }</td>
				    <td align="center">
				    		<s:if test="#bean.income==0"></s:if>
				    		<s:else>${bean.income }</s:else>
				    </td>
				    <td align="center">
				    	<s:if test="#bean.spending==0"></s:if>
				    		<s:else>${bean.spending }</s:else>
				    </td>
				    <td align="center">${bean.usableSum }</td>
				    <td align="center">${bean.freezeSum }</td>
				    <td align="center">${bean.dueinSum }</td>
				    <td align="center">${bean.sum }</td>
		  		</tr>
			  </s:iterator>
			  <tr >
			  	<td colspan="10">合计：总收入：￥${paramMap.SumincomeSum }&nbsp;&nbsp;&nbsp;&nbsp; 总支出：￥${paramMap.SumspendingSum }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  										 可用金额合计：￥${paramMap.SumusableSum }&nbsp;</td>
			  </tr>
			  
		  </s:if>
		  <s:else>
		    <tr><td colspan="11" align="center">暂无信息</td></tr>
		  </s:else>
	</table>
	
	<div class="page">
	<p>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	
    </p>
</div> 