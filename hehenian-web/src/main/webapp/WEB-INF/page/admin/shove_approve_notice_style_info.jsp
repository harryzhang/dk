<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0" align="center">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 10%;" scope="col" align="center">
						序号
					</th>
					<th style="width: 20%;" scope="col" align="center">
						提醒类型
					</th>
					<th style="width: 10%;" scope="col" align="center" >
						标示名
					</th>
					<th scope="col" style="width: 10%;" align="center">
						排序
					</th>
					<th style="width: 30%;" scope="col" align="center">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="5">暂无数据</td>
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
								${title }
							</td>
							<td>
								${nid}
							</td>
							<td>
						   		${sort }
							</td>
							<td>
							<a href="queryNoticeTemplateAllInit.do?id=${id}&&notice_style=${notice_style}">管理</a>
							&nbsp;&nbsp;
							<a href="updateNoticeStyleInit.do?id=${id}">修改</a>
							</td>		
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	