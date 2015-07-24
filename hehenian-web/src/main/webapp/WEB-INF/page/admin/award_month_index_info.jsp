<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col" style="width:50px;">
						序号
					</th>
					<th scope="col" style="width: 130px;">
						经济人账号
					</th>
					<th scope="col">
						真实姓名
					</th>
					<th scope="col">
						身份证
					</th>
					<th scope="col">
					奖励提成
					</th>
						<th scope="col">
					月份
					</th>
					<th scope="col">
					是否已月结
					</th>
					<th scope="col">
					操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td align="center">
					   <s:property value="#st.count+#counts"/>
						</td>
						<td align="center">
					   ${userName}
						</td>
						<td>
						${realName}
						</td>
						<td>
						${card }
						</td>
						<td>
						${moneys }
						</td>
						<td>
						${mothstr }
						</td>
						<td>
						${applystatus==1?'否':'是' }
						</td>
						<td>
						<s:if test="#bean.applystatus==1"><a style="cursor: pointer;" onclick="updatestatus(${id})">结算</a></s:if>
						<s:else>--</s:else>
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr>
				<td  colspan="1"><strong>合计项</strong></td>
				<td colspan="1" align="right" >提成奖励总计:</td>
				<td  colspan="1" >￥<s:if test="%{paramMap.moneySum==''}">0</s:if><s:else>
				${paramMap.moneySum }	</s:else></td>
				<td colspan="1" align="right" >当前页的提成奖励合计:</td>
				<td  align="center" >￥${countMoney}</td>		
				</tr>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
