<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<style>
* {
	font-size: 12px;
	font-family: "tahoma";
	padding: 4px 0px;
}
</style>
<div style="padding: 0px 50px;">
	<h2 style="text-align: center;font-size: 14px;">投标进度</h2>
	<table id="gvNews" style="width: 100%; color: #333333;border-collapse: collapse;" border="1">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">投标时间</th>
				<th scope="col">投标人</th>
				<th scope="col">投资金额</th>
				<th scope="col">投标金额占借款百分比</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="4">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center"><s:date name="#bean.investTime" format="yyyy-MM-dd HH:mm:ss"></s:date></td>
						<td align="center">${bean.username}</td>
						<td align="center">￥${bean.investAmount}</td>
						<td align="center">${bean.trad}</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<div style="margin-top: 10px;" align="right">
		<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	</div>
</div>