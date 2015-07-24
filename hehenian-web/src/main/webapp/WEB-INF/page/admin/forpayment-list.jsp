<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						投资人
					</th>
					<th  style="width: 150px;" scope="col">
						姓名
					</th>
					<th style="width: 150px;" scope="col">
						用户组
					</th>
					<th style="width: 150px;" scope="col">
						投资时间
					</th>
					<th style="width: 150px;" scope="col">
						应还时间
					</th>
					<th style="width: 150px;" scope="col">
						还款期数/总期数
					</th>
					<th style="width: 150px;" scope="col">
						应收金额
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
						<td>
							<s:property value="#s.count+#counts"/>
						</td>
						<td align="center">
							${bean.investor}
						</td>
						<td align="center">
						
							<s:if test='#bean.realName==null '>
								未填写
							</s:if>
							<s:else>
									${bean.realName}
							</s:else>
						</td>
						<td align="center">
							<s:if test='#bean.groupName==null '>
								未分配
							</s:if>
							<s:else>
								${bean.groupName}
							</s:else>
						</td>
						<td>
							${bean.investTime }
						</td>
						<td>
							${bean.repayDate }
						</td>
						<td>
						   ${bean.repayPeriod}
						</td>
					<td>
							${bean.forTotalSum }
					</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr >
					<td  colspan="2"><strong>合计项</strong></td>
					<td colspan="0" align="right" >应收款总合计:</td>
					<td  colspan="2" >￥<s:if test="#request.repaymentMap.amount==''">0</s:if><s:else>${repaymentMap.amount}</s:else>元</td>
					<td colspan="2" align="right" >当前页应收款合计:</td>
					<td  align="center" >￥${receivableAmount }</td>			
					</tr>
			</tbody>
		</table>
		<p/>
		<%--<span class="require-field">当前页应收款合计:￥${receivableAmount}元</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span class="require-field">应收款总合计:￥<s:if test="#request.repaymentMap.amount==''">0</s:if><s:else>${repaymentMap.amount}</s:else>元</span>
--%></div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
