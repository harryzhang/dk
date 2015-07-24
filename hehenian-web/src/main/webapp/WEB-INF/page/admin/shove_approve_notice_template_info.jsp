<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0" align="center">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 10%;" scope="col">
						序号
					</th>
					<th style="width: 20%;" scope="col">
						提醒类型
					</th>
					<th style="width: 10%;" scope="col">
						名称
					</th>
					<th style="width: 10%px;" scope="col">
						标示名
					</th>
					<th style="width: 35%;" scope="col">
						模板
					</th>
					<th scope="col" style="width:7%;" align="center">
						排序
					</th>
					<th style="width: 8%;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
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
								${bean.title }
							</td>
							<td>
								${bean.name}
							</td>
							<td >
						   		${bean.nid }
							</td>
							<td align="left" >
						   		${bean.template }
							</td>
							<td >
						   		${bean.sort }
							</td>
							<td>
							&nbsp;&nbsp;
							<a href="updateNoticeTemplateInit.do?id=${bean.id}&&notice_style=${bean.notice_style}" target="main">修改</a>
							</td>		
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	