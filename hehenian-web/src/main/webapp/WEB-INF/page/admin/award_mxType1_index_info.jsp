<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th scope="col">
						投资人
					</th>
					<th scope="col">
						真实姓名
					</th>
					<th scope="col">
						贡献时间
					</th>
					<th scope="col">
						贡献奖励（元）
					</th>
					<th scope="col">
						最大的待收金额（元）
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td>
						${st.count }
						</td>
						<td>
							${userName}
						</td>
						<td>
						${realName }
						</td>
						<td>
							<s:date name="#bean.addDate" format="yyyy-MM-dd"/>
						</td>
						<td>
							${level2money}
						</td>
						<td>
							${mx }
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
