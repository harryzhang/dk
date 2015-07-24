<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script type="text/javascript">
	
</script>
<div>
	<div></div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 80px;" scope="col">
					会员号
				</th>
				<th style="width: 80px;" scope="col">
					投资人
				</th>
				<th style="width: 80px;" scope="col">
					投资总额
				</th>
				<th style="width: 80px;" scope="col">
					期数
				</th>
				<th style="width: 100px;" scope="col">应收本金</th>
				<th style="width: 100px;" scope="col">应收利息</th>
				<th style="width: 100px;" scope="col">已收本金</th>
				<th style="width: 100px;" scope="col">已收利息</th>
<%--				<th style="width: 80px;" scope="col">--%>
<%--					利率--%>
<%--				</th>--%>
				<th style="width: 80px;" scope="col">
					管理费
				</th>
				<th style="width: 150px;" scope="col">
					投资时间
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="11">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
<%--						<td align="center" style="width: 100px;" class="borrow">--%>
<%--							<input type="hidden" class="borrowId" value="${bean.borrowId}" />--%>
<%--							${bean.number }--%>
<%--						</td>--%>
						<td align="center" style="width: 100px;" class="borrow">
							${bean.userId }
						</td>
						<td>
							${bean.username}
							<input id="userName" value="${bean.username}" type="hidden">
						</td>
						<td>
							${bean.investAmount}
						</td>
						<td>
							${bean.deadline}
						</td>
						<td>
							${bean.recivedPrincipal}
						</td>
						<td>
							${bean.recievedInterest}
						</td>
						<td>
							${bean.hasPrincipal}
						</td>
						<td>
							${bean.hasInterest}
						</td>
<%--						<td>--%>
<%--							${bean.monthRate}--%>
<%--						</td>--%>
						<td>
							${bean.manageFee}
						</td>
						<td>
							<s:date name="#bean.investTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<br />
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>
