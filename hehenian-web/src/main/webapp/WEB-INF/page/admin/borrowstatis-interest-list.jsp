<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						真实姓名
					</th>
					<th style="width: 200px;" scope="col">
						期间投资管理费利息总额
					</th>
					<th style="width: 200px;" scope="col">
						期间收到还款总额
					</th>
					<th style="width: 150px;" scope="col">
						已赚利息总额
					</th>
					<th style="width: 150px;" scope="col">
						利息管理费
					</th>
					<th style="width: 150px;" scope="col">
						待收利息总额
					</th>
					<th style="width: 150px;" scope="col">
						待收总额
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
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
							${bean.investor}
						</td>
						<td align="center">
							${bean.realName}
						</td>
						<td align="center">
							${bean.manageFI}
						</td>
						<td align="center">
							${bean.hasPI}
						</td>
						<td align="center">
							${bean.hasInterest}
						</td>
						<td align="center">
							${bean.manageFee}
						</td>
						<td align="center">
							${bean.forInterest}
						</td>
						<td align="center">
							${bean.forPI}
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>