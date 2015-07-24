<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					
					<th scope="col" style="width: 100px;" >
					银行名称
					</th>
					<th scope="col" style="width: 100px;" >
					账号
					</th>
					<th style="width: 150px;" scope="col">
					开户行
					</th>
					<th style="width: 100px;" scope="col">
					银行图片
					</th>
						<th style="width: 100px;" scope="col">
				操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="5">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						
						<td align="center">
							${bankname}
						</td>
						<td>
							${Account}
						</td>
						<td>
							${accountbank }
						</td>
						<td>
						<img src="../${bankimage}" style="width:150px;height:50px;"/>
						</td>
						<td>
							<a href="updateRechargeBankInit.do?id=${id}">编辑</a>
						</td>
						
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
