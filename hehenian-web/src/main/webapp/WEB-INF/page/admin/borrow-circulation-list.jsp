<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 60px;" scope="col">
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
					    状态
					</th>
					    <th style="width: 150px;" scope="col">
					    代发人
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
							流转标借款
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
							${undertakerName}
					</td>
					<td>
					        <s:if test="#bean.borrowStatus==1">
							<a href="borrowCirculationDetail.do?id=${id}">查看</a>
							</s:if>
							<s:else>
							---
							</s:else>
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
