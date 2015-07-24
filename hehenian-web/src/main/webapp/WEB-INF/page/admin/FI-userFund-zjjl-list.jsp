<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="overflow-x: auto; overflow-y: auto; width: 100%;">
	<table id="help" style="width: 1020px; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">序号</th>
				<th scope="col">用户名</th>
				<th scope="col">类型</th>
				<th scope="col">操作金额</th>
				<th scope="col">总金额</th>
				<th scope="col">可用余额</th>
				<th scope="col">冻结金额</th>
				<th scope="col">待收本金</th>
				<th scope="col">待收利息</th>
				<th scope="col">待收总额</th>
				<th scope="col">交易对方</th>
				<th scope="col">记录时间</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="12">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center"><s:property value="#s.count+#counts" />
						</td>
						<td>${username}</td>
						<td>${bean.fundMode}</td>
						<td>${bean.handleSum}</td>
						<td>${bean.totalSum}</td>
						<td>${bean.usableSum}</td>
						<td>${bean.freezeSum}</td>
						<td>${bean.dueinCapitalSum }</td>
						<td>${bean.dueinAccrualSum }</td>
						<td>${bean.dueinSum}</td>
						<td>${traderName }</td>
						<td><s:date name="#bean.recordTime " format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>


	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>