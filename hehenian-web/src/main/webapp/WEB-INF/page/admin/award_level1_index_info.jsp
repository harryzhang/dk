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
					<th scope="col" style="width: 130px;">
						团队长账号（姓名）
					</th>
					<th scope="col">
						经纪人
					</th>
					<th scope="col">
						真实姓名
					</th>
					<th scope="col">
						身份证
					</th>
					<th scope="col">
						投资人总数
					</th>
					<th scope="col">
					奖励提成（投资人）
					</th>
					<th scope="col">
					理财人总数
					</th>
					<th scope="col">
					奖励提成（理财人）
					</th>
					<th scope="col">
					奖励提成
					</th>
					<th scope="col">
					操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="12">暂无数据</td>
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
						${bean.level1userName }<br/>
						（${bean.level1realName}）
						</td>
						<td>
						${level2userName}
						</td>
						<td>
						${level2realName }
						</td>
						<td>
						${card }
						</td>
						<td>
						${count3userId }
						</td>
						<td>
						${level2money3 }
						</td>
						<td>
						${count4userId }
						</td>
						<td>
						${level2money4 }
						</td>
						<td>
						${level1money3+level1money4 }
						</td>
						<td>
						<a href="queryLeve1AwardInit.do?level2userId=${level2userId }&&level1userId=${level1userId}">提成明细</a>
						<a href="queryLeve2AwardInit.do?parentId=${level1userId }">查看</a>
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr>
				<td  colspan="2"><strong>合计项</strong></td>
				<td colspan="2" align="right" >提成总合计:</td>
				<td  colspan="3" >￥<s:if test="%{paramMap.leve1MoneySum==''}">0</s:if><s:else>
				${paramMap.leve1MoneySum }	</s:else></td>
				<td colspan="2" align="right" >当前页的奖励提成总合计:</td>
				<td  align="center" >￥${summoney}</td>		
				</tr>
			</tbody>
		</table>
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
