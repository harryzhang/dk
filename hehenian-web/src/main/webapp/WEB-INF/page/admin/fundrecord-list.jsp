<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				  
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 100px;" scope="col">
						用户
					</th>
					<th style="width: 100px;" scope="col">
						操作金额
					</th>
					<th style="width: 120px;" scope="col">
						类型名称
					</th>
					<th style="width: 100px;" scope="col">
						交易方
					</th>
					<th style="width: 100px;" scope="col">
						记录时间
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
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
								${bean.handleSum}
							</td>
							<td>
								${bean.fundMode }
							</td>
							<td>
								<s:if test="%{#bean.c_username==null}">
									合和年在线
								</s:if>
								<s:else>
									${bean.c_username}
								</s:else>
							</td>
							<td>
								${bean.recordTime}
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
		<p />
		<span class="require-field">操作金额合计:￥<s:if test="#request.hasMap.sumHand==''">0</s:if><s:else>${hasMap.sumHand}</s:else>元</span>
		
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
