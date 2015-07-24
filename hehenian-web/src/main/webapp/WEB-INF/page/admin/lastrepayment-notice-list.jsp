<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						沟通记录
					</th>
					<th style="width: 150px;" scope="col">
						沟通时间
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="12">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:iterator value="pageBean.page" var="bean">
					<tr class="gvItem">
						<td>
							${bean.id}
						</td>
						<td align="center">
							${bean.serviceContent}
						</td>
						<td>
							${bean.serviceTime}
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
