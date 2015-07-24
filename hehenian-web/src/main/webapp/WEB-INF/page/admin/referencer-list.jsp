<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">ID</th>
				<th scope="col">用户名</th>
				<th scope="col">可用余额</th>
				<th scope="col">邮箱</th>
				<th scope="col">最后登录IP</th>
				<th scope="col">最后登录时间</th>
				<th scope="col">推荐个数</th>
			</tr>
			<s:if test="pageBean.page==null || #request.list.size==0">
				<tr align="center" class="gvItem">
					<td colspan="9">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">${id }</td>
						<td align="center">${bean.username}</td>
						<td align="center">${usableSum}</td>
						<td align="center">${bean.email}</td>
						<td align="center">${bean.lastIP}</td>
						<td align="center"><s:date name="#bean.lastDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center">${bean.count}</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<p />
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>