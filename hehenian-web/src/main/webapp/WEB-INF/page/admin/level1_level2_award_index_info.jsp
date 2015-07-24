<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
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
						投资奖励总提成
					</th>
					<th scope="col">
						投资奖励未结算提成
					</th>
					<th scope="col">
					加入时间
					</th>
					<th scope="col">
					经纪人状态
					</th>
					<th scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
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
						${realName }
						</td>
						<td>
						${idNo }
						</td>
						<td>
						￥${totalMoney==null?0:totalMoney }
						</td>
						<td>
						￥${notuse==null?"--":notuse }
						</td>
						<td>
						<s:date name="#bean.createTime" format="yyyy-MM-dd"/>
						</td>
						<td>
						${enable==1?'启用':'禁用' }
						</td>
						
						<td>
						<a href="queryAwardDetailInit.do?id=${bean.id }">结算</a>
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>

	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
