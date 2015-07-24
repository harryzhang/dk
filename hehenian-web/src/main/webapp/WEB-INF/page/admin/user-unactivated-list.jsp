<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						会员号
					</th>
					<th style="width: 150px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						邮箱
					</th>
					<th style="width: 150px;" scope="col">
						创建时间
					</th>
					<th style="width: 150px;" scope="col">
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
<%--							<s:property value="#s.count+#counts"/>--%>
							${id}
						</td>
						<td align="center">
							${username}
						</td>
							<td>
							${email}
						</td>
						<td>
							${createTime}
						</td>
						<td>
							<a href="javascript:edit(${id})">查看</a>|<a href="javascript:viod(0)" onclick="del(${id})">激活用户</a>
						</td>
						
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<script type="text/javascript">
	
</script>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
