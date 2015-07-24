<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">序号</th>
				<th scope="col">用户名</th>
				<th scope="col">真实姓名</th>
				<th scope="col">借款标题</th>
				<th scope="col">借款金额</th>
				<th scope="col">借款类型</th>
				<th scope="col">借款期限</th>
				<th scope="col">发布时间</th>
				<th scope="col">逾期天数</th>
				<th scope="col">已还总额</th>
				<th scope="col">代偿次数</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="11">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td><s:property value="#s.count+#counts" /></td>
						<td align="center">${bean.username}</td>
						<td>${bean.realName}</td>
						<td><a href="${basePath}/financeDetail.do?id=${bean.borrowId}" target="_blank">${bean.borrowTitle}</a></td>
						<td>￥${bean.borrowAmount}</td>
						<td><s:if test="#bean.borrowWay == 1">
							薪金贷
							</s:if> <s:elseif test="#bean.borrowWay == 2">
							生意贷
							</s:elseif> <s:elseif test="#bean.borrowWay == 3">
							业主贷
							</s:elseif></td>
						<td>${bean.deadline}</td>
						<td><s:date name="#bean.publishTime" format="yyyy-MM-dd"></s:date>
						</td>
						<td>${bean.overDay}</td>
						<td>￥${bean.hasPay}</td>
						<td>${bean.webPayCount}</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<p />
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>