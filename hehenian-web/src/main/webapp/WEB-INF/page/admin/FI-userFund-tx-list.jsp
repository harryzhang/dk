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
						用户名
					</th>
					<th style="width: 100px;" scope="col">
						真实姓名
					</th>
					
					<th style="width: 100px;" scope="col">
						提现账号
					</th>
					<th style="width: 80px;" scope="col">
						提现银行
					</th>
					<th style="width: 100px;" scope="col">
						支行
					</th>
					<th style="width: 80px;" scope="col">
						提现总额
					</th>
					
					<th style="width: 160px;" scope="col">
						提现时间
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
							<td align="center" >
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.username}
							</td>
							<td>
								${bean.realName}
							</td>
							<td>
								${bean.acount}
							</td>
							<td>
								${bean.bankName}
							</td>
							<td>
								${bean.branchBankName}
							</td>
							<td>
								${bean.sum}
							</td>
							
							<td>
							  <s:date name="#bean.applyTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
