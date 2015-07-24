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
					<th scope="col" style="width: 200px;">
						经纪人姓名(姓名)
					</th>
					<th scope="col">
						${level==3?'投资人':'理财人' }
					</th>
					<th scope="col">
						真实姓名
					</th>
					<th scope="col">
						信用积分
					</th>
					<th scope="col">
						会员积分
					</th>
					<th scope="col">
						身份证
					</th>
					<th scope="col">
						投资奖励总提成
					</th>
					<th style="width: 70px;" scope="col">
						加入时间
					</th>
					<th style="width: 70px;" scope="col">
						投资人状态
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td>
						${st.count }
						</td>
						<td align="center">
						${bean.level2userName }<br/>
							(${bean.level2realName})
						</td>
						<td>
							${userName}
						</td>
						<td>
						${realName }
						</td>
						<td>
							${creditrating }
						</td>
						<td>
							${rating}
						</td>
						<td>
						${idNo }
						</td>
						<td>
						${level2moneys }
						</td>
						<td>
						<s:date name="#bean.addDate" format="yyyy-MM-dd"/>
						</td>
						<td>
						${dateStatus}
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr>
				<td  colspan="2"><strong>合计项</strong></td>
				<td colspan="2" align="right" >${level==3?'投资人':'理财人' }提成奖励总计:</td>
				<td  colspan="1" >￥<s:if test="%{paramMap.level2moneySum==''}">0</s:if><s:else>
				${paramMap.level2moneySum }	</s:else></td>
				<td colspan="2" align="right" >当前页的提成奖励总合计:</td>
				<td  align="center" >￥${countMoney}</td>		
				</tr>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
