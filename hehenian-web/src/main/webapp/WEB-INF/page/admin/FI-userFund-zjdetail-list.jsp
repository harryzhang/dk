<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th  scope="col">
						序号
					</th>
					<th  scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						类型
					</th>
					<th  scope="col" style="width: 100px;">
						收入
					</th>
					<th  scope="col" style="width: 100px;">
						支出
					</th>
					<th style="width: 100px;" scope="col">
						可用金额
					</th>
					<th style="width: 80px;" scope="col">
						冻结金额
					</th>
					<th style="width: 80px;" scope="col">
						待收金额
					</th>
					<th style="width: 120px;" scope="col">
						总金额
					</th>
					<th style="width: 100px;" scope="col">
						记录时间
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td align="center" style="width:100px;">
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.username}
							</td>
							<td>
								${bean.fundMode}
							</td>
							
							<td>
								<s:if test="#bean.income==0"></s:if>
								<s:else>${bean.income}</s:else>
							</td>
							<td>
								<s:if test="#bean.spending==0"></s:if>
								<s:else>${bean.spending}</s:else>
							</td>
							<td>
								${bean.usableSum}
							</td>
							<td>
								${bean.freezeSum}
							</td>
							<td>
								${bean.dueinSum}
							</td>
							<td>
							  ${bean.totalSum}
							</td>
							<td>
							${bean.recordTime }
							 <!--   <s:date name="#bean.recordTime " format="yyyy-MM-dd HH:mm:ss"/> -->
							</td>
							
						</tr>
					</s:iterator>
				</s:else>
				 <tr class="gvItem"><td colspan="10" align="left"><font size="2">共有${totalNum }条记录</font></td></tr>
			</tbody>
		</table>
	
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
