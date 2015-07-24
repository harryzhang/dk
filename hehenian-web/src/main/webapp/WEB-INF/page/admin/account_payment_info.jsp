<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 200px;" scope="col">
						支付logo
					</th>
					<th style="width: 100px;" scope="col">
						支付名称
					</th>
					<th style="width: 380px;" scope="col">
						支付简介
					</th>
					<th style="width: 120px;" scope="col">
						操作
					</th>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="4">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td>
								<s:property value="#s.count+#counts"/>
							</td>

							<td>
								<img alt="" src="../${litpic }">
							</td>
							<td>
								${name }
							</td>
								<td align="left">
						   		${description }
							</td>
							<td>
							<a href="updateAccountPayInit.do?nid=${nid }">编辑</a> |
							<s:if test="#bean.status==1">
							<a href="delteAccountPay.do?id=${id}&status=2">关闭</a>
							</s:if><s:else>
							<a href="delteAccountPay.do?id=${id}&status=1">开启</a>
							</s:else>
							</td>		
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	