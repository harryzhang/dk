<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 0px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						真实姓名
					</th>
					<th style="width: 200px;" scope="col">
						通过认证数量
					</th>
					<th style="width: 150px;" scope="col">
						标的类型
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
					    筹标期限
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
				<s:set name="acounts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							<s:property value="#s.count+#acounts"/>
						</td>
						<td align="center">
							${username}
						</td>
						<td>
							${realName}
						</td>
						<td>
						   <s:if test="#bean.counts==null">
							<span class="require-field">0</span>个
							</s:if>
							<s:else>
							 ${counts}个
							</s:else>
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
						<a href="../financeDetail.do?id=${id}"  target="_blank" >${borrowTitle}</a>
					</td>
						<td>
							${borrowAmount}
					</td>
						<td>
							${annualRate}%
					</td>
						<td>
							${deadline }
							<s:if test="%{#bean.isDayThe ==1}">个月</s:if>
							<s:else>天</s:else>
					</td>
					<td>
					<s:if test="%{#bean.borrowShow==2}">${deadline }个月</s:if>
							<s:else>${raiseTerm }天 </s:else>
					</td>
					<td>
							 <s:if test="#bean.flag==0">
					          等待资料认证
					        </s:if>
							<s:elseif test="#bean.borrowStatus==1">
							初审中
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==2">
							招标中
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==3">
							满标
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==4">
								还款中
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==5">
							已还完
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==6">
							流标
							</s:elseif>
					</td>
					<td>
					        <s:if test="#bean.borrowStatus==1">
					          <!-- add by houli  等待资料的借款不能进行审核 -->
					          <s:if test="#bean.flag==0">
					             <a href="borrowwdetail.do?id=${id}">查看</a>
					          </s:if>
					          <s:else>
										 <a href="borrowfdetail.do?id=${id}">查看</a>
					          </s:else>
							</s:if>
							<s:elseif test="#bean.borrowStatus==2">
							<a href="borrowTenderInDetail.do?id=${id}">查看</a>
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==3">
							<a href="borrowFullScaleDetail.do?id=${id}">查看</a>
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==4">
							---
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==5">
							---
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==6">
							<a href="borrowFlowMarkDetail.do?id=${id}">查看</a>
							</s:elseif>
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
