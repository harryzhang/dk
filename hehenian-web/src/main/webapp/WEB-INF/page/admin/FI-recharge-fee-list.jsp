<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<div>
	<table id="help" style="color: #333333; width: 100%" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">ID</th>
				<th scope="col">用户名</th>
				<th scope="col">真实姓名</th>
				<th scope="col">类型</th>
				<th scope="col">操作金额</th>
				<th scope="col">备注</th>
				<th scope="col">操作人员</th>
				<th scope="col">操作时间</th>
				<th scope="col">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="9">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>${bean.name}</td>
						<td>${bean.realName}</td>
						<td>${bean.acount}</td>
						<td>${bean.bankName}</td>
						<td>${bean.branchBankName}</td>
						<td>${bean.sum}</td>
						<td>${bean.realMoney}</td>
						<td>${bean.poundage }</td>
						<td><s:date name="#bean.applyTime"
								format="yyyy-MM-dd HH:mm:ss"></s:date></td>
					</tr>
				</s:iterator>
			</s:else>
			<tr class="gvItem">
				<td colspan="9" align="left"><font size="2">共有${totalNum
						}条记录</font></td>
			</tr>
		</tbody>
	</table>

	<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
		pageSize="${pageBean.pageSize }" theme="jsNumber"
		totalCount="${pageBean.totalNum}"></shove:page>

</div>
<script type="text/javascript" src="../css/admin/popom.js"></script>
