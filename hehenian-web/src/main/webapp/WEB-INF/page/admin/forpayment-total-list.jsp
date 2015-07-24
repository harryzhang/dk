<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						投资人
					</th>
					<th scope="col">
						姓名
					</th>
					<th scope="col">
						用户组
					</th>
					<th>
						投资金额
					</th>
					<th>
						投资占比 
					</th>
					<th>
						 借款时间
					</th>
					<th>
						标旳总金额 
					</th>
					<th>
						标旳类型 
					</th>
					<th>
						是否天标
					</th>
					<th>
						期数/总期数
					</th>
					<th>
						应收时间
					</th>
					<th>
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
						<td>
							<s:if test="#bean.realName==null">
							未填写
							</s:if>
							<s:else>
								${bean.realName}
							</s:else>
						</td>
						<td align="center">
						<s:if test="#bean.groupName==null">
							未分配
						</s:if>
						<s:else>
							${bean.groupName}
						</s:else>
							
						</td>
						<td>
							${bean.investAmount}
					</td>
					<td>
						<s:number name="#bean.scale" type="#.##"/>%
							
					</td>
					<td>
							${bean.publishTime}
					</td>
					<td>
							${bean.borrowAmount}
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
							<s:if test="#bean.isDayThe==1">
								否
							</s:if>
							<s:elseif test="#bean.isDayThe==2">
							    是
						    </s:elseif>	
						</td>
						<td>
						   ${bean.repayPeriod}
						</td>					
						<td>
							${bean.repayDate}
						</td>
						<td>
						   ${bean.forTotalSum}
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr >
					<td  colspan="2"><strong>合计项</strong></td>
					<td colspan="2" align="right" >待收款总合计:</td>
					<td  colspan="4" >￥<s:if test="#request.repaymentMap.amount==''">0</s:if><s:else>${repaymentMap.amount}</s:else>元
					<td colspan="4" align="right" >当前页待收款合计:</td>
					<td  align="center" >￥${payAmount }</td>			
					</tr>
			</tbody>
		</table>
		<p/><%--<span class="require-field">当前页待收款合计:￥${payAmount}元</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span class="require-field">待收款总合计:￥<s:if test="#request.repaymentMap.amount==''">0</s:if><s:else>${repaymentMap.amount}</s:else>元</span>
--%></div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
