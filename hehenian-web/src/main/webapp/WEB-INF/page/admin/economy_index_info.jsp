<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
	<div>
		<table id="gvNews" style="width: 98%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col" style="width: 100px;">
					序号
						</th>
					<th scope="col" style="width: 100px;" >
						经纪人账号
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
					<th style="width: 120px;" scope="col">
						添加时间
					</th>
					<th style="width: 60px;" scope="col">
						状态
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
					<td >
							<s:property value="#st.count+#counts" />
						</td>
						<td align="center">
							${username}
						</td>
						<td>
							${realName}
						</td>
						<td>
							${idNo }
						</td>
						<td>
							${mobilePhone }
						</td>
						<td>
						${bean.createTime }
						</td>
						<td>
							${bean.enable==1?'启用':'禁用' }
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
