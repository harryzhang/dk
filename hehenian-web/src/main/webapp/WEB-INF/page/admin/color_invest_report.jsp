<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<div>
	<div></div>
	<table id="help" style="width: 130%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
            															

				<th align="center" style="width: 100px;" class="borrow">
							投资人账户名
						</th>
						<th>
							投资人姓名
						</th>
						<th>
							投资人电话
						</th>
						<th>
							投资人所属事业部
						</th>
						<th>
							投资人所属小区
						</th>
						<th>
							推荐人账户名
						</th>
						<th>
							推荐人姓名
						</th>
						<th>
							推荐人电话
						</th>
						<th>
							推荐人所属事业部
						</th>
						<th>
							 推荐人所属小区         
						</th>
                        <th>
                        	累计投资金额
                        </th>
                        <th>
                        	在库投资余额
                        </th>
                        <th>
                        	最近投资时间
                        </th>
                        <th>
                        	用户收益
                        </th>
                        <th>
                          推荐人收益
                        </th>
                        <th>
                        彩生活收益
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
						<td align="center" style="width: 100px;" class="borrow">
							${bean.username }
						</td>
						<td>
							${bean.realName}
						</td>
						<td>
							${bean.telephone}
						</td>
						<td>
							${bean.busGroupName}
						</td>
						<td>
							${bean.cname}
						</td>
						<td>
							${bean.refUsername}
						</td>
						<td>
							${bean.refRealName}
						</td>
						<td>
							${bean.refTelephone}
						</td>
						<td>
							${bean.refBusGroupName}
						</td>
						<td>
							${bean.refCname}           
						</td>
                        <td>
                        	<fmt:formatNumber value="${bean.investAmount}" type="currency"/>
                        </td>
                        <td>
                        	<fmt:formatNumber value="${bean.recivedPrincipal}" type="currency"/>
                        </td>
                        <td>
                        	${bean.investTime}
                        </td>
                        <td>
                           <fmt:formatNumber value="${bean.hasInterest}" type="currency"/>
                        </td>
                        <td>
                           <fmt:formatNumber value="${bean.refAmount}" type="currency"/>
                        </td>
                        <td>
                           <fmt:formatNumber value="${bean.colorAmount}" type="currency"/>
                        </td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<br />
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
