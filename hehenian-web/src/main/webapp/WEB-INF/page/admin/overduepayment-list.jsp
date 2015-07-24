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
				<th scope="col">期数</th>
				<th scope="col">类型</th>
				<th scope="col">应还时间</th>
				<th scope="col">逾期天数</th>
				<th scope="col">应还金额</th>
				<th scope="col">逾期金额</th>
				<th scope="col">总还款</th>
				<th scope="col">网站代偿</th>
				<th scope="col">还款状态</th>
				<th scope="col">代偿次数</th>
				<th scope="col">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="15">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td><s:property value="#s.count+#counts" /></td>
						<td align="center">${bean.username}</td>
						<td>${bean.realName}</td>
						<td><a href="${basePath}//financeDetail.do?id=${bean.borrowId}" target="_blank">${bean.borrowTitle}</a></td>
						<td>${bean.repayPeriod}</td>
						<td><s:if test="#bean.borrowWay==1">
							薪金贷
							</s:if> <s:elseif test="#bean.borrowWay==2">
							生意贷
							</s:elseif> <s:elseif test="#bean.borrowWay==3">
							业主贷</s:elseif>
							</td>

						<td>${bean.repayDate}</td>
						<td>${bean.lateDay}</td>
						<td>￥${bean.totalSum}</td>
						<td>￥${bean.lateFI}</td>
						<td>￥${bean.repaySum}</td>
						<td><s:if test="#bean.isWebRepay == 1">
							   否
							</s:if> <s:elseif test="#bean.isWebRepay == 2">
							  是
							</s:elseif></td>
						<td><s:if test="#bean.repayStatus==1">
							   未偿还
							</s:if> <s:elseif test="#bean.repayStatus==2">
							  已偿还
							</s:elseif> <s:elseif test="#bean.repayStatus==3">
							  偿还中
							</s:elseif></td>
							<td>${bean.webPayCount}</td>
						<td><s:if test="#bean.repayStatus == 1 && #bean.isWebRepay == 1">
									<a href="javascript:repaySubmit(${bean.id},${bean.borrowId},this);">代偿</a>
							</s:if>
							<s:else>--</s:else>
<%--							<a href="repaymentDetail.do?id=${bean.id}">查看</a>--%>
							</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<p />
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>