<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 40px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						用户名
					</th>
<%--					<th style="width: 150px;" scope="col">--%>
<%--						用户组--%>
<%--					</th>--%>
					<th style="width: 150px;" scope="col">
						投标扣除金额(元)
					</th>
					<th style="width: 150px;" scope="col">
						交易对方
					</th>
					<th style="width: 200px;" scope="col">
						借款标题
					</th>
					<th style="width: 150px;" scope="col">
						借款类型
					</th>
					<th style="width: 150px;" scope="col">
						是否自动投标
					</th>
					<th style="width: 150px;" scope="col">
						是否投标成功
					</th>
					<th style="width: 150px;" scope="col">
						投标时间
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							<s:property value="#s.count+#counts"/>
						</td>
						<td align="center">
							${bean.investor}
						</td>
<%--						<td align="center">--%>
<%--							${bean.groupName}--%>
<%--						</td>--%>
						<td align="center">
							${bean.realAmount}
						</td>
						<td align="center">
							${bean.borrower}
						</td>
						<td align="center">
							${bean.borrowTitle}
						</td>
						<td align="center">
							<s:if test="#bean.borrowWay==1">
							薪金贷
							</s:if>
							<s:elseif test="#bean.borrowWay==2">
							生意贷
							</s:elseif>
							<s:elseif test="#bean.borrowWay==3">
							业主贷
							</s:elseif>
							<s:elseif test="#bean.borrowWay==4">
							实地考察借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==5">
							机构担保借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==6">
							流转标借款
							</s:elseif>
						</td>
						<td align="center">
						    <s:if test="#bean.isAutoBid==1">
							  否
							</s:if>
							<s:elseif test="#bean.isAutoBid==2">
							  是
							</s:elseif>
						</td>
						<td align="center">
							<s:if test="#bean.borrowStatus==4">
							  是
							</s:if>
							<s:elseif test="#bean.borrowStatus==5">
							是
							</s:elseif>
							<s:else>
							否
							</s:else>
						</td>
						<td align="center">
							${bean.investTime}
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr class="gvItem"><td colspan="10" align="left"><font size="2">共有${totalNum }条记录</font></td></tr>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>