<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						借款者
					</th>
					<th scope="col">
						转让者
					</th>
					<th scope="col">
						标题
					</th>
					<th scope="col">
						借款类型
					</th>
					<th scope="col">
						还款方式
					</th>
					
					<th scope="col">
						年利率
					</th>
					<th scope="col">
						债权期限
					</th>
					<th scope="col">
						债权金额
					</th>
					<th scope="col">
						竞拍底价
					</th>
					<th scope="col">
						最近还款日
					</th>
					<th scope="col">
						有无逾期
					</th>
					<th style="width: 90px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="13">暂无数据</td>
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
							${borrowerName}
						</td>
						<td align="center">
							${alienatorName}
						</td>
						<td align="center">
						<a href="../financeDetail.do?id=${borrowId }" target="_blank">${borrowTitle}</a>
						</td>
						<td align="center">
							<s:if test="#bean.borrowWay==1">
							 薪金贷 
							</s:if>
							<s:elseif test="#bean.borrowWay==2">生意贷</s:elseif>
							<s:elseif test="#bean.borrowWay==3">业主贷</s:elseif>
							<s:elseif test="#bean.borrowWay==4">实地考察借款</s:elseif>
							<s:elseif test="#bean.borrowWay==5">机构担保借款</s:elseif>
							<s:elseif test="#bean.borrowWay==6">流转标借款</s:elseif>
						</td>
						<td align="center">
							<s:if test="#bean.paymentMode==1">
								 按月等额本息还款 
							</s:if>
							<s:elseif test="#bean.paymentMode==2">
								按先息后本还款
							</s:elseif>
							<s:else>
									一次性还款
							</s:else>
						</td>
						<td align="center">
							${annualRate}
						</td>
						<td align="center">
							${debtLimit}个月
						</td>
						<td align="center">
							${debtSum}
						</td>
						<td align="center">
							${auctionBasePrice}元
						</td>
						<td align="center">
							${repayDate}
						</td>
						<td align="center">
						<s:if test="#bean.isLate==1">
								 无 
							</s:if>
							<s:else>
								有
							</s:else>
						
						</td>
							<td>
							<a href="queryApplyDebtAuditDetail.do?id=${id}&paramMap.borrowerName=${borrowerName}&paramMap.isLate=${isLate}&paramMap.alienatorName=${alienatorName}&paramMap.borrowTitle=${borrowTitle}"
							 target="main">
										审核</a>
					</td></tr>
				</s:iterator>
				</s:else>
				<tr >
				<td  colspan="3"><strong>合计项</strong></td>
				<td colspan="5" align="right" >当前页的申请债权转让金额合计:</td>
				<td  align="center" >￥${debtSumm}</td>	
				<td colspan="2" align="right" >所有申请债权转让金额合计:</td>
				<td  colspan="2" >￥
				<s:if test="%{#request.repaymentMap.applydebtSum==''}">0</s:if>
				<s:else> <s:property value="#request.repaymentMap.applydebtSum" default="0"/></s:else>
				
				</td>		
				</tr>
			</tbody>
		</table>
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
