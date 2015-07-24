<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">订单号</th>
				<th scope="col">用户名</th>
				<th scope="col">交易金额</th>
				<th scope="col">交易状态</th>
				<th scope="col">汇付时间</th>
				<th scope="col">汇付流水</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="8">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>${bean.OrdId }</td>
						<td>${bean.username}</td>
						<td>${bean.TransAmt}</td>
						<%--S成功 F失败 I初始 P部分成功 H经办 R拒绝--%>
						<td>
						<s:if test="%{#bean.TransStat == \"S\"}">成功</s:if>
						<s:elseif test="%{#bean.TransStat == \"F\"}">失败</s:elseif>
						<s:elseif test="%{#bean.TransStat == \"I\"}">初始</s:elseif>
						<s:elseif test="%{#bean.TransStat == \"P\"}">部分成功</s:elseif>
						<s:elseif test="%{#bean.TransStat == \"H\"}">经办</s:elseif>
						<s:elseif test="%{#bean.TransStat == \"R\"}">拒绝</s:elseif>
						</td>
						<td>${bean.PnrDate}</td>
						<td>${bean.PnrSeqId }</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<shove:page url="merRechargeCheckList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
