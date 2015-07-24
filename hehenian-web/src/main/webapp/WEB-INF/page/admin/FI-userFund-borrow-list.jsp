<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="overflow-x: auto; overflow-y: auto; width: 100%;">
	<table id="help" style="width: 1020px; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tr class=gvHeader>
			<th style="width: 150px;" scope="col">借款标题</th>
			<th style="width: 160px;" scope="col">借款类型</th>
			<th style="width: 140px;" scope="col">借款金额</th>
			<th style="width: 100px;" scope="col">借款时间</th>
			<th style="width: 100px;" scope="col">年利率</th>
<%-- <th style="width: 230px;" scope="col">借款保底金额</th>--%>
			<th style="width: 160px;" scope="col">还款期限</th>
<%--			<th style="width: 160px;" scope="col">投资人</th>--%>
<%--			<th style="width: 110px;" scope="col">应还本息</th>--%>
<%--			<th style="width: 110px;" scope="col">实还本息</th>--%>
<%--			<th style="width: 160px;" scope="col" nowrap="nowrap">实际还清日期</th>--%>
		</tr>
		<s:if test="pageBean.page==null || pageBean.page.size==0">
			<tr align="center" class="gvItem">
				<td colspan="11">暂无数据</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="pageBean.page" var="bean">
				<tr class="gvItem">
					<td>${bean.borrowTitle}</td>
					<td><s:if test="#bean.borrowWay==1">薪金贷</s:if> <s:if test="#bean.borrowWay==2">生意贷</s:if> <s:if test="#bean.borrowWay==3">业主贷</s:if></td>
					<td>${bean.borrowAmount}</td>
					<td><s:date name="#bean.publishTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${bean.annualRate}%</td>
<%-- <td>${bean.minTenderedSum}</td>--%>
					<td>${bean.deadline}</td>
<%--					<td>${bean.investname}</td>--%>
<%--					<td>${bean.recievedAmount}</td>--%>
<%--					<td>${bean.hasAmount}</td>--%>
<%--					<td><s:date name="#bean.repayDate" format="yyyy-MM-dd" /></td>--%>
				</tr>
			</s:iterator>
		</s:else>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>
