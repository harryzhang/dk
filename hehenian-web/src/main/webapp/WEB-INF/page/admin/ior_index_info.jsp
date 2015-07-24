<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th scope="col">
						借款人
					</th>
					<th scope="col">
						标题
					</th>
					<th scope="col">
						类型
					</th>
					<th scope="col">
						借款金额
					</th>
					<th scope="col">
						年利率
					</th>
					<th scope="col">
						期限
					</th>
					<th scope="col">
						发布时间
					</th>
					<th scope="col">
						投资金额（元）
					</th>
					<th scope="col">
						投资时间
					</th>
					<th scope="col">
						奖励提成金额（元）
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="11">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td>
						${st.count }
						</td>
						<td>
							${userName}
						</td>
						<td>
						${borrowTitle }
						</td>
						<td>
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
						<td>
							${borrowAmount}
						</td>
						<td>
							${annualRate}%
						</td>
						<td>
							${deadline }个月
						</td>
						<td>
							<s:date name="#bean.publishTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${investAmount }
						</td>
						<td>
							<s:date name="#bean.investTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${level2money }
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
