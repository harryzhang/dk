<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th  scope="col">
						序号
					</th>
					<th  scope="col">
						团队长帐号（姓名）
					</th>
					<th  scope="col">
						真实姓名
					</th>
					<th style="width: 100px;" scope="col">
						操作金额(元)
					</th>
					<th style="width: 100px;" scope="col">
						操作人员
					</th>
					<th style="width: 80px;" scope="col">
						操作时间
					</th>
					<th style="width: 100px;" scope="col">
						备注
					</th>
						
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="sta">
				
					<tr class="gvItem">
						<td>
							${sta.count }
						</td>
						<td align="center">
							${bean.userName }<br />(${bean.realName })
						</td>
						<td>
							${bean.realName}
						</td>
						<td>
					     ${bean.handleSum }
					     
						</td>
						<td>
							${bean.checkName}
						</td>
						<td>
						${bean.checkTime }
					</td>
						<td>${bean.remark }
					</td>
					
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
