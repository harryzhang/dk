<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 35px;" scope="col">
					序号
				</th>
				<th style="width: 100px;" scope="col">
					用户名
				</th>
				<th style="width: 100px;" scope="col">
					个人投资情况
				</th>
				<th style="width: 100px;" scope="col">
					风险偏好
				</th>
				<th style="width: 220px;" scope="col">
					互联网金融工具使用情况
				</th>
				<th style="width: 220px;" scope="col">
					P2P网贷平台的认知程度
				</th>
				<th style="width: 80px;" scope="col">
					年龄
				</th>
				<th style="width: 80px;" scope="col">
					性别
				</th>
				<th style="width: 150px;" scope="col">
					收入情况
				</th>
				<th style="width: 110px;" scope="col">
					风险级别
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="10">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							<s:property value="#s.count+#counts" />
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
						<td align="center">
							${bean.forPI}
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>