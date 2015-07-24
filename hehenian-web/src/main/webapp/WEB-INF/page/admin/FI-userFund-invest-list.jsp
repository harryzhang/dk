<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="overflow-x: auto; overflow-y: auto; width: 100%;">
	<table id="help" style="width: 1020px; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tr class=gvHeader>
			<td>借款人</td>
			<td>借款标题</td>
			<td>借款类型</td>
			<td>借款金额</td>
			<td>借款时间</td>
			<td>年利率</td>
			<td>还款期限</td>
			<td>应还本息</td>
			<td>实还本息</td>
<%--<td>提前还款收益</td>--%>
		</tr>
		<s:if test="pageBean.page==null || pageBean.page.size==0">
			<tr align="center" class="gvItem">
				<td colspan="10">暂无数据</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="pageBean.page" var="bean">
				<tr class="gvItem">
					<td>${bean.publishername}</td>
					<td>${bean.borrowTitle}</td>
					<td><s:if test="#bean.borrowWay==1">薪金贷</s:if> <s:if test="#bean.borrowWay==2">生意贷</s:if> <s:if test="#bean.borrowWay==3">业主贷</s:if></td>
					<td>${bean.investAmount}</td>
					<td>${bean.investTime}</td>
					<td>${bean.annualRate}</td>
					<td>${bean.deadline}</td>
					<td>${bean.recievedAmount}</td>
					<td>${bean.hasAmount}</td>
<%--<td>&nbsp;</td>--%>
				</tr>
			</s:iterator>
		</s:else>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>
