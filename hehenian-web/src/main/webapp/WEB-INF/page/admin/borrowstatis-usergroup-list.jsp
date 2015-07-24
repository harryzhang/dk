<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						组名
					</th>
					<th scope="col">
						总金额(元)
					</th>
					<th scope="col">
						冻结金额(元)
					</th>
					<th scope="col">
						待收金额(元)
					</th>
					<th scope="col">
						借款管理费金额
					</th>
					<th scope="col">
						待收利息总额
					</th>
					<th scope="col">
						VIP总额
					</th>
					<th scope="col">
						已还款总额
					</th>
					<th scope="col">
						投资总额
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							<s:property value="#s.count+#counts"/>
						</td>
						<td align="center">
							${bean.groupName}
						</td>
						<td align="center">
							<s:property value="#bean.totalSum" default="0"/>
						</td>
						<td align="center">
							<s:property value="#bean.freezeSum" default="0"/>
						</td>
						<td align="center">
							<s:property value="#bean.forPI" default="0"/>
						</td>
						<td align="center">
							<s:property value="#bean.manageFee" default="0"/>
						</td>
						<td align="center">
							<s:property value="#bean.forInterest" default="0"/>
						</td>
						<td align="center">
							<s:property value="#bean.vipFee" default="0"/>
						</td>
						<td align="center">
							<s:property value="#bean.hasPI" default="0"/>
						</td>
						<td align="center">
							<s:property value="#bean.realAmount" default="0"/>
						</td>
						
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>