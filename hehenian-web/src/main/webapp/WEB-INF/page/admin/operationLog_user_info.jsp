<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">
					ID
				</th>
				<th scope="col">
					用户名
				</th>
				
				<th scope="col">
					类型
				</th>
				<th scope="col">
					时间	
				</th>
				<th scope="col">
					备注	
				</th>
				<th scope="col">
					IP	
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="8">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td scope="col">
							<s:property value="#s.count+#counts" />
						</td>
						<td scope="col">
							${bean.operation_user}
						</td>
						<td scope="col">
							<s:if test="#bean.operation_type==1">增加</s:if>
						<s:if test="#bean.operation_type==2">修改</s:if>
						<s:if test="#bean.operation_type==3">删除</s:if>
						<s:if test="#bean.operation_type==4">下载</s:if>
						<s:if test="#bean.operation_type==5">导出Excel</s:if>
						</td>
						
						<td scope="col">
							${bean.operation_time}
						</td>
						<td scope="col">
								${bean.operation_remarks}
						</td>
						<td scope="col">
							${bean.operation_ip}
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
