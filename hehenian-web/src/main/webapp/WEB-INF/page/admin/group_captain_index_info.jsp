<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
	<div>
		<table id="gvNews" style="width: 98%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					
					<th scope="col" style="width: 100px;" >
						团队长账号
					</th>
					<th scope="col" style="width: 100px;" >
						姓名
					</th>
					<th style="width: 150px;" scope="col">
						身份证号码
					</th>
					<th style="width: 100px;" scope="col">
						手机号码
					</th>
					<th style="width: 260px;" scope="col">
						描述
					</th>
					<th style="width: 120px;" scope="col">
						添加时间
					</th>
					<th style="width: 60px;" scope="col">
						状态
					</th>
					<th style="width: 70px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td align="center">
							${userName}
						</td>
						<td>
							${realName}
						</td>
						<td>
							${card }
						</td>
						<td>
							${telphone }
						</td>
						<td>
							${summary }
						</td>
						<td>
						${bean.addDate }
						</td>
						<td>
							${bean.enable==1?'启用':'禁用' }
						</td>
							<td>
								<a href="javascript:edit('updateGroupCaptainInit.do',${bean.id})" target="main">
						编辑</a>
						
					</td></tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
