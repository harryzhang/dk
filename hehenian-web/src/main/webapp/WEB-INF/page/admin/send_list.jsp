<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				   
					<th style="width: 100px;" scope="col">
						序号
					</th>
					<th style="width: 100px;" scope="col">
					   信息内容
					</th>
					<th style="width: 100px;" scope="col">
					   发送时间
					</th>	
					
					<th style="width: 100px;" scope="col">
						发送人数
					</th>
					<th style="width: 100px;" scope="col">
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
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">						   
							<td>
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.content}
							</td>
							<td>
								${bean.sendTime}
							</td>
							<td>
								${bean.nums}
							</td>
							
							<td>
								<a href="javascript:updates(${bean.id})" >
								  查看详情
								</a>								
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<table style="border-collapse: collapse; border-color: #cccccc"
		cellspacing="1" cellpadding="3" width="100%" align="center" border="1">
		<tbody>
			
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
