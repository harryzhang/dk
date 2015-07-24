<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>

		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
			<tr class=gvHeader>
				<th style="width: 30px;" scope="col">
					序号
				</th>
				<th style="width: 150px;" scope="col">
					标题
				</th>
				
				<th style="width: 100px;" scope="col">
					发布人
				</th>
				<th style="width: 160px;" scope="col">
					发布时间
				</th>
				<th style="width: 100px;" scope="col">
					点击次数
				</th>
				<th style="width: 80px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="6">暂无数据</td>
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
							${bean.question}
						</td>
						
						<td>
							${bean.publisher}
						</td>
						<td>
							${bean.publishTime}
						</td>
						<td>
							${bean.browseCount}
						</td>
						<td>
						<a href="javascript:editSize('updateHelpInit.do?commonId=${bean.questionId }',977,600)" target="main">
							编辑
							</a>
							&nbsp;&nbsp;
							  <a href="javascript:del(${bean.questionId})" > 
							删除
							</a>
							&nbsp;&nbsp;
							  <a href="javascript:preview(${bean.questionId})" > 
							预览
							</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
</table>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
