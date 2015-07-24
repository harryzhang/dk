<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						id
					</th>
					<th style="width: 150px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						借款标题
					</th>
					<th style="width: 150px;" scope="col">
						借款金额
					</th>
						<th style="width: 150px;" scope="col">
						利率
					</th>
						<th style="width: 150px;" scope="col">
						期限
					</th>
					<th style="width: 150px;" scope="col">
						已流转
					</th>
					<th style="width: 150px;" scope="col">
						已回购
					</th>
					
						<th style="width: 150px;" scope="col">
					    状态
					</th>
						<th style="width: 150px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="12">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:iterator value="pageBean.page" var="bean">
					<tr class="gvItem">
						<td>
							${id}
						</td>
						<td align="center">
							${username}
						</td>
						<td>
						${borrowTitle}
					</td>
						<td>
							${borrowAmount}
					</td>
						<td>
							${annualRate}%
					</td>
						<td>
							${deadline }
							<s:if test="%{#bean.isDayThe ==1}">个月</s:if><s:else>天</s:else>
					</td>
					<td>
							${hasCirculationNumber}
					</td>
					<td>
							${hasRepoNumber}
					</td>
					<td>
							<s:if test="#bean.borrowStatus==1">
							初审中
							</s:if>
							<s:elseif test="#bean.borrowStatus==2">
							认购中
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==4">
							回购中
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==5">
							已还完
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==6">
							审核失败
							</s:elseif>
					</td>
					<td>
					    <a href="circulationRepayDetail.do?id=${id}">还款详情</a>
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>